package com.zhuinden.examplegithubclient.presentation.activity.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.application.injection.config.MainComponentConfig;
import com.zhuinden.examplegithubclient.presentation.paths.login.LoginKey;
import com.zhuinden.examplegithubclient.util.DaggerService;
import com.zhuinden.examplegithubclient.util.FlowlessActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import flowless.Flow;
import flowless.KeyManager;
import flowless.ServiceProvider;
import flowless.State;
import flowless.Traversal;
import flowless.TraversalCallback;
import flowless.preset.FlowLifecycleProvider;

public class MainActivity
        extends FlowlessActivity {
    @BindView(R.id.drawer_layout)
    MainView mainView;

    @BindView(R.id.hidden_toolbar)
    Toolbar hiddenToolbar;

    @Inject
    MainPresenter mainPresenter;

    ActionBarDrawerToggle actionBarDrawerToggle;

    private boolean didInject = false;

    private void injectServices() {
        if(didInject) {
            return;
        }
        didInject = true;
        Flow flow = Flow.get(getBaseContext());
        ServiceProvider serviceProvider = ServiceProvider.get(getBaseContext());
        MainKey mainKey = Flow.getKey(getBaseContext());
        MainComponent mainComponent;
        if(!serviceProvider.hasService(mainKey, DaggerService.TAG)) {
            mainComponent = MainComponentConfig.create(flow);
            serviceProvider.bindService(mainKey, DaggerService.TAG, mainComponent);
        } else {
            mainComponent = DaggerService.getGlobalComponent(getBaseContext());
        }
        mainComponent.inject(this);
        KeyManager keyManager = KeyManager.get(getBaseContext());
        State state = keyManager.getState(mainKey);
        mainPresenter.fromBundle(state.getBundle());

        //
        mainComponent.inject(mainView);
    }

    @Override
    protected Object getGlobalKey() {
        return MainKey.KEY;
    }

    @Override
    protected Object getDefaultKey() {
        return LoginKey.create();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(hiddenToolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mainView, hiddenToolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mainPresenter.openDrawer();
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mainPresenter.closeDrawer();
                supportInvalidateOptionsMenu();
            }
        };
        mainView.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);

        transitionDispatcher.getRootHolder().setRoot(mainView.getRoot());

        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        injectServices();
        FlowLifecycleProvider.onViewRestored(mainView);
    }

    @Override
    protected void onDestroy() {
        FlowLifecycleProvider.onViewDestroyed(mainView, false);
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(mainPresenter.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        KeyManager keyManager = KeyManager.get(getBaseContext());
        State state = keyManager.getState(Flow.getKey(getBaseContext()));
        state.setBundle(mainPresenter.toBundle());
        super.onSaveInstanceState(outState);
    }


    @Override
    public void dispatch(@NonNull Traversal traversal, @NonNull TraversalCallback callback) {
        injectServices();

        mainPresenter.dispatch(traversal, callback);

        transitionDispatcher.dispatch(traversal, callback);
    }

    @Override
    public Object getSystemService(String name) {
        try {
            ServiceProvider serviceProvider = ServiceProvider.get(getBaseContext());
            if(serviceProvider.hasService(MainKey.KEY, name)) {
                return serviceProvider.getService(MainKey.KEY, name);
            }
        } catch(IllegalStateException e) { // ServiceProvider and Flow are not initialized before onPostCreate
        }
        return super.getSystemService(name);
    }
}
