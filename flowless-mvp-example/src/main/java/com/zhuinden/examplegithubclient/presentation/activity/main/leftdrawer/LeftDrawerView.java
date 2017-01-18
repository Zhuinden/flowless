package com.zhuinden.examplegithubclient.presentation.activity.main.leftdrawer;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.zhuinden.examplegithubclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhuinden on 2016.12.10..
 */

public class LeftDrawerView
        extends RelativeLayout {
    public LeftDrawerView(Context context) {
        super(context);
        init();
    }

    public LeftDrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LeftDrawerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public LeftDrawerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        if(!isInEditMode()) {
            // .
        }
    }

    @BindView(R.id.left_drawer_recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(!isInEditMode()) {
            ButterKnife.bind(this);
            recyclerView.setAdapter(new LeftDrawerAdapter());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }
    }
}
