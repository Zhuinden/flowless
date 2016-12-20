package flowless;

import android.content.Context;

/**
 * Created by Zhuinden on 2016.12.18..
 */

public interface KeyContextWrapper {
    public static final String KEY_SERVICE_TAG = "flowless.KEY";

    public class Methods {
        private Methods() {
        }

        @SuppressWarnings("unchecked")
        public static <T> T getKey(Context context) {
            //noinspection ResourceType
            return (T) context.getSystemService(KEY_SERVICE_TAG);
        }
    }

    <T> T getKey();
}
