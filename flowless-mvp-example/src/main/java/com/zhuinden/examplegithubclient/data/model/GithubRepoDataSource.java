package com.zhuinden.examplegithubclient.data.model;

import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.GithubRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Owner on 2016.12.26.
 */
@ActivityScope
public class GithubRepoDataSource
        extends BaseDataSource<GithubRepo> {
    @Inject
    public GithubRepoDataSource() {
    }

    private List<GithubRepo> repositories = Collections.synchronizedList(new ArrayList<>());

    @Override
    protected List<GithubRepo> getData() {
        return repositories;
    }
}
