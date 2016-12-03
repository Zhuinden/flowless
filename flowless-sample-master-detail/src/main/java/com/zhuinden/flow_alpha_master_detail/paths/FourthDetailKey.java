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
public abstract class FourthDetailKey
        implements LayoutKey, IsDetail {
    @Override
    public IsMaster getMaster() {
        return FourthMasterKey.create();
    }

    public static FourthDetailKey create() {
        return new AutoValue_FourthDetailKey(R.layout.path_fourth_detail, FlowAnimation.SEGUE);
    }
}
