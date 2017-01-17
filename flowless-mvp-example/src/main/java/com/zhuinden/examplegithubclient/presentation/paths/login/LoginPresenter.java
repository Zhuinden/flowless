package com.zhuinden.examplegithubclient.presentation.paths.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhuinden.examplegithubclient.application.injection.KeyScope;
import com.zhuinden.examplegithubclient.domain.interactor.LoginInteractor;
import com.zhuinden.examplegithubclient.util.BasePresenter;
import com.zhuinden.examplegithubclient.util.BundleFactory;

import javax.inject.Inject;

import flowless.Bundleable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Zhuinden on 2016.12.18..
 */

@KeyScope(LoginKey.class)
public class LoginPresenter
        extends BasePresenter<LoginPresenter.ViewContract>
        implements Bundleable {

    @Inject
    LoginInteractor loginInteractor;

    @Inject
    public LoginPresenter() {
    }

    public interface ViewContract
            extends BasePresenter.ViewContract {
        void handleLoginSuccess();

        void handleLoginError();

        void setUsername(String username);

        void setPassword(String password);

        void showLoading();

        void hideLoading();
    }

    @Override
    protected void initializeView(ViewContract view) {
        view.setUsername(username);
        view.setPassword(password);
        if(isLoading) {
            view.showLoading();
        } else {
            view.hideLoading();
        }
    }

    String username;
    String password;

    static boolean isLoading;

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void login() {
        showLoading();
        loginInteractor.login(username, password).observeOn(AndroidSchedulers.mainThread()).subscribe(result -> {
            hideLoading();
            if(result) {
                view.handleLoginSuccess(); // can View be null here?
            } else {
                view.handleLoginError();
            }
        });
    }

    private void showLoading() {
        isLoading = true;
        if(hasView()) {
            view.showLoading();
        }
    }

    private void hideLoading() {
        isLoading = false;
        if(hasView()) {
            view.hideLoading();
        }
    }

    @Override
    public Bundle toBundle() {
        Bundle bundle = BundleFactory.create();
        bundle.putString("username", username);
        bundle.putString("password", password);
        //bundle.putBoolean("isLoading", isLoading);
        return bundle;
    }

    @Override
    public void fromBundle(@Nullable Bundle bundle) {
        if(bundle != null) {
            username = bundle.getString("username");
            password = bundle.getString("password");
            //isLoading = bundle.getBoolean("isLoading");
        }
    }
}
