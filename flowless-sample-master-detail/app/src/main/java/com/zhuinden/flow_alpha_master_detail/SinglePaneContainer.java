package com.zhuinden.flow_alpha_master_detail;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Zhuinden on 2016.04.16..
 */
public class SinglePaneContainer extends FrameLayout {
    public SinglePaneContainer(Context context) {
        super(context);
    }

    public SinglePaneContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SinglePaneContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public SinglePaneContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
