package com.zhuinden.examplegithubclient.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import flowless.Dispatcher;
import flowless.Flow;

/**
 * Created by Zhuinden on 2016.12.18..
 */

public abstract class FlowlessActivity
        extends AppCompatActivity
        implements Dispatcher {
    protected TransitionDispatcher transitionDispatcher;

    @Override
    protected void attachBaseContext(Context newBase) {
        transitionDispatcher = new TransitionDispatcher();
        newBase = Flow.configure(newBase, this) //
                .defaultKey(getDefaultKey()) //
                .globalKey(getGlobalKey()) //
                .dispatcher(this) //
                .install(); //
        transitionDispatcher.setBaseContext(this);
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        transitionDispatcher.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        transitionDispatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        transitionDispatcher.preSaveViewState();
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if(transitionDispatcher.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    protected abstract Object getGlobalKey();

    protected abstract Object getDefaultKey();
}
