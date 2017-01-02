package com.zhuinden.examplegithubclient.util;


import android.support.annotation.LayoutRes;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Zhuinden on 2016.12.03..
 */

@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Layout {
    @LayoutRes int value();
}