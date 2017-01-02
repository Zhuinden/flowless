package com.zhuinden.examplegithubclient.presentation.paths.repositories;

import android.content.Context;

import com.zhuinden.examplegithubclient.presentation.activity.main.MainComponent;
import com.zhuinden.examplegithubclient.util.ComponentFactory;
import com.zhuinden.examplegithubclient.util.DaggerService;

/**
 * Created by Owner on 2016.12.10.
 */

public class RepositoriesComponentFactory
        implements ComponentFactory.FactoryMethod<RepositoriesComponent> {
    @Override
    public RepositoriesComponent createComponent(Context context) {
        MainComponent mainComponent = DaggerService.getGlobalComponent(context);
        return DaggerRepositoriesComponent.builder().mainComponent(mainComponent).build();
    }
}
