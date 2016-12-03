package com.zhuinden.flowless_dispatcher_sample.extracted;

import android.os.Parcelable;
import android.support.annotation.LayoutRes;

/**
 * Created by Zhuinden on 2016.06.27..
 */
public interface LayoutKey
        extends Parcelable {
    @LayoutRes
    int layout();

    FlowAnimation animation();
}
