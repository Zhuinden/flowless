package com.zhuinden.examplegithubclient.presentation.paths.login;

import android.os.Bundle;

import com.zhuinden.examplegithubclient.domain.interactor.LoginInteractor;
import com.zhuinden.examplegithubclient.util.BundleFactory;
import com.zhuinden.examplegithubclient.util.BundleFactoryConfig;
import com.zhuinden.examplegithubclient.util.PresenterUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Zhuinden on 2016.12.19..
 */
public class LoginPresenterTest {
    LoginPresenter loginPresenter;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    LoginPresenter.ViewContract viewContract;

    @Mock
    LoginInteractor loginInteractor;

    @Before
    public void init() {
        loginPresenter = new LoginPresenter();
        // BoltsConfig.configureMocks();
        BundleFactoryConfig.setProvider(() -> Mockito.mock(Bundle.class));
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
    }

    @Test
    public void initializeViewWhileLoading()
            throws Exception {
        // given
        loginPresenter.username = "Hello";
        loginPresenter.password = "World";
        LoginPresenter.isLoading = true;

        // when
        loginPresenter.attachView(viewContract);

        // then
        Mockito.verify(viewContract).setUsername("Hello");
        Mockito.verify(viewContract).setPassword("World");
        Mockito.verify(viewContract).showLoading();
    }

    @Test
    public void initializeViewWhileNotLoading()
            throws Exception {
        // given
        loginPresenter.username = "Hello";
        loginPresenter.password = "World";
        LoginPresenter.isLoading = false;

        // when
        loginPresenter.attachView(viewContract);

        // then
        Mockito.verify(viewContract).setUsername("Hello");
        Mockito.verify(viewContract).setPassword("World");
        Mockito.verify(viewContract).hideLoading();
    }

    @Test
    public void updateUsername()
            throws Exception {
        // given
        loginPresenter.username = "";

        // when
        loginPresenter.updateUsername("Hello");

        // then
        assertThat(loginPresenter.username).isEqualTo("Hello");
    }

    @Test
    public void updatePassword()
            throws Exception {
        // given
        loginPresenter.password = "";

        // when
        loginPresenter.updatePassword("World");

        // then
        assertThat(loginPresenter.password).isEqualTo("World");
    }

    @Test
    public void loginCallsInteractor()
            throws Exception {
        // given
        loginPresenter.username = "Hello";
        loginPresenter.password = "World";
        loginPresenter.loginInteractor = loginInteractor;
        PresenterUtils.setView(loginPresenter, viewContract);

        Single<Boolean> task = Mockito.mock(Single.class);
        Mockito.when(loginInteractor.login("Hello", "World")).thenReturn(task);

        // when
        loginPresenter.login();

        // then
        Mockito.verify(loginInteractor).login("Hello", "World");
    }

    @Test
    public void loginSuccess()
            throws Exception {
        // given
        loginPresenter.username = "Hello";
        loginPresenter.password = "World";
        loginPresenter.loginInteractor = (username, password) -> Single.just(true);
        PresenterUtils.setView(loginPresenter, viewContract);

        // when
        loginPresenter.login();

        // then
        Mockito.verify(viewContract).showLoading();
        Mockito.verify(viewContract, Mockito.atLeastOnce()).hideLoading();
        Mockito.verify(viewContract).handleLoginSuccess();
    }

    @Test
    public void loginFailure()
            throws Exception {
        // given
        loginPresenter.username = "Hello";
        loginPresenter.password = "World";
        loginPresenter.loginInteractor = (username, password) -> Single.just(false);
        PresenterUtils.setView(loginPresenter, viewContract);

        // when
        loginPresenter.login();

        // then
        Mockito.verify(viewContract).showLoading();
        Mockito.verify(viewContract).hideLoading();
        Mockito.verify(viewContract).handleLoginError();
    }

    @Test
    public void toBundle()
            throws Exception {
        // given
        loginPresenter.username = "Hello";
        loginPresenter.password = "World";

        // when
        Bundle bundle = loginPresenter.toBundle();

        // then
        Mockito.verify(bundle).putString("username", "Hello");
        Mockito.verify(bundle).putString("password", "World");
        Mockito.verifyNoMoreInteractions(bundle);
    }

    @Test
    public void fromBundle()
            throws Exception {
        // given
        loginPresenter.username = "";
        loginPresenter.password = "";

        Bundle bundle = BundleFactory.create();
        Mockito.when(bundle.getString("username")).thenReturn("Hello");
        Mockito.when(bundle.getString("password")).thenReturn("World");

        // when
        loginPresenter.fromBundle(bundle);

        // then
        assertThat(loginPresenter.username).isEqualTo("Hello");
        assertThat(loginPresenter.password).isEqualTo("World");
    }
}