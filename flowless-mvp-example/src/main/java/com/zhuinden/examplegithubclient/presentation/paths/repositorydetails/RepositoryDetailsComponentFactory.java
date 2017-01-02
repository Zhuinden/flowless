package com.zhuinden.examplegithubclient.presentation.paths.repositorydetails;

import android.content.Context;

import com.zhuinden.examplegithubclient.presentation.activity.main.MainComponent;
import com.zhuinden.examplegithubclient.util.ComponentFactory;
import com.zhuinden.examplegithubclient.util.DaggerService;

/**
 * Created by Zhuinden on 2016.12.19..
 */

public class RepositoryDetailsComponentFactory
        implements ComponentFactory.FactoryMethod<RepositoryDetailsComponent> {
    @Override
    public RepositoryDetailsComponent createComponent(Context context) {
        MainComponent mainComponent = DaggerService.getGlobalComponent(context);
        return DaggerRepositoryDetailsComponent.builder().mainComponent(mainComponent).build();
    }
}
