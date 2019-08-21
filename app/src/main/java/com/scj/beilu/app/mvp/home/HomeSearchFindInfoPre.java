package com.scj.beilu.app.mvp.home;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.scj.beilu.app.mvp.find.FindInfoPre;
import com.scj.beilu.app.mvp.find.FindInfoView;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;
import com.scj.beilu.app.mvp.find.bean.FindListBean;
import com.scj.beilu.app.mvp.find.model.FindImpl;
import com.scj.beilu.app.mvp.find.model.IFindInfo;
import com.scj.beilu.app.mvp.home.model.HomeInfoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;

/**
 * @author Mingxun
 * @time on 2019/4/12 02:07
 */
public class HomeSearchFindInfoPre extends FindInfoPre<FindInfoView> {

    private PublishSubject<String> mPublishSubject;

    private IFindInfo mFindInfo;
    private HomeInfoImpl mHomeInfo;

    private List<FindInfoBean> mFindInfoList;

    public HomeSearchFindInfoPre(Context context) {
        super(context);
        mFindInfo = new FindImpl(mBuilder);
        mHomeInfo = new HomeInfoImpl(mBuilder);
    }


    public void startSearch(final String keyName, final int currentPage) {

        onceViewAttached(new ViewAction<FindInfoView>() {
            @Override
            public void run(@NonNull FindInfoView mvpView) {
                if (mPublishSubject == null) {
                    mPublishSubject = PublishSubject.create();
                    mPublishSubject.debounce(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                            .filter(new Predicate<String>() {
                                @Override
                                public boolean test(String s) throws Exception {
                                    return s.length() > 0;
                                }
                            })

                            .switchMap(new Function<String, Observable<String>>() {
                                @Override
                                public Observable<String> apply(String s) throws Exception {
                                    return mHomeInfo.content(s);
                                }
                            })
                            .subscribe(new ObserverCallback<String>(mBuilder.build(mvpView)) {
                                @Override
                                public void onNext(String keyName) {
                                    searchFindInfoList(keyName, currentPage);
                                }
                            });

                }

                mPublishSubject.onNext(keyName);
            }
        });
    }


    private void searchFindInfoList(final String keyName, final int currentPage) {
        onceViewAttached(new ViewAction<FindInfoView>() {
            @Override
            public void run(@NonNull final FindInfoView mvpView) {
                mFindInfo.startSearchFind(currentPage, keyName)
                        .subscribe(new ObserverCallback<FindListBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(FindListBean findListBean) {
                                try {
                                    List<FindInfoBean> list = findListBean.getPage().getList();
                                    if (currentPage == 0) {
                                        if (mFindInfoList == null) {
                                            mFindInfoList = new ArrayList<>();
                                        }
                                        mFindInfoList.clear();
                                    }
                                    if (list == null) {
                                        list = new ArrayList<>();
                                    }
                                    mFindInfoList.addAll(list);
                                    mvpView.onCheckLoadMore(list);

                                    mvpView.onFindListResult(mFindInfoList);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        });
            }
        });
    }

}
