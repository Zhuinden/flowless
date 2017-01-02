package com.zhuinden.examplegithubclient.presentation.activity.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.presentation.paths.login.LoginKey;
import com.zhuinden.examplegithubclient.util.AnnotationCache;
import com.zhuinden.examplegithubclient.util.BasePresenter;
import com.zhuinden.examplegithubclient.util.BundleFactory;
import com.zhuinden.examplegithubclient.util.Presenter;

import java.util.Set;

import javax.inject.Inject;

import flowless.Bundleable;
import flowless.Direction;
import flowless.Dispatcher;
import flowless.Flow;
import flowless.History;
import flowless.Traversal;
import flowless.TraversalCallback;
import flowless.preset.DispatcherUtils;

/**
 * Created by Owner on 2016.12.10.
 */
@ActivityScope
public class MainPresenter
        extends BasePresenter<MainPresenter.ViewContract>
        implements Bundleable, Dispatcher {
    @Inject
    AnnotationCache annotationCache;


    @Inject
    public MainPresenter() {
    }

    @StringRes
    int title;
    boolean isDrawerOpen;
    boolean toolbarGoPreviousVisible;
    boolean drawerToggleVisible;
    boolean leftDrawerEnabled;

    public interface ViewContract
            extends Presenter.ViewContract {
        void openDrawer();

        void closeDrawer();

        void setTitle(@StringRes int resourceId);

        void enableLeftDrawer();

        void disableLeftDrawer();

        void hideDrawerToggle();

        void showDrawerToggle();

        void hideToolbarGoPrevious();

        void showToolbarGoPrevious();
    }

    @Override
    protected void initializeView(ViewContract view) {
        view.setTitle(title);
        if(isDrawerOpen) {
            openDrawer();
        } else {
            closeDrawer();
        }
        if(toolbarGoPreviousVisible) {
            showToolbarGoPrevious();
        } else {
            hideToolbarGoPrevious();
        }
        if(drawerToggleVisible) {
            showDrawerToggle();
        } else {
            hideDrawerToggle();
        }
    }

    public void setTitle(int title) {
        this.title = title;
        if(hasView()) {
            getView().setTitle(title);
        }
    }

    public void goToKey(Flow flow, Object newKey) {
        closeDrawer();
        if(newKey instanceof LoginKey) {
            flow.setHistory(History.single(newKey), Direction.FORWARD);
        } else {
            flow.set(newKey);
        }
    }

    public boolean onBackPressed() {
        if(isDrawerOpen) {
            closeDrawer();
            return true;
        }
        return false;
    }

    public void openDrawer() {
        isDrawerOpen = true;
        if(hasView()) {
            getView().openDrawer();
        }
    }

    public void closeDrawer() {
        isDrawerOpen = false;
        if(hasView()) {
            getView().closeDrawer();
        }
    }

    public void toggleDrawer() {
        if(isDrawerOpen) {
            closeDrawer();
        } else {
            openDrawer();
        }
    }

    @Override
    public void dispatch(@NonNull Traversal traversal, @NonNull TraversalCallback callback) {
        Object newKey = DispatcherUtils.getNewKey(traversal);
        setTitle(annotationCache.getTitle(newKey));
        boolean isLeftDrawerEnabled = annotationCache.getLeftDrawerEnabled(newKey);
        if(isLeftDrawerEnabled) {
            enableLeftDrawer();
        } else {
            disableLeftDrawer();
        }

        boolean isToolbarButtonVisible = annotationCache.getToolbarButtonVisibility(newKey);
        Set<Class<?>> parents = annotationCache.getChildOf(newKey);
        if(parents.size() > 0) {
            hideDrawerToggle();
            showToolbarGoPrevious();
        } else {
            if(isToolbarButtonVisible) {
                showDrawerToggle();
            } else {
                hideDrawerToggle();
            }
            hideToolbarGoPrevious();
        }
    }

    private void enableLeftDrawer() {
        this.leftDrawerEnabled = true;
        if(hasView()) {
            view.enableLeftDrawer();
        }
    }

    private void disableLeftDrawer() {
        this.leftDrawerEnabled = false;
        if(hasView()) {
            view.disableLeftDrawer();
        }
    }

    private void hideToolbarGoPrevious() {
        this.toolbarGoPreviousVisible = false;
        if(hasView()) {
            view.hideToolbarGoPrevious();
        }
    }

    private void showToolbarGoPrevious() {
        this.toolbarGoPreviousVisible = true;
        if(hasView()) {
            view.showToolbarGoPrevious();
        }
    }

    private void showDrawerToggle() {
        this.drawerToggleVisible = true;
        if(hasView()) {
            view.showDrawerToggle();
        }
    }

    private void hideDrawerToggle() {
        this.drawerToggleVisible = false;
        if(hasView()) {
            view.hideDrawerToggle();
        }
    }

    @Override
    public Bundle toBundle() {
        Bundle bundle = BundleFactory.create();
        bundle.putBoolean("isDrawerOpen", isDrawerOpen);
        bundle.putInt("title", title);
        bundle.putBoolean("toolbarGoPreviousVisible", toolbarGoPreviousVisible);
        bundle.putBoolean("drawerToggleVisible", drawerToggleVisible);
        bundle.putBoolean("leftDrawerEnabled", leftDrawerEnabled);
        return bundle;
    }

    @Override
    public void fromBundle(@Nullable Bundle bundle) {
        if(bundle != null) {
            isDrawerOpen = bundle.getBoolean("isDrawerOpen");
            title = bundle.getInt("title");
            toolbarGoPreviousVisible = bundle.getBoolean("toolbarGoPreviousVisible");
            drawerToggleVisible = bundle.getBoolean("drawerToggleVisible");
            leftDrawerEnabled = bundle.getBoolean("leftDrawerEnabled");
        }
    }
}
