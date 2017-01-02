package com.zhuinden.examplegithubclient.data.model;

import com.zhuinden.examplegithubclient.application.BoltsExecutors;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Zhuinden on 2017.01.02..
 */

public abstract class BaseDataSource<M>
        implements DataSource<M> {
    protected abstract List<M> getData();

    private Set<ChangeListener<M>> changeListenerSet = new HashSet<>();

    public Unbinder registerChangeListener(final ChangeListener<M> changeListener) {
        changeListenerSet.add(changeListener);
        changeListener.onChange(Collections.unmodifiableList(getData()));
        return () -> changeListenerSet.remove(changeListener);
    }

    private void notifyChangeListeners() {
        List<M> unmodifiable = Collections.unmodifiableList(getData());
        for(ChangeListener<M> changeListener : changeListenerSet) {
            changeListener.onChange(unmodifiable);
        }
    }

    public <R> R modify(Modify<R, M> modify) {
        R m = modify.modify(getData());
        BoltsExecutors.UI_THREAD.execute(this::notifyChangeListeners);
        return m;
    }

    public <R> R search(Search<R, M> search) {
        return search.search(Collections.unmodifiableList(getData()));
    }
}
