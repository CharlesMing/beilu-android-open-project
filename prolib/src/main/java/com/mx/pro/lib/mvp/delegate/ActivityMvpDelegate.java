package com.mx.pro.lib.mvp.delegate;

import android.app.Activity;
import android.os.Bundle;

import com.mx.pro.lib.mvp.MvpPresenter;
import com.mx.pro.lib.mvp.MvpView;


public interface ActivityMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {

    /**
     * This method must b
     */
    void onCreate(Bundle savedInstanceState);

    /**
     * {@link Activity#onStart()}
     */
    void onStart();

    /**
     * {@link Activity#onRestart()}
     */
    void onRestart();

    /**
     * {@link Activity#onResume()}
     */
    void onResume();

    /**
     * {@link Activity#onPause()}
     */
    void onPause();

    /**
     * {@link Activity#onDestroy()}
     */
    void onDestroy();

    /**
     * {@link Activity#onStop()}
     */
    void onStop();

    /**
     * {@link Activity#onContentChanged()}
     */
    void onContentChanged();

    /**
     * {@link Activity#onSaveInstanceState(Bundle)}
     */
    void onSaveInstanceState(Bundle outState);

    /**
     * {@link Activity#onPostCreate(Bundle)}
     */
    void onPostCreate(Bundle savedInstanceState);
}
