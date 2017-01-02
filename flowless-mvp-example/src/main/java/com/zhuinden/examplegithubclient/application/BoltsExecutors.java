package com.zhuinden.examplegithubclient.application;

import java.util.concurrent.Executor;

import bolts.Task;

/**
 * Created by Zhuinden on 2016.12.19..
 */

public class BoltsExecutors {
    public static Executor UI_THREAD = Task.UI_THREAD_EXECUTOR;
    public static Executor BACKGROUND_THREAD = Task.BACKGROUND_EXECUTOR;
}
