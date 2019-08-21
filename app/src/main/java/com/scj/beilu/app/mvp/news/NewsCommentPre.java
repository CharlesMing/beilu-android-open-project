package com.scj.beilu.app.mvp.news;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.news.bean.NewsCommentBean;
import com.scj.beilu.app.mvp.news.bean.NewsDetailsCommentBean;
import com.scj.beilu.app.mvp.news.bean.NewsReplyCommentBean;
import com.scj.beilu.app.mvp.news.model.INewsInfo;
import com.scj.beilu.app.mvp.news.model.NewsInfoImpl;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/15 21:54
 */
public class NewsCommentPre extends BaseMvpPresenter<NewsCommentPre.NewsCommentView> {
    private INewsInfo mINews;

    //评论列表数据
    private final List<NewsCommentBean> mCommentBeans = new ArrayList<NewsCommentBean>();
    private int mCurrentPage;

    public NewsCommentPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mINews = new NewsInfoImpl(mBuilder);
    }

    public void getCommentList(final int newsId, final int currentPage) {
        mCurrentPage = currentPage;
        onceViewAttached(new ViewAction<NewsCommentView>() {
            @Override
            public void run(@NonNull final NewsCommentView mvpView) {
                mINews.setParamsNewsDetailsCommentByNewsId(newsId, currentPage)
                        .subscribe(new BaseResponseCallback<NewsDetailsCommentBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(NewsDetailsCommentBean newsDetailsCommentBean) {
                                List<NewsCommentBean> commentList = newsDetailsCommentBean.getPage().getList();
                                if (mCurrentPage == 0) {
                                    mCommentBeans.clear();
                                }
                                mvpView.onCheckLoadMore(commentList);
                                mCommentBeans.addAll(commentList);
                                mvpView.onCommentList(mCommentBeans);
                            }
                        });
            }
        });
    }


    public void crateComment(final int newsId, final String content) {
        mBuilder.setLoadType(ShowConfig.NONE);
        onceViewAttached(new ViewAction<NewsCommentView>() {
            @Override
            public void run(@NonNull final NewsCommentView mvpView) {
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

    public void replyCommentContent(final int newsComId, final int toUserId, final String commentContent) {
        onceViewAttached(new ViewAction<NewsCommentView>() {
            @Override
            public void run(@NonNull final NewsCommentView mvpView) {
                mINews.setReplyCommentContent(newsComId, toUserId, commentContent)
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                mvpView.onReplyResult(resultMsgBean.getResult());

                            }
                        });
            }
        });
    }

    /**
     * 将回复的评论内容显示到列表中
     */
    public void notifyReplyContentChanged(final int position, final NewsCommentBean replyToUser,
                                          final NewsReplyCommentBean replyChild, final String commentContent) {
        onceViewAttached(new ViewAction<NewsCommentView>() {
            @Override
            public void run(@NonNull final NewsCommentView mvpView) {
                mINews.getUserInfoByToken()
                        .subscribe(new ObserverCallback<UserInfoEntity>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(UserInfoEntity userInfoEntity) {
                                NewsReplyCommentBean replyCommentBean = null;
                                if (replyToUser != null) {//创建评论时
                                    replyCommentBean = setReplyContent(replyToUser.getComId(), (int) userInfoEntity.getUserId(),
                                            userInfoEntity.getUserNickName(), replyToUser.getUserId(),
                                            replyToUser.getUserName(), commentContent);
                                } else if (replyChild != null) {//回复评论时
                                    replyCommentBean = setReplyContent(replyChild.getNewsComId(), (int) userInfoEntity.getUserId(),
                                            userInfoEntity.getUserNickName(),
                                            replyChild.getToUserId(), replyChild.getToUserName(), commentContent);
                                }

                                if (replyCommentBean != null) {
                                    List<NewsReplyCommentBean> replyList = mCommentBeans.get(position).getNewsComReplyList();
                                    replyList.add(replyCommentBean);

                                    mvpView.notifyItemChange(position);
                                }
                            }
                        });
            }
        });
    }

    private NewsReplyCommentBean setReplyContent(int newsComId, int fromUserId, String fromUserName, int toUserId, String toUserName, String commentContent) {
        NewsReplyCommentBean replyBean = new NewsReplyCommentBean();
        replyBean.setFromUserId(fromUserId);
        replyBean.setFromUserName(fromUserName);
        replyBean.setToUserName(toUserName);
        replyBean.setToUserId(toUserId);
        replyBean.setNewsComId(newsComId);
        replyBean.setNewsReplyContent(commentContent);
        return replyBean;
    }

    public interface NewsCommentView extends BaseCheckArrayView {

        void onCommentList(List<NewsCommentBean> commentList);

        void onReplyResult(String result);

        void onCommentResult(String result);

        void notifyItemChange(int position);
    }
}
