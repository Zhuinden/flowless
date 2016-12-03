package com.zhuinden.flowtransitions;

import android.support.annotation.LayoutRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Zhuinden on 2016.12.03..
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Layout {
    @LayoutRes int value();
}
