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

final class InternalContextWrapper
        extends ContextWrapper
        implements InternalContext {
    private final Activity activity;
    private Flow flow;
    private KeyManager keyManager;
    private ServiceProvider serviceProvider;

    InternalContextWrapper(Context baseContext, Activity activity) {
        super(baseContext);
        this.activity = activity;
    }

    private Flow findFlow() {
        if(flow == null) {
            flow = InternalLifecycleIntegration.find(activity).flow;
        }
        return flow;
    }

    @Override
    public Object getSystemService(String name) {
        if(FLOW_SERVICE.equals(name)) {
            return findFlow();
        } else if(CONTEXT_MANAGER_SERVICE.equals(name)) {
            Flow flow = findFlow();
            if(keyManager == null) {
                keyManager = flow.keyManager;
            }
            return keyManager;
        } else if(SERVICE_PROVIDER.equals(name)) {
            Flow flow = findFlow();
            if(serviceProvider == null) {
                serviceProvider = flow.serviceProvider;
            }
            return serviceProvider;
        } else if(ACTIVITY.equals(name)) {
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
}
