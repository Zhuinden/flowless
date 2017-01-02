package com.zhuinden.examplegithubclient.util;

import com.zhuinden.examplegithubclient.application.BoltsExecutors;

import java.util.concurrent.Executor;

/**
 * Created by Zhuinden on 2016.12.21..
 */

public class BoltsConfig {
    private BoltsConfig() {

    }

    public static void configureMocks() {
        Executor executor = Runnable::run;
        BoltsExecutors.UI_THREAD = executor;
        BoltsExecutors.BACKGROUND_THREAD = executor;
    }
}
