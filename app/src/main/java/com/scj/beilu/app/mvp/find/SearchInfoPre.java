package com.scj.beilu.app.mvp.find;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.mvp.common.entity.SearchHistoryEntity;
import com.scj.beilu.app.mvp.common.entity.SearchType;
import com.scj.beilu.app.mvp.common.search.ISearchInfo;
import com.scj.beilu.app.mvp.common.search.SearchImpl;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;
import com.scj.beilu.app.mvp.find.bean.FindDetailsRecommendBean;
import com.scj.beilu.app.mvp.find.bean.FindListBean;
import com.scj.beilu.app.mvp.find.model.FindImpl;
import com.scj.beilu.app.mvp.find.model.IFindInfo;
import com.scj.beilu.app.mvp.user.bean.RecommendUserInfoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;

/**
 * @author Mingxun
 * @time on 2019/4/10 19:21
 */
public class SearchInfoPre extends FindInfoPre<SearchInfoPre.SearchLoadHistoryView> {

    private ISearchInfo mSearchInfo;
    private IFindInfo mFindInfo;

    private PublishSubject<String> mPublishSubject;


    public SearchInfoPre(Context context) {
        super(context);
        mSearchInfo = new SearchImpl(mBuilder);
        mFindInfo = new FindImpl(mBuilder);
    }

    public void getCurrentPageData() {
        onceViewAttached(new ViewAction<SearchLoadHistoryView>() {
            @Override
            public void run(@NonNull final SearchLoadHistoryView mvpView) {
                Observable.merge(mSearchInfo.getHistoryList(SearchType.SEARCH_FIND), mFindInfo.getRecommendList())
                        .subscribe(new ObserverCallback<Object>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(Object result) {
                                if (result instanceof ArrayList) {
                                    List<SearchHistoryEntity> searchHistoryEntities = (List<SearchHistoryEntity>) result;
                                    mvpView.onSearchHistoryList(searchHistoryEntities);
                                } else if (result instanceof FindDetailsRecommendBean) {
                                    try {
                                        if (mUserInfoList == null) {
                                            mUserInfoList = new ArrayList<>();
                                        }

                                        FindDetailsRecommendBean userInfo = (FindDetailsRecommendBean) result;
                                        List<RecommendUserInfoBean> users = userInfo.getUsers();
                                        mUserInfoList.addAll(users);

                                        mvpView.onUserList(mUserInfoList);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
            }
        });
    }




    public void startSearch(final String txt) {
        mBuilder.setLoadType(ShowConfig.LOADING_REFRESH);
        onceViewAttached(new ViewAction<SearchLoadHistoryView>() {
            @Override
            public void run(@NonNull final SearchLoadHistoryView mvpView) {
                if (mPublishSubject == null) {
                    mPublishSubject = PublishSubject.create();
                    mPublishSubject.debounce(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                            .filter(new Predicate<String>() {
                                @Override
                                public boolean test(String s) throws Exception {
                                    return s.length() > 0;
                                }
                            })
                            .switchMap(new Function<String, ObservableSource<FindListBean>>() {
                                @Override
                                public ObservableSource<FindListBean> apply(String s) throws Exception {
                                    return mFindInfo.startSearchFind(0, s);
                                }
                            })
                            .subscribe(new ObserverCallback<FindListBean>(mBuilder.build(mvpView)) {
                                @Override
                                public void onNext(FindListBean findListBean) {
                                    try {
                                        List<FindInfoBean> list = findListBean.getPage().getList();
                                        mFindList.clear();
                                        if (list == null) {
                                            list = new ArrayList<>();
                                        }
                                        mFindList.addAll(list);
                                        mvpView.onCheckLoadMore(list);

                                        mvpView.onFindListResult(mFindList);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        mSearchInfo.addSearchContentToData(txt, SearchType.SEARCH_FIND)
                                                .subscribe(new ObserverCallback<Long>(mBuilder.build(mvpView)) {
                                                    @Override
                                                    public void onNext(Long aLong) {
                                                        //none
                                                    }
                                                });
                                    }
                                }
                            });

                }

                mPublishSubject.onNext(txt);
            }
        });
    }

    public interface SearchLoadHistoryView extends FindInfoView {
        void onSearchHistoryList(List<SearchHistoryEntity> searchHistoryList);

    }

}
