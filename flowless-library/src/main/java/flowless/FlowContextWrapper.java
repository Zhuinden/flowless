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

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;

final class FlowContextWrapper
        extends ContextWrapper
        implements KeyContextWrapper {
    final Object key;

    private LayoutInflater inflater;

    FlowContextWrapper(Object key, Context baseContext) {
        super(baseContext);
        this.key = key;
    }

    @Override
    public Object getSystemService(String name) {
        if(KEY_CONTEXT_WRAPPER.equals(name)) {
            return this;
        }
        if(LAYOUT_INFLATER_SERVICE.equals(name)) {
            if(inflater == null) {
                inflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
            }
            return inflater;
        }
        return super.getSystemService(name);
    }

    @Override
    public <T> T getKey() {
        //noinspection unchecked
        return (T) key;
    }
}
