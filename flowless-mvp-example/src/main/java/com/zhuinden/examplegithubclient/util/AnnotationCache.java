package com.zhuinden.examplegithubclient.util;

import android.content.Context;

import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainComponent;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by Owner on 2016.12.10.
 */

@ActivityScope
public class AnnotationCache {
    public static final String TAG = "AnnotationCache";

    public static AnnotationCache getCache(Context context) {
        MainComponent mainComponent = DaggerService.getGlobalComponent(context);
        return mainComponent.annotationCache();
    }

    @Inject
    public AnnotationCache() {
    }

    // from flow-sample: https://github.com/Zhuinden/flow-sample/blob/master/src/main/java/com/example/flow/pathview/SimplePathContainer.java#L100-L114
    private final Map<Class, Integer> PATH_LAYOUT_CACHE = new LinkedHashMap<>();
    private final Map<Class, Integer> PATH_TITLE_CACHE = new LinkedHashMap<>();
    private final Map<Class, Class<? extends ComponentFactory.FactoryMethod<?>>> PATH_COMPONENT_FACTORY_CACHE = new LinkedHashMap<>();
    private final Map<Class, Boolean> LEFT_DRAWER_STATE_CACHE = new LinkedHashMap<>();
    private final Map<Class, Boolean> TOOLBAR_BUTTON_STATE_CACHE = new LinkedHashMap<>();
    private final Map<Class, Class<?>[]> IS_CHILD_OF_CACHE = new LinkedHashMap<>();

    // from flow-sample: https://github.com/Zhuinden/flow-sample/blob/master/src/main/java/com/example/flow/pathview/SimplePathContainer.java#L100-L114
    public int getLayout(Object path) {
        return getValueFromAnnotation(PATH_LAYOUT_CACHE, path, Layout.class, false);
    }

    public int getTitle(Object path) {
        return getValueFromAnnotation(PATH_TITLE_CACHE, path, Title.class, false);
    }

    public ComponentFactory.FactoryMethod<?> getComponentFactory(Object path) {
        Class<? extends ComponentFactory.FactoryMethod<?>> value = getValueFromAnnotation(PATH_COMPONENT_FACTORY_CACHE,
                path,
                ComponentFactory.class,
                true);
        try {
            if(value != null) {
                return value.newInstance();
            }
        } catch(InstantiationException e) {
            // shouldn't happen
            e.printStackTrace();
        } catch(IllegalAccessException e) {
            // shouldn't happen
            e.printStackTrace();
        }
        return null;
    }

    public boolean getLeftDrawerEnabled(Object path) {
        Boolean state = getValueFromAnnotation(LEFT_DRAWER_STATE_CACHE, path, LeftDrawerEnabled.class, true);
        return state == null ? true : state;
    }

    public boolean getToolbarButtonVisibility(Object path) {
        Boolean state = getValueFromAnnotation(TOOLBAR_BUTTON_STATE_CACHE, path, ToolbarButtonVisibility.class, true);
        return state == null ? true : state;
    }

    // details, details
    private <T, A> T getValueFromAnnotation(Map<Class, T> cache, Object path, Class<A> annotationClass, boolean optional) {
        Class pathType = path.getClass();
        T value = cache.get(pathType);
        if(value == null) {
            A annotation = (A) pathType.getAnnotation(annotationClass);
            if(annotation == null) {
                if(!optional) {
                    throw new IllegalArgumentException(String.format("@%s annotation not found on class %s",
                            annotationClass.getSimpleName(),
                            pathType.getName()));
                } else {
                    return null;
                }
            }
            try {
                value = (T) annotationClass.getMethod("value").invoke(annotation);
            } catch(IllegalAccessException e) {
                // shouldn't happen
                e.printStackTrace();
            } catch(InvocationTargetException e) {
                // shouldn't happen
                e.printStackTrace();
            } catch(NoSuchMethodException e) {
                // shouldn't happen
                e.printStackTrace();
            }
            cache.put(pathType, value);
        }
        return value;
    }

    public Set<Class<?>> getChildOf(Object path) {
        Class<?>[] value = getValueFromAnnotation(IS_CHILD_OF_CACHE, path, IsChildOf.class, true);
        if(value != null) {
            return new HashSet<>(Arrays.asList(value));
        } else {
            return Collections.emptySet();
        }
    }
}
