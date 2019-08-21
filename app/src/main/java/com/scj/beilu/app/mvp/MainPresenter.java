package com.scj.beilu.app.mvp;

import android.content.Context;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BaseMvpPresenter<MainPresenter.MainView> {

    public MainPresenter(Context context) {
        super(context);
    }

    private <T> ObservableSource<T> create(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public interface MainView extends MvpView {
        void showContent(String content);
    }
}

