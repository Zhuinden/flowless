package com.zhuinden.examplegithubclient.presentation.activity.main.leftdrawer;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.presentation.paths.about.AboutKey;
import com.zhuinden.examplegithubclient.presentation.paths.login.LoginKey;
import com.zhuinden.examplegithubclient.presentation.paths.repositories.RepositoriesKey;

/**
 * Created by Zhuinden on 2016.12.10..
 */

public enum LeftDrawerItems {
    REPOSITORIES(R.string.title_repositories, R.drawable.icon_repositories, RepositoriesKey::create),
    ABOUT(R.string.title_about, R.drawable.icon_about, AboutKey::create),
    LOGOUT(R.string.title_logout, R.drawable.icon_logout, LoginKey::create);

    private final int labelId;
    private final int imageId;

    private final KeyCreator keyCreator;

    private LeftDrawerItems(@StringRes int labelId, @DrawableRes int imageId, KeyCreator keyCreator) {
        this.labelId = labelId;
        this.imageId = imageId;
        this.keyCreator = keyCreator;
    }

    public int getLabelId() {
        return labelId;
    }

    public int getImageId() {
        return imageId;
    }

    public KeyCreator getKeyCreator() {
        return keyCreator;
    }

    static interface KeyCreator {
        public Object createKey();
    }
}
