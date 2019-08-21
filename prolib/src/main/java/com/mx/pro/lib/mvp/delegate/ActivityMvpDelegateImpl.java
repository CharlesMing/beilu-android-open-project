package com.mx.pro.lib.mvp.delegate;

import android.app.Activity;
import android.os.Bundle;

import com.mx.pro.lib.BuildConfig;
import com.mx.pro.lib.mvp.MvpPresenter;
import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.manager.PresenterManager;
import com.orhanobut.logger.Logger;

import java.util.UUID;

/**
 * @param <V> View
 * @param <P> Presenter
 */
public class ActivityMvpDelegateImpl<V extends MvpView, P extends MvpPresenter<V>>
        implements ActivityMvpDelegate {

    private MvpDelegateCallback<V, P> mDelegateCallback;
    private Activity mActivity;
    protected boolean keepPresenterInstance;
    protected String mProId = null;
    protected static final String KEY_PRO_VIEW_ID = "app.beilu.scj.com.mvp.id";


    public ActivityMvpDelegateImpl(Activity activity, MvpDelegateCallback<V, P> delegateCallback,
                                   boolean keepPresenterInstance) {

        if (activity == null) {
            throw new NullPointerException("Activity is null");
        }

        if (delegateCallback == null) {
            throw new NullPointerException("MvpDelegateCallback is null");
        }

        this.mDelegateCallback = delegateCallback;
        this.mActivity = activity;
        this.keepPresenterInstance = keepPresenterInstance;
    }

    static boolean retainPresenterInstance(boolean keepPresenterInstance, Activity activity) {
        return keepPresenterInstance && (activity.isChangingConfigurations()) || !activity.isFinishing();
    }

    private P createViewIdAndCreatePresenter() {
        P presenter = mDelegateCallback.createPresenter();
        if (presenter == null) {
            throw new NullPointerException("Presenter returned from createPresenter() is null,Activity is " + mActivity);
        }

        if (keepPresenterInstance) {
            mProId = UUID.randomUUID().toString();
            PresenterManager.putPresenter(mActivity, mProId, presenter);
        }
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        P presenter = null;
        if (savedInstanceState != null && keepPresenterInstance) {
            mProId = savedInstanceState.getString(KEY_PRO_VIEW_ID);
            if (BuildConfig.LOG_DEBUG) {
                Logger.d("MosbyView ID = " + mProId + " for MvpView: " + mDelegateCallback.getMvpView());
            }

            if (mProId != null
                    && (presenter = PresenterManager.getPresenter(mActivity, mProId)) != null) {
                if (BuildConfig.LOG_DEBUG) {
                    Logger.d("Reused presenter " + presenter + " for view " + mDelegateCallback.getMvpView());
                }
            } else {
                //No presenter found in cache, most likely caused by process death
                presenter = createViewIdAndCreatePresenter();
                if (BuildConfig.LOG_DEBUG) {
                    Logger.d("No presenter found although view Id was here: "
                            + mProId
                            + ". Most likely this was caused by a process death. New Presenter created"
                            + presenter
                            + " for view "
                            + getMvpView());
                }
            }
        } else {
            presenter = createViewIdAndCreatePresenter();
            if (BuildConfig.LOG_DEBUG) {
                Logger.d("New presenter " + presenter + " for view " + getMvpView());
            }
        }

        if (presenter == null) {
            throw new IllegalStateException("Oops, Presenter is null.");
        }
        mDelegateCallback.setPresenter(presenter);
        getPresenter().attachView(getMvpView());
        if (BuildConfig.LOG_DEBUG) {
            Logger.d("View" + getMvpView() + " attached to Presenter " + presenter);
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        boolean presenterInstance = retainPresenterInstance(keepPresenterInstance, mActivity);
        getPresenter().detachView();
        if (!presenterInstance) {
            getPresenter().destroy();
        }
        if (!presenterInstance && mProId != null) {
            PresenterManager.remove(mActivity, mProId);
        }
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onContentChanged() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (keepPresenterInstance && outState != null) {
            outState.putString(KEY_PRO_VIEW_ID, mProId);
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {

    }


    private P getPresenter() {
        P presenter = mDelegateCallback.getPresenter();
        if (presenter == null) {
            throw new NullPointerException("Presenter returned from getPresenter() is null");
        }
        return presenter;
    }

    private V getMvpView() {
        V view = mDelegateCallback.getMvpView();
        if (view == null) {
            throw new NullPointerException("View returned from getMvpView() is null");
        }
        return view;
    }
}
