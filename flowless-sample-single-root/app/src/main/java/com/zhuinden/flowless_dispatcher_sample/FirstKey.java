package com.zhuinden.flowless_dispatcher_sample;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import flow.preset.FlowAnimation;
import flow.preset.LayoutPath;

/**
 * Created by Zhuinden on 2016.06.28..
 */
@AutoValue
public abstract class FirstKey implements LayoutPath {
    public static FirstKey create() {
        return new AutoValue_FirstKey(R.layout.path_first, FlowAnimation.NONE);
    }
}
