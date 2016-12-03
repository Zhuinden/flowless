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
public abstract class SingleRootDispatcher
        extends BaseDispatcher
        implements FlowContainerLifecycleListener {
    private boolean hasActiveView() {
        return rootHolder != null && rootHolder.root != null && rootHolder.root.getChildCount() > 0;
    }

    @Override
    public void onCreate(Bundle bundle) {
        if(hasActiveView()) {
            FlowLifecycleProvider.onCreate(rootHolder.root.getChildAt(0), bundle);
        }
    }

    @Override
    public void onDestroy() {
        if(hasActiveView()) {
            FlowLifecycleProvider.onDestroy(rootHolder.root.getChildAt(0));
        }
        if(rootHolder != null && rootHolder.root != null) {
            rootHolder.root = null;
        }
    }

    @Override
    public void onResume() {
        if(hasActiveView()) {
            FlowLifecycleProvider.onResume(rootHolder.root.getChildAt(0));
        }
    }

    @Override
    public void onPause() {
        if(hasActiveView()) {
            FlowLifecycleProvider.onPause(rootHolder.root.getChildAt(0));
        }
    }

    @Override
    public void onStart() {
        if(hasActiveView()) {
            FlowLifecycleProvider.onStart(rootHolder.root.getChildAt(0));
        }
    }

    @Override
    public void onStop() {
        if(hasActiveView()) {
            FlowLifecycleProvider.onStop(rootHolder.root.getChildAt(0));
        }
    }

    @Override
    public void onViewRestored(boolean forcedWithBundler) {
        if(hasActiveView()) {
            FlowLifecycleProvider.onViewRestored(rootHolder.root.getChildAt(0), forcedWithBundler);
        }
    }

    @Override
    public void onViewDestroyed(boolean removedByFlow) {
        if(hasActiveView()) {
            FlowLifecycleProvider.onViewDestroyed(rootHolder.root.getChildAt(0), removedByFlow);
        }
    }

    @Override
    public void preSaveViewState(@Nullable Bundle outState) {
        if(hasActiveView()) {
            FlowLifecycleProvider.preSaveViewState(rootHolder.root.getChildAt(0), outState);

            // Important: Single Root Dispatcher can handle its child's state directly, but Container Root Dispatcher cannot.
            ForceBundler.saveToBundle(activity, rootHolder.root.getChildAt(0));
        }
    }

    @Override
    @CheckResult
    public boolean onBackPressed() {
        boolean childHandledEvent = false;
        if(hasActiveView()) {
            childHandledEvent = FlowLifecycleProvider.onBackPressed(rootHolder.root.getChildAt(0));
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
            FlowLifecycleProvider.onActivityResult(rootHolder.root.getChildAt(0), requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(hasActiveView()) {
            FlowLifecycleProvider.onRequestPermissionsResult(rootHolder.root.getChildAt(0), requestCode, permissions, grantResults);
        }
    }

    public SingleRootDispatcher(Activity activity) {
        super(activity);
    }
}
