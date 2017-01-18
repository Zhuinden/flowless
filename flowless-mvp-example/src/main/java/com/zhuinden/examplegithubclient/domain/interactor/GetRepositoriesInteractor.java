package com.zhuinden.examplegithubclient.domain.interactor;

import com.zhuinden.examplegithubclient.domain.data.response.repositories.GithubRepo;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Owner on 2016.12.10.
 */

public interface GetRepositoriesInteractor {
    Single<List<GithubRepo>> getRepositories(String user, int page);
}
