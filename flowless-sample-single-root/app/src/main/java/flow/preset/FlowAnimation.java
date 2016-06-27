package flow.preset;

import android.animation.Animator;
import android.support.annotation.Nullable;
import android.view.View;

import java.io.Serializable;

import flow.Direction;

/**
 * Created by Zhuinden on 2016.06.27..
 */
public abstract class FlowAnimation implements Serializable {
    public static FlowAnimation NONE = new FlowAnimation() {
        @Nullable
        @Override
        public Animator createAnimation(View previousView, View newView, Direction direction) {
            return null;
        }

        @Override
        public boolean showChildOnTopWhenAdded(Direction direction) {
            return true;
        }
    };

    @Nullable
    public abstract Animator createAnimation(View previousView, View newView, Direction direction);

    public abstract boolean showChildOnTopWhenAdded(Direction direction);

    @Override
    public boolean equals(Object object) {
        return object instanceof FlowAnimation || this == object;
    }

    @Override
    public int hashCode() {
        return FlowAnimation.class.getName().hashCode();
    }
}
