package flowless.preset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Zhuinden on 2016.07.01..
 */
public interface FlowLifecycles {
    public interface BackPressListener {
        boolean onBackPressed();
    }

    public interface CreateDestroyListener {
        void onCreate(Bundle bundle);

        void onDestroy();
    }

    public interface StartStopListener {
        void onStart();

        void onStop();
    }

    public interface ResumePauseListener {
        void onResume();

        void onPause();
    }

    public interface ViewStatePersistenceListener {
        void preSaveViewState(@Nullable Bundle outState);
    }

    public interface ViewLifecycleListener {
        void onViewRestored();

        void onViewDestroyed(boolean removedByFlow);
    }

    public interface ActivityResultListener {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    public interface PermissionRequestListener {
        void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
    }
}
