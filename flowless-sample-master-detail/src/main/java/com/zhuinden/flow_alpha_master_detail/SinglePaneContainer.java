package com.zhuinden.flow_alpha_master_detail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import flowless.ActivityUtils;
import flowless.Direction;
import flowless.Dispatcher;
import flowless.ForceBundler;
import flowless.Traversal;
import flowless.TraversalCallback;
import flowless.ViewUtils;
import flowless.preset.DispatcherUtils;
import flowless.preset.FlowContainerLifecycleListener;
import flowless.preset.FlowLifecycleProvider;
import flowless.preset.LayoutPath;

/**
 * Created by Zhuinden on 2016.04.16..
 */
public class SinglePaneContainer
        extends FrameLayout
        implements Dispatcher, FlowContainerLifecycleListener {
    public SinglePaneContainer(Context context) {
        super(context);
        init();
    }

    public SinglePaneContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SinglePaneContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public SinglePaneContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    FlowLifecycleProvider flowLifecycleProvider;

    private void init() {
        flowLifecycleProvider = new FlowLifecycleProvider();
    }

    private boolean hasActiveView() {
        return getChildCount() > 0;
    }

    @Override
    public void dispatch(@NonNull Traversal traversal, final @NonNull TraversalCallback callback) { // TODO: delegation for single root persistence logic
        final ViewGroup root = this;
        if(DispatcherUtils.isPreviousKeySameAsNewKey(traversal.origin, traversal.destination)) { //short circuit on same key
            DispatcherUtils.finishTraversal(callback);
            return;
        }
        final LayoutPath newKey = DispatcherUtils.getNewKey(traversal);
        final LayoutPath previousKey = DispatcherUtils.getPreviousKey(traversal);

        final Direction direction = traversal.direction;

        final View previousView = root.getChildAt(0);
        DispatcherUtils.persistViewToState(traversal, previousView);

        final View newView = DispatcherUtils.createViewFromKey(traversal, newKey, root, ((ContextWrapper) getContext()).getBaseContext());
        DispatcherUtils.restoreViewFromState(traversal, newView);

        final LayoutPath animatedKey = DispatcherUtils.selectAnimatedKey(direction, previousKey, newKey);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(hasActiveView()) {
            flowLifecycleProvider.onActivityResult(getChildAt(0), requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onBackPressed() {
        if(hasActiveView()) {
            return flowLifecycleProvider.onBackPressed(getChildAt(0));
        }
        return false;
    }

    @Override
    public void onCreate(Bundle bundle) {
        if(hasActiveView()) {
            flowLifecycleProvider.onActivityCreated(getChildAt(0), bundle);
        }
    }

    @Override
    public void onDestroy() {
        if(hasActiveView()) {
            flowLifecycleProvider.onActivityDestroyed(getChildAt(0));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(hasActiveView()) {
            flowLifecycleProvider.onRequestPermissionsResult(getChildAt(0), requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onResume() {
        if(hasActiveView()) {
            flowLifecycleProvider.onActivityResumed(getChildAt(0));
        }
    }

    @Override
    public void onPause() {
        if(hasActiveView()) {
            flowLifecycleProvider.onActivityPaused(getChildAt(0));
        }
    }

    @Override
    public void onStart() {
        if(hasActiveView()) {
            flowLifecycleProvider.onActivityStarted(getChildAt(0));
        }
    }

    @Override
    public void onStop() {
        if(hasActiveView()) {
            flowLifecycleProvider.onActivityStopped(getChildAt(0));
        }
    }

    @Override
    public void onViewRestored(boolean forcedWithBundler) {
        if(hasActiveView()) {
            flowLifecycleProvider.onViewRestored(getChildAt(0), forcedWithBundler);
        }
    }

    @Override
    public void onViewDestroyed(boolean removedByFlow) {
        if(hasActiveView()) {
            flowLifecycleProvider.onViewDestroyed(getChildAt(0), removedByFlow);
        }
    }

    @Override
    public void preSaveViewState(@Nullable Bundle outState) {
        if(hasActiveView()) {
            flowLifecycleProvider.onActivitySaveInstanceState(getChildAt(0), outState);
            ForceBundler.saveToBundle(ActivityUtils.getActivity(getContext()), getChildAt(0));
        }
    }
}
