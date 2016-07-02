package com.zhuinden.flow_alpha_master_detail.paths;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Zhuinden on 2016.04.16..
 */
public class FourthDetailSecondView extends LinearLayout {
    public FourthDetailSecondView(Context context) {
        super(context);
    }

    public FourthDetailSecondView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FourthDetailSecondView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public FourthDetailSecondView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
