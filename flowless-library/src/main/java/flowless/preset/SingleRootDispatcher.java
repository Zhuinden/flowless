package flowless.preset;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import flowless.Direction;
import flowless.Flow;
import flowless.ForceBundler;
import flowless.Traversal;
import flowless.TraversalCallback;
import flowless.ViewUtils;

/**
 * Created by Zhuinden on 2016.06.27..
 */
public class SingleRootDispatcher
        extends BaseDispatcher
        implements FlowContainerLifecycleListener {
    private boolean hasActiveView() {
        return rootHolder != null && rootHolder.root != null && rootHolder.root.getChildCount() > 0;
    }

    @Override
    public void onCreate(Bundle bundle) {
        if(hasActiveView()) {
            flowLifecycleProvider.onCreate(rootHolder.root.getChildAt(0), bundle);
        }
    }

    @Override
    public void onDestroy() {
        if(hasActiveView()) {
            flowLifecycleProvider.onDestroy(rootHolder.root.getChildAt(0));
        }
        if(rootHolder != null && rootHolder.root != null) {
            rootHolder.root = null;
        }
    }

    @Override
    public void onResume() {
        if(hasActiveView()) {
            flowLifecycleProvider.onResume(rootHolder.root.getChildAt(0));
        }
    }

    @Override
    public void onPause() {
        if(hasActiveView()) {
            flowLifecycleProvider.onPause(rootHolder.root.getChildAt(0));
        }
    }

    @Override
    public void onStart() {
        if(hasActiveView()) {
            flowLifecycleProvider.onStart(rootHolder.root.getChildAt(0));
        }
    }

    @Override
    public void onStop() {
        if(hasActiveView()) {
            flowLifecycleProvider.onStop(rootHolder.root.getChildAt(0));
        }
    }

    @Override
    public void onViewRestored(boolean forcedWithBundler) {
        if(hasActiveView()) {
            flowLifecycleProvider.onViewRestored(rootHolder.root.getChildAt(0), forcedWithBundler);
        }
    }

    @Override
    public void onViewDestroyed(boolean removedByFlow) {
        if(hasActiveView()) {
            flowLifecycleProvider.onViewDestroyed(rootHolder.root.getChildAt(0), removedByFlow);
        }
    }

    @Override
    public void preSaveViewState(@Nullable Bundle outState) {
        if(hasActiveView()) {
            flowLifecycleProvider.preSaveViewState(rootHolder.root.getChildAt(0), outState);

            // Important: Single Root Dispatcher can handle its child's state directly, but Container Root Dispatcher cannot.
            ForceBundler.saveToBundle(activity, rootHolder.root.getChildAt(0));
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

    public SingleRootDispatcher(Activity activity) {
        super(activity);
    }

    @Override
    public void dispatch(@NonNull Traversal traversal, final @NonNull TraversalCallback callback) {
        final ViewGroup root = rootHolder.root;
        if(DispatcherUtils.isPreviousKeySameAsNewKey(traversal.origin, traversal.destination)) { //short circuit on same key
            DispatcherUtils.finishTraversal(callback);
            return;
        }
        final LayoutKey newKey = DispatcherUtils.getNewKey(traversal);
        final LayoutKey previousKey = DispatcherUtils.getPreviousKey(traversal);

        final Direction direction = traversal.direction;

        final View previousView = root.getChildAt(0);
        DispatcherUtils.persistViewToState(traversal, previousView);

        final View newView = DispatcherUtils.createViewFromKey(traversal, newKey, root, baseContext);
        DispatcherUtils.restoreViewFromState(traversal, newView);

        final LayoutKey animatedKey = DispatcherUtils.selectAnimatedKey(direction, previousKey, newKey);
        DispatcherUtils.addViewToGroupForKey(direction, newView, root, animatedKey);
        ViewUtils.waitForMeasure(newView, new ViewUtils.OnMeasuredCallback() {
            @Override
            public void onMeasured(View view, int width, int height) {
                Animator animator = DispatcherUtils.createAnimatorForViews(animatedKey, previousView, newView, direction);
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
