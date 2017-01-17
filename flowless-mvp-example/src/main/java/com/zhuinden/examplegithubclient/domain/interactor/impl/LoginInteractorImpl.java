package com.zhuinden.examplegithubclient.domain.interactor.impl;

import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.domain.interactor.LoginInteractor;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Zhuinden on 2016.12.18..
 */

@ActivityScope
public class LoginInteractorImpl
        implements LoginInteractor {
    @Inject
    public LoginInteractorImpl() {
    }

    @Override
    public Single<Boolean> login(String username, String password) {
        return Single.just(true).delay(3250, TimeUnit.MILLISECONDS);
    }
}