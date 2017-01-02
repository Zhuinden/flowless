package com.zhuinden.examplegithubclient.presentation.paths.repositorydetails;

import com.zhuinden.examplegithubclient.application.injection.KeyScope;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainComponent;

import dagger.Component;

/**
 * Created by Owner on 2016.12.10.
 */
@KeyScope(RepositoryDetailsKey.class)
@Component(dependencies = MainComponent.class)
public interface RepositoryDetailsComponent {
    RepositoryDetailsPresenter repositoryDetailsPresenter();

    void inject(RepositoryDetailsView repositoryDetailsView);
}
