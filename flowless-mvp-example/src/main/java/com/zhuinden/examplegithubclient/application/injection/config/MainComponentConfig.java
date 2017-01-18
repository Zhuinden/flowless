package com.zhuinden.examplegithubclient.application.injection.config;

import com.zhuinden.examplegithubclient.application.injection.modules.InteractorModule;
import com.zhuinden.examplegithubclient.application.injection.modules.NavigationModule;
import com.zhuinden.examplegithubclient.application.injection.modules.OkHttpModule;
import com.zhuinden.examplegithubclient.application.injection.modules.RetrofitModule;
import com.zhuinden.examplegithubclient.application.injection.modules.ServiceModule;
import com.zhuinden.examplegithubclient.presentation.activity.main.DaggerMainComponent;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainComponent;

import flowless.Flow;

/**
 * Created by Zhuinden on 2016.12.21..
 */

public class MainComponentConfig {
    public interface Provider<T> {
        T get();
    }

    private MainComponentConfig() {
    }

    static OkHttpModule okHttpModule = new OkHttpModule();
    static RetrofitModule retrofitModule = new RetrofitModule();
    static InteractorModule interactorModule = new InteractorModule();
    static ServiceModule serviceModule = new ServiceModule();

    static Provider<NavigationModule> navigationModule(Flow flow) {
        return () -> new NavigationModule(flow);
    }

    public static MainComponent create(Flow flow) {
        return DaggerMainComponent.builder() //
                .navigationModule(navigationModule(flow).get()) //
                .okHttpModule(okHttpModule) //
                .retrofitModule(retrofitModule) //
                .interactorModule(interactorModule) //
                .serviceModule(serviceModule) //
                .build(); //
    }
}