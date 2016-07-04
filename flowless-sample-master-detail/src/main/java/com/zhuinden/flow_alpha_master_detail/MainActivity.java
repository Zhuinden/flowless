package com.zhuinden.flow_alpha_master_detail;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.zhuinden.flow_alpha_master_detail.paths.FirstKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import flowless.Flow;
import flowless.preset.ContainerRootDispatcher;

public class MainActivity
        extends AppCompatActivity {

    @BindView(R.id.main_root)
    ViewGroup root;

    ContainerRootDispatcher flowDispatcher;

    @Override
    protected void attachBaseContext(Context newBase) {
        flowDispatcher = new ContainerRootDispatcher(this);
        newBase = Flow.configure(newBase, this) //
                .defaultKey(FirstKey.create()) //
                .dispatcher(flowDispatcher) //
                .install(); //
        flowDispatcher.setBaseContext(newBase);
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        flowDispatcher.getRootHolder().setRoot((ViewGroup)root.getChildAt(0));
    }

    @Override
    public void onBackPressed() {
        boolean didGoBack = flowDispatcher.onBackPressed();
        if(didGoBack) {
            return;
        }
        super.onBackPressed();
    }
}
