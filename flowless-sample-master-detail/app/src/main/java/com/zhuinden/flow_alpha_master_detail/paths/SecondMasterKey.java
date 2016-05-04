package com.zhuinden.flow_alpha_master_detail.paths;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhuinden.flow_alpha_master_detail.IsMaster;
import com.zhuinden.flow_alpha_master_detail.R;

import flow.ClassKey;

/**
 * Created by Zhuinden on 2016.04.16..
 */
public class SecondMasterKey extends ClassKey implements Parcelable, IsMaster {
    public SecondMasterKey() {
    }

    protected SecondMasterKey(Parcel in) {
    }

    public static final Creator<SecondMasterKey> CREATOR = new Creator<SecondMasterKey>() {
        @Override
        public SecondMasterKey createFromParcel(Parcel in) {
            return new SecondMasterKey(in);
        }

        @Override
        public SecondMasterKey[] newArray(int size) {
            return new SecondMasterKey[size];
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
        return R.layout.path_second_master;
    }
}
