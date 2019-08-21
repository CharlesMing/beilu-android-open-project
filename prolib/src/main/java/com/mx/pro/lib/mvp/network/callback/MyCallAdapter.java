package com.mx.pro.lib.mvp.network.callback;

import java.io.IOException;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Mingxun
 * @time on 2018/12/25 10:54
 */
public class MyCallAdapter<T> implements MyCall<T> {

    private final Call<T> mCall;
    private final Executor callbackExecutor;

    public MyCallAdapter(Call<T> call, Executor callbackExecutor) {
        this.mCall = call;
        this.callbackExecutor = callbackExecutor;
    }

    @Override
    public void cancel() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    @Override
    public void enqueue(final MyCallback<T> callback) {
        if (mCall == null) return;
        mCall.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                int code = response.code();
                if (code >= 200 && code < 300) {
                    callback.success(response);
                } else if (code == 401) {
                    callback.unauthenticated(response);
                } else if (code >= 400 && code < 500) {
                    callback.clientError(response);
                } else if (code >= 500 && code < 600) {
                    callback.serverError(response);
                } else {
                    callback.unexpectedError(new RuntimeException("Unexpected response " + response));
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (t instanceof IOException) {
                    callback.networkError((IOException) t);
                } else {
                    callback.unexpectedError(t);
                }
            }
        });
    }

    @Override
    public MyCall<T> clone() {
        return new MyCallAdapter<>(mCall.clone(), callbackExecutor);
    }
}
