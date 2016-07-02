package com.zhuinden.flow_alpha_master_detail.paths;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.zhuinden.flow_alpha_master_detail.IsFullScreen;
import com.zhuinden.flow_alpha_master_detail.R;

import flow.ClassKey;
import flow.preset.FlowAnimation;
import flow.preset.LayoutPath;

/**
 * Created by Zhuinden on 2016.04.16..
 */
@AutoValue
public abstract class FirstKey implements LayoutPath, IsFullScreen {
    public static FirstKey create() {
        return new AutoValue_FirstKey(R.layout.path_first, FlowAnimation.SEGUE);
    }
}
