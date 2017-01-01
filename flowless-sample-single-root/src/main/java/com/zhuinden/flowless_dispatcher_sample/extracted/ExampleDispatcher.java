package com.zhuinden.flowless_dispatcher_sample.extracted;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import flowless.Direction;
import flowless.Traversal;
import flowless.TraversalCallback;
import flowless.ViewUtils;
import flowless.preset.SingleRootDispatcher;

/**
 * Created by Zhuinden on 2016.12.03..
 */

public class ExampleDispatcher extends SingleRootDispatcher {

    @Override
    public void dispatch(@NonNull Traversal traversal, final @NonNull TraversalCallback callback) {
        final ViewGroup root = rootHolder.getRoot();
        if(flowless.preset.DispatcherUtils.isPreviousKeySameAsNewKey(traversal.origin, traversal.destination)) { //short circuit on same key
            callback.onTraversalCompleted();
            onTraversalCompleted();
            return;
        }
        final LayoutKey newKey = flowless.preset.DispatcherUtils.getNewKey(traversal);
        final LayoutKey previousKey = flowless.preset.DispatcherUtils.getPreviousKey(traversal);

        final Direction direction = traversal.direction;

        final View previousView = root.getChildAt(0);
        flowless.preset.DispatcherUtils.persistViewToStateAndNotifyRemoval(traversal, previousView);

        final View newView = DispatcherUtils.createViewFromKey(traversal, newKey, root, baseContext);
        flowless.preset.DispatcherUtils.restoreViewFromState(traversal, newView);

        final LayoutKey animatedKey = DispatcherUtils.selectAnimatedKey(direction, previousKey, newKey);
        DispatcherUtils.addViewToGroupForKey(direction, newView, root, animatedKey);

        configure(previousKey, newKey);

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

    protected void configure(LayoutKey previousKey, LayoutKey newKey) {
    }

    private void finishTransition(View previousView, ViewGroup root, @NonNull TraversalCallback callback) {
        if(previousView != null) {
            root.removeView(previousView);
        }
        callback.onTraversalCompleted();
        onTraversalCompleted();
    }

    protected void onTraversalCompleted() {
    }
}
