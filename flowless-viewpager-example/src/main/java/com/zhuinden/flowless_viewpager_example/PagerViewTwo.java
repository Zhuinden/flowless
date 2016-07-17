package com.zhuinden.flowless_viewpager_example;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import flowless.preset.FlowLifecycles;

/**
 * Created by Zhuinden on 2016.07.17..
 */
public class PagerViewTwo extends RelativeLayout implements FlowLifecycles.ViewLifecycleListener {
    private static final String TAG = "PagerViewTwo";

    public PagerViewTwo(Context context) {
        super(context);
    }

    public PagerViewTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PagerViewTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public PagerViewTwo(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void onViewRestored(boolean forcedWithBundler) {
        Log.i(TAG, "View restored in [" + TAG + "]");
    }

    @Override
    public void onViewDestroyed(boolean removedByFlow) {
        Log.i(TAG, "View destroyed in [" + TAG + "]");
    }
}
