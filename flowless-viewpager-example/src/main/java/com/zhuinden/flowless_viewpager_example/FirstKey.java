package com.zhuinden.flowless_viewpager_example;

import com.google.auto.value.AutoValue;

import flowless.preset.FlowAnimation;
import flowless.preset.LayoutKey;

/**
 * Created by Zhuinden on 2016.07.17..
 */
@AutoValue
public abstract class FirstKey
        implements LayoutKey {
    public static FirstKey create() {
        return new AutoValue_FirstKey(R.layout.path_first, FlowAnimation.SEGUE);
    }
}
