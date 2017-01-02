package com.zhuinden.examplegithubclient.util;

/**
 * Created by Owner on 2016.12.10.
 */

public interface Presenter<V extends Presenter.ViewContract> {
    interface ViewContract {
    }

    void attachView(V v);

    void detachView();
}
