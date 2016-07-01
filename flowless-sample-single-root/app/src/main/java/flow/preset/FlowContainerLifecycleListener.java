package flow.preset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.view.View;

/**
 * Created by Zhuinden on 2016.07.01..
 */
public interface FlowContainerLifecycleListener {
    void onActivityCreated(View view, Bundle savedInstanceState);

    void onActivityStarted(View view);

    void onActivityResumed(View view);

    void onActivityPaused(View view);

    void onActivityStopped(View view);

    void onActivitySaveInstanceState(View view, Bundle outState);

    void onActivityDestroyed(View view);

    boolean onBackPressed(View view);

    void onActivityResult(View view, int requestCode, int resultCode, Intent data);

    void onRequestPermissionsResult(View view, int requestCode, String[] permissions, int[] grantResults);
}
