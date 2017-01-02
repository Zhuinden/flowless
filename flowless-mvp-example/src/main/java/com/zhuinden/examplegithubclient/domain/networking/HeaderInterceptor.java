package com.zhuinden.examplegithubclient.domain.networking;

import com.zhuinden.examplegithubclient.application.injection.ActivityScope;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Owner on 2016.12.10.
 */
@ActivityScope
public class HeaderInterceptor
        implements Interceptor {
    @Inject
    public HeaderInterceptor() {
    }

    @Override
    public Response intercept(Chain chain)
            throws IOException {
        return chain.proceed( //
                chain.request().newBuilder() //
                        .addHeader("Accept", "application/vnd.github.v3+json") //
                        .build()); //
    }
}
