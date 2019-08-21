package com.scj.beilu.app.mvp.news;

import android.content.Context;

import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.news.bean.NewsAuthorInfoBean;
import com.scj.beilu.app.mvp.news.bean.NewsAuthorListBean;
import com.scj.beilu.app.mvp.news.model.INewsInfo;
import com.scj.beilu.app.mvp.news.model.NewsInfoImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/5/13 11:06
 */
public class NewsMyAttentionAuthorListPre extends BaseMvpPresenter<NewsMyAttentionAuthorListPre.NewsMyAttentionAuthorListView> {

    private INewsInfo mNewsInfo;
    private final List<NewsAuthorInfoBean> mRecommendAuthorList = new ArrayList<>();

    public NewsMyAttentionAuthorListPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mNewsInfo = new NewsInfoImpl(mBuilder);
    }

    public void getMyAttentionAuthorList(int currentPage) {
        onceViewAttached(mvpView -> mNewsInfo.getNewsAuthorAttentionList(currentPage)
                .subscribe(new BaseResponseCallback<NewsAuthorListBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(NewsAuthorListBean newsAuthorListBean) {
                        try {
                            List<NewsAuthorInfoBean> list = newsAuthorListBean.getPage().getList();
                            mvpView.onCheckLoadMore(list);
                            if (currentPage == 0) {
                                mRecommendAuthorList.clear();
                            }
                            mRecommendAuthorList.addAll(list);
                            mvpView.onMyAttentionAuthorList(mRecommendAuthorList);
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

    public void getRecommendAuthorList(int currentPage) {
        onceViewAttached(mvpView -> mNewsInfo.getRecommendAuthor(currentPage)
                .subscribe(new BaseResponseCallback<NewsAuthorListBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(NewsAuthorListBean newsAuthorListBean) {
                        List<NewsAuthorInfoBean> list = newsAuthorListBean.getPage().getList();
                        mvpView.onCheckLoadMore(list);
                        if (currentPage == 0) {
                            mRecommendAuthorList.clear();
                        }
                        mRecommendAuthorList.addAll(list);
                        mvpView.onMyAttentionAuthorList(mRecommendAuthorList);
                    }
                }));
    }

    public interface NewsMyAttentionAuthorListView extends BaseCheckArrayView {
        void onMyAttentionAuthorList(List<NewsAuthorInfoBean> authorInfoList);

        void onNotifyAttentionStatus(int position);
    }
}
