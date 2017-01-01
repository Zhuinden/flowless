package flowless.preset;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import flowless.Dispatcher;
import flowless.Traversal;
import flowless.TraversalCallback;

/**
 * Created by Zhuinden on 2016.07.01..
 */
public abstract class BaseDispatcher
        implements Dispatcher, FlowLifecycles {
    public static class RootHolder {
        protected ViewGroup root;

        public RootHolder() {
        }

        public ViewGroup getRoot() {
            return root;
        }

        public void setRoot(ViewGroup root) {
            this.root = root;
        }
    }

    protected Context baseContext;
    protected final RootHolder rootHolder;

    public BaseDispatcher() {
        this.rootHolder = createRootHolder();
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
