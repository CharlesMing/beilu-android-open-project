package com.scj.beilu.app.base;

import android.app.Service;

import com.mx.pro.lib.mvp.delegate.LoadDelegate;
import com.mx.pro.lib.mvp.network.config.ShowConfig;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Mingxun
 * @time on 2019/2/21 11:24
 */
public abstract class BaseService extends Service {
    protected final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    protected LoadDelegate.Builder mBuilder;

    @Override
    public void onCreate() {
        super.onCreate();
        initLoad(ShowConfig.NONE, ShowConfig.ERROR_TOAST);
    }

    public void initLoad(@ShowConfig.loading int loadType, @ShowConfig.error int loadErrType) {
        mBuilder = new LoadDelegate.Builder()
                .setLoadType(loadType)
                .setLoadErrType(loadErrType)
                .setContext(getBaseContext())
                .setCompositeDisposable(mCompositeDisposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancel();
    }

    public void cancel() {
        if (mCompositeDisposable == null) return;
        mCompositeDisposable.clear();
    }
}
