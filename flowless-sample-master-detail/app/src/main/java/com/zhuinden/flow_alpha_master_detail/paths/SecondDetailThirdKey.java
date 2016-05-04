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
public class SecondDetailThirdKey extends DetailKey
        implements Parcelable, IsDetail {
    public SecondDetailThirdKey() {

    }

    protected SecondDetailThirdKey(Parcel in) {
    }

    public static final Creator<SecondDetailThirdKey> CREATOR = new Creator<SecondDetailThirdKey>() {
        @Override
        public SecondDetailThirdKey createFromParcel(Parcel in) {
            return new SecondDetailThirdKey(in);
        }

        @Override
        public SecondDetailThirdKey[] newArray(int size) {
            return new SecondDetailThirdKey[size];
        }
    };

    @Override
    public IsMaster getMaster() {
        return new SecondMasterKey();
    }

    @Override
    public int getLayout() {
        return R.layout.path_second_detail_third;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
