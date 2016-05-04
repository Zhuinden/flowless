package com.zhuinden.flow_alpha_master_detail.paths;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhuinden.flow_alpha_master_detail.IsMaster;
import com.zhuinden.flow_alpha_master_detail.R;

import flow.ClassKey;

/**
 * Created by Zhuinden on 2016.04.16..
 */
public class FourthMasterKey extends ClassKey implements Parcelable, IsMaster {
    public FourthMasterKey() {
    }

    protected FourthMasterKey(Parcel in) {
    }

    public static final Creator<FourthMasterKey> CREATOR = new Creator<FourthMasterKey>() {
        @Override
        public FourthMasterKey createFromParcel(Parcel in) {
            return new FourthMasterKey(in);
        }

        @Override
        public FourthMasterKey[] newArray(int size) {
            return new FourthMasterKey[size];
        }
    };

    @Override
    public int getLayout() {
        return R.layout.path_fourth_master;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
