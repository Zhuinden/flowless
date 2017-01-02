package com.zhuinden.examplegithubclient.util;

import android.support.annotation.StringRes;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Zhuinden on 2016.12.10..
 */

@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Title {
    @StringRes int value();
}
