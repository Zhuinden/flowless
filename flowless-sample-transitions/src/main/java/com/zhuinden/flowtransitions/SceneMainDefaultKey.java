package com.zhuinden.flowtransitions;

import android.os.Parcel;
import android.os.Parcelable;

import flowless.ClassKey;

/**
 * Created by Zhuinden on 2016.12.03..
 */

@Layout(R.layout.scene_main_default)
public class SceneMainDefaultKey
        extends ClassKey
        implements Parcelable {
    public static SceneMainDefaultKey create() {
        return new SceneMainDefaultKey();
    }

    private SceneMainDefaultKey() {
    }

    protected SceneMainDefaultKey(Parcel in) {
    }

    public static final Creator<SceneMainDefaultKey> CREATOR = new Creator<SceneMainDefaultKey>() {
        @Override
        public SceneMainDefaultKey createFromParcel(Parcel in) {
            return new SceneMainDefaultKey(in);
        }

        @Override
        public SceneMainDefaultKey[] newArray(int size) {
            return new SceneMainDefaultKey[size];
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
