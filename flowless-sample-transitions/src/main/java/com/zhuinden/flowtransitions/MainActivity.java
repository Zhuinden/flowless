package com.zhuinden.flowtransitions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import flowless.Flow;

public class MainActivity
        extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.path_container)
    ViewGroup root;

    TransitionDispatcher transitionDispatcher;

    @Override
    protected void attachBaseContext(Context newBase) {
        transitionDispatcher = new TransitionDispatcher();
        newBase = Flow.configure(newBase, this)
                .defaultKey(SceneMainDefaultKey.create())
                .dispatcher(transitionDispatcher)
                .install();
        transitionDispatcher.setBaseContext(this);
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        transitionDispatcher.getRootHolder().setRoot(root);
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
}

//  @BindView(R.id.main_button)
// Button button;
//
//    @BindView(R.id.main_hello_world)
//    TextView helloWorld;
//    @OnClick(R.id.main_button)
//    public void goToFirstScene() {
//        Log.d(TAG, "Go to first scene!");
//        ViewGroup newScene = (ViewGroup) LayoutInflater.from(MainActivity.this).inflate(R.layout.scene_main_default, root, false);
//        TransitionManager.beginDelayedTransition(root);
//        root.removeViewAt(0);
//        root.addView(newScene);
//        ButterKnife.bind(MainActivity.this);
//        goToSecondSceneWithDelay();
//    }
//
//    private void goToSecondSceneWithDelay() {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//                    Log.d(TAG, "Do In Background");
//                    Thread.sleep(3000);
//                } catch(InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                Log.d(TAG, "OnPostExecute");
//                super.onPostExecute(aVoid);
//                ViewGroup newScene = (ViewGroup) LayoutInflater.from(MainActivity.this).inflate(R.layout.scene_main_second, root, false);
//                TransitionManager.beginDelayedTransition(root);
//                root.removeViewAt(0);
//                root.addView(newScene);
//                ButterKnife.bind(MainActivity.this);
//                Log.d(TAG, "Button is [" + button.hashCode() + "]");
//            }
//        }.execute();
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
//        Log.d(TAG, "Button is [" + button.hashCode() + "]");
//        goToSecondSceneWithDelay();
//    }
//}
