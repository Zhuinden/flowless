package com.zhuinden.examplegithubclient.presentation.paths.repositories;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;

/**
 * Created by Zhuinden on 2016.12.19..
 */

public class RepositoriesPage {
    public ViewInteraction repositoriesView() {
        return Espresso.onView(ViewMatchers.isAssignableFrom(RepositoriesView.class));
    }
}
