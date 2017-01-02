package com.zhuinden.examplegithubclient.presentation.paths.repositories;


import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.zhuinden.examplegithubclient.presentation.activity.main.MainActivity;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainPage;
import com.zhuinden.examplegithubclient.util.FlowViewAssertions;
import com.zhuinden.examplegithubclient.util.idlingresource.EspressoIdlingResource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import flowless.Direction;
import flowless.Flow;
import flowless.History;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RepositoriesInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class,
            true,
            false);

    MainPage mainPage;
    RepositoriesPage repositoriesPage;

    @Before
    public void setup() {
        Espresso.registerIdlingResources(EspressoIdlingResource.getIdlingResource());
        mainPage = new MainPage();
        repositoriesPage = new RepositoriesPage();
        mainActivityActivityTestRule.launchActivity(null);
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MainActivity mainActivity = mainActivityActivityTestRule.getActivity();
        instrumentation.runOnMainSync(() -> {
            Flow.get(mainActivity.getBaseContext()).setHistory(History.single(RepositoriesKey.create()), Direction.REPLACE);
        });
    }

    @Test
    public void assertRepositoriesViewIsActive() {
        mainPage.checkRootChildIs(RepositoriesView.class);
    }

    @Test
    public void assertRepositoriesViewHasRepositoriesKey() {
        repositoriesPage.repositoriesView().check(FlowViewAssertions.hasKey(RepositoriesKey.create()));
    }
}
