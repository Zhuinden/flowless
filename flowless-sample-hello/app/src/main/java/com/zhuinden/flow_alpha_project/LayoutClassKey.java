package com.zhuinden.flow_alpha_project;

import android.support.annotation.LayoutRes;

import flow.ClassKey;

/**
 * Created by Zhuinden on 2016.03.02..
 */
public abstract class LayoutClassKey
        extends ClassKey {
    public abstract
    @LayoutRes
    int getLayout();
}
