/*
 * Copyright 2016 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package flow;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

import static flow.Preconditions.checkArgument;
import static flow.Preconditions.checkNotNull;

/**
 * Pay no attention to this class. It's only public because it has to be.
 */
public final class InternalLifecycleIntegration extends Fragment {
  static final String INTENT_KEY = "Flow_history";
  static final String TAG = "flow-lifecycle-integration";
  static final String PERSISTENCE_KEY = "Flow_state";

  static InternalLifecycleIntegration find(Activity activity) {
    return (InternalLifecycleIntegration) activity.getFragmentManager().findFragmentByTag(TAG);
  }

  static void install(final Application app, final Activity activity,
      @Nullable final KeyParceler parceler, final History defaultHistory,
      final Dispatcher dispatcher, final KeyManager keyManager) {
    app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
      @Override public void onActivityCreated(Activity a, Bundle savedInstanceState) {
        if (a == activity) {
          InternalLifecycleIntegration fragment = find(activity);
          boolean newFragment = fragment == null;
          if (newFragment) {
            fragment = new InternalLifecycleIntegration();
          }
          if (fragment.keyManager == null) {
            fragment.defaultHistory = defaultHistory;
            fragment.parceler = parceler;
            fragment.keyManager = keyManager;
          }
          // We always replace the dispatcher because it frequently references the Activity.
          fragment.dispatcher = dispatcher;
            fragment.intent = a.getIntent();
          if (newFragment) {
            activity.getFragmentManager() //
                .beginTransaction() //
                .add(fragment, TAG) //
                .commit();
          }
          app.unregisterActivityLifecycleCallbacks(this);
        }
      }

      @Override public void onActivityStarted(Activity activity) {
      }

      @Override public void onActivityResumed(Activity activity) {
      }

      @Override public void onActivityPaused(Activity activity) {
      }

      @Override public void onActivityStopped(Activity activity) {
      }

      @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
      }

      @Override public void onActivityDestroyed(Activity a) {
      }
    });
  }

  Flow flow;
  KeyManager keyManager;
  @Nullable KeyParceler parceler;
  History defaultHistory;
  Dispatcher dispatcher;
  Intent intent;

  private boolean dispatcherSet;
  private boolean isBootstrapNeeded;

  public InternalLifecycleIntegration() {
    super();
    setRetainInstance(true);
    isBootstrapNeeded = true;
  }

  static void addHistoryToIntent(Intent intent, History history, KeyParceler parceler, KeyManager keyManager) {
    Bundle bundle = new Bundle();
    ArrayList<Parcelable> parcelables = new ArrayList<>(history.size());
    final Iterator<Object> keys = history.reverseIterator();
    while (keys.hasNext()) {
      Object key = keys.next();
        //parcelables.add(State.empty(key).toBundle(parceler)); // original code
        State keyState;
        if(keyManager.hasState(key)) {
            //Log.d(TAG, "Key [" + key + "] has state");
            keyState = keyManager.getState(key);
        } else {
            //Log.d(TAG, "Key [" + key + "] has no state");
            keyState = State.empty(key);
        }
        parcelables.add(keyState.toBundle(parceler));
    }
      bundle.putParcelableArrayList(PERSISTENCE_KEY, parcelables);
    intent.putExtra(INTENT_KEY, bundle);
    save(bundle, parceler, history, keyManager);
  }

  void onNewIntent(Intent intent) {
    if (intent.hasExtra(INTENT_KEY)) {
      checkNotNull(parceler,
          "Intent has a Flow history extra, but Flow was not installed with a KeyParceler");
      History.Builder builder = History.emptyBuilder();
      load((Bundle) intent.getParcelableExtra(INTENT_KEY), parceler, builder, keyManager);
      flow.setHistory(builder.build(), Direction.REPLACE);
    }
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (flow == null) {
      History savedHistory = null;
      if (savedInstanceState != null && savedInstanceState.containsKey(INTENT_KEY)) {
        checkNotNull(parceler, "no KeyParceler installed");
        History.Builder builder = History.emptyBuilder();
        Bundle bundle = savedInstanceState.getParcelable(INTENT_KEY);
        load(bundle, parceler, builder, keyManager);
        savedHistory = builder.build();
      }
      History history = selectHistory(intent, savedHistory, defaultHistory, parceler, keyManager);
      flow = new Flow(keyManager, history);
    }
    flow.setDispatcher(dispatcher, true);
    isBootstrapNeeded = false;
    dispatcherSet = true;
  }

  @Override public void onResume() {
    super.onResume();
    if (!dispatcherSet) {
      flow.setDispatcher(dispatcher, isBootstrapNeeded);
      dispatcherSet = true;
    }
  }

  @Override public void onPause() {
    flow.removeDispatcher(dispatcher);
    isBootstrapNeeded = false;
    dispatcherSet = false;
    super.onPause();
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    checkArgument(outState != null, "outState may not be null");
    if (parceler == null) {
      return;
    }

    Bundle bundle = new Bundle();
    save(bundle, parceler, flow.getHistory(), keyManager);
    if (!bundle.isEmpty()) {
      outState.putParcelable(INTENT_KEY, bundle);
    }
  }

  private static History selectHistory(Intent intent, History saved, History defaultHistory,
      @Nullable KeyParceler parceler, KeyManager keyManager) {
    if (saved != null) {
        //Log.i(TAG, "Saved selected [" + saved + "]");
      return saved;
    }
    if (intent != null && intent.hasExtra(INTENT_KEY)) {
      checkNotNull(parceler,
          "Intent has a Flow history extra, but Flow was not installed with a KeyParceler");
      History.Builder history = History.emptyBuilder();
      load(intent.<Bundle>getParcelableExtra(INTENT_KEY), parceler, history, keyManager);
        //Log.i(TAG, "Intent Key selected [" + history + "]");
      return history.build();
    } else {
        if(intent == null) {
            //Log.i(TAG, "Starting intent is null");
        } else {
            //Log.i(TAG, "The intent [" + intent + "] has no extra called [" + INTENT_KEY + "]");
        }
    }
    //Log.i(TAG, "Default history selected [" + defaultHistory + "]");
    return defaultHistory;
  }

  private static void save(Bundle bundle, KeyParceler parceler, History history, KeyManager keyManager) {
    ArrayList<Parcelable> parcelables = new ArrayList<>(history.size());
    final Iterator<Object> keys = history.reverseIterator();
    while (keys.hasNext()) {
      Object key = keys.next();
      if (!key.getClass().isAnnotationPresent(NotPersistent.class)) {
        parcelables.add(keyManager.getState(key).toBundle(parceler));
      }
    }
    bundle.putParcelableArrayList(PERSISTENCE_KEY, parcelables);
  }

  private static void load(Bundle bundle, KeyParceler parceler, History.Builder builder,
      KeyManager keyManager) {
    if (!bundle.containsKey(PERSISTENCE_KEY)) return;
    ArrayList<Parcelable> stateBundles = bundle.getParcelableArrayList(PERSISTENCE_KEY);
    //noinspection ConstantConditions
    for (Parcelable stateBundle : stateBundles) {
      State state = State.fromBundle((Bundle) stateBundle, parceler);
      builder.push(state.getKey());
      if (!keyManager.hasState(state.getKey())) {
        keyManager.addState(state);
      }
    }
  }
}
