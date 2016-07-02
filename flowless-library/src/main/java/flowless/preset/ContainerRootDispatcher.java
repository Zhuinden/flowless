package flowless.preset;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
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
        implements Application.ActivityLifecycleCallbacks {
    public ContainerRootDispatcher(Context baseContext, Activity activity) {
        super(baseContext, activity);
        application.registerActivityLifecycleCallbacks(this);
    }

    private boolean hasActiveView() {
        return rootHolder != null && rootHolder.root != null;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if(activity == this.activity) {
            if(hasActiveView()) {
                flowLifecycleProvider.onActivityCreated(rootHolder.root, savedInstanceState);
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if(activity == this.activity) {
            if(hasActiveView()) {
                flowLifecycleProvider.onActivityStarted(rootHolder.root);
            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if(activity == this.activity) {
            if(hasActiveView()) {
                flowLifecycleProvider.onActivityResumed(rootHolder.root);
            }
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if(activity == this.activity) {
            if(hasActiveView()) {
                flowLifecycleProvider.onActivityPaused(rootHolder.root);
            }
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if(activity == this.activity) {
            if(hasActiveView()) {
                flowLifecycleProvider.onActivityStopped(rootHolder.root);
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        if(activity == this.activity) {
            if(rootHolder != null && rootHolder.root != null && rootHolder.root instanceof ViewStatePersistenceListener) {
                flowLifecycleProvider.onActivitySaveInstanceState(rootHolder.root, outState);
                // you must call the ForceBundler manually within the Dispatcher Container
            }
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if(activity == this.activity) {
            if(hasActiveView()) {
                flowLifecycleProvider.onActivityDestroyed(rootHolder.root);
            }
            if(hasActiveView()) {
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
