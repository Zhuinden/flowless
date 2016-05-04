package com.zhuinden.flow_alpha_project;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zhuinden on 2016.03.02..
 */
public class OtherKey
        extends LayoutClassKey //equals, hashcode based on class of key
        implements Parcelable {
    public OtherKey() {
    }

    protected OtherKey(Parcel in) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<OtherKey> CREATOR = new Creator<OtherKey>() {
        @Override
        public OtherKey createFromParcel(Parcel in) {
            return new OtherKey(in);
        }

        @Override
        public OtherKey[] newArray(int size) {
            return new OtherKey[size];
        }
    };

    @Override
    public int getLayout() {
        return R.layout.path_other;
    }
}