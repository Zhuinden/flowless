package com.zhuinden.examplegithubclient.util;

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


import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.view.View;

import flowless.Flow;
import flowless.ServiceProvider;

public final class FlowViewAssertions {
    private FlowViewAssertions() {
        // No instances.
    }

    public static ViewAssertion doesNotHaveFlowService(final String serviceName) {
        return new FlowServiceIsNull(serviceName, false);
    }

    public static ViewAssertion hasFlowService(final String serviceName) {
        return new FlowServiceIsNull(serviceName, true);
    }

    public static <T> ViewAssertion hasKey(final T key) {
        return new FlowHasKey<T>(key);
    }

    private static final class FlowHasKey<T>
            implements ViewAssertion {
        private final T key;

        public FlowHasKey(T key) {
            this.key = key;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            T key = Flow.getKey(view);
            if(key == null || !key.equals(this.key)) {
                throw new AssertionError("Key [" + key + "] does not equal [" + this.key + "]");
            }
        }
    }

    private static final class FlowServiceIsNull
            implements ViewAssertion {
        private final String serviceName;
        private final boolean shouldHaveService;

        public FlowServiceIsNull(String serviceName, boolean shouldHaveService) {
            this.serviceName = serviceName;
            this.shouldHaveService = shouldHaveService;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if(ServiceProvider.get(view).hasService(view, serviceName) != shouldHaveService) {
                throw new AssertionError("Service [" + serviceName + "] was " + (shouldHaveService ? "not" : "") + " found, even though it should" + (shouldHaveService ? "" : "n't") + " have been");
            }
        }
    }
}