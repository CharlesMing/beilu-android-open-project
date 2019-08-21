package com.mx.pro.lib.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

public interface MvpPresenter<V extends MvpView> {

    @UiThread
    void attachView(@NonNull V view);

    @UiThread
    void detachView();

    @UiThread
    void destroy();
}
