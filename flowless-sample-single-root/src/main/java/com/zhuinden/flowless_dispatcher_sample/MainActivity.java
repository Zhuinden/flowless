package com.zhuinden.flowless_dispatcher_sample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import flowless.Flow;
import flowless.preset.SingleRootDispatcher;

public class MainActivity
        extends AppCompatActivity {

    @BindView(R.id.main_root)
    ViewGroup root;

    SingleRootDispatcher flowDispatcher;

    @Override
    protected void attachBaseContext(Context newBase) {
        flowDispatcher = new SingleRootDispatcher(this);
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
        flowDispatcher.getRootHolder().setRoot(root);
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
