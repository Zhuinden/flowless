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

package flowless;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Iterator;

import flowless.preset.FlowLifecycles;

import static flowless.Preconditions.checkArgument;
import static flowless.Preconditions.checkNotNull;

/**
 * Pay no attention to this class. It's only public because it has to be.
 */
public /*final*/ class InternalLifecycleIntegration
        extends Fragment {
    static final String INTENT_KEY = "Flow_history";
    static final String TAG = "flow-lifecycle-integration";
    static final String PERSISTENCE_KEY = "Flow_state";

    static InternalLifecycleIntegration find(Activity activity) {
        return (InternalLifecycleIntegration) activity.getFragmentManager().findFragmentByTag(TAG);
    }

    static void install(final Application app, final Activity activity, @Nullable final KeyParceler parceler, final History defaultHistory, final Dispatcher dispatcher, final ServiceProvider serviceProvider, final KeyManager keyManager) {
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity a, Bundle savedInstanceState) {
                if(a == activity) {
                    InternalLifecycleIntegration fragment = find(activity);
                    boolean newFragment = fragment == null;
                    if(newFragment) {
                        fragment = new InternalLifecycleIntegration();
                    }
                    if(fragment.keyManager == null) {
                        fragment.defaultHistory = defaultHistory;
                        fragment.parceler = parceler;
                        fragment.keyManager = keyManager;
                        fragment.serviceProvider = serviceProvider;
                    }
                    // We always replace the dispatcher because it frequently references the Activity.
                    fragment.dispatcher = dispatcher;
                    fragment.intent = a.getIntent();
                    if(newFragment) {
                        activity.getFragmentManager() //
                                .beginTransaction() //
                                .add(fragment, TAG) //
                                .commit();
                    }
                    app.unregisterActivityLifecycleCallbacks(this);
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity a) {
            }
        });
    }

    Flow flow;
    KeyManager keyManager;
    ServiceProvider serviceProvider;
    @Nullable
    KeyParceler parceler;
    History defaultHistory;
    Dispatcher dispatcher;
    Intent intent;

    public InternalLifecycleIntegration() {
        super();
        setRetainInstance(true);
    }

    static void addHistoryToIntent(Intent intent, History history, KeyParceler parceler, KeyManager keyManager) {
        Bundle bundle = new Bundle();
        Bundle innerBundle = new Bundle();
        ArrayList<Parcelable> parcelables = new ArrayList<>(history.size());
        final Iterator<Object> keys = history.reverseIterator();
        while(keys.hasNext()) {
            Object key = keys.next();
            State keyState;
            if(keyManager != null && keyManager.hasState(key)) {
                keyState = keyManager.getState(key);
            } else {
                keyState = State.empty(key);
            }
            parcelables.add(keyState.toBundle(parceler));
        }
        innerBundle.putParcelableArrayList(KeyManager.HISTORY_KEYS, parcelables);

        if(keyManager != null) {
            innerBundle.putParcelableArrayList(KeyManager.GLOBAL_KEYS,
                    collectStatesFromKeys(keyManager, parceler, keyManager.globalKeys.iterator(), keyManager.globalKeys.size()));
        }
        bundle.putBundle(PERSISTENCE_KEY, innerBundle);
        intent.putExtra(INTENT_KEY, bundle);
    }

    void onNewIntent(Intent intent) {
        if(intent.hasExtra(INTENT_KEY)) {
            checkNotNull(parceler, "Intent has a Flow history extra, but Flow was not installed with a KeyParceler");
            History.Builder builder = History.emptyBuilder();
            load(intent.<Bundle>getParcelableExtra(INTENT_KEY), parceler, builder, keyManager);
            flow.setHistory(builder.build(), Direction.REPLACE);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(flow == null) {
            History savedHistory = null;
            if(savedInstanceState != null && savedInstanceState.containsKey(INTENT_KEY)) {
                checkNotNull(parceler, "no KeyParceler installed");
                History.Builder builder = History.emptyBuilder();
                Bundle bundle = savedInstanceState.getBundle(INTENT_KEY);
                load(bundle, parceler, builder, keyManager);
                savedHistory = builder.build();
            }
            History history = selectHistory(intent, savedHistory, defaultHistory, parceler, keyManager);
            flow = new Flow(keyManager, serviceProvider, history);
        }
        Flow.get(getActivity().getBaseContext()); // force existence of Flow in InternalContextWrapper
        flow.setDispatcher(dispatcher, true);
        if(dispatcher instanceof FlowLifecycles.CreateDestroyListener) {
            ((FlowLifecycles.CreateDestroyListener) dispatcher).onCreate(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(dispatcher instanceof FlowLifecycles.StartStopListener) {
            ((FlowLifecycles.StartStopListener) dispatcher).onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!flow.hasDispatcher()) {
            flow.setDispatcher(dispatcher, false);
        }
        if(dispatcher instanceof FlowLifecycles.ResumePauseListener) {
            ((FlowLifecycles.ResumePauseListener) dispatcher).onResume();
        }
    }

    @Override
    public void onPause() {
        if(dispatcher instanceof FlowLifecycles.ResumePauseListener) {
            ((FlowLifecycles.ResumePauseListener) dispatcher).onPause();
        }
        if(flow.hasDispatcher()) {
            flow.removeDispatcher(dispatcher);
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if(dispatcher instanceof FlowLifecycles.StartStopListener) {
            ((FlowLifecycles.StartStopListener) dispatcher).onStop();
        }
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        if(dispatcher instanceof FlowLifecycles.CreateDestroyListener) {
            ((FlowLifecycles.CreateDestroyListener) dispatcher).onDestroy();
        }
        if(flow != null) {
            flow.executePendingTraversal();
        }
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(dispatcher instanceof FlowLifecycles.ViewStatePersistenceListener) {
            ((FlowLifecycles.ViewStatePersistenceListener) dispatcher).onSaveInstanceState(outState);
        }
        checkArgument(outState != null, "outState may not be null");
        if(parceler == null) {
            return;
        }

        Bundle bundle = new Bundle();
        save(bundle, parceler, flow.getHistory(), keyManager);
        if(!bundle.isEmpty()) {
            outState.putParcelable(INTENT_KEY, bundle);
        }
    }

    private static History selectHistory(Intent intent, History saved, History defaultHistory, @Nullable KeyParceler parceler, KeyManager keyManager) {
        if(saved != null) {
            return saved;
        }
        if(intent != null && intent.hasExtra(INTENT_KEY)) {
            checkNotNull(parceler, "Intent has a Flow history extra, but Flow was not installed with a KeyParceler");
            History.Builder history = History.emptyBuilder();
            load(intent.<Bundle>getParcelableExtra(INTENT_KEY), parceler, history, keyManager);
            return history.build();
        }
        return defaultHistory;
    }

    private static void save(Bundle bundle, KeyParceler parceler, History history, KeyManager keyManager) {
        ArrayList<Parcelable> historyStates = collectStatesFromKeys(keyManager, parceler, history.reverseIterator(), history.size());
        ArrayList<Parcelable> globalStates = collectStatesFromKeys(keyManager,
                parceler,
                keyManager.globalKeys.iterator(),
                keyManager.globalKeys.size());
        Bundle innerBundle = new Bundle();
        innerBundle.putParcelableArrayList(KeyManager.GLOBAL_KEYS, globalStates);
        innerBundle.putParcelableArrayList(KeyManager.HISTORY_KEYS, historyStates);
        bundle.putBundle(PERSISTENCE_KEY, innerBundle);
    }

    private static ArrayList<Parcelable> collectStatesFromKeys(KeyManager keyManager, KeyParceler parceler, Iterator<Object> keys, int size) {
        ArrayList<Parcelable> parcelables = new ArrayList<>(size);
        while(keys.hasNext()) {
            Object key = keys.next();
            if(!key.getClass().isAnnotationPresent(NotPersistent.class)) {
                parcelables.add(keyManager.getState(key).toBundle(parceler));
            }
        }
        return parcelables;
    }

    private static void loadStatesIntoManager(ArrayList<Parcelable> stateBundles, KeyParceler parceler, KeyManager keyManager, History.Builder builder, boolean addToHistory) {
        if(stateBundles != null) {
            for(Parcelable stateBundle : stateBundles) {
                State state = State.fromBundle((Bundle) stateBundle, parceler);
                if(addToHistory) {
                    builder.push(state.getKey());
                }
                if(!keyManager.hasState(state.getKey())) {
                    keyManager.addState(state);
                }
            }
        }

    }

    private static void load(Bundle bundle, KeyParceler parceler, History.Builder builder, KeyManager keyManager) {
        if(!bundle.containsKey(PERSISTENCE_KEY)) {
            return;
        }
        Bundle innerBundle = bundle.getBundle(PERSISTENCE_KEY);
        if(innerBundle != null) {
            //noinspection ConstantConditions
            loadStatesIntoManager(innerBundle.getParcelableArrayList(KeyManager.HISTORY_KEYS), parceler, keyManager, builder, true);
            loadStatesIntoManager(innerBundle.getParcelableArrayList(KeyManager.GLOBAL_KEYS), parceler, keyManager, builder, false);
        }
    }
}
