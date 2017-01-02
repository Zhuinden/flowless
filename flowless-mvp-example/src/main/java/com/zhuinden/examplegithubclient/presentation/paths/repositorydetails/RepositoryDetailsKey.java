package com.zhuinden.examplegithubclient.presentation.paths.repositorydetails;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.presentation.paths.repositories.RepositoriesKey;
import com.zhuinden.examplegithubclient.util.ComponentFactory;
import com.zhuinden.examplegithubclient.util.IsChildOf;
import com.zhuinden.examplegithubclient.util.Layout;
import com.zhuinden.examplegithubclient.util.LeftDrawerEnabled;
import com.zhuinden.examplegithubclient.util.Title;

/**
 * Created by Zhuinden on 2016.12.10..
 */

@AutoValue
@Title(R.string.title_repository_details)
@Layout(R.layout.path_repositorydetails)
@ComponentFactory(RepositoryDetailsComponentFactory.class)
@LeftDrawerEnabled(false)
@IsChildOf(RepositoriesKey.class)
public abstract class RepositoryDetailsKey
        implements Parcelable {
    abstract RepositoriesKey parent();

    abstract String url();

    public static RepositoryDetailsKey create(RepositoriesKey parent, String url) {
        return new AutoValue_RepositoryDetailsKey(parent, url);
    }
}
