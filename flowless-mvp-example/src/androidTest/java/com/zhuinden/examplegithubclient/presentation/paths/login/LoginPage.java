package com.zhuinden.examplegithubclient.presentation.paths.login;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;

import com.zhuinden.examplegithubclient.R;

/**
 * Created by Zhuinden on 2016.12.19..
 */

public class LoginPage {
    public ViewInteraction loginView() {
        return Espresso.onView(ViewMatchers.isAssignableFrom(LoginView.class));
    }

    public ViewInteraction username() {
        return Espresso.onView(ViewMatchers.withId(R.id.login_username));
    }

    public ViewInteraction password() {
        return Espresso.onView(ViewMatchers.withId(R.id.login_password));
    }

    public ViewInteraction loginButton() {
        return Espresso.onView(ViewMatchers.withId(R.id.login_login));
    }
}
