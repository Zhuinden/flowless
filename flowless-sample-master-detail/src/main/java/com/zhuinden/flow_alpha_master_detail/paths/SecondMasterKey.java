package com.zhuinden.flow_alpha_master_detail.paths;

import com.google.auto.value.AutoValue;
import com.zhuinden.flow_alpha_master_detail.IsMaster;
import com.zhuinden.flow_alpha_master_detail.R;

import flowless.preset.FlowAnimation;
import flowless.preset.LayoutKey;

/**
 * Created by Zhuinden on 2016.04.16..
 */
@AutoValue
public abstract class SecondMasterKey implements LayoutKey, IsMaster {
    public static SecondMasterKey create() {
        return new AutoValue_SecondMasterKey(R.layout.path_second_master, FlowAnimation.SEGUE);
    }
}
