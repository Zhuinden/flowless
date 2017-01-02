package com.zhuinden.examplegithubclient.data.repository.impl;

import com.zhuinden.examplegithubclient.application.injection.ActivityScope;
import com.zhuinden.examplegithubclient.data.model.DataSource;
import com.zhuinden.examplegithubclient.data.model.RepositoryDataSource;
import com.zhuinden.examplegithubclient.data.repository.RepositoryRepository;
import com.zhuinden.examplegithubclient.domain.data.response.repositories.Repository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Zhuinden on 2017.01.02..
 */
@ActivityScope
public class RepositoryRepositoryInMemoryImpl
        implements RepositoryRepository {
    @Inject
    RepositoryDataSource repositoryDataSource;

    @Inject
    public RepositoryRepositoryInMemoryImpl() {
    }

    @Override
    public DataSource.Unbinder subscribe(DataSource.ChangeListener<Repository> changeListener) {
        return repositoryDataSource.registerChangeListener(changeListener);
    }

    @Override
    public Repository findOne(Integer id) {
        return repositoryDataSource.search(repositories -> {
            if(repositories != null) {
                for(Repository repository : repositories) {
                    if(repository.getId().equals(id)) {
                        return repository;
                    }
                }
            }
            return null;
        });
    }

    @Override
    public List<Repository> findAll() {
        return repositoryDataSource.search(immutableRepositories -> immutableRepositories);
    }

    @Override
    public Repository saveOrUpdate(final Repository repository) {
        if(repository == null) {
            throw new NullPointerException();
        }
        return repositoryDataSource.modify(mutableRepositories -> {
            mutableRepositories.add(repository);
            return repository;
        });
    }

    @Override
    public List<Repository> saveOrUpdate(List<Repository> items) {
        if(items == null) {
            throw new NullPointerException();
        }
        return repositoryDataSource.modify(mutableRepositories -> {
            mutableRepositories.addAll(items);
            return items;
        });
    }

    @Override
    public Repository delete(Repository repository) {
        if(repository == null) {
            return null;
        }
        return repositoryDataSource.modify(mutableRepositories -> delete(repository.getId()));
    }

    @Override
    public Repository delete(Integer id) {
        if(id == null) {
            return null;
        }
        return repositoryDataSource.modify(mutableRepositories -> {
            Repository _repository = findOne(id);
            mutableRepositories.remove(_repository);
            return _repository;
        });
    }

    @Override
    public List<Repository> delete(List<Repository> items) {
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
    public Repository findByUrl(String url) {
        return repositoryDataSource.search(repositories -> {
            if(repositories != null) {
                for(Repository repository : repositories) {
                    if(repository.getUrl().equals(url)) {
                        return repository;
                    }
                }
            }
            return null;
        });
    }
}
