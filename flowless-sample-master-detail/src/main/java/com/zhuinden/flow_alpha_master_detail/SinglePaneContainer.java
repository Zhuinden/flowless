package com.zhuinden.flow_alpha_master_detail;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.zhuinden.flow_alpha_master_detail.extracted.ExampleDispatcher;

import flowless.ActivityUtils;
import flowless.Dispatcher;
import flowless.Traversal;
import flowless.TraversalCallback;
import flowless.preset.FlowContainerLifecycleListener;
import flowless.preset.SingleRootDispatcher;

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

    SingleRootDispatcher singleRootDispatcher;

    private void init() {
        singleRootDispatcher = new ExampleDispatcher(ActivityUtils.getActivity(getContext()));
        singleRootDispatcher.getRootHolder().setRoot(this);
        singleRootDispatcher.setBaseContext(((ContextWrapper)getContext()).getBaseContext());
    }

    @Override
    public void dispatch(@NonNull Traversal traversal, final @NonNull TraversalCallback callback) {
        singleRootDispatcher.dispatch(traversal, callback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        singleRootDispatcher.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onBackPressed() {
        return singleRootDispatcher.onBackPressed();
    }

    @Override
    public void onCreate(Bundle bundle) {
        singleRootDispatcher.onCreate(bundle);
    }

    @Override
    public void onDestroy() {
        singleRootDispatcher.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        singleRootDispatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onResume() {
        singleRootDispatcher.onResume();
    }

    @Override
    public void onPause() {
        singleRootDispatcher.onPause();
    }

    @Override
    public void onStart() {
        singleRootDispatcher.onStart();
    }

    @Override
    public void onStop() {
        singleRootDispatcher.onStop();
    }

    @Override
    public void onViewRestored(boolean forcedWithBundler) {
        singleRootDispatcher.onViewRestored(forcedWithBundler);
    }

    @Override
    public void onViewDestroyed(boolean removedByFlow) {
        singleRootDispatcher.onViewDestroyed(removedByFlow);
    }

    @Override
    public void preSaveViewState(@Nullable Bundle outState) {
        singleRootDispatcher.preSaveViewState(outState);
    }
}
