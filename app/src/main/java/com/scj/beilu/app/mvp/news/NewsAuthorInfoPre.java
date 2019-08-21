package com.scj.beilu.app.mvp.news;

import android.content.Context;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.news.bean.NewsAuthorInfoBean;
import com.scj.beilu.app.mvp.news.bean.NewsAuthorInfoResultBean;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;
import com.scj.beilu.app.mvp.news.bean.NewsListBean;
import com.scj.beilu.app.mvp.news.model.INewsInfo;
import com.scj.beilu.app.mvp.news.model.NewsInfoImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/5/13 12:09
 */
public class NewsAuthorInfoPre extends BaseMvpPresenter<NewsAuthorInfoPre.NewsAuthorInfoView> {

    private INewsInfo mNewsInfo;
    private final List<NewsInfoBean> mNewsInfoBeans = new ArrayList<>();

    public NewsAuthorInfoPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mNewsInfo = new NewsInfoImpl(mBuilder);
    }

    public void getAuthorInfo(int authorId) {
        mBuilder.setLoadType(ShowConfig.LOADING_DIALOG);
        onceViewAttached(mvpView ->
                Observable.mergeArray(mNewsInfo.getAuthorInfoById(authorId), mNewsInfo.getNewsListByAuthorInfo(authorId, 0))
                        .subscribe(new ObserverCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(ResultMsgBean resultMsgBean) {
                                try {
                                    if (resultMsgBean instanceof NewsAuthorInfoResultBean) {
                                        NewsAuthorInfoResultBean authorInfo = (NewsAuthorInfoResultBean) resultMsgBean;
                                        NewsAuthorInfoBean mediaUser = authorInfo.getMediaUser();
                                        mvpView.onAuthorInfo(mediaUser);
                                    } else if (resultMsgBean instanceof NewsListBean) {
                                        NewsListBean newsList = (NewsListBean) resultMsgBean;
                                        List<NewsInfoBean> list = newsList.getPage().getList();
                                        mNewsInfoBeans.clear();
                                        mNewsInfoBeans.addAll(list);
                                        mvpView.onNewsList(mNewsInfoBeans);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }));
    }

    public void getNewsList(int authorId, int currentPage) {
        mBuilder.setLoadType(ShowConfig.LOADING_REFRESH);
        onceViewAttached(mvpView -> mNewsInfo.getNewsListByAuthorInfo(authorId, currentPage)
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

    public void setAttentionAuthor(int newsUserId) {
        onceViewAttached(mvpView -> mNewsInfo.setAttentionParams(newsUserId, -1)
                .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(ResultMsgBean resultMsgBean) {
                        try {
                            mvpView.onAttentionResult(resultMsgBean.getResult());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }));
    }

    public interface NewsAuthorInfoView extends BaseCheckArrayView {
        void onAuthorInfo(NewsAuthorInfoBean authorInfoBean);


        void onNewsList(List<NewsInfoBean> newsInfoList);

        void onAttentionResult(String result);
    }

}
