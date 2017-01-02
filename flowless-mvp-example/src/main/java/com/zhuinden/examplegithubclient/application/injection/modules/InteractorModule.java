package com.zhuinden.examplegithubclient.application.injection.modules;

import com.zhuinden.examplegithubclient.domain.interactor.GetRepositoriesInteractor;
import com.zhuinden.examplegithubclient.domain.interactor.LoginInteractor;
import com.zhuinden.examplegithubclient.domain.interactor.impl.GetRepositoriesInteractorImpl;
import com.zhuinden.examplegithubclient.domain.interactor.impl.LoginInteractorImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Owner on 2016.12.10.
 */

@Module
public class InteractorModule {
    @Provides
    GetRepositoriesInteractor getRepositoriesInteractor(GetRepositoriesInteractorImpl getRepositoriesInteractor) {
        return getRepositoriesInteractor;
    }

    @Provides
    LoginInteractor loginInteractor(LoginInteractorImpl loginInteractor) {
        return loginInteractor;
    }
}
