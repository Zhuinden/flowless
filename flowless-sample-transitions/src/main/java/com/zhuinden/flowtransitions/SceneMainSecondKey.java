package com.zhuinden.flowtransitions;

import android.os.Parcel;
import android.os.Parcelable;

import flowless.ClassKey;

/**
 * Created by Zhuinden on 2016.12.03..
 */

@Layout(R.layout.scene_main_second)
public class SceneMainSecondKey
        extends ClassKey
        implements Parcelable {
    public static SceneMainSecondKey create() {
        return new SceneMainSecondKey();
    }

    private SceneMainSecondKey() {
    }

    protected SceneMainSecondKey(Parcel in) {
    }

    public static final Creator<SceneMainSecondKey> CREATOR = new Creator<SceneMainSecondKey>() {
        @Override
        public SceneMainSecondKey createFromParcel(Parcel in) {
            return new SceneMainSecondKey(in);
        }

        @Override
        public SceneMainSecondKey[] newArray(int size) {
            return new SceneMainSecondKey[size];
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
