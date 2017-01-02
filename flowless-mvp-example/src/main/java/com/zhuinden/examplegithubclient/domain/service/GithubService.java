package com.zhuinden.examplegithubclient.domain.service;

import com.zhuinden.examplegithubclient.domain.data.response.organization.Organization;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;

import java.util.List;

import bolts.Task;

/**
 * Created by Owner on 2016.12.10.
 */

public interface GithubService {
    Task<List<Organization>> getOrganizations(String user);

    Task<List<Repository>> getRepositories(String user, int page);
}
