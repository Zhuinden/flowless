package flow.preset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.view.View;

/**
 * Created by Zhuinden on 2016.07.01..
 */
public interface FlowContainerLifecycleListener
        extends FlowLifecycles.ActivityResultListener, //
                FlowLifecycles.BackPressListener, //
                FlowLifecycles.CreateDestroyListener, //
                FlowLifecycles.StartStopListener, //
                FlowLifecycles.ResumePauseListener,
                FlowLifecycles.ViewStatePersistenceListener,
                FlowLifecycles.PermissionRequestListener,
                FlowLifecycles.ViewLifecycleListener
{
}
