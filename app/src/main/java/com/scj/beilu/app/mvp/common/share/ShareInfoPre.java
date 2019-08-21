package com.scj.beilu.app.mvp.common.share;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.mx.pro.lib.fragmentation.support.SupportActivity;
import com.mx.pro.lib.fragmentation.support.SupportFragment;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.common.share.model.IShareInfo;
import com.scj.beilu.app.mvp.common.share.model.ShareInfoImpl;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;
import com.scj.beilu.app.mvp.find.model.FindImpl;
import com.scj.beilu.app.mvp.find.model.IFindInfo;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;
import com.scj.beilu.app.ui.find.FindShareDialog;
import com.scj.beilu.app.util.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/10 11:46
 */
public class ShareInfoPre<mvpView extends ShareInfoView> extends BaseMvpPresenter<mvpView> {
    private IShareInfo mShareInfo;

    public ShareInfoPre(Context context) {
        this(context, ShowConfig.NONE, ShowConfig.NONE);
    }

    public ShareInfoPre(Context context, int loadType, int loadErrType) {
        super(context, loadType, loadErrType);
        mShareInfo = new ShareInfoImpl(mBuilder);
    }

    private void startShare(final String url, String title, final String description, @IShareInfo.shareType final int scene) {
        mShareInfo.WXShareWebPage(url, title, description, scene);
    }

    public void startShareFind(final SupportFragment frag, final FindInfoBean findBean) {
        onceViewAttached(mvpView -> {
            int id = findBean.getIsOwn() == 0 ? findBean.getUserId() : -1;
            final String description;
            if (findBean.getDynamicDec() == null) {
                description = findBean.getUserNickName() + "的动态";
            } else {
                description = findBean.getDynamicDec();
            }

            final FindShareDialog mFindShareDialog = FindShareDialog.newInstance(id);

            mFindShareDialog.setOnDelFind(viewId -> {
                switch (viewId) {
                    case R.id.tv_share_to_wechat:
                        startShare(findBean.getDynamicShareAddr(), null, description, SendMessageToWX.Req.WXSceneSession);
                        break;
                    case R.id.tv_share_to_wechat_firend:
                        startShare(findBean.getDynamicShareAddr(), null, description, SendMessageToWX.Req.WXSceneTimeline);
                        break;
                    case R.id.tv_share_del:
                        mvpView.onFindDel();
                        break;
                }
                if (mFindShareDialog != null) {
                    mFindShareDialog.dismiss();
                }
            });
            mFindShareDialog.show(frag.getChildFragmentManager(), "tag");
        });
    }

    public void startShareFind(final SupportActivity activity, final FindInfoBean findBean) {

        onceViewAttached(mvpView -> {
            int id = findBean.getIsOwn() == 0 ? findBean.getUserId() : -1;
            final String description;
            if (findBean.getDynamicDec() == null) {
                description = findBean.getUserNickName() + "的动态";
            } else {
                description = findBean.getDynamicDec();
            }

            final FindShareDialog mFindShareDialog = FindShareDialog.newInstance(id);

            mFindShareDialog.setOnDelFind(viewId -> {
                switch (viewId) {
                    case R.id.tv_share_to_wechat:
                        startShare(findBean.getDynamicShareAddr(), null, description, SendMessageToWX.Req.WXSceneSession);
                        break;
                    case R.id.tv_share_to_wechat_firend:
                        startShare(findBean.getDynamicShareAddr(), null, description, SendMessageToWX.Req.WXSceneTimeline);
                        break;
                    case R.id.tv_share_del:
                        if (findBean.getDynamicId() == 0) {
                            ToastUtils.showToast(mContext, "文件正在上传中，不能删除哦！");
                        } else {
                            mvpView.onFindDel();
                        }
                        break;
                }
                if (mFindShareDialog != null) {
                    mFindShareDialog.dismiss();
                }
            });
            mFindShareDialog.show(activity.getSupportFragmentManager(), "tag");
        });
    }

    public void startShareNews(SupportFragment frag, final NewsInfoBean newsInfo) {
        final FindShareDialog mFindShareDialog = FindShareDialog.newInstance(0);
        mFindShareDialog.setOnDelFind(viewId -> {
            switch (viewId) {
                case R.id.tv_share_to_wechat:
                    startShare(newsInfo.getNewsShareWebSite(), newsInfo.getNewsTitle(), null, SendMessageToWX.Req.WXSceneSession);
                    break;
                case R.id.tv_share_to_wechat_firend:
                    startShare(newsInfo.getNewsShareWebSite(), newsInfo.getNewsTitle(), null, SendMessageToWX.Req.WXSceneTimeline);
                    break;

            }
            if (mFindShareDialog != null) {
                mFindShareDialog.dismiss();
            }
        });

        mFindShareDialog.show(frag.getChildFragmentManager(), "tag");
    }

    public void startShareNews(SupportActivity activity, final NewsInfoBean newsInfo) {
        final FindShareDialog mFindShareDialog = FindShareDialog.newInstance(-1);
        mFindShareDialog.setOnDelFind(viewId -> {
            switch (viewId) {
                case R.id.tv_share_to_wechat:
                    startShare(newsInfo.getNewsShareWebSite(), newsInfo.getNewsTitle(), newsInfo.getNewsContent(), SendMessageToWX.Req.WXSceneSession);
                    break;
                case R.id.tv_share_to_wechat_firend:
                    startShare(newsInfo.getNewsShareWebSite(), newsInfo.getNewsTitle(), newsInfo.getNewsContent(), SendMessageToWX.Req.WXSceneTimeline);
                    break;

            }
            if (mFindShareDialog != null) {
                mFindShareDialog.dismiss();
            }
        });

        mFindShareDialog.show(activity.getSupportFragmentManager(), "tag");
    }

    public void setFindShareCount(final int dynamicId) {
        onceViewAttached(mvpView -> mShareInfo.setShareFindCount(dynamicId)
                .subscribe(new ObserverCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onNext(ResultMsgBean resultMsgBean) {
                        mvpView.onShareCountResult();
                    }
                }));
    }

    private IFindInfo mFindInfo;

    public void delFind(final List<FindInfoBean> findList, final int dynamicId) {
        new AlertDialog.Builder(mContext)
                .setPositiveButton(R.string.button_sure, (dialog, which) -> {
                    if (mFindInfo == null) {
                        mFindInfo = new FindImpl(mBuilder);
                    }
                    mBuilder.setLoadType(ShowConfig.LOADING_DIALOG);
                    onceViewAttached(mvpView -> mFindInfo.delWithFind(findList, dynamicId)
                            .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                                @Override
                                public void onRequestResult(ResultMsgBean resultMsgBean) {
                                    mvpView.onFindDelResult(resultMsgBean);
                                }
                            }));
                })
                .setNegativeButton(R.string.button_cancel, (dialog, which) -> dialog.cancel())
                .setCancelable(false)
                .setMessage("删除后不可恢复，确定删除吗？")
                .show();


    }
}
