package com.zhuinden.flow_alpha_master_detail.paths;

import com.google.auto.value.AutoValue;
import com.zhuinden.flow_alpha_master_detail.IsDetail;
import com.zhuinden.flow_alpha_master_detail.IsMaster;
import com.zhuinden.flow_alpha_master_detail.R;

import flowless.preset.FlowAnimation;
import flowless.preset.LayoutKey;

/**
 * Created by Zhuinden on 2016.04.16..
 */
@AutoValue
public abstract class SecondDetailSecondKey implements LayoutKey, IsDetail {
    @Override
    public IsMaster getMaster() {
        return SecondMasterKey.create();
    }

    public static SecondDetailSecondKey create() {
        return new AutoValue_SecondDetailSecondKey(R.layout.path_second_detail_second, FlowAnimation.SEGUE);
    }
}
