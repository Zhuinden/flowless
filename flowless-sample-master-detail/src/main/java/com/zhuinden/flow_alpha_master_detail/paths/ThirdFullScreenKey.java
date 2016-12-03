package com.zhuinden.flow_alpha_master_detail.paths;

import com.google.auto.value.AutoValue;
import com.zhuinden.flow_alpha_master_detail.IsFullScreen;
import com.zhuinden.flow_alpha_master_detail.R;
import com.zhuinden.flow_alpha_master_detail.extracted.FlowAnimation;
import com.zhuinden.flow_alpha_master_detail.extracted.LayoutKey;

/**
 * Created by Zhuinden on 2016.04.16..
 */
@AutoValue
public abstract class ThirdFullScreenKey
        implements LayoutKey, IsFullScreen {
    public static ThirdFullScreenKey create() {
        return new AutoValue_ThirdFullScreenKey(R.layout.path_third_fullscreen, FlowAnimation.SEGUE);
    }
}
