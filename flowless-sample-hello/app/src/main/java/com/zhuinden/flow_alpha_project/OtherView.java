package com.zhuinden.flow_alpha_project;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import flow.Bundleable;
import flow.Flow;

/**
 * Created by Zhuinden on 2016.03.02..
 */
public class OtherView
        extends LinearLayout
        implements Bundleable {
    private static final String TAG = "OtherView";

    public OtherView(Context context) {
        super(context);
    }

    public OtherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OtherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public OtherView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    State state;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(!isInEditMode()) {
            OtherKey otherKey = Flow.getKey(this);
            Log.i(TAG, "Obtained key: " + otherKey);
        }
        findViewById(R.id.other_setstate1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                state = State.STATE_1;
                Log.i(TAG, "Set state " + state.name());
            }
        });
        findViewById(R.id.other_setstate2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                state = State.STATE_2;
                Log.i(TAG, "Set state " + state.name());
            }
        });
        findViewById(R.id.other_setstate3).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                state = State.STATE_3;
                Log.i(TAG, "Set state " + state.name());
            }
        });
        if(state == null) {
            state = State.STATE_1;
        }
        Log.i(TAG, "Post-inflation state is " + state);
    }

    @Override
    public Bundle toBundle() {
        Log.i(TAG, "Saving state " + state.name() + " to bundle");
        Bundle bundle = new Bundle();
        bundle.putString("STATE", state.name());
        return bundle;
    }

    @Override
    public void fromBundle(@Nullable Bundle bundle) {
        if(bundle != null) {
            state = State.valueOf(bundle.getString("STATE", State.STATE_1.name()));
            Log.i(TAG, "Restored state " + state.name() + " from bundle");
        } else {
            Log.i(TAG, "Bundle is null on restore");
        }
    }

    enum State {
        STATE_1,
        STATE_2,
        STATE_3;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Log.i(TAG, "Saving view state");
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Log.i(TAG, "Restoring view state");
        super.onRestoreInstanceState(state);
    }
}
