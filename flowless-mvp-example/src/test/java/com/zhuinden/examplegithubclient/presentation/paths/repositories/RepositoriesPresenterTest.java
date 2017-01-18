package com.zhuinden.examplegithubclient.presentation.paths.repositories;

import com.zhuinden.examplegithubclient.data.repository.GithubRepoRepository;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.GithubRepo;
import com.zhuinden.examplegithubclient.domain.interactor.GetRepositoriesInteractor;
import com.zhuinden.examplegithubclient.util.PresenterUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Zhuinden on 2016.12.21..
 */
public class RepositoriesPresenterTest {
    RepositoriesPresenter repositoriesPresenter;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    RepositoriesPresenter.ViewContract viewContract;

    @Mock
    GetRepositoriesInteractor getRepositoriesInteractor;

    @Mock
    GithubRepoRepository githubRepoRepository;

    @Before
    public void init() {
        repositoriesPresenter = new RepositoriesPresenter();
        // BoltsConfig.configureMocks();
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
    }

    @Test
    public void didDownloadAllTrue()
            throws Exception {
        repositoriesPresenter.downloadedAll = true;

        assertThat(repositoriesPresenter.didDownloadAll()).isTrue();
    }

    @Test
    public void didDownloadAllFalse()
            throws Exception {
        repositoriesPresenter.downloadedAll = false;

        assertThat(repositoriesPresenter.didDownloadAll()).isFalse();
    }

    @Test
    public void initializeViewNoData()
            throws Exception {
        // given
        repositoriesPresenter.repositories = null;
        repositoriesPresenter.isDownloading = true;
        repositoriesPresenter.downloadedAll = false;
        repositoriesPresenter.currentPage = 1;
        repositoriesPresenter.getRepositoriesInteractor = getRepositoriesInteractor;
        repositoriesPresenter.githubRepoRepository = githubRepoRepository;
        Single<List<GithubRepo>> task = Mockito.mock(Single.class);
        Mockito.when(getRepositoriesInteractor.getRepositories(RepositoriesPresenter.REPO_NAME, 1)).thenReturn(task);


        // when
        repositoriesPresenter.attachView(viewContract);

        // then
        assertThat(repositoriesPresenter.isDownloading).isTrue();
        Mockito.verify(getRepositoriesInteractor).getRepositories(RepositoriesPresenter.REPO_NAME, 1);
    }

    @Test
    public void initializeViewWithData()
            throws Exception {
        // given
        List<GithubRepo> repositories = new ArrayList<GithubRepo>() {{
            add(new GithubRepo());
        }};
        repositoriesPresenter.repositories = repositories;
        repositoriesPresenter.downloadedAll = true;
        repositoriesPresenter.isDownloading = false;
        repositoriesPresenter.currentPage = 2;
        repositoriesPresenter.getRepositoriesInteractor = getRepositoriesInteractor;
        repositoriesPresenter.githubRepoRepository = githubRepoRepository;

        // when
        repositoriesPresenter.attachView(viewContract);

        // then
        assertThat(repositoriesPresenter.isDownloading).isFalse();
        Mockito.verify(getRepositoriesInteractor, Mockito.never()).getRepositories(RepositoriesPresenter.REPO_NAME, 2);
        Mockito.verify(viewContract).updateRepositories(repositories);
    }

    @Test
    public void getRepositories()
            throws Exception {
        // given
        List<GithubRepo> repositories = new ArrayList<GithubRepo>() {{
            add(new GithubRepo());
        }};
        repositoriesPresenter.repositories = repositories;

        // when
        List<GithubRepo> presenterRepos = repositoriesPresenter.getRepositories();

        // then
        assertThat(repositories).isEqualTo(presenterRepos);
    }

    @Test
    public void openRepository()
            throws Exception {
        // given
        GithubRepo githubRepo = new GithubRepo();
        githubRepo.setUrl("blah");
        PresenterUtils.setView(repositoriesPresenter, viewContract);

        // when
        repositoriesPresenter.openRepository(githubRepo);

        // then
        Mockito.verify(viewContract).openRepository("blah");
    }

    @Test
    public void downloadMoreWhileDownloading()
            throws Exception {
        // given
        repositoriesPresenter.currentPage = 1;
        repositoriesPresenter.isDownloading = true;
        repositoriesPresenter.getRepositoriesInteractor = getRepositoriesInteractor;
        PresenterUtils.setView(repositoriesPresenter, viewContract);

        // when
        repositoriesPresenter.downloadMore();

        // then
        Mockito.verify(getRepositoriesInteractor, Mockito.never()).getRepositories(RepositoriesPresenter.REPO_NAME, 1);
    }

    @Test
    public void downloadMoreWhileNotDownloading()
            throws Exception {
        // given
        repositoriesPresenter.currentPage = 1;
        repositoriesPresenter.isDownloading = false;
        repositoriesPresenter.getRepositoriesInteractor = getRepositoriesInteractor;
        PresenterUtils.setView(repositoriesPresenter, viewContract);
        Single<List<GithubRepo>> task = Mockito.mock(Single.class);
        Mockito.when(getRepositoriesInteractor.getRepositories(RepositoriesPresenter.REPO_NAME, 1)).thenReturn(task);

        // when
        repositoriesPresenter.downloadMore();

        // then
        Mockito.verify(getRepositoriesInteractor).getRepositories(RepositoriesPresenter.REPO_NAME, 1);
    }

    // TODO: FIX TEST
//    @Test
//    public void downloadWithNoListSetsListAndUpdates() {
//        // given
//        final List<GithubRepo> repositories = new ArrayList<GithubRepo>() {{
//            add(new GithubRepo());
//        }};
//        repositoriesPresenter.currentPage = 1;
//        repositoriesPresenter.repositories = null;
//        repositoriesPresenter.isDownloading = false;
//        repositoriesPresenter.downloadedAll = false;
//        repositoriesPresenter.getRepositoriesInteractor = (user, page) -> Task.call(() -> repositories);
//        PresenterUtils.setView(repositoriesPresenter, viewContract);
//
//        // when
//        repositoriesPresenter.downloadMore();
//
//        // then
//        assertThat(repositoriesPresenter.currentPage).isEqualTo(2);
//        assertThat(repositoriesPresenter.isDownloading).isFalse();
//        assertThat(repositoriesPresenter.repositories).isEqualTo(repositories);
//        Mockito.verify(viewContract).updateRepositories(repositories);
//    }

    // TODO: FIX TEST
//    @Test
//    public void downloadWithListAddsItemsAndUpdates() {
//        // given
//        final List<GithubRepo> originalRepository = new ArrayList<GithubRepo>() {{
//            add(new GithubRepo());
//        }};
//        final GithubRepo newRepository = new GithubRepo();
//        final List<GithubRepo> newRepositories = new ArrayList<GithubRepo>() {{
//            add(newRepository);
//        }};
//        repositoriesPresenter.currentPage = 2;
//        repositoriesPresenter.repositories = originalRepository;
//        repositoriesPresenter.isDownloading = false;
//        repositoriesPresenter.downloadedAll = false;
//        repositoriesPresenter.getRepositoriesInteractor = (user, page) -> Task.call(() -> newRepositories);
//        PresenterUtils.setView(repositoriesPresenter, viewContract);
//
//        // when
//        repositoriesPresenter.downloadMore();
//
//        // then
//        assertThat(repositoriesPresenter.isDownloading).isFalse();
//        assertThat(repositoriesPresenter.downloadedAll).isFalse();
//        assertThat(repositoriesPresenter.currentPage).isEqualTo(3);
//        assertThat(repositoriesPresenter.repositories).isSameAs(originalRepository);
//        assertThat(repositoriesPresenter.repositories).contains(newRepository);
//        Mockito.verify(viewContract).updateRepositories(originalRepository);
//    }

    // TODO: FIX TEST
//    @Test
//    public void downloadingEmptyListSetsDownloadedAll() {
//        // given
//        final List<GithubRepo> originalRepository = new ArrayList<GithubRepo>() {{
//            add(new GithubRepo());
//        }};
//        final List<GithubRepo> newRepositories = new ArrayList<GithubRepo>();
//        repositoriesPresenter.currentPage = 2;
//        repositoriesPresenter.repositories = originalRepository;
//        repositoriesPresenter.isDownloading = false;
//        repositoriesPresenter.downloadedAll = false;
//        repositoriesPresenter.getRepositoriesInteractor = (user, page) -> Task.call(() -> newRepositories);
//        PresenterUtils.setView(repositoriesPresenter, viewContract);
//
//        // when
//        repositoriesPresenter.downloadMore();
//
//        // then
//        assertThat(repositoriesPresenter.isDownloading).isFalse();
//        assertThat(repositoriesPresenter.downloadedAll).isTrue();
//        assertThat(repositoriesPresenter.currentPage).isEqualTo(2);
//        assertThat(repositoriesPresenter.repositories).isSameAs(originalRepository);
//        Mockito.verify(viewContract).updateRepositories(originalRepository);
//    }

    @Test
    public void doNotDownloadWhenDownloadedAll() {
        // given
        repositoriesPresenter.currentPage = 2;
        repositoriesPresenter.downloadedAll = true;
        repositoriesPresenter.getRepositoriesInteractor = getRepositoriesInteractor;
        PresenterUtils.setView(repositoriesPresenter, viewContract);

        // when
        repositoriesPresenter.downloadMore();

        // then
        Mockito.verify(getRepositoriesInteractor, Mockito.never()).getRepositories(RepositoriesPresenter.REPO_NAME, 2);
    }
}