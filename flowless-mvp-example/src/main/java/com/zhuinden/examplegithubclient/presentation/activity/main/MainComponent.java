package com.zhuinden.examplegithubclient.presentation.activity.main;

import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.application.injection.modules.InteractorModule;
import com.zhuinden.examplegithubclient.application.injection.modules.OkHttpModule;
import com.zhuinden.examplegithubclient.application.injection.modules.RepositoryModule;
import com.zhuinden.examplegithubclient.application.injection.modules.RetrofitModule;
import com.zhuinden.examplegithubclient.application.injection.modules.ServiceModule;
import com.zhuinden.examplegithubclient.data.model.RepositoryDataSource;
import com.zhuinden.examplegithubclient.data.repository.RepositoryRepository;
import com.zhuinden.examplegithubclient.domain.interactor.GetRepositoriesInteractor;
import com.zhuinden.examplegithubclient.domain.interactor.LoginInteractor;
import com.zhuinden.examplegithubclient.domain.networking.HeaderInterceptor;
import com.zhuinden.examplegithubclient.domain.service.GithubService;
import com.zhuinden.examplegithubclient.domain.service.retrofit.RetrofitGithubService;
import com.zhuinden.examplegithubclient.util.AnnotationCache;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Owner on 2016.12.10.
 */
@ActivityScope
@Component(modules = {OkHttpModule.class, RetrofitModule.class, InteractorModule.class, RepositoryModule.class, ServiceModule.class})
public interface MainComponent {
    HeaderInterceptor headerInterceptor();

    AnnotationCache annotationCache();

    MainPresenter mainPresenter();

    OkHttpClient okHttpClient();

    RetrofitGithubService retrofitGithubService();

    Retrofit retrofit();

    GithubService githubService();

    GetRepositoriesInteractor getRepositoriesInteractor();

    LoginInteractor loginInteractor();

    RepositoryDataSource repositoryDataSource();

    RepositoryRepository repositoryRepository();

    void inject(MainActivity mainActivity);

    void inject(MainView mainView);
}
