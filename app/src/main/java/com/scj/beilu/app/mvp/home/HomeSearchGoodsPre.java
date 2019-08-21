package com.scj.beilu.app.mvp.home;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;
import com.scj.beilu.app.mvp.find.bean.FindListBean;
import com.scj.beilu.app.mvp.find.model.FindImpl;
import com.scj.beilu.app.mvp.find.model.IFindInfo;
import com.scj.beilu.app.mvp.home.model.HomeInfoImpl;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoBean;
import com.scj.beilu.app.mvp.user.bean.RecommendUserInfoBean;

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
public class HomeSearchGoodsPre extends BaseMvpPresenter<HomeSearchGoodsPre.HomeSearchView> {

    private PublishSubject<String> mPublishSubject;

    private IFindInfo mFindInfo;
    private HomeInfoImpl mHomeInfo;

    private List<FindInfoBean> mFindInfoList;

    public HomeSearchGoodsPre(Context context) {
        super(context, ShowConfig.LOADING_STATE, ShowConfig.ERROR_TOAST);
        mFindInfo = new FindImpl(mBuilder);
        mHomeInfo = new HomeInfoImpl(mBuilder);
    }


    public void startSearch(final int indexType, final String keyName, final int currentPage) {

        onceViewAttached(new ViewAction<HomeSearchView>() {
            @Override
            public void run(@NonNull HomeSearchView mvpView) {
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
                                    switch (indexType) {
                                        case 0:
                                            searchFindInfoList(keyName, currentPage);
                                            break;
                                        case 1:
                                            searchNewsInfoList(keyName, currentPage);
                                            break;
                                        case 2:
                                            searchGoodsInfoList(keyName, currentPage);
                                            break;
                                        case 3:
                                            searchUserInfoList(keyName, currentPage);
                                            break;
                                    }
                                }
                            });

                }

                mPublishSubject.onNext(keyName);
            }
        });
    }

    public void startSearchNextPage(final int indexType, final String keyName, final int currentPage) {
        onceViewAttached(new ViewAction<HomeSearchView>() {
            @Override
            public void run(@NonNull HomeSearchView mvpView) {
                switch (indexType) {
                    case 0:
                        searchFindInfoList(keyName, currentPage);
                        break;
                    case 1:
                        searchNewsInfoList(keyName, currentPage);
                        break;
                    case 2:
                        searchGoodsInfoList(keyName, currentPage);
                        break;
                    case 3:
                        searchUserInfoList(keyName, currentPage);
                        break;
                }
            }
        });
    }

    private void searchFindInfoList(final String keyName, final int currentPage) {
        onceViewAttached(new ViewAction<HomeSearchView>() {
            @Override
            public void run(@NonNull final HomeSearchView mvpView) {
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

                                    mvpView.onFindInfoList(mFindInfoList);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        });
            }
        });
    }

    private void searchNewsInfoList(String keyName, int currentPage) {
        onceViewAttached(new ViewAction<HomeSearchView>() {
            @Override
            public void run(@NonNull HomeSearchView mvpView) {

            }
        });
    }

    private void searchGoodsInfoList(String keyName, int currentPage) {
        onceViewAttached(new ViewAction<HomeSearchView>() {
            @Override
            public void run(@NonNull HomeSearchView mvpView) {

            }
        });
    }

    private void searchUserInfoList(String keyName, int currentPage) {
        onceViewAttached(new ViewAction<HomeSearchView>() {
            @Override
            public void run(@NonNull HomeSearchView mvpView) {

            }
        });
    }

    public interface HomeSearchView extends BaseCheckArrayView {
        void onFindInfoList(List<FindInfoBean> findInfoList);

        void onNewsInfoList(List<NewsInfoBean> newsInfoList);

        void onGoodsInfoList(List<GoodsInfoBean> goodsInfoList);

        void onUserInfoList(List<RecommendUserInfoBean> userInfoList);
    }
}
