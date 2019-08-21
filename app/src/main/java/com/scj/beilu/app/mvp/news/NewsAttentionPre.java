package com.scj.beilu.app.mvp.news;

import android.content.Context;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.news.bean.NewsAuthorInfoBean;
import com.scj.beilu.app.mvp.news.bean.NewsAuthorListBean;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;
import com.scj.beilu.app.mvp.news.bean.NewsListBean;
import com.scj.beilu.app.mvp.news.model.INewsInfo;
import com.scj.beilu.app.mvp.news.model.NewsInfoImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/5/11 17:44
 */
public class NewsAttentionPre extends BaseMvpPresenter<NewsAttentionPre.NewsAttentionView> {

    private INewsInfo mNewsInfo;
    private final List<NewsAuthorInfoBean> mRecommendAuthorList = new ArrayList<>();
    private final List<NewsInfoBean> mNewsInfoBeans = new ArrayList<>();

    public NewsAttentionPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mNewsInfo = new NewsInfoImpl(mBuilder);
    }

    public void getAttentionList() {
        onceViewAttached(mvpView ->
                mNewsInfo.getNewsAuthorAttentionList(0)
                        .subscribe(new BaseResponseCallback<NewsAuthorListBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(NewsAuthorListBean newsAuthorListBean) {
                                try {
                                    List<NewsAuthorInfoBean> list = newsAuthorListBean.getPage().getList();
                                    mvpView.onCheckLoadMore(list);
                                    if (list.size() == 0) {
                                        getRecommendAuthorList();
                                    } else {
                                        mvpView.onAttentionList(list);
                                        getRecommendNewsList(2, 0);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }));
    }

    public void getRecommendAuthorList() {

        onceViewAttached(mvpView -> {
                    mvpView.onInitRecommendView();
                    Observable.mergeArray(mNewsInfo.getRecommendAuthor(0),
                            mNewsInfo.setNewsArrayByNavId(1, 0))
                            .subscribe(new ObserverCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                                @Override
                                public void onNext(ResultMsgBean resultMsgBean) {
                                    try {
                                        if (resultMsgBean instanceof NewsAuthorListBean) {
                                            NewsAuthorListBean newsAuthorListBean = (NewsAuthorListBean) resultMsgBean;
                                            List<NewsAuthorInfoBean> list = newsAuthorListBean.getPage().getList();
                                            mRecommendAuthorList.clear();
                                            mRecommendAuthorList.addAll(list);
                                            mvpView.onNotAttentionList(mRecommendAuthorList);
                                        } else if (resultMsgBean instanceof NewsListBean) {
                                            NewsListBean newsList = (NewsListBean) resultMsgBean;
                                            List<NewsInfoBean> list = newsList.getPage().getList();
                                            mNewsInfoBeans.clear();
                                            mNewsInfoBeans.addAll(list);
                                            mvpView.onCheckLoadMore(list);
                                            mvpView.onNewsList(mNewsInfoBeans);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }
                                }
                            });
                }

        );
    }

    public void setAttentionAuthor(int newsUserId) {
        onceViewAttached(mvpView -> mNewsInfo.setAttentionParams(newsUserId, -1)
                .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(ResultMsgBean resultMsgBean) {
                        try {
                            int size = mRecommendAuthorList.size();
                            int notifyPosition = -1;
                            for (int i = 0; i < size; i++) {
                                NewsAuthorInfoBean authorInfoBean = mRecommendAuthorList.get(i);
                                if (authorInfoBean.getUserId() == newsUserId) {
                                    authorInfoBean.setIsFocus(authorInfoBean.getIsFocus() == 1 ? 0 : 1);
                                    mRecommendAuthorList.set(i, authorInfoBean);
                                    notifyPosition = i;
                                    break;
                                }
                            }
                            mvpView.onNotifyAttentionStatus(notifyPosition);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }));
    }

    public void getRecommendNewsList(int navId, int currentPage) {
        onceViewAttached(mvpView -> mNewsInfo.setNewsArrayByNavId(navId, currentPage)
                .subscribe(new BaseResponseCallback<NewsListBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(NewsListBean newsListBean) {
                        try {
                            List<NewsInfoBean> list = newsListBean.getPage().getList();
                            mvpView.onCheckLoadMore(list);
                            if (currentPage == 0) {
                                mNewsInfoBeans.clear();
                            }
                            mNewsInfoBeans.addAll(list);
                            mvpView.onNewsList(mNewsInfoBeans);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }));
    }

    public interface NewsAttentionView extends BaseCheckArrayView {
        void onAttentionList(List<NewsAuthorInfoBean> authorInfoList);

        void onInitRecommendView();

        void onNotAttentionList(List<NewsAuthorInfoBean> authorInfoList);

        void onNewsList(List<NewsInfoBean> newsInfoList);

        void onNotifyAttentionStatus(int position);
    }
}
