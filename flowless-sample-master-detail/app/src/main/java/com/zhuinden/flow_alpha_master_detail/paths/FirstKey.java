package com.zhuinden.flow_alpha_master_detail.paths;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhuinden.flow_alpha_master_detail.IsFullScreen;
import com.zhuinden.flow_alpha_master_detail.R;

import flow.ClassKey;

/**
 * Created by Zhuinden on 2016.04.16..
 */
public class FirstKey extends ClassKey implements Parcelable, IsFullScreen {
    public FirstKey() {
    }

    protected FirstKey(Parcel in) {
    }

    public static final Creator<FirstKey> CREATOR = new Creator<FirstKey>() {
        @Override
        public FirstKey createFromParcel(Parcel in) {
            return new FirstKey(in);
        }

        @Override
        public FirstKey[] newArray(int size) {
            return new FirstKey[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int getLayout() {
        return R.layout.path_first;
    }
}
