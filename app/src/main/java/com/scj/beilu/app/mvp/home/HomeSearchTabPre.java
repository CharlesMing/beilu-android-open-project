package com.scj.beilu.app.mvp.home;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.orhanobut.logger.Logger;
import com.scj.beilu.app.mvp.base.TabPresenter;
import com.scj.beilu.app.mvp.base.TabView;
import com.scj.beilu.app.mvp.home.bean.EventSearchTypeBean;
import com.scj.beilu.app.mvp.home.model.HomeInfoImpl;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;

/**
 * @author Mingxun
 * @time on 2019/4/12 02:59
 */
public class HomeSearchTabPre extends TabPresenter<HomeSearchTabPre.HomeSearchTabView> {

    private HomeInfoImpl mHomeInfo;

    private PublishSubject<EventSearchTypeBean> mPublishSubject;

    public HomeSearchTabPre(Context context) {
        super(context);

        mHomeInfo = new HomeInfoImpl(mBuilder);

    }

    public void startSearch(final EventSearchTypeBean searcContent) {
        onceViewAttached(new ViewAction<HomeSearchTabView>() {
            @Override
            public void run(@NonNull HomeSearchTabView mvpView) {
                if (mPublishSubject == null) {
                    mPublishSubject = PublishSubject.create();
                    mPublishSubject.debounce(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                            .filter(new Predicate<EventSearchTypeBean>() {
                                @Override
                                public boolean test(EventSearchTypeBean s) throws Exception {
                                    return s.keyName.length() > 0;
                                }
                            })
                            .switchMap(new Function<EventSearchTypeBean, Observable<EventSearchTypeBean>>() {
                                @Override
                                public Observable<EventSearchTypeBean> apply(EventSearchTypeBean content) throws Exception {
                                    return mHomeInfo.setContent(content);
                                }
                            })
                            .subscribe(new ObserverCallback<EventSearchTypeBean>(mBuilder.build(mvpView)) {
                                @Override
                                public void onNext(EventSearchTypeBean content) {
                                    EventBus.getDefault().post(content);
                                }
                            });

                }

                mPublishSubject.onNext(searcContent);
            }
        });
    }



    public interface HomeSearchTabView extends TabView {

    }
}
