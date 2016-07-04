package flowless.preset;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import flowless.Dispatcher;
import flowless.Flow;
import flowless.Traversal;
import flowless.TraversalCallback;

/**
 * Created by Zhuinden on 2016.07.01..
 */
public class ContainerRootDispatcher
        extends BaseDispatcher
        implements FlowContainerLifecycleListener {
    public ContainerRootDispatcher(Activity activity) {
        super(activity);
    }

    private boolean hasActiveView() {
        return rootHolder != null && rootHolder.root != null;
    }

    @Override
    public void onCreate(Bundle bundle) {
        if(hasActiveView()) {
            flowLifecycleProvider.onCreate(rootHolder.root, bundle);
        }
    }

    @Override
    public void onDestroy() {
        if(hasActiveView()) {
            flowLifecycleProvider.onDestroy(rootHolder.root);
        }
        if(hasActiveView()) {
            rootHolder.root = null;
        }
    }

    @Override
    public void onResume() {
        if(hasActiveView()) {
            flowLifecycleProvider.onResume(rootHolder.root);
        }
    }

    @Override
    public void onPause() {
        if(hasActiveView()) {
            flowLifecycleProvider.onPause(rootHolder.root);
        }
    }

    @Override
    public void onStart() {
        if(hasActiveView()) {
            flowLifecycleProvider.onStart(rootHolder.root);
        }
    }

    @Override
    public void onStop() {
        if(hasActiveView()) {
            flowLifecycleProvider.onStop(rootHolder.root);
        }
    }

    @Override
    public void onViewRestored(boolean forcedWithBundler) {
        if(hasActiveView()) {
            flowLifecycleProvider.onViewRestored(rootHolder.root, forcedWithBundler);
        }
    }

    @Override
    public void onViewDestroyed(boolean removedByFlow) {
        if(hasActiveView()) {
            flowLifecycleProvider.onViewDestroyed(rootHolder.root, removedByFlow);
        }
    }

    @Override
    public void preSaveViewState(@Nullable Bundle outState) {
        if(hasActiveView()) {
            flowLifecycleProvider.preSaveViewState(rootHolder.root, outState);
            // you must call the ForceBundler manually within the Dispatcher Container
        }
    }

    @Override
    @CheckResult
    public boolean onBackPressed() {
        boolean childHandledEvent = false;
        if(hasActiveView()) {
            childHandledEvent = flowLifecycleProvider.onBackPressed(rootHolder.root);
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
            flowLifecycleProvider.onActivityResult(rootHolder.root, requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(hasActiveView()) {
            flowLifecycleProvider.onRequestPermissionsResult(rootHolder.root, requestCode, permissions, grantResults);
        }
    }

    @Override
    public void dispatch(@NonNull Traversal traversal, @NonNull TraversalCallback traversalCallback) {
        ViewGroup root = rootHolder.root;
        if(root instanceof Dispatcher) {
            Dispatcher dispatchContainer = (Dispatcher) root;
            dispatchContainer.dispatch(traversal, traversalCallback);
        } else {
            throw new IllegalArgumentException("The Root [" + root + "] of a ContainerRootDispatcher must be a Dispatcher.");
        }
    }
}
