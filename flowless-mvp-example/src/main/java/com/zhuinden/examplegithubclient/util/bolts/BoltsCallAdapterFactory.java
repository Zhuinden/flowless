package com.zhuinden.examplegithubclient.util.bolts;

/*
 * Copyright (C) 2016 zeng1990java.
 *     https://github.com/zeng1990java
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;

import bolts.Task;
import bolts.TaskCompletionSource;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zengxiangbin on 2016/9/24 12:03.
 */
public final class BoltsCallAdapterFactory
        extends CallAdapter.Factory {
    public static BoltsCallAdapterFactory create() {
        return new BoltsCallAdapterFactory(null);
    }

    public static BoltsCallAdapterFactory createWithExecutor(Executor executor) {
        if(executor == null) {
            throw new NullPointerException("executor == null");
        }
        return new BoltsCallAdapterFactory(executor);
    }

    private Executor executor;

    private BoltsCallAdapterFactory(Executor executor) {
        this.executor = executor;
    }

    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if(getRawType(returnType) != Task.class) {
            return null;
        }

        if(!(returnType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Task return type must be parameterized" + " as Task<Foo> or Task<? extends Foo>");
        }
        Type innerType = getParameterUpperBound(0, (ParameterizedType) returnType);

        if(getRawType(innerType) != Response.class) {
            return new BodyCallAdapter(executor, innerType);
        }

        if(!(innerType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Response must be parameterized" + " as Response<Foo> or Response<? extends Foo>");
        }
        Type responseType = getParameterUpperBound(0, (ParameterizedType) innerType);
        return new ResponseCallAdapter(executor, responseType);
    }

    private static class BodyCallAdapter
            implements CallAdapter<Task<?>> {

        private Executor executor;
        private final Type responseType;

        BodyCallAdapter(Executor executor, Type responseType) {
            this.executor = executor;
            this.responseType = responseType;
        }

        @Override
        public Type responseType() {
            return responseType;
        }

        @Override
        public <R> Task<?> adapt(final Call<R> call) {
            final TaskCompletionSource<R> taskCompletionSource = new TaskCompletionSource<>();

            if(executor != null) {
                executor.execute(() -> {
                    try {
                        Response<R> response = call.execute();
                        setResponseResult(response, taskCompletionSource);
                    } catch(IOException e) {
                        taskCompletionSource.setError(e);
                    }
                });
            } else {
                call.enqueue(new Callback<R>() {
                    @Override
                    public void onResponse(Call<R> call, Response<R> response) {
                        setResponseResult(response, taskCompletionSource);
                    }

                    @Override
                    public void onFailure(Call<R> call, Throwable t) {
                        taskCompletionSource.setError(new Exception(t));
                    }
                });
            }

            return taskCompletionSource.getTask();
        }

        private <R> void setResponseResult(Response<R> response, TaskCompletionSource<R> taskCompletionSource) {
            try {
                if(response.isSuccessful()) {
                    taskCompletionSource.setResult(response.body());
                } else {
                    taskCompletionSource.setError(new HttpException(response));
                }
            } catch(CancellationException e) {
                taskCompletionSource.setCancelled();
            } catch(Exception e) {
                taskCompletionSource.setError(e);
            }
        }
    }

    private static class ResponseCallAdapter
            implements CallAdapter<Task<?>> {
        private final Executor executor;
        private final Type responseType;

        ResponseCallAdapter(Executor executor, Type responseType) {
            this.executor = executor;
            this.responseType = responseType;
        }

        @Override
        public Type responseType() {
            return responseType;
        }

        @Override
        public <R> Task<?> adapt(final Call<R> call) {
            final TaskCompletionSource<Response<R>> taskCompletionSource = new TaskCompletionSource<>();

            if(executor != null) {
                executor.execute(() -> {
                    try {
                        Response<R> response = call.execute();
                        setResponseResult(response, taskCompletionSource);
                    } catch(IOException e) {
                        taskCompletionSource.setError(e);
                    }
                });
            } else {
                call.enqueue(new Callback<R>() {
                    @Override
                    public void onResponse(Call<R> call, Response<R> response) {
                        setResponseResult(response, taskCompletionSource);
                    }

                    @Override
                    public void onFailure(Call<R> call, Throwable t) {
                        taskCompletionSource.setError(new Exception(t));
                    }
                });
            }
            return taskCompletionSource.getTask();
        }

        private <R> void setResponseResult(Response<R> response, TaskCompletionSource<Response<R>> tcs) {
            try {
                tcs.setResult(response);
            } catch(CancellationException e) {
                tcs.setCancelled();
            } catch(Exception e) {
                tcs.setError(e);
            }
        }
    }

    /**
     * Exception for an unexpected, non-2xx HTTP response.
     */
    public static final class HttpException
            extends Exception {
        private final int code;
        private final String message;
        private final transient Response<?> response;

        public HttpException(Response<?> response) {
            super("HTTP " + response.code() + " " + response.message());
            this.code = response.code();
            this.message = response.message();
            this.response = response;
        }

        /**
         * HTTP status code.
         */
        public int code() {
            return code;
        }

        /**
         * HTTP status message.
         */
        public String message() {
            return message;
        }

        /**
         * The full HTTP response. This may be null if the exception was serialized.
         */
        public Response<?> response() {
            return response;
        }
    }
}