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

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;

final class InternalContextWrapper
        extends ContextWrapper
        implements InternalContext {
    private final Activity activity;
    private Flow flow;
    private KeyManager keyManager;

    InternalContextWrapper(Context baseContext, Activity activity) {
        super(baseContext);
        this.activity = activity;
    }

    @Override
    public Resources.Theme getTheme() {
        return activity.getTheme();
    }

    @Override
    public Object getSystemService(String name) {
        if(FLOW_SERVICE.equals(name)) {
            if(flow == null) {
                flow = InternalLifecycleIntegration.find(activity).flow;
            }
            return flow;
        } else if(CONTEXT_MANAGER_SERVICE.equals(name)) {
            if(keyManager == null) {
                keyManager = InternalLifecycleIntegration.find(activity).keyManager;
            }
            return keyManager;
        } else if(ACTIVITY.equals(name)) {
            return activity;
        } else {
            return super.getSystemService(name);
        }
    }
}
