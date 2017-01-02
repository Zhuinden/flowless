package com.zhuinden.examplegithubclient.domain.service.retrofit;

import com.zhuinden.examplegithubclient.domain.data.response.organization.Organization;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;

import java.util.List;

import bolts.Task;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Owner on 2016.12.10.
 */

public interface RetrofitGithubService {
    @GET("orgs/{user}/repos")
    Task<List<Organization>> getOrganizations(@Path("user") String user);

    @GET("users/{user}/repos")
    Task<List<Repository>> getRepositories(@Path("user") String user, @Query("page") int page);
}
