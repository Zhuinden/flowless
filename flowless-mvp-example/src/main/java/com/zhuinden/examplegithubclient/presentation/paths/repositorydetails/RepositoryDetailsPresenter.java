package com.zhuinden.examplegithubclient.presentation.paths.repositorydetails;

import com.zhuinden.examplegithubclient.application.injection.KeyScope;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.GithubRepo;
import com.zhuinden.examplegithubclient.util.BasePresenter;
import com.zhuinden.examplegithubclient.util.Presenter;

import javax.inject.Inject;

/**
 * Created by Zhuinden on 2016.12.19..
 */

@KeyScope(RepositoryDetailsKey.class)
public class RepositoryDetailsPresenter
        extends BasePresenter<RepositoryDetailsPresenter.ViewContract> {
    public interface ViewContract
            extends Presenter.ViewContract {
        GithubRepo getSelectedGithubRepo();

        void setupView(GithubRepo githubRepo);
    }

    @Inject
    public RepositoryDetailsPresenter() {
    }

    GithubRepo selectedGithubRepo;

    @Override
    protected void initializeView(ViewContract view) {
        selectedGithubRepo = view.getSelectedGithubRepo();
        if(selectedGithubRepo != null) { // proper persistence would fix this across process death
            view.setupView(selectedGithubRepo);
        }
    }
}
