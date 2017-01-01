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
import android.support.annotation.NonNull;
import android.view.View;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class KeyManager {
    public static final String SERVICE_TAG = "flowless.KEY_MANAGER";

    public static KeyManager get(@NonNull Context context) {
        //noinspection ResourceType
        return (KeyManager) context.getSystemService(SERVICE_TAG);
    }

    public static KeyManager get(@NonNull View view) {
        return get(view.getContext());
    }

    static final String GLOBAL_KEYS = "GLOBAL_KEYS";
    static final String HISTORY_KEYS = "HISTORY_KEYS";
    static final String REGISTERED_KEYS = "REGISTERED_KEYS";

    /* private */ final Map<Object, State> states = new LinkedHashMap<>();
    final Set<Object> globalKeys;
    final Set<Object> registeredKeys = new HashSet<>();

    KeyManager() {
        this(null);
    }

    KeyManager(Object globalKey) {
        if(globalKey == null) {
            this.globalKeys = Collections.emptySet();
        } else {
            this.globalKeys = new LinkedHashSet<>(Arrays.asList(globalKey));
        }
    }

    public boolean hasState(Object key) {
        return states.containsKey(key);
    }

    void addState(State state) {
        states.put(state.getKey(), state);
    }

    public State getState(Object key) {
        State state = states.get(key);
        if(state == null) {
            state = new State(key);
            addState(state);
        }
        return state;
    }

    public boolean registerKey(Object key) {
        getState(key); // initialize state if does not exist
        return registeredKeys.add(key);
    }

    public boolean unregisterKey(Object key) {
        return registeredKeys.remove(key);
    }

    void clearNonGlobalStatesExcept(List<Object> keep) {
        Iterator<Object> keys = states.keySet().iterator();
        while(keys.hasNext()) {
            final Object key = keys.next();
            if(!globalKeys.contains(key) && !registeredKeys.contains(key) && !keep.contains(key)) {
                keys.remove();
            }
        }
    }
}
