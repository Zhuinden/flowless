package flow.preset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.view.View;

/**
 * Created by Zhuinden on 2016.07.01..
 */
public class FlowLifecycleProvider implements FlowContainerLifecycleListener {
    @Override
    public void onActivityCreated(View view, Bundle savedInstanceState) {
        if(view != null && view instanceof FlowLifecycles.CreateDestroyListener) {
            ((FlowLifecycles.CreateDestroyListener) view).onCreate(savedInstanceState);
        }
    }

    @Override
    public void onActivityStarted(View view) {
        if(view != null && view instanceof FlowLifecycles.CreateDestroyListener) {
            ((FlowLifecycles.StartStopListener) view).onStart();
        }
    }

    @Override
    public void onActivityResumed(View view) {
        if(view != null && view instanceof FlowLifecycles.CreateDestroyListener) {
            ((FlowLifecycles.ResumePauseListener) view).onResume();
        }
    }

    @Override
    public void onActivityPaused(View view) {
        if(view != null && view instanceof FlowLifecycles.CreateDestroyListener) {
            ((FlowLifecycles.ResumePauseListener) view).onPause();
        }
    }

    @Override
    public void onActivityStopped(View view) {
        if(view != null && view instanceof FlowLifecycles.CreateDestroyListener) {
            ((FlowLifecycles.StartStopListener) view).onStop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(View view, Bundle outState) {
        if(view != null && view instanceof FlowLifecycles.ViewStatePersistenceListener) {
            ((FlowLifecycles.ViewStatePersistenceListener) view).preSaveViewState();
        }
    }

    @Override
    public void onActivityDestroyed(View view) {
        if(view != null && view instanceof FlowLifecycles.CreateDestroyListener) {
            ((FlowLifecycles.CreateDestroyListener) view).onDestroy();
        }
        if(view != null && view instanceof FlowLifecycles.ViewLifecycleListener) {
            ((FlowLifecycles.ViewLifecycleListener) view).onViewDestroyed(false);
        }
    }

    @Override
    @CheckResult
    public boolean onBackPressed(View view) {
        if(view != null && view instanceof FlowLifecycles.BackPressListener) {
            return ((FlowLifecycles.BackPressListener) view).onBackPressed();
        }
        return false;
    }

    public void onActivityResult(View view, int requestCode, int resultCode, Intent data) {
        if(view != null && view instanceof FlowLifecycles.ActivityResultListener) {
            ((FlowLifecycles.ActivityResultListener) view).onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onRequestPermissionsResult(View view, int requestCode, String[] permissions, int[] grantResults) {
        if(view != null && view instanceof FlowLifecycles.PermissionRequestListener) {
            ((FlowLifecycles.PermissionRequestListener) view).onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
