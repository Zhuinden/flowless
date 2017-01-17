package com.zhuinden.examplegithubclient.domain.service.retrofit;

import com.zhuinden.examplegithubclient.domain.data.response.organization.Organization;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.GithubRepo;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Owner on 2016.12.10.
 */

public interface RetrofitGithubService {
    @GET("orgs/{user}/repos")
    Single<List<Organization>> getOrganizations(@Path("user") String user);

    @GET("users/{user}/repos")
    Single<List<GithubRepo>> getRepositories(@Path("user") String user, @Query("page") int page);
}
