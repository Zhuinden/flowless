package com.zhuinden.flow_alpha_master_detail.paths;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.zhuinden.flow_alpha_master_detail.R;

import flow.Flow;

/**
 * Created by Zhuinden on 2016.04.16..
 */
public class SecondDetailSecondView extends LinearLayout {
    public SecondDetailSecondView(Context context) {
        super(context);
    }

    public SecondDetailSecondView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SecondDetailSecondView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public SecondDetailSecondView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.second_detail_second_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Flow.get(v).set(new SecondDetailThirdKey());
            }
        });
    }
}
