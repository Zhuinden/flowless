package com.zhuinden.examplegithubclient.util.idlingresource;


import flowless.FlowIdlingResource;

/**
 * Created by Zhuinden on 2016.07.30..
 */
public class FlowlessIdlingResource
        implements FlowIdlingResource {
    public static final FlowlessIdlingResource INSTANCE = new FlowlessIdlingResource();

    private FlowlessIdlingResource() {
    }

    @Override
    public void increment() {
        EspressoIdlingResource.increment();
    }

    @Override
    public void decrement() {
        EspressoIdlingResource.decrement();
    }
}
