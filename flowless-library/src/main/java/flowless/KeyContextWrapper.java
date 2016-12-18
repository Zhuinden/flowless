package flowless;

import android.content.Context;

/**
 * Created by Zhuinden on 2016.12.18..
 */

public interface KeyContextWrapper {
    static final String KEY_CONTEXT_WRAPPER = "flow_services_context_wrapper";

    public class Methods {
        private Methods() {
        }

        public static KeyContextWrapper get(Context context) {
            //noinspection ResourceType
            @SuppressWarnings("WrongConstant") KeyContextWrapper wrapper = (KeyContextWrapper) context.getSystemService(KEY_CONTEXT_WRAPPER);
            return wrapper;
        }
    }

    <T> T getKey();
}
