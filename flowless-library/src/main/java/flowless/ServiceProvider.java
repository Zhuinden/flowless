package flowless;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Zhuinden on 2016.08.29..
 */
public class ServiceProvider {
    public static class NoServiceException
            extends RuntimeException {
        private Object key;
        private String tag;

        public NoServiceException(Object key, String tag) {
            super("No service found for tag [" + tag + "] for key [" + key + "]");
            this.key = key;
            this.tag = tag;
        }

        public Object getKey() {
            return key;
        }

        public String getTag() {
            return tag;
        }
    }

    protected Map<Object, Map<String, Object>> services;

    public ServiceProvider() {
        services = new LinkedHashMap<>();
    }

    public <T> ServiceProvider bindService(View view, String tag, T service) {
        return bindService(Flow.getKey(view), tag, service);
    }

    public <T> ServiceProvider bindService(Context context, String tag, T service) {
        return bindService(Flow.getKey(context), tag, service);
    }

    public <T> ServiceProvider bindService(Object key, String tag, T service) {
        if(!services.containsKey(key)) {
            services.put(key, new HashMap<String, Object>());
        }
        Map<String, Object> serviceMap = services.get(key);
        serviceMap.put(tag, service);
        return this;
    }

    @NonNull
    public <T> T getService(View view, String tag) {
        return getService(Flow.getKey(view), tag);
    }

    @NonNull
    public <T> T getService(Context context, String tag) {
        return getService(Flow.getKey(context), tag);
    }

    @NonNull
    public <T> T getService(Object key, String tag) {
        if(!services.containsKey(key)) {
            throw new NoServiceException(key, tag);
        }
        Map<String, Object> serviceMap = services.get(key);
        if(serviceMap.containsKey(tag)) {
            //noinspection unchecked
            return (T) serviceMap.get(tag);
        } else {
            throw new NoServiceException(key, tag);
        }
    }

    public Map<String, Object> unbindServices(View view) {
        return unbindServices(Flow.getKey(view));
    }

    public Map<String, Object> unbindServices(Context context) {
        return unbindServices(Flow.getKey(context));
    }

    public Map<String, Object> unbindServices(Object key) {
        return services.remove(key);
    }
}
