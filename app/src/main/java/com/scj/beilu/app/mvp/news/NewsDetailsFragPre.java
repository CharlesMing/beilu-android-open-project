package com.scj.beilu.app.mvp.news;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.common.share.ShareInfoPre;
import com.scj.beilu.app.mvp.common.share.ShareInfoView;
import com.scj.beilu.app.mvp.news.bean.CommentCountBean;
import com.scj.beilu.app.mvp.news.bean.NewsDetailsBean;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;
import com.scj.beilu.app.mvp.news.model.INewsInfo;
import com.scj.beilu.app.mvp.news.model.NewsInfoImpl;

/**
 * @author Mingxun
 * @time on 2019/1/29 16:12
 */
public class NewsDetailsFragPre extends ShareInfoPre<NewsDetailsFragPre.NewsDetailsView> {

    private INewsInfo mINews;

    public NewsDetailsFragPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mINews = new NewsInfoImpl(mBuilder);
    }

    public void getDetailsById(final int newsId) {
        onceViewAttached(new ViewAction<NewsDetailsView>() {
            @Override
            public void run(@NonNull final NewsDetailsView mvpView) {
                mINews.getNewsDetailsById(newsId)
                        .subscribe(new BaseResponseCallback<NewsDetailsBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(NewsDetailsBean newsDetailsBean) {
                                mvpView.onNewsInfo(newsDetailsBean.getNewsDetail());
                            }
                        });
            }
        });
    }

    public void crateComment(final int newsId, final String content) {
        mBuilder.setLoadType(ShowConfig.NONE);
        onceViewAttached(new ViewAction<NewsDetailsView>() {
            @Override
            public void run(@NonNull final NewsDetailsView mvpView) {
                mINews.setParamsCreateComment(newsId, content)
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                mvpView.onCommentResult(resultMsgBean.getResult());
                            }
                        });
            }
        });
    }

    public void getCommentCount(final int newsId) {
        onceViewAttached(new ViewAction<NewsDetailsView>() {
            @Override
            public void run(@NonNull final NewsDetailsView mvpView) {
                mINews.getCommentCountByNewsId(newsId)
                        .subscribe(new BaseResponseCallback<CommentCountBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(CommentCountBean commentCountBean) {
                                mvpView.onCommentCountResult(commentCountBean.getNewsCommentCount());
                            }
                        });
            }
        });
    }

    public void setAttentionAction(final int newsUserId, final int isFocus) {
        onceViewAttached(new ViewAction<NewsDetailsView>() {
            @Override
            public void run(@NonNull final NewsDetailsView mvpView) {
                mINews.setAttentionParams(newsUserId, isFocus)
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                mvpView.onAttentionResult(resultMsgBean.getResult());
                            }
                        });
            }
        });
    }

    /**
     * 收藏动作
     *
     * @param newsId        资讯Id
     * @param collectStatus 状态设置 1 0
     */
    public void setCollectAction(final int newsId, final int collectStatus) {
        onceViewAttached(new ViewAction<NewsDetailsView>() {
            @Override
            public void run(@NonNull final NewsDetailsView mvpView) {
                mINews.setNewsCollectAction(newsId, collectStatus)
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                mvpView.onCollectResult(resultMsgBean.getResult());
                            }
                        });
            }
        });
    }

    public interface NewsDetailsView extends ShareInfoView {
        void onNewsInfo(NewsInfoBean infoBean);

        void onCommentResult(String result);

        void onCommentCountResult(int count);

        void onAttentionResult(String result);

        void onCollectResult(String msg);
    }

}
