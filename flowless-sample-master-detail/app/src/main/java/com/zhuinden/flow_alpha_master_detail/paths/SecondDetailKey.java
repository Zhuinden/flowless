package com.zhuinden.flow_alpha_master_detail.paths;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhuinden.flow_alpha_master_detail.DetailKey;
import com.zhuinden.flow_alpha_master_detail.IsDetail;
import com.zhuinden.flow_alpha_master_detail.IsMaster;
import com.zhuinden.flow_alpha_master_detail.R;

/**
 * Created by Zhuinden on 2016.04.16..
 */
public class SecondDetailKey extends DetailKey
        implements Parcelable, IsDetail {
    public SecondDetailKey() {
    }

    protected SecondDetailKey(Parcel in) {
    }

    public static final Creator<SecondDetailKey> CREATOR = new Creator<SecondDetailKey>() {
        @Override
        public SecondDetailKey createFromParcel(Parcel in) {
            return new SecondDetailKey(in);
        }

        @Override
        public SecondDetailKey[] newArray(int size) {
            return new SecondDetailKey[size];
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
    public IsMaster getMaster() {
        return new SecondMasterKey();
    }

    @Override
    public int getLayout() {
        return R.layout.path_second_detail;
    }
}
