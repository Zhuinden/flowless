package com.zhuinden.examplegithubclient.data.model;

import java.util.List;

/**
 * Created by Zhuinden on 2017.01.02..
 */

public interface DataSource<M> {
    interface ChangeListener<M> {
        void onChange(List<M> repositories);
    }

    interface Unbinder {
        void unbind();
    }

    public interface Modify<R, M> {
        R modify(List<M> mutableRepositories);
    }

    public interface Search<R, M> {
        R search(List<M> immutableRepositories);
    }
}
