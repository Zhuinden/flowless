package flowless.preset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by Zhuinden on 2016.07.01..
 */
public class FlowLifecycleProvider {
    private FlowLifecycleProvider() {
        // No instance
    }

    public static void onCreate(View view, Bundle savedInstanceState) {
        if(view != null && view instanceof FlowLifecycles.CreateDestroyListener) {
            ((FlowLifecycles.CreateDestroyListener) view).onCreate(savedInstanceState);
        }
    }

    public static void onStart(View view) {
        if(view != null && view instanceof FlowLifecycles.StartStopListener) {
            ((FlowLifecycles.StartStopListener) view).onStart();
        }
    }

    public static void onResume(View view) {
        if(view != null && view instanceof FlowLifecycles.ResumePauseListener) {
            ((FlowLifecycles.ResumePauseListener) view).onResume();
        }
    }

    public static void onPause(View view) {
        if(view != null && view instanceof FlowLifecycles.ResumePauseListener) {
            ((FlowLifecycles.ResumePauseListener) view).onPause();
        }
    }

    public static void onStop(View view) {
        if(view != null && view instanceof FlowLifecycles.StartStopListener) {
            ((FlowLifecycles.StartStopListener) view).onStop();
        }
    }

    public static void onDestroy(View view) {
        if(view != null && view instanceof FlowLifecycles.CreateDestroyListener) {
            ((FlowLifecycles.CreateDestroyListener) view).onDestroy();
        }
        if(view != null && view instanceof FlowLifecycles.ViewLifecycleListener) {
            ((FlowLifecycles.ViewLifecycleListener) view).onViewDestroyed(false);
        }
    }

    @CheckResult
    public static boolean onBackPressed(View view) {
        if(view != null && view instanceof FlowLifecycles.BackPressListener) {
            return ((FlowLifecycles.BackPressListener) view).onBackPressed();
        }
        return false;
    }

    public static void onActivityResult(View view, int requestCode, int resultCode, Intent data) {
        if(view != null && view instanceof FlowLifecycles.ActivityResultListener) {
            ((FlowLifecycles.ActivityResultListener) view).onActivityResult(requestCode, resultCode, data);
        }
    }

    public static void onRequestPermissionsResult(View view, int requestCode, String[] permissions, int[] grantResults) {
        if(view != null && view instanceof FlowLifecycles.PermissionRequestListener) {
            ((FlowLifecycles.PermissionRequestListener) view).onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public static void preSaveViewState(View view, Bundle bundle) {
        if(view != null && view instanceof FlowLifecycles.ViewStatePersistenceListener) {
            ((FlowLifecycles.ViewStatePersistenceListener) view).preSaveViewState(bundle);
        }
    }

    public static void onViewRestored(View view, boolean forcedWithBundler) {
        if(view != null && view instanceof FlowLifecycles.ViewLifecycleListener) {
            ((FlowLifecycles.ViewLifecycleListener) view).onViewRestored(forcedWithBundler);
        }
    }

    public static void onViewDestroyed(View view, boolean removedByFlow) {
        if(view != null && view instanceof FlowLifecycles.ViewLifecycleListener) {
            ((FlowLifecycles.ViewLifecycleListener) view).onViewDestroyed(removedByFlow);
        }
    }
}
