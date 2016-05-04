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

package flow;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class KeyManager {
    private final Map<Object, State> states = new LinkedHashMap<>();

    KeyManager() {
    }

    boolean hasState(Object key) {
        return states.containsKey(key);
    }

    void addState(State state) {
        states.put(state.getKey(), state);
    }

    State getState(Object key) {
        State state = states.get(key);
        if(state == null) {
            state = new State(key);
            addState(state);
        }
        return state;
    }

    void clearStatesExcept(List<Object> keep) {
        Iterator<Object> keys = states.keySet().iterator();
        while(keys.hasNext()) {
            final Object key = keys.next();
            if(!keep.contains(key)) {
                keys.remove();
            }
        }
    }
}
