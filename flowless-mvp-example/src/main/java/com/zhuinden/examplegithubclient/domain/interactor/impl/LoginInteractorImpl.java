package com.zhuinden.examplegithubclient.domain.interactor.impl;

import com.zhuinden.examplegithubclient.application.BoltsExecutors;
import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.domain.interactor.LoginInteractor;

import javax.inject.Inject;

import bolts.Task;

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
    public Task<Boolean> login(String username, String password) {
        return Task.call(() -> {
            Thread.sleep(3250); // simulate login
            return true;
        }, BoltsExecutors.BACKGROUND_THREAD);
    }
}
