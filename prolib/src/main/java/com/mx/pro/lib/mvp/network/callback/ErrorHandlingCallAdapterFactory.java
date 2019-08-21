package com.mx.pro.lib.mvp.network.callback;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * @author Mingxun
 * @time on 2018/12/25 10:51
 */
public class ErrorHandlingCallAdapterFactory extends CallAdapter.Factory {
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != MyCall.class) {
            return null;
        }
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException(
                    "MyCall must have generic type (e.g., MyCall<ResponseBody>)");
        }

        Type parameterUpperBound = getParameterUpperBound(0, (ParameterizedType) returnType);
        Executor executor = retrofit.callbackExecutor();
        return new ErrorHandlingCallAdapter<>(parameterUpperBound, executor);
    }
}
