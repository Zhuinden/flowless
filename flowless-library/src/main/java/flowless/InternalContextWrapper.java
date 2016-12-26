/*
 * Copyright 2016 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package flowless;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Resources;
import android.os.Bundle;

/*final*/ class InternalContextWrapper
        extends ContextWrapper
        implements KeyContextWrapper {
    private final Activity activity;
    private Flow flow;
    private Object globalKey;

    private static final String FLOW_NOT_YET_INITIALIZED = "Flow instance does not exist before `onPostCreate()`";

    InternalContextWrapper(Context baseContext, Activity activity, Object globalKey) {
        super(baseContext);
        this.activity = activity;
        this.globalKey = globalKey;
    }

    private Flow findFlow() {
        if(flow == null) {
            InternalLifecycleIntegration internalLifecycleIntegration = InternalLifecycleIntegration.find(activity);
            if(internalLifecycleIntegration == null) {
                throw new IllegalStateException(FLOW_NOT_YET_INITIALIZED);
            }
            flow = internalLifecycleIntegration.flow;
            if(flow == null) {
                throw new IllegalStateException(FLOW_NOT_YET_INITIALIZED);
            }
        }
        return flow;
    }

    static Activity getActivity(Context context) {
        //noinspection ResourceType
        Activity activity = (Activity) context.getSystemService(ActivityUtils.ACTIVITY_SERVICE_TAG);
        return activity;
    }

    @Override
    public Object getSystemService(String name) {
        if(KEY_SERVICE_TAG.equals(name)) {
            return getKey();
        } else if(Flow.SERVICE_TAG.equals(name)) {
            return findFlow();
        } else if(KeyManager.SERVICE_TAG.equals(name)) {
            Flow flow = findFlow();
            return flow.getStates();
        } else if(ServiceProvider.SERVICE_TAG.equals(name)) {
            Flow flow = findFlow();
            return flow.getServices();
        } else if(ActivityUtils.ACTIVITY_SERVICE_TAG.equals(name)) {
            return activity;
        } else {
            return super.getSystemService(name);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        activity.startActivity(intent);
    }

    @TargetApi(16)
    @Override
    public void startActivity(Intent intent, Bundle options) {
        activity.startActivity(intent, options);
    }


    @Override
    public void startActivities(Intent[] intents) {
        activity.startActivities(intents);
    }

    @TargetApi(16)
    @Override
    public void startActivities(Intent[] intents, Bundle options) {
        activity.startActivities(intents, options);
    }

    @Override
    public void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags)
            throws IntentSender.SendIntentException {
        activity.startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags);
    }

    @TargetApi(16)
    @Override
    public void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options)
            throws IntentSender.SendIntentException {
        activity.startIntentSender(intent, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }

    @Override
    public void setTheme(int resid) {
        activity.setTheme(resid);
    }

    @Override
    public Resources.Theme getTheme() {
        return activity.getTheme();
    }

    @Override
    public <T> T getKey() {
        // noinspection unchecked
        return (T) globalKey;
    }
}
