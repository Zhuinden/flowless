package com.zhuinden.examplegithubclient.application.injection.modules;

import com.zhuinden.examplegithubclient.data.repository.GithubRepoRepository;
import com.zhuinden.examplegithubclient.data.repository.impl.GithubRepoRepositoryInMemoryImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zhuinden on 2017.01.02..
 */

@Module
public class RepositoryModule {
    @Provides
    GithubRepoRepository repositoryRepository(GithubRepoRepositoryInMemoryImpl repositoryRepositoryInMemory) {
        return repositoryRepositoryInMemory;
    }
}
