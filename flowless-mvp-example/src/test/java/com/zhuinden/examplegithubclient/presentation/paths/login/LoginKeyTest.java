package com.zhuinden.examplegithubclient.presentation.paths.login;

import android.os.Parcelable;

import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.util.ComponentFactory;
import com.zhuinden.examplegithubclient.util.Layout;
import com.zhuinden.examplegithubclient.util.LeftDrawerEnabled;
import com.zhuinden.examplegithubclient.util.Title;
import com.zhuinden.examplegithubclient.util.ToolbarButtonVisibility;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Zhuinden on 2016.12.20..
 */
public class LoginKeyTest {
    private <T> T getValueFromAnnotation(Class<?> annotationClass, Annotation annotation)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (T) annotationClass.getMethod("value").invoke(annotation);
    }

    @Test
    public void hasAnnotationTitle()
            throws Exception {
        assertThat(LoginKey.class.getAnnotation(Title.class)).isNotNull();
        assertThat((int) getValueFromAnnotation(Title.class, LoginKey.class.getAnnotation(Title.class))).isEqualTo(R.string.title_login);
    }

    @Test
    public void hasAnnotationLayout()
            throws Exception {
        assertThat(LoginKey.class.getAnnotation(Layout.class)).isNotNull();
        assertThat((int) getValueFromAnnotation(Layout.class, LoginKey.class.getAnnotation(Layout.class))).isEqualTo(R.layout.path_login);
    }

    @Test
    public void hasAnnotationComponentFactory()
            throws Exception {
        assertThat(LoginKey.class.getAnnotation(ComponentFactory.class)).isNotNull();
        assertThat((Class) getValueFromAnnotation(ComponentFactory.class, LoginKey.class.getAnnotation(ComponentFactory.class))).isEqualTo(
                LoginComponentFactory.class);
    }

    @Test
    public void hasAnnotationLeftDrawerEnabled()
            throws Exception {
        assertThat(LoginKey.class.getAnnotation(LeftDrawerEnabled.class)).isNotNull();
        assertThat((Boolean) getValueFromAnnotation(LeftDrawerEnabled.class,
                LoginKey.class.getAnnotation(LeftDrawerEnabled.class))).isEqualTo(false);
    }

    @Test
    public void hasAnnotationToolbarButtonVisibility()
            throws Exception {
        assertThat(LoginKey.class.getAnnotation(ToolbarButtonVisibility.class)).isNotNull();
        assertThat((Boolean) getValueFromAnnotation(ToolbarButtonVisibility.class,
                LoginKey.class.getAnnotation(ToolbarButtonVisibility.class))).isEqualTo(false);
    }

    @Test
    public void create()
            throws Exception {
        // when
        LoginKey loginKey = LoginKey.create();

        // then
        assertThat(loginKey).isInstanceOf(Parcelable.class);
        assertThat(loginKey).isInstanceOf(AutoValue_LoginKey.class);
    }
}