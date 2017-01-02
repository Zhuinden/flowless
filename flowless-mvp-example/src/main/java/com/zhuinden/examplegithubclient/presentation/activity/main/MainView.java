package com.zhuinden.examplegithubclient.presentation.activity.main;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhuinden.examplegithubclient.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flowless.ActivityUtils;
import flowless.preset.FlowLifecycles;

/**
 * Created by Zhuinden on 2016.12.21..
 */

public class MainView
        extends DrawerLayout
        implements MainPresenter.ViewContract, FlowLifecycles.ViewLifecycleListener {
    public MainView(Context context) {
        super(context);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @BindView(R.id.root)
    ViewGroup root;

    @BindView(R.id.toolbar_text)
    TextView toolbarText;

    @BindView(R.id.toolbar)
    ViewGroup toolbar;

    @BindView(R.id.toolbar_drawer_toggle)
    View drawerToggle;

    @BindView(R.id.toolbar_go_previous)
    View toolbarGoPrevious;

    @Inject
    MainPresenter mainPresenter;

    @OnClick(R.id.toolbar_drawer_toggle)
    public void onClickDrawerToggle() {
        mainPresenter.toggleDrawer();
    }

    @OnClick(R.id.toolbar_go_previous)
    public void onClickGoPrevious() {
        ActivityUtils.getActivity(getContext()).onBackPressed();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(!isInEditMode()) {
            ButterKnife.bind(this);
        }
    }

    @Override
    public void disableLeftDrawer() {
        setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void enableLeftDrawer() {
        setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void hideDrawerToggle() {
        drawerToggle.setVisibility(View.GONE);
    }

    @Override
    public void showDrawerToggle() {
        drawerToggle.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideToolbarGoPrevious() {
        toolbarGoPrevious.setVisibility(View.GONE);
    }

    @Override
    public void showToolbarGoPrevious() {
        toolbarGoPrevious.setVisibility(View.VISIBLE);
    }

    @Override
    public void openDrawer() {
        openDrawer(Gravity.LEFT);
    }

    @Override
    public void closeDrawer() {
        closeDrawer(Gravity.LEFT);
    }

    @Override
    public void setTitle(@StringRes int title) {
        toolbarText.setText(title);
    }

    public ViewGroup getRoot() {
        return root;
    }

    @Override
    public void onViewRestored() {
        mainPresenter.attachView(this);
    }

    @Override
    public void onViewDestroyed(boolean removedByFlow) {
        mainPresenter.detachView();
    }
}
