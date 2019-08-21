package com.scj.beilu.app.mvp.mine.message;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.mine.message.bean.MsgCommentInfoBean;
import com.scj.beilu.app.mvp.mine.message.bean.MsgCommentInfoResultBean;
import com.scj.beilu.app.mvp.mine.message.bean.MsgLikeInfoBean;
import com.scj.beilu.app.mvp.mine.message.bean.MsgLikeInfoResultBean;
import com.scj.beilu.app.mvp.mine.message.bean.MsgReplyInfoBean;
import com.scj.beilu.app.mvp.mine.message.bean.MsgReplyInfoResultBean;
import com.scj.beilu.app.mvp.mine.message.model.IMsgManager;
import com.scj.beilu.app.mvp.mine.message.model.MsgManagerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/1/14 20:01
 */
public class MsgManagerFragPre extends BaseMvpPresenter<MsgManagerFragPre.MsgManagerFragView> {

    private IMsgManager mMsgManager;

    private final List<MsgReplyInfoBean> mMsgReplyInfoBeans = new ArrayList<>();
    private final List<MsgCommentInfoBean> mCommentInfoBeans = new ArrayList<>();
    private final List<MsgLikeInfoBean> mMsgLikeInfoBeans = new ArrayList<>();

    public MsgManagerFragPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mMsgManager = new MsgManagerImpl(mBuilder);
    }

    public void getMsgList(int index, int currentPage) {
        switch (index) {
            case 0:
                getReplyList(currentPage);
                break;
            case 1:
                getLikeList(currentPage);
                break;
            case 2:
                getCommentList(currentPage);
                break;
        }
    }

    private void getReplyList(final int currentPage) {
        onceViewAttached(new ViewAction<MsgManagerFragView>() {
            @Override
            public void run(@NonNull final MsgManagerFragView mvpView) {
                mMsgManager.getReplyMsgList(currentPage)
                        .subscribe(new BaseResponseCallback<MsgReplyInfoResultBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(MsgReplyInfoResultBean msgReplyInfoResultBean) {
                                try {
                                    List<MsgReplyInfoBean> list = msgReplyInfoResultBean.getPage().getList();
                                    if (currentPage == 0) {
                                        mMsgReplyInfoBeans.clear();
                                    }
                                    mMsgReplyInfoBeans.addAll(list);
                                    mvpView.onCheckLoadMore(list);
                                    mvpView.onReplyList(mMsgReplyInfoBeans);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    private void getCommentList(final int currentPage) {
        onceViewAttached(new ViewAction<MsgManagerFragView>() {
            @Override
            public void run(@NonNull final MsgManagerFragView mvpView) {
                mMsgManager.getCommentMsgList(currentPage)
                        .subscribe(new BaseResponseCallback<MsgCommentInfoResultBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(MsgCommentInfoResultBean msgCommentInfoResultBean) {
                                try {
                                    List<MsgCommentInfoBean> list = msgCommentInfoResultBean.getPage().getList();

                                    if (currentPage == 0) {
                                        mCommentInfoBeans.clear();
                                    }
                                    mCommentInfoBeans.addAll(list);
                                    mvpView.onCheckLoadMore(list);
                                    mvpView.onCommentList(mCommentInfoBeans);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    private void getLikeList(final int currentPage) {
        onceViewAttached(new ViewAction<MsgManagerFragView>() {
            @Override
            public void run(@NonNull final MsgManagerFragView mvpView) {
                mMsgManager.getLikeMsgList(currentPage)
                        .subscribe(new BaseResponseCallback<MsgLikeInfoResultBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(MsgLikeInfoResultBean msgLikeInfoResultBean) {
                                try {
                                    List<MsgLikeInfoBean> list = msgLikeInfoResultBean.getPage().getList();
                                    if (currentPage == 0) {
                                        mMsgLikeInfoBeans.clear();
                                    }
                                    mMsgLikeInfoBeans.addAll(list);
                                    mvpView.onCheckLoadMore(list);
                                    mvpView.onLikeList(mMsgLikeInfoBeans);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    public interface MsgManagerFragView extends BaseCheckArrayView {
        void onReplyList(List<MsgReplyInfoBean> replyInfoBeanList);

        void onLikeList(List<MsgLikeInfoBean> likeInfoBeanList);

        void onCommentList(List<MsgCommentInfoBean> commentInfoBeanList);
    }
}
