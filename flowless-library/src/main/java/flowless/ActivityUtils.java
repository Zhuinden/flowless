package flowless;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

/**
 * Created by Zhuinden on 2016.05.26..
 */
public class ActivityUtils {
    public static final String ACTIVITY_SERVICE_TAG = "flowless.ACTIVITY";

    public static Activity getActivity(Context context) {
        if(context instanceof Activity) {
            return (Activity) context;
        } else if(context instanceof ContextWrapper) {
            if(context instanceof FlowContextWrapper || context instanceof InternalContextWrapper) {
                return InternalContextWrapper.getActivity(context);
            } else {
                Context baseContext = ((ContextWrapper) context).getBaseContext();
                if(baseContext instanceof Activity) {
                    return (Activity) baseContext;
                }
            }
        }
        throw new IllegalStateException("No activity could be found in [" + context + "]");
    }
}
