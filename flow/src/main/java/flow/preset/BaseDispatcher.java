package flow.preset;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import flow.Dispatcher;
import flow.Traversal;
import flow.TraversalCallback;

/**
 * Created by Zhuinden on 2016.07.01..
 */
public abstract class BaseDispatcher implements Dispatcher, FlowLifecycles, FlowActivityCallbacks {
    public static class RootHolder {
        ViewGroup root;

        public RootHolder() {
        }

        public void setRoot(ViewGroup root) {
            this.root = root;
        }
    }

    protected Context baseContext;
    protected final RootHolder rootHolder;
    protected final FlowLifecycleProvider flowLifecycleProvider;

    protected final Activity activity;
    protected final Application application;

    public BaseDispatcher(Context baseContext, Activity activity) {
        this.activity = activity;
        this.rootHolder = createRootHolder();
        this.flowLifecycleProvider = new FlowLifecycleProvider();

        application = (Application)baseContext.getApplicationContext();
    }

    protected RootHolder createRootHolder() {
        return new RootHolder();
    }

    public void setBaseContext(Context baseContext) {
        this.baseContext = baseContext;
    }

    public RootHolder getRootHolder() {
        return rootHolder;
    }

    @Override
    public abstract void dispatch(@NonNull Traversal traversal, @NonNull TraversalCallback callback);
}
