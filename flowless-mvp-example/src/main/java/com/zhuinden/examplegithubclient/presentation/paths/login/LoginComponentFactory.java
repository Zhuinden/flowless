package com.zhuinden.examplegithubclient.presentation.paths.login;

import android.content.Context;

import com.zhuinden.examplegithubclient.presentation.activity.main.MainComponent;
import com.zhuinden.examplegithubclient.util.ComponentFactory;
import com.zhuinden.examplegithubclient.util.DaggerService;

/**
 * Created by Zhuinden on 2016.12.18..
 */

public class LoginComponentFactory
        implements ComponentFactory.FactoryMethod<LoginComponent> {
    @Override
    public LoginComponent createComponent(Context context) {
        MainComponent mainComponent = DaggerService.getGlobalComponent(context);
        return DaggerLoginComponent.builder().mainComponent(mainComponent).build();
    }
}
