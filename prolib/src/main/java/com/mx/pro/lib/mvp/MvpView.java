package com.mx.pro.lib.mvp;

import android.support.annotation.IdRes;
import android.support.annotation.UiThread;

import com.mx.pro.lib.mvp.network.config.ShowConfig;

/**
 * View
 */
public interface MvpView {

    @UiThread
    void showLoading(@ShowConfig.loading int loading, boolean isShow);

    @UiThread
    void showError(@ShowConfig.error int errorCode, String throwableContent);

    void userIsLogin(boolean login, @IdRes int id);
}
