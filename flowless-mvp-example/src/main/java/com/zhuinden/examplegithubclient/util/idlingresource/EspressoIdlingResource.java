package com.zhuinden.examplegithubclient.util.idlingresource;

/**
 * Created by Zhuinden on 2016.12.19..
 */

import android.support.test.espresso.IdlingResource;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Contains a static reference to {@link IdlingResource}, only available in the 'mock' build type.
 */
public class EspressoIdlingResource {

    private static final String RESOURCE = "GLOBAL";

    private static SimpleCountingIdlingResource mCountingIdlingResource = new SimpleCountingIdlingResource(RESOURCE);

    public static void increment() {
        mCountingIdlingResource.increment();
    }

    public static void decrement() {
        mCountingIdlingResource.decrement();
    }

    public static IdlingResource getIdlingResource() {
        return mCountingIdlingResource;
    }

    /**
     * A simple counter implementation of {@link IdlingResource} that determines idleness by
     * maintaining an internal counter. When the counter is 0 - it is considered to be idle, when it is
     * non-zero it is not idle. This is very similar to the way a {@link java.util.concurrent.Semaphore}
     * behaves.
     * <p>
     * This class can then be used to wrap up operations that while in progress should block tests from
     * accessing the UI.
     */
    public static final class SimpleCountingIdlingResource
            implements IdlingResource {

        private static final String TAG = "CountingIdlingResource";
        private final String mResourceName;

        private final AtomicInteger counter = new AtomicInteger(0);

        // written from main thread, read from any thread.
        private volatile ResourceCallback resourceCallback;

        /**
         * Creates a SimpleCountingIdlingResource
         *
         * @param resourceName the resource name this resource should report to Espresso.
         */
        public SimpleCountingIdlingResource(String resourceName) {
            if(resourceName == null) {
                throw new IllegalArgumentException("The resource is null, when expected not to be!");
            }
            mResourceName = resourceName;
        }

        @Override
        public String getName() {
            return mResourceName;
        }

        @Override
        public boolean isIdleNow() {
            return counter.get() == 0;
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
            this.resourceCallback = resourceCallback;
        }

        /**
         * Increments the count of in-flight transactions to the resource being monitored.
         */
        public void increment() {
            counter.getAndIncrement();
        }

        /**
         * Decrements the count of in-flight transactions to the resource being monitored.
         *
         * If this operation results in the counter falling below 0 - an exception is raised.
         *
         * @throws IllegalStateException if the counter is below 0.
         */
        public void decrement() {
            int counterVal = counter.decrementAndGet();
            if(counterVal == 0) {
                // we've gone from non-zero to zero. That means we're idle now! Tell espresso.
                if(null != resourceCallback) {
                    resourceCallback.onTransitionToIdle();
                }
            }

            if(counterVal < 0) {
                Log.e(TAG, "Counter has been corrupted!");
            }
        }
    }
}
