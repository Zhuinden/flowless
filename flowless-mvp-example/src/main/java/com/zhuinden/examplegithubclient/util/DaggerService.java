package com.zhuinden.examplegithubclient.util;

import android.content.Context;

import com.zhuinden.examplegithubclient.presentation.activity.main.MainKey;

import flowless.ServiceProvider;

/**
 * Created by Owner on 2016.12.10.
 */

public class DaggerService {
    public static final String TAG = "DaggerService";

    @SuppressWarnings("unchecked")
    public static <T> T getComponent(Context context) {
        //noinspection ResourceType
        return (T) ServiceProvider.get(context).getService(context, TAG);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getGlobalComponent(Context context) {
        //noinspection ResourceType
        return (T) ServiceProvider.get(context).getService(MainKey.KEY, TAG);
    }
}