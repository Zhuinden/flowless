package com.zhuinden.examplegithubclient.data.repository;

import com.zhuinden.examplegithubclient.domain.data.response.repositories.GithubRepo;

/**
 * Created by Zhuinden on 2017.01.02..
 */

public interface GithubRepoRepository
        extends Repository<GithubRepo, Integer> {
    GithubRepo findByUrl(String url);
}
