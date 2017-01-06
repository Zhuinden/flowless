package com.zhuinden.examplegithubclient.data.repository.impl;

import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.data.model.DataSource;
import com.zhuinden.examplegithubclient.data.model.RepositoryDataSource;
import com.zhuinden.examplegithubclient.data.repository.GithubRepoRepository;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.GithubRepo;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Zhuinden on 2017.01.02..
 */
@ActivityScope
public class GithubRepoRepositoryInMemoryImpl
        implements GithubRepoRepository {
    @Inject
    RepositoryDataSource repositoryDataSource;

    @Inject
    public GithubRepoRepositoryInMemoryImpl() {
    }

    @Override
    public DataSource.Unbinder subscribe(DataSource.ChangeListener<GithubRepo> changeListener) {
        return repositoryDataSource.registerChangeListener(changeListener);
    }

    @Override
    public GithubRepo findOne(Integer id) {
        return repositoryDataSource.search(repositories -> {
            if(repositories != null) {
                for(GithubRepo githubRepo : repositories) {
                    if(githubRepo.getId().equals(id)) {
                        return githubRepo;
                    }
                }
            }
            return null;
        });
    }

    @Override
    public List<GithubRepo> findAll() {
        return repositoryDataSource.search(immutableRepositories -> immutableRepositories);
    }

    @Override
    public GithubRepo saveOrUpdate(final GithubRepo githubRepo) {
        if(githubRepo == null) {
            throw new NullPointerException();
        }
        return repositoryDataSource.modify(mutableRepositories -> {
            mutableRepositories.add(githubRepo);
            return githubRepo;
        });
    }

    @Override
    public List<GithubRepo> saveOrUpdate(List<GithubRepo> items) {
        if(items == null) {
            throw new NullPointerException();
        }
        return repositoryDataSource.modify(mutableRepositories -> {
            mutableRepositories.addAll(items);
            return items;
        });
    }

    @Override
    public GithubRepo delete(GithubRepo githubRepo) {
        if(githubRepo == null) {
            return null;
        }
        return repositoryDataSource.modify(mutableRepositories -> delete(githubRepo.getId()));
    }

    @Override
    public GithubRepo delete(Integer id) {
        if(id == null) {
            return null;
        }
        return repositoryDataSource.modify(mutableRepositories -> {
            GithubRepo _githubRepo = findOne(id);
            mutableRepositories.remove(_githubRepo);
            return _githubRepo;
        });
    }

    @Override
    public List<GithubRepo> delete(List<GithubRepo> items) {
        if(items == null) {
            throw new NullPointerException();
        }
        return repositoryDataSource.modify(mutableRepositories -> {
            mutableRepositories.removeAll(items);
            return items;
        });
    }

    @Override
    public long count() {
        return repositoryDataSource.search(immutableRepositories -> ((Integer) immutableRepositories.size()).longValue());
    }

    @Override
    public GithubRepo findByUrl(String url) {
        return repositoryDataSource.search(repositories -> {
            if(repositories != null) {
                for(GithubRepo githubRepo : repositories) {
                    if(githubRepo.getUrl().equals(url)) {
                        return githubRepo;
                    }
                }
            }
            return null;
        });
    }
}
