package com.zhuinden.examplegithubclient.data.repository;

/**
 * Created by Zhuinden on 2017.01.02..
 */

public interface RepositoryRepository
        extends Repository<com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository, Integer> {
    com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository findByUrl(String url);
}
