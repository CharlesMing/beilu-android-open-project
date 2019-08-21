package com.scj.beilu.app.mvp.mine;

import android.content.Context;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.mvp.mine.model.IMine;
import com.scj.beilu.app.mvp.mine.model.MineImpl;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mingxun
 * @time on 2019/4/12 19:53
 */
public class MineAccountPre extends BaseMvpPresenter<MineAccountPre.MineAccountView> {

    private IMine mMine;

    public MineAccountPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mMine = new MineImpl(mBuilder);
    }

    public void getCacheSize() {
        onceViewAttached(mvpView -> mMine.getCacheSize()
                .subscribe(new ObserverCallback<String>(mBuilder.build(mvpView)) {
                    @Override
                    public void onNext(String s) {
                        mvpView.onCacheSize(s);
                    }
                }));
    }

    public void clearCache() {
        onceViewAttached(mvpView -> mMine.clearCache()
                .flatMap((Function<String, ObservableSource<String>>) s ->
                        mMine.getCacheSize())
                .subscribe(new ObserverCallback<String>(mBuilder.build(mvpView)) {
                    @Override
                    public void onNext(String cacheSize) {
                        GlideApp.get(mContext).clearMemory();
                        mvpView.onCacheSize(cacheSize);
                    }
                }));
    }

    public interface MineAccountView extends MvpView {
        void onCacheSize(String cacheSize);
    }

}
