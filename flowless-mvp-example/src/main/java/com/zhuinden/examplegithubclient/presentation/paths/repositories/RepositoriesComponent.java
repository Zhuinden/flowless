package com.zhuinden.examplegithubclient.presentation.paths.repositories;

import com.zhuinden.examplegithubclient.application.injection.KeyScope;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainComponent;

import dagger.Component;

/**
 * Created by Owner on 2016.12.10.
 */
@KeyScope(RepositoriesKey.class)
@Component(dependencies = MainComponent.class)
public interface RepositoriesComponent {
    RepositoriesPresenter repositoriesPresenter();

    void inject(RepositoriesView repositoriesView);

    void inject(RepositoriesAdapter repositoriesAdapter);

    void inject(RepositoriesAdapter.ViewHolder repositoriesViewHolder);
}
