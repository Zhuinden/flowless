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
public class FourthDetailKey extends DetailKey
        implements IsDetail, Parcelable {
    public FourthDetailKey() {
    }

    protected FourthDetailKey(Parcel in) {
    }

    public static final Creator<FourthDetailKey> CREATOR = new Creator<FourthDetailKey>() {
        @Override
        public FourthDetailKey createFromParcel(Parcel in) {
            return new FourthDetailKey(in);
        }

        @Override
        public FourthDetailKey[] newArray(int size) {
            return new FourthDetailKey[size];
        }
    };

    @Override
    public IsMaster getMaster() {
        return new FourthMasterKey();
    }

    @Override
    public int getLayout() {
        return R.layout.path_fourth_detail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
