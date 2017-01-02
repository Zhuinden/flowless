package com.zhuinden.examplegithubclient.application.injection.modules;

import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.domain.networking.HeaderInterceptor;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by Owner on 2016.12.10.
 */
@Module
public class OkHttpModule {
    @Provides
    @ActivityScope
    OkHttpClient okHttpClient(HeaderInterceptor headerInterceptor) {
        return new OkHttpClient.Builder() //
                .addInterceptor(headerInterceptor) //
                .build();
    }
}
