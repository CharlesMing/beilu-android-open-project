package com.scj.beilu.app.mvp.user;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;

import com.mx.pro.lib.album.AlbumManager;
import com.mx.pro.lib.album.GlideEngine;
import com.mx.pro.lib.album.MimeType;
import com.mx.pro.lib.album.internal.entity.CaptureStrategy;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.common.share.ShareInfoPre;
import com.scj.beilu.app.mvp.common.share.ShareInfoView;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;
import com.scj.beilu.app.mvp.find.bean.FindListBean;
import com.scj.beilu.app.mvp.find.bean.ModifyRecordBean;
import com.scj.beilu.app.mvp.find.model.FindImpl;
import com.scj.beilu.app.mvp.find.model.IFindInfo;
import com.scj.beilu.app.mvp.user.bean.AvatarResult;
import com.scj.beilu.app.mvp.user.bean.UserDetailsBean;
import com.scj.beilu.app.mvp.user.model.IUserDetails;
import com.scj.beilu.app.mvp.user.model.UserDetailsImpl;
import com.scj.beilu.app.ui.user.UserHomePageFrag;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.scj.beilu.app.api.Constants.DIRECTORY;

/**
 * @author Mingxun
 * @time on 2019/3/4 18:45
 * 主页操作
 */
public class UserHomePagePre extends ShareInfoPre<UserHomePagePre.UserHomePageView> {

    private IUserDetails mUserDetails;
    private IFindInfo mFind;
    public static final int RESULT_POSITION = -123;
    private final List<FindInfoBean> mFindList = new ArrayList<>();

    public UserHomePagePre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mUserDetails = new UserDetailsImpl(mBuilder);
        mFind = new FindImpl(mBuilder);
    }

    public void getUserInfo(final int userId, final int currentPage) {
        mBuilder.setLoadType(ShowConfig.LOADING_REFRESH);
        onceViewAttached(mvpView -> Observable
                .zip(mUserDetails.getUserDetails(userId),
                        mFind.getUserDynamicList(userId, currentPage),
                        (userDetailsBean, findInfo) -> {
                            mvpView.onUserDetailsResult(userDetailsBean);
                            return findInfo;
                        })
                .subscribe(new BaseResponseCallback<FindListBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(FindListBean findInfo) {
                        List<FindInfoBean> list = null;
                        try {
                            list = findInfo.getPage().getList();
                            if (currentPage == 0) {
                                mFindList.clear();
                            }
                        } catch (Exception e) {
                            list = new ArrayList<>();
                        } finally {
                            mvpView.onCheckLoadMore(list);
                            mFindList.addAll(list);
                            mvpView.onFindListResult(mFindList);
                        }
                    }
                }));
    }

    public void getLoadMoreDynamicList(final int userId, final int currentPage) {
        mBuilder.setLoadType(ShowConfig.LOADING_REFRESH);
        onceViewAttached(mvpView -> mFind.getUserDynamicList(userId, currentPage)
                .subscribe(new ObserverCallback<FindListBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onNext(FindListBean findInfo) {
                        List<FindInfoBean> list = findInfo.getPage().getList();
                        if (currentPage == 0) {
                            mFindList.clear();
                        }
                        mvpView.onCheckLoadMore(list);
                        mFindList.addAll(list);
                        mvpView.onFindListResult(mFindList);
                    }
                }));
    }

    public void setAttention(final int userId) {
        mBuilder.setLoadType(ShowConfig.NONE);
        onceViewAttached(mvpView -> mFind.setAttentionParams(mFindList, userId)
                .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(final ResultMsgBean resultMsgBean) {
                        mvpView.onNotifyListChangeResult(resultMsgBean.getResult(), RESULT_POSITION);
                    }
                }));
    }

    public void setLike(final int dynamicId, final int position) {
        mBuilder.setLoadType(ShowConfig.NONE);
        onceViewAttached(mvpView -> mFind.setLikeParamsByList(mFindList, dynamicId, position)
                .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(ResultMsgBean resultMsgBean) {
                        mvpView.onNotifyListChangeResult(resultMsgBean.getResult(), position);
                    }
                }));
    }

    public void modifyRecord(final ModifyRecordBean record, final int position) {
        mBuilder.setLoadType(ShowConfig.NONE);
        onceViewAttached(mvpView -> mFind.updateFindDetailsResult(mFindList, position, record)
                .subscribe(new ObserverCallback<Integer>(mBuilder.build(mvpView)) {
                    @Override
                    public void onNext(Integer position1) {
                        mvpView.onModifyResult(position1);
                    }
                }));
    }

    public void startSelectPicture(Fragment fragment) {
        AlbumManager.from(fragment)
                .choose(MimeType.ofImage())
                .showSingleMediaType(true)
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, mContext.getPackageName() + ".fileprovider", DIRECTORY))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())  // for glide-V4
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .forResult(UserHomePageFrag.REQUEST_PICTURE_CODE);
    }

    public void updatePicture(final String path) {
        mBuilder.setLoadType(ShowConfig.LOADING_DIALOG);
        onceViewAttached(mvpView -> mUserDetails.updateBackgroundImg(path)
                .subscribe(new BaseResponseCallback<AvatarResult>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(AvatarResult resultMsgBean) {
                        mvpView.onNotifyBackgroundImg(resultMsgBean);
                    }
                }));
    }

    public void getUserInfo(int userId) {
        mBuilder.setLoadType(ShowConfig.NONE);
        onceViewAttached(mvpView -> mUserDetails.getUserDetails(userId)
                .subscribe(new BaseResponseCallback<UserDetailsBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(UserDetailsBean userDetailsBean) {
                        mvpView.onUserDetailsResult(userDetailsBean);
                    }
                }));
    }

    public void delFind(int dynamicId) {
        super.delFind(mFindList, dynamicId);
    }

    public interface UserHomePageView extends ShareInfoView {
        void onUserDetailsResult(UserDetailsBean resultInfo);

        void onFindListResult(List<FindInfoBean> findList);

        void onNotifyListChangeResult(String result, int position);

        void onModifyResult(int position);

        void onNotifyBackgroundImg(AvatarResult result);
    }
}
