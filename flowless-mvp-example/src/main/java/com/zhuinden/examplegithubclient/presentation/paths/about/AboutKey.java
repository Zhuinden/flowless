package com.zhuinden.examplegithubclient.presentation.paths.about;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.util.Layout;
import com.zhuinden.examplegithubclient.util.Title;

/**
 * Created by Zhuinden on 2016.12.10..
 */

@AutoValue
@Title(R.string.title_about)
@Layout(R.layout.path_about)
public abstract class AboutKey
        implements Parcelable {
    public static AboutKey create() {
        return new AutoValue_AboutKey();
    }
}
