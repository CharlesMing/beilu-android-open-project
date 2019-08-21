package com.scj.beilu.app.mvp.news;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.common.entity.SearchHistoryEntity;
import com.scj.beilu.app.mvp.common.entity.SearchType;
import com.scj.beilu.app.mvp.news.bean.NewsListBean;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;
import com.scj.beilu.app.mvp.news.model.NewsInfoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;

/**
 * @author Mingxun
 * @time on 2019/2/18 10:29
 */
public class NewsSearchPre extends BaseMvpPresenter<NewsSearchPre.NewsSearchView> {
    private NewsInfoImpl mNewsImpl;
    private PublishSubject<String> mPublishSubject;
    private int mCurrentPage = -1;

    private final List<NewsInfoBean> mNewsInfoBeans = new ArrayList<>();

    public NewsSearchPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mNewsImpl = new NewsInfoImpl(mBuilder);
    }


    public void startSearch(final String keyWord, final int currentPage) {
        mCurrentPage = currentPage;
        onceViewAttached(new ViewAction<NewsSearchView>() {
            @Override
            public void run(@NonNull final NewsSearchView mvpView) {
                if (mPublishSubject == null) {
                    mPublishSubject = PublishSubject.create();
                    mPublishSubject.debounce(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                            .filter(new Predicate<String>() {
                                @Override
                                public boolean test(String s) throws Exception {
                                    return s.length() > 0;
                                }
                            })
                            .switchMap(new Function<String, ObservableSource<NewsListBean>>() {
                                @Override
                                public ObservableSource<NewsListBean> apply(String s) throws Exception {
                                    addHistory(s);
                                    return mNewsImpl.searchNewsParams(s, mCurrentPage);
                                }
                            })
                            .subscribe(new BaseResponseCallback<NewsListBean>(mBuilder.build(mvpView)) {
                                @Override
                                public void onRequestResult(NewsListBean newsArrayBean) {
                                    List<NewsInfoBean> list = newsArrayBean.getPage().getList();

                                    if (mCurrentPage == 0) {
                                        mNewsInfoBeans.clear();
                                    }

                                    mvpView.onCheckLoadMore(list);
                                    mNewsInfoBeans.addAll(list);
                                    mvpView.onNewsList(mNewsInfoBeans);

                                }
                            });
                }
                mPublishSubject.onNext(keyWord);
            }
        });
    }

    private void addHistory(final String tagName) {
        onceViewAttached(new ViewAction<NewsSearchView>() {
            @Override
            public void run(@NonNull final NewsSearchView mvpView) {
                mNewsImpl.addSearchContentToData(tagName, SearchType.SEARCH_NEWS)
                        .filter(new Predicate<Long>() {
                            @Override
                            public boolean test(Long aLong) throws Exception {
                                return aLong > 0;
                            }
                        })
                        .switchMap(new Function<Long, ObservableSource<List<SearchHistoryEntity>>>() {
                            @Override
                            public ObservableSource<List<SearchHistoryEntity>> apply(Long aLong) throws Exception {
                                return mNewsImpl.getHistoryList(SearchType.SEARCH_NEWS);
                            }
                        })
                        .subscribe(new ObserverCallback<List<SearchHistoryEntity>>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(List<SearchHistoryEntity> historyEntities) {
                                mvpView.onHistoryList(historyEntities);
                            }
                        });
            }
        });
    }

    public void getSearchHistoryList() {
        onceViewAttached(new ViewAction<NewsSearchView>() {
            @Override
            public void run(@NonNull final NewsSearchView mvpView) {
                mNewsImpl.getHistoryList(SearchType.SEARCH_NEWS)
                        .subscribe(new ObserverCallback<List<SearchHistoryEntity>>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(List<SearchHistoryEntity> newsSearchHistoryEntities) {
                                mvpView.onHistoryList(newsSearchHistoryEntities);
                            }
                        });
            }
        });
    }


    public interface NewsSearchView extends BaseCheckArrayView {
        void onHistoryList(List<SearchHistoryEntity> historyList);

        void onNewsList(List<NewsInfoBean> newsList);
    }

}
