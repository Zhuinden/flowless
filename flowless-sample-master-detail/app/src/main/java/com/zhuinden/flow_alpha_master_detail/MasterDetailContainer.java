package com.zhuinden.flow_alpha_master_detail;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Zhuinden on 2016.04.16..
 */
public class MasterDetailContainer extends LinearLayout {
    public MasterDetailContainer(Context context) {
        super(context);
    }

    public MasterDetailContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MasterDetailContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public MasterDetailContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    RelativeLayout masterContainer;
    RelativeLayout detailContainer;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        masterContainer = (RelativeLayout)findViewById(R.id.master_container);
        detailContainer = (RelativeLayout)findViewById(R.id.detail_container);
    }

    public RelativeLayout getMasterContainer() {
        return masterContainer;
    }

    public RelativeLayout getDetailContainer() {
        return detailContainer;
    }
}
