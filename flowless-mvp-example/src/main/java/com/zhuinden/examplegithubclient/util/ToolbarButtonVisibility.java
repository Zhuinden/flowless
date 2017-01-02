package com.zhuinden.examplegithubclient.util;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Zhuinden on 2016.12.18..
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ToolbarButtonVisibility {
    boolean value() default true;
}
