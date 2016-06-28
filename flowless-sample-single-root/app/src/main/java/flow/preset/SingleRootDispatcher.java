package flow.preset;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import flow.Bundleable;
import flow.Direction;
import flow.Dispatcher;
import flow.Flow;
import flow.ForceBundler;
import flow.History;
import flow.State;
import flow.Traversal;
import flow.TraversalCallback;
import flow.ViewUtils;

/**
 * Created by Zhuinden on 2016.06.27..
 */
public class SingleRootDispatcher
        implements Dispatcher, Application.ActivityLifecycleCallbacks {
    private static final String TAG = "FlowDispatcher";

    public interface BackPressListener {
        boolean onBackPressed();
    }

    public interface CreateDestroyListener {
        void onCreate(Bundle bundle);

        void onDestroy();
    }

    public interface StartStopListener {
        void onStart();

        void onStop();
    }

    public interface ResumePauseListener {
        void onResume();

        void onPause();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if(activity == this.activity) {
            if(rootHolder != null && rootHolder.root != null && rootHolder.root.getChildCount() > 0 && rootHolder.root.getChildAt(0) instanceof CreateDestroyListener) {
                ((CreateDestroyListener) rootHolder.root.getChildAt(0)).onCreate(savedInstanceState);
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if(activity == this.activity) {
            if(rootHolder != null && rootHolder.root != null && rootHolder.root.getChildCount() > 0 && rootHolder.root.getChildAt(0) instanceof CreateDestroyListener) {
                ((StartStopListener) rootHolder.root.getChildAt(0)).onStart();
            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if(activity == this.activity) {
            if(rootHolder != null && rootHolder.root != null && rootHolder.root.getChildCount() > 0 && rootHolder.root.getChildAt(0) instanceof CreateDestroyListener) {
                ((ResumePauseListener) rootHolder.root.getChildAt(0)).onResume();
            }
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if(activity == this.activity) {
            if(rootHolder != null && rootHolder.root != null && rootHolder.root.getChildCount() > 0 && rootHolder.root.getChildAt(0) instanceof CreateDestroyListener) {
                ((ResumePauseListener) rootHolder.root.getChildAt(0)).onPause();
            }
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if(activity == this.activity) {
            if(rootHolder != null && rootHolder.root != null && rootHolder.root.getChildCount() > 0 && rootHolder.root.getChildAt(0) instanceof CreateDestroyListener) {
                ((StartStopListener) rootHolder.root.getChildAt(0)).onStop();
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        if(activity == this.activity) {
            if(rootHolder != null && rootHolder.root != null && rootHolder.root.getChildCount() > 0 && rootHolder.root.getChildAt(0) instanceof ViewStatePersistenceListener) {
                ((ViewStatePersistenceListener) rootHolder.root.getChildAt(0)).preSaveViewState();
            }
            if(rootHolder != null && rootHolder.root != null && rootHolder.root.getChildCount() > 0) {
                ForceBundler.saveToBundle(activity, rootHolder.root.getChildAt(0));
            }
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if(activity == this.activity) {
            if(rootHolder != null && rootHolder.root != null && rootHolder.root.getChildCount() > 0 && rootHolder.root.getChildAt(0) instanceof CreateDestroyListener) {
                ((CreateDestroyListener) rootHolder.root.getChildAt(0)).onDestroy();
            }
            if(rootHolder != null && rootHolder.root != null && rootHolder.root.getChildCount() > 0 && rootHolder.root.getChildAt(0) instanceof ViewLifecycleListener) {
                ((ViewLifecycleListener) rootHolder.root.getChildAt(0)).onViewDestroyed(false);
            }
            if(rootHolder != null && rootHolder.root != null) {
                rootHolder.root = null;
            }
            application.unregisterActivityLifecycleCallbacks(this);
        }
    }

    @CheckResult
    public boolean onBackPressed() {
        if(rootHolder != null && rootHolder.root != null && rootHolder.root.getChildCount() > 0 && rootHolder.root.getChildAt(0) instanceof BackPressListener) {
            return ((BackPressListener) rootHolder.root.getChildAt(0)).onBackPressed();
        }
        Flow flow = Flow.get(baseContext);
        return flow.goBack();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(rootHolder != null && rootHolder.root != null && rootHolder.root.getChildCount() > 0 && rootHolder.root.getChildAt(0) instanceof ActivityResultListener) {
            ((ActivityResultListener)rootHolder.root.getChildAt(0)).onActivityResult(requestCode, resultCode, data);
        }
    };

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(rootHolder != null && rootHolder.root != null && rootHolder.root.getChildCount() > 0 && rootHolder.root.getChildAt(0) instanceof PermissionRequestListener) {
            ((PermissionRequestListener)rootHolder.root.getChildAt(0)).onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    };

    public static class RootHolder {
        ViewGroup root;

        public RootHolder() {
        }

        public void setRoot(ViewGroup root) {
            this.root = root;
        }
    }

    public interface ViewStatePersistenceListener {
        void preSaveViewState();
    }

    public interface ViewLifecycleListener {
        void onViewRestored();

        void onViewDestroyed(boolean removedByFlow);
    }

    public interface ActivityResultListener {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    public interface PermissionRequestListener {
        void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
    }

    Context baseContext;
    RootHolder rootHolder;

    Activity activity;
    Application application;

    Object activeKey;

    public Object getActiveKey() {
        return activeKey;
    }

    public SingleRootDispatcher(Context baseContext, Activity activity) {
        this.activity = activity;
        this.rootHolder = new RootHolder();

        application = (Application)baseContext.getApplicationContext();
        application.registerActivityLifecycleCallbacks(this);
    }

    public void setBaseContext(Context baseContext) {
        this.baseContext = baseContext;
    }

    public RootHolder getRootHolder() {
        return rootHolder;
    }

    @Override
    public void dispatch(@NonNull Traversal traversal, final @NonNull TraversalCallback callback) {
        ViewGroup root = rootHolder.root;

        History destination = traversal.destination;
        History origin = traversal.origin;
        if(origin != null && origin.top() != null && origin.top().equals(destination.top())) { //short circuit on same key
            callbackCompleted(callback);
            return;
        }
        activeKey = destination.top();
        State outgoingState = null;
        LayoutPath _previousKey = null;
        if(origin != null && origin.top() != null) {
            outgoingState = traversal.getState(origin.top());
            _previousKey = outgoingState.getKey();
        }
        final LayoutPath previousKey = _previousKey;
        State incomingState = traversal.getState(destination.top());
        final Direction direction = traversal.direction;

        final View previousView = root.getChildAt(0);
        if(previousView != null && previousView instanceof ViewStatePersistenceListener) {
            ((ViewStatePersistenceListener) previousView).preSaveViewState();
        }
        persistViewToState(traversal, previousView);

        final LayoutPath newKey = incomingState.getKey();
        Context internalContext = traversal.createContext(newKey, baseContext);
        LayoutInflater layoutInflater = LayoutInflater.from(internalContext);
        final View newView = layoutInflater.inflate(newKey.layout(), root, false);
        restoreViewFromState(traversal, newView);
        if(newView instanceof ViewLifecycleListener) {
            ((ViewLifecycleListener) newView).onViewRestored();
        }

        configure(previousKey, newKey);

        if(direction == Direction.REPLACE) {
            if(previousView != null) {
                root.removeView(previousView);
            }
            root.addView(newView);
            if(previousView instanceof ViewLifecycleListener) {
                ((ViewLifecycleListener) previousView).onViewDestroyed(true);
            }
            callbackCompleted(callback);
        } else {
            final LayoutPath animatedKey = direction == Direction.BACKWARD ? (previousKey != null ? previousKey : newKey) : newKey;
            if(animatedKey.animation() != null && !animatedKey.animation().showChildOnTopWhenAdded(direction)) {
                root.addView(newView, 0);
            } else {
                root.addView(newView);
            }
            ViewUtils.waitForMeasure(newView, new ViewUtils.OnMeasuredCallback() {
                @Override
                public void onMeasured(View view, int width, int height) {
                    Animator animator = null;
                    if(animatedKey.animation() != null) {
                        animator = animatedKey.animation().createAnimation(previousView, newView, direction);
                    }
                    if(animator != null) {
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                onTransitionFinished(animation, previousView, newView, callback);
                            }
                        });
                        animator.start();
                    } else {
                        onTransitionFinished(null, previousView, newView, callback);
                    }
                }
            });
        }
    }

    protected void configure(LayoutPath previousKey, LayoutPath newKey) {
    }

    private void onTransitionFinished(Animator animation, View previousView, View newView, TraversalCallback callback) {
        if(previousView != null) {
            if(rootHolder.root != null) {
                rootHolder.root.removeView(previousView);
            }
        }
        if(previousView instanceof ViewLifecycleListener) {
            ((ViewLifecycleListener) previousView).onViewDestroyed(true);
        }
        callbackCompleted(callback);
    }

    protected void callbackCompleted(TraversalCallback callback) {
        callback.onTraversalCompleted();
    }

    protected final void persistViewToState(Traversal traversal, View view) {
        if(view != null) {
            if(Flow.getKey(view.getContext()) != null) {
                State outgoingState = traversal.getState(Flow.getKey(view.getContext()));
                if(outgoingState != null) {
                    outgoingState.save(view);
                    if(view instanceof Bundleable) {
                        outgoingState.setBundle(((Bundleable) view).toBundle());
                    }
                }
            }
        }
    }

    protected final void restoreViewFromState(Traversal traversal, View view) {
        if(view != null) {
            if(Flow.getKey(view.getContext()) != null) {
                State incomingState = traversal.getState(Flow.getKey(view.getContext()));
                if(incomingState != null) {
                    incomingState.restore(view);
                    if(view instanceof Bundleable) {
                        ((Bundleable) view).fromBundle(incomingState.getBundle());
                    }
                }
            }
        }
    }
}
