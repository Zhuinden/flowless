package com.zhuinden.examplegithubclient.presentation.paths.login;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.zhuinden.examplegithubclient.presentation.activity.main.MainActivity;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainPage;
import com.zhuinden.examplegithubclient.presentation.paths.repositories.RepositoriesView;
import com.zhuinden.examplegithubclient.util.DaggerService;
import com.zhuinden.examplegithubclient.util.FlowViewAssertions;
import com.zhuinden.examplegithubclient.util.conditionwatcher.ConditionWatcher;
import com.zhuinden.examplegithubclient.util.idlingresource.EspressoIdlingResource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import flowless.Direction;
import flowless.Flow;
import flowless.History;
import flowless.ServiceProvider;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class,
            true,
            false);

    MainPage mainPage = new MainPage();
    LoginPage loginPage = new LoginPage();

    LoginPresenter loginPresenter;

    @Before
    public void setup() {
        Espresso.registerIdlingResources(EspressoIdlingResource.getIdlingResource());
        mainActivityActivityTestRule.launchActivity(null);
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MainActivity mainActivity = mainActivityActivityTestRule.getActivity();
        instrumentation.runOnMainSync(() -> {
            Flow.get(mainActivity.getBaseContext()).setHistory(History.single(LoginKey.create()), Direction.REPLACE);
        });
        ServiceProvider serviceProvider = ServiceProvider.get(mainActivity.getBaseContext());
        LoginComponent loginComponent = serviceProvider.getService(LoginKey.create(), DaggerService.TAG);
        loginPresenter = loginComponent.loginPresenter();
    }

    @Test
    public void assertLoginViewIsActive() {
        mainPage.checkRootChildIs(LoginView.class);
    }

    @Test
    public void assertLoginViewHasLoginKey() {
        loginPage.loginView().check(FlowViewAssertions.hasKey(LoginKey.create()));
    }

    @Test
    public void assertGoesToRepositoryAfterSuccessfulLogin()
            throws Exception {
        loginPage.username().perform(ViewActions.typeText("hello"));
        assertThat(loginPresenter.username).isEqualTo("hello");
        loginPage.password().perform(ViewActions.typeText("world"));
        assertThat(loginPresenter.password).isEqualTo("world");

        loginPage.loginButton().perform(ViewActions.click());
        ConditionWatcher.waitForCondition(new LoginWaitForDialogInstruction(loginPresenter));
        mainPage.checkRootChildIs(RepositoriesView.class);
    }
}
