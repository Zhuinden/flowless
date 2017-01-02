package com.zhuinden.examplegithubclient.data.model;

import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Owner on 2016.12.26.
 */
@ActivityScope
public class RepositoryDataSource
        extends BaseDataSource<Repository> {
    @Inject
    public RepositoryDataSource() {
    }

    private List<Repository> repositories = Collections.synchronizedList(new ArrayList<>());

    @Override
    protected List<Repository> getData() {
        return repositories;
    }
}
