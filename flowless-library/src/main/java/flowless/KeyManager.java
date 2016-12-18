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

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class KeyManager {
    static final String GLOBAL_KEYS = "GLOBAL_KEYS";
    static final String HISTORY_KEYS = "HISTORY_KEYS";

    private final Map<Object, State> states = new LinkedHashMap<>();
    final Set<Object> globalKeys;

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

    void clearNonGlobalStatesExcept(List<Object> keep) {
        Iterator<Object> keys = states.keySet().iterator();
        while(keys.hasNext()) {
            final Object key = keys.next();
            if(!globalKeys.contains(key) && !keep.contains(key)) {
                keys.remove();
            }
        }
    }
}
