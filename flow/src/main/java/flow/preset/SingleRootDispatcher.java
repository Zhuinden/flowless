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
import android.view.View;
import android.view.ViewGroup;

import flow.Direction;
import flow.Flow;
import flow.ForceBundler;
import flow.Traversal;
import flow.TraversalCallback;
import flow.ViewUtils;

/**
 * Created by Zhuinden on 2016.06.27..
 */
public class SingleRootDispatcher
        extends BaseDispatcher
        implements Application.ActivityLifecycleCallbacks {
    private boolean hasActiveView() {
        return rootHolder != null && rootHolder.root != null && rootHolder.root.getChildCount() > 0;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if(activity == this.activity) {
            if(hasActiveView()) {
                flowLifecycleProvider.onActivityCreated(rootHolder.root.getChildAt(0), savedInstanceState);
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if(activity == this.activity) {
            if(hasActiveView()) {
                flowLifecycleProvider.onActivityStarted(rootHolder.root.getChildAt(0));
            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if(activity == this.activity) {
            if(hasActiveView()) {
                flowLifecycleProvider.onActivityResumed(rootHolder.root.getChildAt(0));
            }
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if(activity == this.activity) {
            if(hasActiveView()) {
                flowLifecycleProvider.onActivityPaused(rootHolder.root.getChildAt(0));
            }
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if(activity == this.activity) {
            if(hasActiveView()) {
                flowLifecycleProvider.onActivityStopped(rootHolder.root.getChildAt(0));
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        if(activity == this.activity) {
            if(hasActiveView()) {
                flowLifecycleProvider.onActivitySaveInstanceState(rootHolder.root.getChildAt(0), outState);

                // Important: Single Root Dispatcher can handle its child's state directly, but Container Root Dispatcher cannot.
                ForceBundler.saveToBundle(activity, rootHolder.root.getChildAt(0));
            }
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if(activity == this.activity) {
            if(hasActiveView()) {
                flowLifecycleProvider.onActivityDestroyed(rootHolder.root.getChildAt(0));
            }
            if(rootHolder != null && rootHolder.root != null) {
                rootHolder.root = null;
            }
            application.unregisterActivityLifecycleCallbacks(this);
        }
    }

    @Override
    @CheckResult
    public boolean onBackPressed() {
        boolean childHandledEvent = false;
        if(hasActiveView()) {
            childHandledEvent = flowLifecycleProvider.onBackPressed(rootHolder.root.getChildAt(0));
            if(childHandledEvent) {
                return true;
            }
        }
        Flow flow = Flow.get(baseContext);
        return flow.goBack();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(hasActiveView()) {
            flowLifecycleProvider.onActivityResult(rootHolder.root.getChildAt(0), requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(hasActiveView()) {
            flowLifecycleProvider.onRequestPermissionsResult(rootHolder.root.getChildAt(0), requestCode, permissions, grantResults);
        }
    }

    public SingleRootDispatcher(Context baseContext, Activity activity) {
        super(baseContext, activity);
        application.registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void dispatch(@NonNull Traversal traversal, final @NonNull TraversalCallback callback) {
        final ViewGroup root = rootHolder.root;
        if(DispatcherUtils.isPreviousKeySameAsNewKey(traversal.origin, traversal.destination)) { //short circuit on same key
            DispatcherUtils.finishTraversal(callback);
            return;
        }
        final LayoutPath newKey = DispatcherUtils.getNewKey(traversal);
        final LayoutPath previousKey = DispatcherUtils.getPreviousKey(traversal);

        final Direction direction = traversal.direction;

        final View previousView = root.getChildAt(0);
        DispatcherUtils.persistViewToState(traversal, previousView);

        final View newView = DispatcherUtils.createViewFromKey(traversal, newKey, root, baseContext);
        DispatcherUtils.restoreViewFromState(traversal, newView);

        final LayoutPath animatedKey = DispatcherUtils.selectAnimatedKey(direction, previousKey, newKey);
        DispatcherUtils.addViewToGroupForKey(direction, newView, root, animatedKey);
        ViewUtils.waitForMeasure(newView, new ViewUtils.OnMeasuredCallback() {
            @Override
            public void onMeasured(View view, int width, int height) {
                Animator animator = DispatcherUtils.createAnimatorForViews(animatedKey, previousView, newView , direction);
                if(animator != null) {
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            finishTransition(previousView, root, callback);
                        }
                    });
                    animator.start();
                } else {
                    finishTransition(previousView, root, callback);
                }
            }
        });
    }

    private void finishTransition(View previousView, ViewGroup root, @NonNull TraversalCallback callback) {
        DispatcherUtils.removeViewFromGroup(previousView, root);
        DispatcherUtils.finishTraversal(callback);
    }
}
