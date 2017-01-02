package com.zhuinden.examplegithubclient.domain.interactor;

import bolts.Task;

/**
 * Created by Zhuinden on 2016.12.18..
 */

public interface LoginInteractor {
    Task<Boolean> login(String username, String password);
}
