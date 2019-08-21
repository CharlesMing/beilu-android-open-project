package com.mx.pro.lib.mvp.delegate;

import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpPresenter;
import com.mx.pro.lib.mvp.MvpView;

public interface MvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>> {
    @NonNull
    P createPresenter();

    P getPresenter();

    void setPresenter(P presenter);

    V getMvpView();
}
