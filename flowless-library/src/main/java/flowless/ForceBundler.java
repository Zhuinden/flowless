package flowless;

import android.view.View;

import flowless.preset.FlowLifecycles;

/**
 * Created by Zhuinden on 2016.03.02..
 */
public class ForceBundler {
    public static void saveToBundle(View... activeViews) {
        if(activeViews != null && activeViews.length > 0) {
            for(View view : activeViews) {
                if(view != null) {
                    KeyManager keyManager = KeyManager.get(view);
                    State state = keyManager.getState(Flow.getKey(view));
                    state.save(view);
                    if(view instanceof Bundleable) {
                        state.setBundle(((Bundleable) view).toBundle());
                    }
                }
            }
        }
    }

    public static void restoreFromBundle(View... activeViews) {
        if(activeViews != null && activeViews.length > 0) {
            for(View view : activeViews) {
                if(view != null) {
                    KeyManager keyManager = KeyManager.get(view);
                    State state = keyManager.getState(Flow.getKey(view));
                    if(state != null) {
                        state.restore(view);
                        if(view instanceof Bundleable) {
                            ((Bundleable) view).fromBundle(state.getBundle());
                        }
                    }
                }
                if(view instanceof FlowLifecycles.ViewLifecycleListener) {
                    ((FlowLifecycles.ViewLifecycleListener) view).onViewRestored();
                }
            }
        }
    }
}