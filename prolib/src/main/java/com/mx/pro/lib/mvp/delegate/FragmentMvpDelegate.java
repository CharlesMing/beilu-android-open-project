package com.mx.pro.lib.mvp.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.mx.pro.lib.mvp.MvpPresenter;
import com.mx.pro.lib.mvp.MvpView;


/**
 * A delegate for Fragments to attach them to project mvp.
 * <p>
 * The following methods must be invoked from the corresponding Fragments lifecycle methods:
 *
 * <ul>
 * <li>{@link #onCreate(Bundle)}</li>
 * <li>{@link #onDestroy()}</li>
 * <li>{@link #onPause()} </li>
 * <li>{@link #onResume()} </li>
 * <li>{@link #onStart()} </li>
 * <li>{@link #onStop()} </li>
 * <li>{@link #onViewCreated(View, Bundle)} </li>
 * <li>{@link #onActivityCreated(Bundle)} </li>
 * <li>{@link #onSaveInstanceState(Bundle)} </li>
 * <li>{@link #onAttach(Activity)} </li>
 * <li>{@link #onDetach()}</li>
 * <li></li>
 * </ul>
 * </p>
 */
public interface FragmentMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {
    /**
     * Must be called from {@link Fragment#onCreate(Bundle)}
     */
    void onCreate(Bundle savedInstanceState);

    /**
     * {@link Fragment#onDestroy()}
     */
    void onDestroy();

    /**
     * {@link Fragment#onViewCreated(View, Bundle)}
     */
    void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState);

    /**
     * {@link Fragment#onDestroyView()}
     */
    void onDestroyView();

    /**
     * {@link Fragment#onPause()}
     */
    void onPause();

    /**
     * {@link Fragment#onResume()}
     */
    void onResume();

    /**
     * {@link Fragment#onStart()}
     */
    void onStart();

    /**
     * {@link Fragment#onStop()}
     */
    void onStop();

    /**
     * {@link Fragment#onActivityCreated(Bundle)}
     */
    void onActivityCreated(@Nullable Bundle savedInstanceState);

    /**
     * {@link Fragment#onAttach(Activity)}
     */
    void onAttach(Activity activity);

    /**
     * {@link Fragment#onDetach()}
     */
    void onDetach();

    /**
     * {@link Fragment#onSaveInstanceState(Bundle)}
     */
    void onSaveInstanceState(@NonNull Bundle outState);
}

