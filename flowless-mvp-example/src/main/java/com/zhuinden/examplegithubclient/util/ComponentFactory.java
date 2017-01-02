package com.zhuinden.examplegithubclient.util;

import android.content.Context;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Owner on 2016.12.10.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentFactory {
    Class<? extends FactoryMethod<?>> value();

    interface FactoryMethod<T> {
        T createComponent(Context context);
    }
}
