package com.zhuinden.examplegithubclient.domain.service;

import com.zhuinden.examplegithubclient.domain.data.response.organization.Organization;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.GithubRepo;

import java.util.List;

import io.reactivex.Single;


/**
 * Created by Owner on 2016.12.10.
 */

public interface GithubService {
    Single<List<Organization>> getOrganizations(String user);

    Single<List<GithubRepo>> getRepositories(String user, int page);
}
