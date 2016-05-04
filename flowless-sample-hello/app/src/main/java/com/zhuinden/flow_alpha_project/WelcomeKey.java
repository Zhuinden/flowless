package com.zhuinden.flow_alpha_project;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zhuinden on 2016.03.02..
 */
public class WelcomeKey
        extends LayoutClassKey //equals, hashcode based on class of key
        implements Parcelable {
    public WelcomeKey() {
    }

    protected WelcomeKey(Parcel in) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<WelcomeKey> CREATOR = new Creator<WelcomeKey>() {
        @Override
        public WelcomeKey createFromParcel(Parcel in) {
            return new WelcomeKey(in);
        }

        @Override
        public WelcomeKey[] newArray(int size) {
            return new WelcomeKey[size];
        }
    };

    @Override
    public int getLayout() {
        return R.layout.path_welcome;
    }
}
