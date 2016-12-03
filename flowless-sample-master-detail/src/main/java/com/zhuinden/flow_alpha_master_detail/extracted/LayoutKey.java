package com.zhuinden.flow_alpha_master_detail.extracted;

import android.os.Parcelable;
import android.support.annotation.LayoutRes;

/**
 * Created by Zhuinden on 2016.12.03..
 */

public interface LayoutKey
        extends Parcelable {
    @LayoutRes
    int layout();

    FlowAnimation animation();
}
