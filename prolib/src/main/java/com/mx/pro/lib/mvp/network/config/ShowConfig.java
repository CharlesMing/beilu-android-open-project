package com.mx.pro.lib.mvp.network.config;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Mingxun
 * @time on 2018/12/26 16:00
 */
public interface ShowConfig {
    int NONE = 0;

    int LOADING_DIALOG = 0x001;
    int LOADING_REFRESH = 0x002;
    int LOADING_STATE = 0x003;

    int ERROR_TOAST = 0x001;
    int ERROR_DIALOG = 0x002;
    int ERROR_USER = 0x003;
    int ERROR_NET = 0x004;

    @IntDef({NONE, LOADING_DIALOG, LOADING_REFRESH, LOADING_STATE})
    @Retention(RetentionPolicy.SOURCE)
    @interface loading {

    }

    @IntDef({NONE, ERROR_DIALOG, ERROR_TOAST, ERROR_USER, ERROR_NET})
    @Retention(RetentionPolicy.SOURCE)
    @interface error {

    }
}
