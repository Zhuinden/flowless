package flowless;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

/**
 * Created by Zhuinden on 2016.05.26..
 */
public class ActivityUtils {
    public static Activity getActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            Context contextWrapper = context;
            while (contextWrapper instanceof ContextWrapper && ((ContextWrapper) context).getBaseContext() != null) {
                contextWrapper = ((ContextWrapper) contextWrapper).getBaseContext();
                if (contextWrapper instanceof Activity) {
                    return (Activity) contextWrapper;
                }
            }
        }
        throw new IllegalStateException("No activity could be found in [" + context + "]");
    }
}
