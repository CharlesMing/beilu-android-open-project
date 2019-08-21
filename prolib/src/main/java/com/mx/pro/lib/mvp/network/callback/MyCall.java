package com.mx.pro.lib.mvp.network.callback;

/**
 * @author Mingxun
 * @time on 2018/12/25 10:55
 */
public interface MyCall<T> {
    void cancel();

    void enqueue(MyCallback<T> callback);

    MyCall<T> clone();
}
