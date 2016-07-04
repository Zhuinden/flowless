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
public abstract class FourthDetailSecondKey implements LayoutKey, IsDetail {
    @Override
    public IsMaster getMaster() {
        return FourthMasterKey.create();
    }

    public static FourthDetailSecondKey create() {
        return new AutoValue_FourthDetailSecondKey(R.layout.path_fourth_detail_second, FlowAnimation.SEGUE);
    }
}
