package com.mx.pro.lib.mvp.network.callback;

import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.CallAdapter;

/**
 * @author Mingxun
 * @time on 2018/12/25 11:02
 */
public class ErrorHandlingCallAdapter<R> implements CallAdapter<R, MyCall<R>> {
    private final Type responseType;
    private final Executor callbackExecutor;

    public ErrorHandlingCallAdapter(Type responseType, Executor callbackExecutor) {
        this.responseType = responseType;
        this.callbackExecutor = callbackExecutor;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public MyCall<R> adapt(Call<R> call) {
        return new MyCallAdapter<>(call, callbackExecutor);
    }
}
