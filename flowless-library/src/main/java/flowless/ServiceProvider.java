package flowless;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static flowless.Preconditions.checkNotNull;

/**
 * Created by Zhuinden on 2016.08.29..
 */
public class ServiceProvider {
    public static class NoServiceException
            extends RuntimeException {
        private Object key;
        private String serviceTag;

        public NoServiceException(Object key, String serviceTag) {
            super("No service found for serviceTag [" + serviceTag + "] for key [" + key + "]");
            this.key = key;
            this.serviceTag = serviceTag;
        }

        public Object getKey() {
            return key;
        }

        public String getServiceTag() {
            return serviceTag;
        }
    }

    protected Map<Object, Map<String, Object>> services;

    public ServiceProvider() {
        services = new LinkedHashMap<>();
    }

    public <T> ServiceProvider bindService(View view, String serviceTag, T service) {
        return bindService(Flow.getKey(view), serviceTag, service);
    }

    public <T> ServiceProvider bindService(Context context, String serviceTag, T service) {
        return bindService(Flow.getKey(context), serviceTag, service);
    }

    public <T> ServiceProvider bindService(Object key, String serviceTag, T service) {
        checkNotNull(key, "key");
        checkNotNull(serviceTag, "serviceTag");
        checkNotNull(service, "service");
        if(!services.containsKey(key)) {
            services.put(key, new HashMap<String, Object>());
        }
        Map<String, Object> serviceMap = services.get(key);
        serviceMap.put(serviceTag, service);
        return this;
    }

    public boolean hasService(View view, String serviceTag) {
        return hasService(Flow.getKey(view), serviceTag);
    }

    public boolean hasService(Context context, String serviceTag) {
        return hasService(Flow.getKey(context), serviceTag);
    }

    public boolean hasService(Object key, String serviceTag) {
        checkNotNull(key, "key");
        checkNotNull(serviceTag, "serviceTag");
        if(!services.containsKey(key)) {
            return false;
        }
        Map<String, Object> serviceMap = services.get(key);
        return serviceMap.containsKey(serviceTag);
    }

    @NonNull
    public <T> T getService(View view, String serviceTag) {
        return getService(Flow.getKey(view), serviceTag);
    }

    @NonNull
    public <T> T getService(Context context, String serviceTag) {
        return getService(Flow.getKey(context), serviceTag);
    }

    @NonNull
    public <T> T getService(Object key, String serviceTag) {
        checkNotNull(key, "key");
        checkNotNull(serviceTag, "serviceTag");
        if(!services.containsKey(key)) {
            throw new NoServiceException(key, serviceTag);
        }
        Map<String, Object> serviceMap = services.get(key);
        if(serviceMap.containsKey(serviceTag)) {
            //noinspection unchecked
            return (T) serviceMap.get(serviceTag);
        } else {
            throw new NoServiceException(key, serviceTag);
        }
    }

    @Nullable
    public Map<String, Object> unbindServices(View view) {
        return unbindServices(Flow.getKey(view));
    }

    @Nullable
    public Map<String, Object> unbindServices(Context context) {
        return unbindServices(Flow.getKey(context));
    }

    @Nullable
    public Map<String, Object> unbindServices(Object key) {
        checkNotNull(key, "key");
        return services.remove(key);
    }
}
