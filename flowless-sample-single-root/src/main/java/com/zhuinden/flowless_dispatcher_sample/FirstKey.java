package com.zhuinden.flowless_dispatcher_sample;

import com.google.auto.value.AutoValue;
import com.zhuinden.flowless_dispatcher_sample.extracted.FlowAnimation;
import com.zhuinden.flowless_dispatcher_sample.extracted.LayoutKey;

/**
 * Created by Zhuinden on 2016.06.28..
 */
@AutoValue
public abstract class FirstKey
        implements LayoutKey {
    public static FirstKey create() {
        return new AutoValue_FirstKey(R.layout.path_first, FlowAnimation.NONE);
    }
}
