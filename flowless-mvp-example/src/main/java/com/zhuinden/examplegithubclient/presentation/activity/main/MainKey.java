package com.zhuinden.examplegithubclient.presentation.activity.main;

import android.os.Parcel;
import android.os.Parcelable;

import flowless.ClassKey;

/**
 * Created by Owner on 2016.12.10.
 */

public class MainKey
        extends ClassKey
        implements Parcelable {
    public static final MainKey KEY = new MainKey();

    private MainKey() {
    }

    protected MainKey(Parcel in) {
    }

    public static final Creator<MainKey> CREATOR = new Creator<MainKey>() {
        @Override
        public MainKey createFromParcel(Parcel in) {
            return new MainKey(in);
        }

        @Override
        public MainKey[] newArray(int size) {
            return new MainKey[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
