package com.zhuinden.examplegithubclient.domain.interactor;


import io.reactivex.Single;

/**
 * Created by Zhuinden on 2016.12.18..
 */

public interface LoginInteractor {
    Single<Boolean> login(String username, String password);
}
