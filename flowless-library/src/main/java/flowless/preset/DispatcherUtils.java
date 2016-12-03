package flowless.preset;

import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import flowless.Bundleable;
import flowless.Direction;
import flowless.Flow;
import flowless.History;
import flowless.State;
import flowless.Traversal;
import flowless.TraversalCallback;

/**
 * Created by Zhuinden on 2016.07.01..
 */
public class DispatcherUtils {
    private DispatcherUtils() {
    }

    public static boolean isPreviousKeySameAsNewKey(History origin, History destination) {
        return origin != null && origin.top() != null && origin.top().equals(destination.top());
    }

    public static <T> T getNewKey(Traversal traversal) {
        return traversal.destination.top();
    }

    public static <T> T getPreviousKey(Traversal traversal) {
        T previousKey = null;
        if(traversal.origin != null) {
            previousKey = traversal.origin.top();
        }
        return previousKey;
    }

    private static void persistViewToState(Traversal traversal, View view) {
        if(view != null) {
            if(view != null && view instanceof FlowLifecycles.ViewStatePersistenceListener) {
                ((FlowLifecycles.ViewStatePersistenceListener) view).preSaveViewState(null);
            }
            if(Flow.getKey(view.getContext()) != null) {
                State outgoingState = traversal.getState(Flow.getKey(view.getContext()));
                if(outgoingState != null) {
                    outgoingState.save(view);
                    if(view instanceof Bundleable) {
                        outgoingState.setBundle(((Bundleable) view).toBundle());
                    }
                }
            }
        }
    }

    public static void persistViewToStateWithoutNotifyRemoval(Traversal traversal, View view) {
        persistViewToState(traversal, view);
    }

    public static void persistViewToStateAndNotifyRemoval(Traversal traversal, View view) {
        persistViewToState(traversal, view);
        notifyViewForFlowRemoval(view);
    }

    public static void restoreViewFromState(Traversal traversal, View view) {
        if(view != null) {
            if(Flow.getKey(view.getContext()) != null) {
                State incomingState = traversal.getState(Flow.getKey(view.getContext()));
                if(incomingState != null) {
                    incomingState.restore(view);
                    if(view instanceof Bundleable) {
                        ((Bundleable) view).fromBundle(incomingState.getBundle());
                    }
                }
            }
            if(view instanceof FlowLifecycles.ViewLifecycleListener) {
                ((FlowLifecycles.ViewLifecycleListener) view).onViewRestored(false);
            }
        }
    }

    public static void notifyViewForFlowRemoval(View previousView) {
        if(previousView != null && previousView instanceof FlowLifecycles.ViewLifecycleListener) {
            ((FlowLifecycles.ViewLifecycleListener) previousView).onViewDestroyed(true);
        }
    }
}
