package com.zhuinden.flow_alpha_master_detail.paths;

import com.google.auto.value.AutoValue;
import com.zhuinden.flow_alpha_master_detail.IsDetail;
import com.zhuinden.flow_alpha_master_detail.IsMaster;
import com.zhuinden.flow_alpha_master_detail.R;

import flowless.preset.FlowAnimation;
import flowless.preset.LayoutPath;

/**
 * Created by Zhuinden on 2016.04.16..
 */
@AutoValue
public abstract class SecondDetailThirdKey implements LayoutPath, IsDetail {
    @Override
    public IsMaster getMaster() {
        return SecondMasterKey.create();
    }

    public static SecondDetailThirdKey create() {
        return new AutoValue_SecondDetailThirdKey(R.layout.path_second_detail_third, FlowAnimation.SEGUE);
    }
}
