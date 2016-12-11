package flowless;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Zhuinden on 2016.07.31..
 */
interface InternalContext {
    static final String FLOW_SERVICE = "flow.InternalContextWrapper.FLOW_SERVICE";
    static final String CONTEXT_MANAGER_SERVICE = "flow.InternalContextWrapper.CONTEXT_MANAGER_SERVICE";
    static final String ACTIVITY = "flow.InternalContextWrapper.ACTIVITY_SERVICE";
    static final String SERVICE_PROVIDER = "flow.InternalContextWrapper.SERVICE_PROVIDER";

    class Methods {
        static Flow getFlow(Context context) {
            //noinspection ResourceType
            @SuppressWarnings("WrongConstant") Flow systemService = (Flow) context.getSystemService(FLOW_SERVICE);
            return systemService;
        }

        static Activity getActivity(Context context) {
            //noinspection ResourceType
            Activity activity = (Activity) context.getSystemService(ACTIVITY);
            return activity;
        }
    }
}
