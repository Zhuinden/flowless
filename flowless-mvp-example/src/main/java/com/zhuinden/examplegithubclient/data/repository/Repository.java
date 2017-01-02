package com.zhuinden.examplegithubclient.data.repository;

import com.zhuinden.examplegithubclient.data.model.DataSource;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zhuinden on 2017.01.02..
 */

interface Repository<M, ID extends Serializable> {
    DataSource.Unbinder subscribe(DataSource.ChangeListener<M> changeListener);

    M findOne(ID id);

    List<M> findAll();

    M saveOrUpdate(M m);

    List<M> saveOrUpdate(List<M> items);

    M delete(M m);

    M delete(ID id);

    List<M> delete(List<M> items);

    long count();
}
