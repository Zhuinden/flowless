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

    public static Context createContextForKey(Traversal traversal, LayoutKey newKey, Context baseContext) {
        return traversal.createContext(newKey, baseContext);
    }

    public static LayoutKey selectAnimatedKey(Direction direction, LayoutKey previousKey, LayoutKey newKey) {
        return direction == Direction.BACKWARD ? (previousKey != null ? previousKey : newKey) : newKey;
    }

    public static boolean isPreviousKeySameAsNewKey(History origin, History destination) {
        return origin != null && origin.top() != null && origin.top().equals(destination.top());
    }

    public static void persistViewToState(Traversal traversal, View view) {
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

    public static void addViewToGroupForKey(Direction direction, View view, ViewGroup root, LayoutKey animatedKey) {
        if(animatedKey.animation() != null && !animatedKey.animation().showChildOnTopWhenAdded(direction)) {
            root.addView(view, 0);
        } else {
            root.addView(view);
        }
    }

    public static void removeViewFromGroup(View previousView, ViewGroup root) {
        if(previousView != null) {
            root.removeView(previousView);
        }
    }

    public static LayoutKey getNewKey(Traversal traversal) {
        return traversal.destination.top();
    }

    public static LayoutKey getPreviousKey(Traversal traversal) {
        LayoutKey previousKey = null;
        if(traversal.origin != null) {
            previousKey = traversal.origin.top();
        }
        return previousKey;
    }

    public static View createViewFromKey(Traversal traversal, LayoutKey newKey, ViewGroup root, Context baseContext) {
        Context internalContext = DispatcherUtils.createContextForKey(traversal, newKey, baseContext);
        LayoutInflater layoutInflater = LayoutInflater.from(internalContext);
        final View newView = layoutInflater.inflate(newKey.layout(), root, false);
        return newView;
    }

    public static Animator createAnimatorForViews(LayoutKey animatedKey, View previousView, View newView, Direction direction) {
        if(previousView == null) {
            return null;
        }
        if(animatedKey.animation() != null) {
            return animatedKey.animation().createAnimation(previousView, newView, direction);
        }
        return null;
    }

    public static void finishTraversal(TraversalCallback callback) {
        callback.onTraversalCompleted();
    }

    public static void notifyViewForFlowRemoval(View previousView) {
        if(previousView != null && previousView instanceof FlowLifecycles.ViewLifecycleListener) {
            ((FlowLifecycles.ViewLifecycleListener) previousView).onViewDestroyed(true);
        }
    }
}
