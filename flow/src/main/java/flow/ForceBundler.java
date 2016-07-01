package flow;

import android.app.Activity;
import android.view.View;

import flow.preset.FlowLifecycles;

/**
 * Created by Zhuinden on 2016.03.02..
 */
public class ForceBundler {
    public static void saveToBundle(Activity activity, View... activeViews) {
        InternalLifecycleIntegration internalLifeCycleIntegration = InternalLifecycleIntegration.find(activity);
        if(internalLifeCycleIntegration != null) {
            KeyManager keyManager = internalLifeCycleIntegration.keyManager;
            for(View view : activeViews) {
                if(view != null) {
                    State state = keyManager.getState(Flow.getKey(view));
                    state.save(view);
                    if(view instanceof Bundleable) {
                        state.setBundle(((Bundleable) view).toBundle());
                    }
                }
            }
        }
    }

    public static void restoreFromBundle(Activity activity, View... activeViews) {
        InternalLifecycleIntegration internalLifeCycleIntegration = InternalLifecycleIntegration.find(activity);
        if(internalLifeCycleIntegration != null) {
            KeyManager keyManager = internalLifeCycleIntegration.keyManager;
            for(View view : activeViews) {
                if(view != null) {
                    State state = keyManager.getState(Flow.getKey(view));
                    if(state != null) {
                        state.restore(view);
                        if(view instanceof Bundleable) {
                            ((Bundleable)view).fromBundle(state.getBundle());
                        }
                    }
                }
                if(view instanceof FlowLifecycles.ViewLifecycleListener) {
                    ((FlowLifecycles.ViewLifecycleListener) view).onViewRestored(true);
                }
            }
        }
    }
}