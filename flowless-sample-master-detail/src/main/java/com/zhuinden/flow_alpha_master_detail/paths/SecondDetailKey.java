package com.zhuinden.flow_alpha_master_detail.paths;

import com.google.auto.value.AutoValue;
import com.zhuinden.flow_alpha_master_detail.IsDetail;
import com.zhuinden.flow_alpha_master_detail.IsMaster;
import com.zhuinden.flow_alpha_master_detail.R;
import com.zhuinden.flow_alpha_master_detail.extracted.FlowAnimation;
import com.zhuinden.flow_alpha_master_detail.extracted.LayoutKey;

/**
 * Created by Zhuinden on 2016.04.16..
 */
@AutoValue
public abstract class SecondDetailKey
        implements LayoutKey, IsDetail {
    @Override
    public IsMaster getMaster() {
        return SecondMasterKey.create();
    }

    public static SecondDetailKey create() {
        return new AutoValue_SecondDetailKey(R.layout.path_second_detail, FlowAnimation.SEGUE);
    }
}
