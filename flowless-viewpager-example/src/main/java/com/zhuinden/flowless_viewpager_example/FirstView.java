package com.zhuinden.flowless_viewpager_example;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import flowless.preset.FlowLifecycles;

/**
 * Created by Zhuinden on 2016.07.17..
 */
public class FirstView extends LinearLayout implements FlowLifecycles.ViewLifecycleListener {
    private static final String TAG = "FirstView";

    public FirstView(Context context) {
        super(context);
    }

    public FirstView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FirstView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public FirstView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @BindView(R.id.first_viewpager)
    ViewPager viewPager;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        viewPager.setAdapter(new FlowlessPagerAdapter() {
            @Override
            public View getItem(int position) {
                switch(position) {
                    case 0:
                        return LayoutInflater.from(getContext()).inflate(R.layout.pager_view_one, viewPager, false);
                    case 1:
                        return LayoutInflater.from(getContext()).inflate(R.layout.pager_view_two, viewPager, false);
                    case 2:
                        return LayoutInflater.from(getContext()).inflate(R.layout.pager_view_three, viewPager, false);
                    case 3:
                        return LayoutInflater.from(getContext()).inflate(R.layout.pager_view_four, viewPager, false);
                    case 4:
                        return LayoutInflater.from(getContext()).inflate(R.layout.pager_view_five, viewPager, false);
                    default:
                        Log.i(TAG, "Unknown view at position [" + position + "]");
                        throw new IllegalArgumentException("Unknown view at position [" + position + "]");
                }
            }

            @Override
            public int getCount() {
                return 5;
            }
        });
    }

    @Override
    public void onViewRestored() {
        Log.i(TAG, "View restored in [" + TAG + "]");
    }

    @Override
    public void onViewDestroyed(boolean removedByFlow) {
        Log.i(TAG, "View destroyed in [" + TAG + "]");
    }
}
