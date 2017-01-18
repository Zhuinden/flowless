package com.zhuinden.examplegithubclient.presentation.paths.repositories;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhuinden.examplegithubclient.application.injection.KeyScope;
import com.zhuinden.examplegithubclient.data.model.GithubRepoDataSource;
import com.zhuinden.examplegithubclient.data.repository.GithubRepoRepository;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.GithubRepo;
import com.zhuinden.examplegithubclient.domain.interactor.GetRepositoriesInteractor;
import com.zhuinden.examplegithubclient.util.BasePresenter;
import com.zhuinden.examplegithubclient.util.BundleFactory;
import com.zhuinden.examplegithubclient.util.Presenter;

import java.util.List;

import javax.inject.Inject;

import flowless.Bundleable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Zhuinden on 2016.12.18..
 */

@KeyScope(RepositoriesKey.class)
public class RepositoriesPresenter
        extends BasePresenter<RepositoriesPresenter.ViewContract>
        implements Bundleable {
    static final String REPO_NAME = "square";

    @Inject
    GetRepositoriesInteractor getRepositoriesInteractor;

    @Inject
    GithubRepoRepository githubRepoRepository;

    @Inject
    public RepositoriesPresenter() {
    }

    List<GithubRepo> repositories;

    GithubRepoDataSource.Unbinder unbinder;

    @Override
    protected void onAttach() {
        unbinder = githubRepoRepository.subscribe( //
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
        void updateRepositories(List<GithubRepo> repositories);

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
            getRepositoriesInteractor.getRepositories(REPO_NAME, currentPage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(repositories -> {
                isDownloading = false;
                if(repositories.size() <= 0) {
                    downloadedAll = true;
                } else {
                    currentPage++;
                }
                    });
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

    public List<GithubRepo> getRepositories() {
        return repositories;
    }

    public void openRepository(GithubRepo githubRepo) {
        view.openRepository(githubRepo.getUrl());
    }

    public void downloadMore() {
        if(!isDownloading) {
            downloadPage();
        }
    }

    @Override
    public Bundle toBundle() {
        Bundle bundle = BundleFactory.create();
        bundle.putInt("currentPage", currentPage);
        bundle.putBoolean("downloadedAll", downloadedAll);
        return bundle;
    }

    @Override
    public void fromBundle(@Nullable Bundle bundle) {
        if(bundle != null) {
            currentPage = bundle.getInt("currentPage");
            downloadedAll = bundle.getBoolean("downloadedAll");
        }
    }
}
