package com.zhuinden.examplegithubclient.util.conditionwatcher;

import android.os.Bundle;

import com.zhuinden.examplegithubclient.util.BundleFactory;

/**
 * Created by F1sherKK on 16/12/15.
 */
public abstract class Instruction {

    private Bundle dataContainer = BundleFactory.create();

    public final void setData(Bundle dataContainer) {
        this.dataContainer = dataContainer;
    }

    public final Bundle getDataContainer() {
        return dataContainer;
    }

    public abstract String getDescription();

    public abstract boolean checkCondition();
}