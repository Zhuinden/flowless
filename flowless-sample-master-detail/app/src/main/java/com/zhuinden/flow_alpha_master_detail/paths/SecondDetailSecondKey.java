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
public class SecondDetailSecondKey extends DetailKey
        implements Parcelable, IsDetail {
    public SecondDetailSecondKey() {
    }

    protected SecondDetailSecondKey(Parcel in) {
    }

    public static final Creator<SecondDetailSecondKey> CREATOR = new Creator<SecondDetailSecondKey>() {
        @Override
        public SecondDetailSecondKey createFromParcel(Parcel in) {
            return new SecondDetailSecondKey(in);
        }

        @Override
        public SecondDetailSecondKey[] newArray(int size) {
            return new SecondDetailSecondKey[size];
        }
    };

    @Override
    public IsMaster getMaster() {
        return new SecondMasterKey();
    }

    @Override
    public int getLayout() {
        return R.layout.path_second_detail_second;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
