package com.zhuinden.examplegithubclient.presentation.paths.repositories;

import com.zhuinden.examplegithubclient.application.BoltsExecutors;
import com.zhuinden.examplegithubclient.application.injection.KeyScope;
import com.zhuinden.examplegithubclient.data.model.RepositoryDataSource;
import com.zhuinden.examplegithubclient.data.repository.RepositoryRepository;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;
import com.zhuinden.examplegithubclient.domain.interactor.GetRepositoriesInteractor;
import com.zhuinden.examplegithubclient.util.BasePresenter;
import com.zhuinden.examplegithubclient.util.Presenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Zhuinden on 2016.12.18..
 */

@KeyScope(RepositoriesKey.class)
public class RepositoriesPresenter
        extends BasePresenter<RepositoriesPresenter.ViewContract> {
    static final String REPO_NAME = "square";

    @Inject
    GetRepositoriesInteractor getRepositoriesInteractor;

    @Inject
    RepositoryRepository repositoryRepository;

    @Inject
    public RepositoriesPresenter() {
    }

    List<Repository> repositories;

    RepositoryDataSource.Unbinder unbinder;

    @Override
    protected void onAttach() {
        unbinder = repositoryRepository.subscribe( //
                newRepositories -> {
                    RepositoriesPresenter.this.repositories = newRepositories;
                    updateRepositoriesInView();
                });
    }

    @Override
    protected void onDetach() {
        unbinder.unbind();
    }

    public interface ViewContract
            extends Presenter.ViewContract {
        void updateRepositories(List<Repository> repositories);

        void openRepository(String url);
    }

    int currentPage = 1;

    boolean isDownloading;
    boolean downloadedAll;

    public boolean didDownloadAll() {
        return downloadedAll;
    }

    private void downloadPage() {
        if(!downloadedAll) {
            isDownloading = true;
            getRepositoriesInteractor.getRepositories(REPO_NAME, currentPage).continueWith(task -> {
                isDownloading = false;
                List<Repository> repositories = task.getResult();
                if(repositories.size() <= 0) {
                    downloadedAll = true;
                } else {
                    currentPage++;
                }
                return null;
            }, BoltsExecutors.UI_THREAD);
        }
    }

    @Override
    protected void initializeView(ViewContract view) {
        if(repositories == null || repositories.isEmpty()) {
            downloadPage();
        } else {
            updateRepositoriesInView();
        }
    }

    private void updateRepositoriesInView() {
        if(hasView()) {
            view.updateRepositories(repositories);
        }
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void openRepository(Repository repository) {
        view.openRepository(repository.getUrl());
    }

    public void downloadMore() {
        if(!isDownloading) {
            downloadPage();
        }
    }
}
