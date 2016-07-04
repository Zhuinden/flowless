package com.zhuinden.flow_alpha_master_detail;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import flowless.ActivityUtils;
import flowless.Dispatcher;
import flowless.ForceBundler;
import flowless.Traversal;
import flowless.TraversalCallback;
import flowless.preset.DispatcherUtils;
import flowless.preset.FlowContainerLifecycleListener;
import flowless.preset.FlowLifecycleProvider;
import flowless.preset.LayoutPath;

/**
 * Created by Zhuinden on 2016.04.16..
 */
public class MasterDetailContainer
        extends LinearLayout
        implements Dispatcher, FlowContainerLifecycleListener {
    public MasterDetailContainer(Context context) {
        super(context);
        init();
    }

    public MasterDetailContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MasterDetailContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public MasterDetailContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    FlowLifecycleProvider flowLifecycleProvider;

    private void init() {
        flowLifecycleProvider = new FlowLifecycleProvider();
    }

    @BindView(R.id.master_container)
    SinglePaneContainer masterContainer;

    @BindView(R.id.detail_container)
    SinglePaneContainer detailContainer;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public SinglePaneContainer getMasterContainer() {
        return masterContainer;
    }

    public SinglePaneContainer getDetailContainer() {
        return detailContainer;
    }

    @Override
    public void dispatch(@NonNull Traversal traversal, @NonNull TraversalCallback callback) {
        if(DispatcherUtils.isPreviousKeySameAsNewKey(traversal.origin, traversal.destination)) { //short circuit on same key
            DispatcherUtils.finishTraversal(callback);
            return;
        }

        final LayoutPath newKey = DispatcherUtils.getNewKey(traversal);
        final LayoutPath previousKey = DispatcherUtils.getPreviousKey(traversal);

        final View newView = DispatcherUtils.createViewFromKey(traversal, newKey, this, ((ContextWrapper)getContext()).getBaseContext());
        DispatcherUtils.restoreViewFromState(traversal, newView);

        // TODO: animations
        if(newKey instanceof IsFullScreen) {
            persistMaster(traversal);
            persistDetail(traversal);
            masterContainer.removeAllViews();
            masterContainer.setVisibility(GONE);
            detailContainer.removeAllViews();
            detailContainer.setVisibility(GONE);
            addView(newView, 0);
        } else {
            if(getChildCount() > 0 && !(getChildAt(0) instanceof SinglePaneContainer)) {
                DispatcherUtils.persistViewToState(traversal, getChildAt(0));
                removeViewAt(0);
            }
            masterContainer.setVisibility(VISIBLE);
            detailContainer.setVisibility(VISIBLE);
            if(newKey instanceof IsDetail) {
                persistDetail(traversal);
                detailContainer.removeAllViews();
                detailContainer.addView(newView);
                if(masterContainer.getChildCount() <= 0) {
                    final View restoredMasterView = DispatcherUtils.createViewFromKey(traversal, (LayoutPath)(((IsDetail) newKey).getMaster()), masterContainer, ((ContextWrapper)getContext()).getBaseContext());
                    DispatcherUtils.restoreViewFromState(traversal, restoredMasterView);
                    masterContainer.addView(restoredMasterView);
                }
            } else if(newKey instanceof IsMaster) {
                persistMaster(traversal);
                persistDetail(traversal);
                masterContainer.removeAllViews();
                detailContainer.removeAllViews();
                masterContainer.addView(newView);
            }
        }
        DispatcherUtils.finishTraversal(callback);
    }

    private void persistDetail(@NonNull Traversal traversal) {
        if(detailContainer.getChildCount() > 0) {
            DispatcherUtils.persistViewToState(traversal, detailContainer.getChildAt(0));
        }
    }

    private void persistMaster(@NonNull Traversal traversal) {
        if(masterContainer.getChildCount() > 0) {
            DispatcherUtils.persistViewToState(traversal, masterContainer.getChildAt(0));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(masterContainer.getChildCount() > 0) {
            masterContainer.onActivityResult(requestCode, resultCode, data);
        }
        if(detailContainer.getChildCount() > 0) {
            detailContainer.onActivityResult(requestCode, resultCode, data);
        }
        if(getChildCount() > 0 && !(getChildAt(0) instanceof SinglePaneContainer)) {
            flowLifecycleProvider.onActivityResult(getChildAt(0), requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onBackPressed() {
        boolean didHandle = false;
        if(detailContainer.getChildCount() > 0) {
            didHandle = detailContainer.onBackPressed();
        }
        if(didHandle) {
            return true;
        }

        if(masterContainer.getChildCount() > 0) {
            didHandle = masterContainer.onBackPressed();
        }
        if(didHandle) {
            return true;
        }

        if(getChildCount() > 0 && !(getChildAt(0) instanceof SinglePaneContainer)) {
            didHandle = flowLifecycleProvider.onBackPressed(getChildAt(0));
        }
        return didHandle;
    }

    @Override
    public void onCreate(Bundle bundle) {
        if(masterContainer.getChildCount() > 0) {
            masterContainer.onCreate(bundle);
        }
        if(detailContainer.getChildCount() > 0) {
            detailContainer.onCreate(bundle);
        }
        if(getChildCount() > 0 && !(getChildAt(0) instanceof SinglePaneContainer)) {
            flowLifecycleProvider.onCreate(getChildAt(0), bundle);
        }
    }

    @Override
    public void onDestroy() {
        if(masterContainer.getChildCount() > 0) {
            masterContainer.onDestroy();
        }
        if(detailContainer.getChildCount() > 0) {
            detailContainer.onDestroy();
        }
        if(getChildCount() > 0 && !(getChildAt(0) instanceof SinglePaneContainer)) {
            flowLifecycleProvider.onDestroy(getChildAt(0));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(masterContainer.getChildCount() > 0) {
            masterContainer.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        if(detailContainer.getChildCount() > 0) {
            detailContainer.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        if(getChildCount() > 0 && !(getChildAt(0) instanceof SinglePaneContainer)) {
            flowLifecycleProvider.onRequestPermissionsResult(getChildAt(0), requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onResume() {
        if(masterContainer.getChildCount() > 0) {
            masterContainer.onResume();
        }
        if(detailContainer.getChildCount() > 0) {
            detailContainer.onResume();
        }
        if(getChildCount() > 0 && !(getChildAt(0) instanceof SinglePaneContainer)) {
            flowLifecycleProvider.onResume(getChildAt(0));
        }
    }

    @Override
    public void onPause() {
        if(masterContainer.getChildCount() > 0) {
            masterContainer.onPause();
        }
        if(detailContainer.getChildCount() > 0) {
            detailContainer.onPause();
        }
        if(getChildCount() > 0 && !(getChildAt(0) instanceof SinglePaneContainer)) {
            flowLifecycleProvider.onPause(getChildAt(0));
        }
    }

    @Override
    public void onStart() {
        if(masterContainer.getChildCount() > 0) {
            masterContainer.onStart();
        }
        if(detailContainer.getChildCount() > 0) {
            detailContainer.onStart();
        }
        if(getChildCount() > 0 && !(getChildAt(0) instanceof SinglePaneContainer)) {
            flowLifecycleProvider.onStart(getChildAt(0));
        }
    }

    @Override
    public void onStop() {
        if(masterContainer.getChildCount() > 0) {
            masterContainer.onStop();
        }
        if(detailContainer.getChildCount() > 0) {
            detailContainer.onStop();
        }
        if(getChildCount() > 0 && !(getChildAt(0) instanceof SinglePaneContainer)) {
            flowLifecycleProvider.onStop(getChildAt(0));
        }
    }

    @Override
    public void onViewRestored(boolean forcedWithBundler) {
        if(masterContainer.getChildCount() > 0) {
            masterContainer.onViewRestored(forcedWithBundler);
        }
        if(detailContainer.getChildCount() > 0) {
            detailContainer.onViewRestored(forcedWithBundler);
        }
        if(getChildCount() > 0 && !(getChildAt(0) instanceof SinglePaneContainer)) {
            flowLifecycleProvider.onViewRestored(getChildAt(0), forcedWithBundler);
        }
    }

    @Override
    public void onViewDestroyed(boolean removedByFlow) {
        if(masterContainer.getChildCount() > 0) {
            masterContainer.onViewDestroyed(removedByFlow);
        }
        if(detailContainer.getChildCount() > 0) {
            detailContainer.onViewDestroyed(removedByFlow);
        }
        if(getChildCount() > 0 && !(getChildAt(0) instanceof SinglePaneContainer)) {
            flowLifecycleProvider.onViewDestroyed(getChildAt(0), removedByFlow);
        }
    }

    @Override
    public void preSaveViewState(@Nullable Bundle outState) {
        if(masterContainer.getChildCount() > 0) {
            masterContainer.preSaveViewState(outState);
        }
        if(detailContainer.getChildCount() > 0) {
            detailContainer.preSaveViewState(outState);
        }
        if(getChildCount() > 0 && !(getChildAt(0) instanceof SinglePaneContainer)) {
            flowLifecycleProvider.preSaveViewState(getChildAt(0), outState);
            ForceBundler.saveToBundle(ActivityUtils.getActivity(getContext()), getChildAt(0));
        }
    }
}
