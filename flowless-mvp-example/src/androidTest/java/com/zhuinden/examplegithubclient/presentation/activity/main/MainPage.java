package com.zhuinden.examplegithubclient.presentation.activity.main;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;

import com.zhuinden.examplegithubclient.R;

/**
 * Created by Zhuinden on 2016.12.19..
 */

public class MainPage {
    public ViewInteraction drawerLayout() {
        return Espresso.onView(ViewMatchers.withId(R.id.drawer_layout));
    }

    public ViewInteraction root() {
        return Espresso.onView(ViewMatchers.withId(R.id.root));
    }

    public void checkRootChildIs(Class<? extends View> clazz) {
        root().check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.isAssignableFrom(clazz))));
    }

    public ViewInteraction hiddenToolbar() {
        return Espresso.onView(ViewMatchers.withId(R.id.hidden_toolbar));
    }

    public ViewInteraction toolbarText() {
        return Espresso.onView(ViewMatchers.withId(R.id.toolbar_text));
    }

    public ViewInteraction toolbar() {
        return Espresso.onView(ViewMatchers.withId(R.id.toolbar));
    }

    public ViewInteraction toolbarDrawerToggle() {
        return Espresso.onView(ViewMatchers.withId(R.id.toolbar_drawer_toggle));
    }

    public ViewInteraction toolbarGoPrevious() {
        return Espresso.onView(ViewMatchers.withId(R.id.toolbar_go_previous));
    }

    public void clickDrawerToggle() {
        toolbarDrawerToggle().perform(ViewActions.click());
    }

    public void clickGoPrevious() {
        toolbarGoPrevious().perform(ViewActions.click());
    }
}
