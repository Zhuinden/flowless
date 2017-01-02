package com.zhuinden.examplegithubclient.util;

/**
 * Created by Zhuinden on 2016.12.21..
 */

public class PresenterUtils {
    public static <P extends BasePresenter<V>, V extends BasePresenter.ViewContract> void setView(P presenter, V viewContract) {
        presenter.view = viewContract;
    }
}
