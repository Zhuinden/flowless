package flowless;

import android.app.Activity;
import android.content.Context;
import android.view.ContextThemeWrapper;

/**
 * Created by Zhuinden on 2016.07.31..
 */
final class InternalContextThemeWrapper
        extends ContextThemeWrapper
        implements InternalContext {

    private final Activity activity;
    private Flow flow;
    private KeyManager keyManager;

    InternalContextThemeWrapper(Context baseContext, Activity activity, int themeResource) {
        super(baseContext, themeResource);
        this.activity = activity;
    }

    @Override
    public Object getSystemService(String name) {
        if(FLOW_SERVICE.equals(name)) {
            if(flow == null) {
                flow = InternalLifecycleIntegration.find(activity).flow;
            }
            return flow;
        } else if(CONTEXT_MANAGER_SERVICE.equals(name)) {
            if(keyManager == null) {
                keyManager = InternalLifecycleIntegration.find(activity).keyManager;
            }
            return keyManager;
        } else if(ACTIVITY.equals(name)) {
            return activity;
        } else {
            return super.getSystemService(name);
        }
    }
}
