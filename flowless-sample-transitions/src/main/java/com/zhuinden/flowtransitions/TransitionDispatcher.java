package com.zhuinden.flowtransitions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.transitionseverywhere.TransitionManager;

import java.util.LinkedHashMap;
import java.util.Map;

import flowless.Traversal;
import flowless.TraversalCallback;
import flowless.preset.DispatcherUtils;
import flowless.preset.SingleRootDispatcher;

/**
 * Created by Zhuinden on 2016.12.02..
 */

public class TransitionDispatcher extends SingleRootDispatcher {
    @Override
    public void dispatch(@NonNull Traversal traversal, @NonNull TraversalCallback callback) {
        if(DispatcherUtils.isPreviousKeySameAsNewKey(traversal.origin, traversal.destination)) { //short circuit on same key
            callback.onTraversalCompleted();
            return;
        }
        final Object newKey = DispatcherUtils.getNewKey(traversal);
        int newKeyLayout = getLayout(newKey);

        final ViewGroup root = rootHolder.getRoot();
        final View previousView = root.getChildAt(0);
        DispatcherUtils.persistViewToStateAndNotifyRemoval(traversal, previousView);

        Context internalContext = traversal.createContext(newKey, baseContext);
        LayoutInflater layoutInflater = LayoutInflater.from(internalContext);
        final View newView = layoutInflater.inflate(newKeyLayout, root, false);

        DispatcherUtils.restoreViewFromState(traversal, newView);

        TransitionManager.beginDelayedTransition(root);
        if(previousView != null) {
            root.removeView(previousView);
        }
        root.addView(newView);
        callback.onTraversalCompleted();
    }

    // from flow-sample: https://github.com/Zhuinden/flow-sample/blob/master/src/main/java/com/example/flow/pathview/SimplePathContainer.java#L100-L114
    private static final Map<Class, Integer> PATH_LAYOUT_CACHE = new LinkedHashMap<>();

    protected int getLayout(Object path) {
        Class pathType = path.getClass();
        Integer layoutResId = PATH_LAYOUT_CACHE.get(pathType);
        if (layoutResId == null) {
            Layout layout = (Layout) pathType.getAnnotation(Layout.class);
            if (layout == null) {
                throw new IllegalArgumentException(
                        String.format("@%s annotation not found on class %s", Layout.class.getSimpleName(),
                                pathType.getName()));
            }
            layoutResId = layout.value();
            PATH_LAYOUT_CACHE.put(pathType, layoutResId);
        }
        return layoutResId;
    }
}
