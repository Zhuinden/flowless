package com.zhuinden.flow_alpha_master_detail.paths;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhuinden.flow_alpha_master_detail.IsFullScreen;
import com.zhuinden.flow_alpha_master_detail.R;

import flow.ClassKey;

/**
 * Created by Zhuinden on 2016.04.16..
 */
public class ThirdFullScreenKey extends ClassKey implements Parcelable, IsFullScreen {
    public ThirdFullScreenKey() {
    }

    protected ThirdFullScreenKey(Parcel in) {
    }

    public static final Creator<ThirdFullScreenKey> CREATOR = new Creator<ThirdFullScreenKey>() {
        @Override
        public ThirdFullScreenKey createFromParcel(Parcel in) {
            return new ThirdFullScreenKey(in);
        }

        @Override
        public ThirdFullScreenKey[] newArray(int size) {
            return new ThirdFullScreenKey[size];
        }
    };

    @Override
    public int getLayout() {
        return R.layout.path_third_fullscreen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
