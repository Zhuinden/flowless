package com.zhuinden.examplegithubclient.application.injection.modules;

import com.zhuinden.examplegithubclient.domain.service.GithubService;
import com.zhuinden.examplegithubclient.domain.service.impl.GithubServiceImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Owner on 2016.12.10.
 */

@Module
public class ServiceModule {
    @Provides
    GithubService githubService(GithubServiceImpl githubService) {
        return githubService;
    }
}
