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
public abstract class ThirdFullScreenKey implements LayoutPath, IsFullScreen {
    public static ThirdFullScreenKey create() {
        return new AutoValue_ThirdFullScreenKey(R.layout.path_third_fullscreen, FlowAnimation.SEGUE);
    }
}
