package com.zhuinden.examplegithubclient.application.injection.modules;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.zhuinden.examplegithubclient.application.BoltsExecutors;
import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.domain.service.retrofit.RetrofitGithubService;
import com.zhuinden.examplegithubclient.util.bolts.BoltsCallAdapterFactory;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Owner on 2016.12.10.
 */

@Module
public class RetrofitModule {
    @Provides
    @ActivityScope
    Retrofit retrofit(OkHttpClient okHttpClient, BoltsCallAdapterFactory boltsCallAdapterFactory) {
        return new Retrofit.Builder().addConverterFactory(LoganSquareConverterFactory.create()) //
                .baseUrl("https://api.github.com/") //
                .addCallAdapterFactory(boltsCallAdapterFactory)
                .client(okHttpClient) //
                .build();
    }

    @Provides
    @ActivityScope
    BoltsCallAdapterFactory boltsCallAdapterFactory() {
        return BoltsCallAdapterFactory.createWithExecutor(BoltsExecutors.BACKGROUND_THREAD);
    }

    @Provides
    @ActivityScope
    RetrofitGithubService retrofitGithubService(Retrofit retrofit) {
        return retrofit.create(RetrofitGithubService.class);
    }
}
