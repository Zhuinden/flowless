package com.zhuinden.flowless_dispatcher_sample;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Bundleable;
import flow.Flow;
import flow.preset.SingleRootDispatcher;

/**
 * Created by Zhuinden on 2016.06.28..
 */
public class FirstView
        extends RelativeLayout
        implements SingleRootDispatcher.ViewLifecycleListener, Bundleable {
    public FirstView(Context context) {
        super(context);
        init();
    }

    public FirstView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FirstView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public FirstView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    FirstKey firstKey;

    public void init() {
        if(!isInEditMode()) {
            firstKey = Flow.getKey(this);
        }
    }

    @OnClick(R.id.first_button)
    public void firstButtonClick(View view) {
        Flow.get(view).set(SecondKey.create());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public Bundle toBundle() {
        return new Bundle();
    }

    @Override
    public void fromBundle(@Nullable Bundle bundle) {
        if(bundle != null) {
        }
    }

    @Override
    public void onViewRestored() {

    }

    @Override
    public void onViewDestroyed(boolean removedByFlow) {

    }
}
