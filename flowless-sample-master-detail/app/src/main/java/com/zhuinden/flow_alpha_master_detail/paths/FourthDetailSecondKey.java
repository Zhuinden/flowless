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
public class FourthDetailSecondKey extends DetailKey
        implements Parcelable, IsDetail {
    public FourthDetailSecondKey() {
    }

    protected FourthDetailSecondKey(Parcel in) {
    }

    public static final Creator<FourthDetailSecondKey> CREATOR = new Creator<FourthDetailSecondKey>() {
        @Override
        public FourthDetailSecondKey createFromParcel(Parcel in) {
            return new FourthDetailSecondKey(in);
        }

        @Override
        public FourthDetailSecondKey[] newArray(int size) {
            return new FourthDetailSecondKey[size];
        }
    };

    @Override
    public IsMaster getMaster() {
        return new FourthMasterKey();
    }

    @Override
    public int getLayout() {
        return R.layout.path_fourth_detail_second;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
