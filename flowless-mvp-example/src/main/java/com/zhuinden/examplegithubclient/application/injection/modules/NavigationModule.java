package com.zhuinden.examplegithubclient.application.injection.modules;

import dagger.Module;
import dagger.Provides;
import flowless.Flow;
import flowless.KeyManager;
import flowless.ServiceProvider;

/**
 * Created by Owner on 2017. 01. 18..
 */

@Module
public class NavigationModule {
    private Flow flow;

    public NavigationModule(Flow flow) {
        this.flow = flow;
    }

    @Provides
    Flow flow() {
        return flow;
    }

    @Provides
    ServiceProvider serviceProvider() {
        return flow.getServices();
    }

    @Provides
    KeyManager keyManager() {
        return flow.getStates();
    }
}
