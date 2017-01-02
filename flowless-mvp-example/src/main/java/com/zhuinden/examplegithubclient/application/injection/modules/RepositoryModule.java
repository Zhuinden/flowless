package com.zhuinden.examplegithubclient.application.injection.modules;

import com.zhuinden.examplegithubclient.data.repository.RepositoryRepository;
import com.zhuinden.examplegithubclient.data.repository.impl.RepositoryRepositoryInMemoryImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Zhuinden on 2017.01.02..
 */

@Module
public class RepositoryModule {
    @Provides
    RepositoryRepository repositoryRepository(RepositoryRepositoryInMemoryImpl repositoryRepositoryInMemory) {
        return repositoryRepositoryInMemory;
    }
}
