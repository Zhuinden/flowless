package com.zhuinden.examplegithubclient.presentation.activity.main;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.zhuinden.examplegithubclient.presentation.paths.login.LoginView;
import com.zhuinden.examplegithubclient.util.DaggerService;
import com.zhuinden.examplegithubclient.util.FlowViewAssertions;
import com.zhuinden.examplegithubclient.util.idlingresource.EspressoIdlingResource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class,
            true,
            false);

    MainPage mainPage;

    @Before
    public void setup() {
        Espresso.registerIdlingResources(EspressoIdlingResource.getIdlingResource());
        mainPage = new MainPage();
        mainActivityActivityTestRule.launchActivity(null);

    }

    @Test
    public void useAppContext()
            throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertThat(appContext.getPackageName()).isEqualTo("com.zhuinden.examplegithubclient");
    }

    @Test
    public void assertHiddenToolbarIsNotVisible() {
        mainPage.hiddenToolbar().check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void assertToolbarIsVisible() {
        mainPage.toolbar().check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void assertDefaultViewIsLogin() {
        mainPage.checkRootChildIs(LoginView.class);
    }

    @Test
    public void assertDaggerServiceExists() {
        mainPage.root().check(FlowViewAssertions.hasFlowService(DaggerService.TAG));
    }

    @Test
    public void assertDoesNotHaveNonExistentService() {
        mainPage.root().check(FlowViewAssertions.doesNotHaveFlowService("BLAH"));
    }

    @Test
    public void assertMainHasMainKey() {
        mainPage.root().check(FlowViewAssertions.hasKey(MainKey.KEY));
    }
//
//    @Test
//    public void startSecondActivity() {
//        Espresso.onView(ViewMatchers.withId(R.id.main_hello_world)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.hello_world)));
//        Espresso.onView(ViewMatchers.withId(R.id.main_start_next)).perform(ViewActions.click());
//        Espresso.onView(ViewMatchers.withId(R.id.second_root)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
//    }
}