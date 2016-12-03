package com.zhuinden.flowtransitions;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flowless.Flow;

/**
 * Created by Zhuinden on 2016.12.03..
 */

public class SceneMainView
        extends RelativeLayout {
    private static final String TAG = "SceneMainView";

    public SceneMainView(Context context) {
        super(context);
        init();
    }

    public SceneMainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SceneMainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public SceneMainView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        if(!isInEditMode()) {
            // .
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        if(Flow.getKey(this) instanceof SceneMainDefaultKey) {
            goToSecondSceneWithDelay();
        }
    }

    @BindView(R.id.main_button)
    Button button;

    @BindView(R.id.main_hello_world)
    TextView helloWorld;

    @OnClick(R.id.main_button)
    public void goToFirstScene() {
        Log.d(TAG, "Go to first scene!");
        Flow.get(this).set(SceneMainDefaultKey.create());
    }

    private void goToSecondSceneWithDelay() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Log.d(TAG, "Do In Background");
                    Thread.sleep(3000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.d(TAG, "OnPostExecute");
                super.onPostExecute(aVoid);
                Flow.get(SceneMainView.this).set(SceneMainSecondKey.create());
            }
        }.execute();
    }
}
