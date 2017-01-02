package com.zhuinden.examplegithubclient.application;

import android.app.Application;

import com.zhuinden.examplegithubclient.util.idlingresource.FlowlessIdlingResource;

import flowless.Flow;

/**
 * Created by Zhuinden on 2016.12.09..
 */

public class CustomApplication extends Application {
    private static CustomApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        Flow.setFlowIdlingResource(FlowlessIdlingResource.INSTANCE);
    }

    public static CustomApplication get() {
        return INSTANCE;
    }
}
