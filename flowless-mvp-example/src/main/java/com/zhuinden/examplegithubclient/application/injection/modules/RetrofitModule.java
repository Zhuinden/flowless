package com.zhuinden.examplegithubclient.application.injection.modules;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.domain.service.retrofit.RetrofitGithubService;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Owner on 2016.12.10.
 */

@Module
public class RetrofitModule {
    @Provides
    @ActivityScope
    Retrofit retrofit(OkHttpClient okHttpClient, RxJava2CallAdapterFactory rxJava2CallAdapterFactory) {
        return new Retrofit.Builder().addConverterFactory(LoganSquareConverterFactory.create()) //
                .baseUrl("https://api.github.com/") //
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .client(okHttpClient) //
                .build();
    }

    @Provides
    @ActivityScope
    RxJava2CallAdapterFactory rxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
    }

    @Provides
    @ActivityScope
    RetrofitGithubService retrofitGithubService(Retrofit retrofit) {
        return retrofit.create(RetrofitGithubService.class);
    }
}
