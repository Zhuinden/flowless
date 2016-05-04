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
public class SecondDetailView extends LinearLayout {
    public SecondDetailView(Context context) {
        super(context);
    }

    public SecondDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SecondDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public SecondDetailView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.second_detail_button_full_screen).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Flow.get(v).set(new ThirdFullScreenKey());
            }
        });
        findViewById(R.id.second_detail_button_detail).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Flow.get(v).set(new SecondDetailSecondKey());
            }
        });
    }
}
