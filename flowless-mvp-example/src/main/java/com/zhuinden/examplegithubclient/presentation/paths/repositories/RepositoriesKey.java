package com.zhuinden.examplegithubclient.presentation.paths.repositories;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.util.ComponentFactory;
import com.zhuinden.examplegithubclient.util.Layout;
import com.zhuinden.examplegithubclient.util.Title;

/**
 * Created by Zhuinden on 2016.12.10..
 */

@AutoValue
@Title(R.string.title_repositories)
@Layout(R.layout.path_repositories)
@ComponentFactory(RepositoriesComponentFactory.class)
public abstract class RepositoriesKey
        implements Parcelable {
    public static RepositoriesKey create() {
        return new AutoValue_RepositoriesKey();
    }
}
