package com.mx.pro.lib.mvp.network.observer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * @author Mingxun
 * @time on 2018/12/25 11:11
 */
public class ObserveOnMainCallAdapterFactory extends CallAdapter.Factory {
    private final Scheduler mScheduler;

    public ObserveOnMainCallAdapterFactory(Scheduler scheduler) {
        mScheduler = scheduler;
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != Observable.class) {
            return null; // Ignore non-Observable types.
        }
        final CallAdapter<Object, Observable<?>> delegate =
                (CallAdapter<Object, Observable<?>>) retrofit.nextCallAdapter(this, returnType, annotations);


        return new CallAdapter<Object, Object>() {
            @Override
            public Type responseType() {
                return delegate.responseType();
            }

            @Override
            public Object adapt(Call<Object> call) {
                Observable<?> o = delegate.adapt(call);
                return o.observeOn(mScheduler);
            }
        };
    }

}
