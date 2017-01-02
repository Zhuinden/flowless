package com.zhuinden.examplegithubclient.application.injection.config;

import com.zhuinden.examplegithubclient.application.injection.modules.InteractorModule;
import com.zhuinden.examplegithubclient.application.injection.modules.OkHttpModule;
import com.zhuinden.examplegithubclient.application.injection.modules.RetrofitModule;
import com.zhuinden.examplegithubclient.application.injection.modules.ServiceModule;
import com.zhuinden.examplegithubclient.presentation.activity.main.DaggerMainComponent;
import com.zhuinden.examplegithubclient.presentation.activity.main.MainComponent;

/**
 * Created by Zhuinden on 2016.12.21..
 */

public class MainComponentConfig {
    private MainComponentConfig() {
    }

    static OkHttpModule okHttpModule = new OkHttpModule();
    static RetrofitModule retrofitModule = new RetrofitModule();
    static InteractorModule interactorModule = new InteractorModule();
    static ServiceModule serviceModule = new ServiceModule();

    public static MainComponent create() {
        return DaggerMainComponent.builder() //
                .okHttpModule(okHttpModule) //
                .retrofitModule(retrofitModule) //
                .interactorModule(interactorModule) //
                .serviceModule(serviceModule) //
                .build(); //
    }
}
