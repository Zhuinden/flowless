package com.zhuinden.examplegithubclient.domain.service.impl;

import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.domain.data.response.organization.Organization;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;
import com.zhuinden.examplegithubclient.domain.service.GithubService;
import com.zhuinden.examplegithubclient.domain.service.retrofit.RetrofitGithubService;

import java.util.List;

import javax.inject.Inject;

import bolts.Task;

/**
 * Created by Owner on 2016.12.10.
 */
@ActivityScope
public class GithubServiceImpl
        implements GithubService {
    private RetrofitGithubService retrofitGithubService;

    @Inject
    public GithubServiceImpl(RetrofitGithubService retrofitGithubService) {
        this.retrofitGithubService = retrofitGithubService;
    }

    @Override
    public Task<List<Organization>> getOrganizations(String user) {
        return retrofitGithubService.getOrganizations(user);
    }

    @Override
    public Task<List<Repository>> getRepositories(String user, int page) {
        return retrofitGithubService.getRepositories(user, page);
    }
}
