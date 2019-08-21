package com.scj.beilu.app.mvp.find;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.common.share.ShareInfoPre;
import com.scj.beilu.app.mvp.common.share.ShareInfoView;
import com.scj.beilu.app.mvp.find.bean.FindCommentBean;
import com.scj.beilu.app.mvp.find.bean.FindCommentListBean;
import com.scj.beilu.app.mvp.find.bean.FindDetailsBean;
import com.scj.beilu.app.mvp.find.bean.FindDetailsViewBean;
import com.scj.beilu.app.mvp.find.model.FindImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/2/22 21:04
 */
public class FindDetailsPre extends ShareInfoPre<FindDetailsPre.FindDetailsView> {
    private FindImpl mFindImpl;
    private int mDynamicId;
    private FindDetailsBean mFindDetailsBean = null;
    private FindCommentListBean mCommentListBean = null;
    private final List<FindCommentBean> mFindCommentList = new ArrayList<>();

    public FindDetailsPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mFindImpl = new FindImpl(mBuilder);
    }

    public void getDetails(int dynamicId) {
        mDynamicId = dynamicId;
        onceViewAttached(new ViewAction<FindDetailsView>() {
            @Override
            public void run(@NonNull final FindDetailsView mvpView) {

                Observable.merge(mFindImpl.getFindDetailsById(mDynamicId), mFindImpl.getFindCommentList(mDynamicId, 0))
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ResultMsgBean result) {
                                try {
                                    if (result instanceof FindCommentListBean) {
                                        mCommentListBean = (FindCommentListBean) result;
                                        List<FindCommentBean> commentBeanList = mCommentListBean.getList();
                                        mFindCommentList.clear();
                                        mvpView.onCheckLoadMore(commentBeanList);
                                        mFindCommentList.addAll(commentBeanList);
                                    } else if (result instanceof FindDetailsBean) {
                                        mFindDetailsBean = (FindDetailsBean) result;
                                    }
                                    FindDetailsViewBean findDetailsInfo = new FindDetailsViewBean(mFindDetailsBean, mCommentListBean);
                                    mvpView.onDetailsResult(findDetailsInfo, mFindCommentList);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    public void getCommentLit(final int currentPage) {
        onceViewAttached(new ViewAction<FindDetailsView>() {
            @Override
            public void run(@NonNull final FindDetailsView mvpView) {
                mFindImpl.getFindCommentList(mDynamicId, currentPage)
                        .subscribe(new BaseResponseCallback<FindCommentListBean>(
                                mBuilder.build(mvpView)
                        ) {
                            @Override
                            public void onRequestResult(FindCommentListBean findCommentListBean) {
                                List<FindCommentBean> findCommentBeanList =
                                        findCommentListBean.getPage().getList();
                                if (currentPage == 0) {
                                    mFindCommentList.clear();
                                }
                                mvpView.onCheckLoadMore(findCommentBeanList);
                                mFindCommentList.addAll(findCommentBeanList);
                                mvpView.onCommentListResult(mFindCommentList);
                            }
                        });

            }
        });
    }

    /**
     * 直接通过FindDetailsBean 获取动态id
     */
    public void setLike() {
        if (mFindDetailsBean == null) return;
        onceViewAttached(new ViewAction<FindDetailsView>() {
            @Override
            public void run(@NonNull final FindDetailsView mvpView) {
                mFindImpl.setLikeParamsByDetails(mFindDetailsBean)
                        .subscribe(new BaseResponseCallback<FindDetailsBean>(
                                mBuilder.build(mvpView)
                        ) {
                            @Override
                            public void onRequestResult(FindDetailsBean detailsBean) {
                                mvpView.onUpdateLikeResult(detailsBean);
                            }
                        });
            }
        });
    }

    public void setAttention() {
        if (mFindDetailsBean == null) return;
        onceViewAttached(new ViewAction<FindDetailsView>() {
            @Override
            public void run(@NonNull final FindDetailsView mvpView) {
                mFindImpl.setAttentionParamsByDetails(mFindDetailsBean)
                        .subscribe(new BaseResponseCallback<FindDetailsBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(final FindDetailsBean resultMsgBean) {
                                try {
                                    mvpView.onUpdateAttentionResult(resultMsgBean);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }


    public void checkIsOwn(final FindCommentBean commentInfo) {
        onceViewAttached(new ViewAction<FindDetailsView>() {
            @Override
            public void run(@NonNull final FindDetailsView mvpView) {
                mFindImpl.checkIsOwn(commentInfo)
                        .subscribe(new ObserverCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(ResultMsgBean s) {
                                mvpView.showEditTextHint(s);
                            }
                        });
            }
        });
    }

    public void createComment(final FindCommentBean comment,
                              final String comContent,
                              final int position) {
        if (mFindDetailsBean == null && mCommentListBean == null) return;
        onceViewAttached(new ViewAction<FindDetailsView>() {
            @Override
            public void run(@NonNull final FindDetailsView mvpView) {
                mFindImpl.createCommentParams(mDynamicId, comment, comContent, mFindCommentList, position).
                        subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                mvpView.onCommentResult(resultMsgBean.getResult(), mFindCommentList);
                            }
                        });

            }
        });
    }

    public void delFind(int dynamicId) {
        super.delFind(null, dynamicId);
    }

    public interface FindDetailsView extends ShareInfoView {
        void onDetailsResult(FindDetailsViewBean findDetailsInfo, List<FindCommentBean> commentList);

        void onCommentListResult(List<FindCommentBean> commentList);

        void onUpdateLikeResult(FindDetailsBean result);

        void onUpdateAttentionResult(FindDetailsBean result);

        void onCommentResult(String result, List<FindCommentBean> commentList);

        void showEditTextHint(ResultMsgBean result);
    }

}
