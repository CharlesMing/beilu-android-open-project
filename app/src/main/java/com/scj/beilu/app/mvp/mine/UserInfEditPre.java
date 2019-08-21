package com.scj.beilu.app.mvp.mine;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.orhanobut.logger.Logger;
import com.scj.beilu.app.mvp.common.bean.DictrictOptionBean;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.mine.model.IMine;
import com.scj.beilu.app.mvp.mine.model.MineImpl;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mingxun
 * @time on 2019/1/12 16:08
 */
public class UserInfEditPre extends BaseMvpPresenter<UserInfEditPre.UserInfoEditView> {
    private IMine mIMe;

    public UserInfEditPre(Context context) {
        super(context, ShowConfig.NONE, ShowConfig.ERROR_TOAST);
        mIMe = new MineImpl(mBuilder);
        getDistrictList();
    }

    public void getUserInfo() {
        onceViewAttached(new ViewAction<UserInfoEditView>() {
            @Override
            public void run(@NonNull final UserInfoEditView mvpView) {
                mIMe.getUserInfoByToken()
                        .subscribe(new ObserverCallback<UserInfoEntity>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(UserInfoEntity userInfoEntity) {
                                mvpView.onUserInfo(userInfoEntity);
                            }
                        });
            }
        });
    }

    /**
     * 修改性别 生日 所在地
     *
     * @param userInfoEntity
     */
    public void modifyUserInfo(final UserInfoEntity userInfoEntity) {
        onceViewAttached(new ViewAction<UserInfoEditView>() {
            @Override
            public void run(@NonNull final UserInfoEditView mvpView) {
                Map<String, Object> params = new HashMap<>();
                if (userInfoEntity.getUserNickName() != null) {
                    params.put("nickName", userInfoEntity.getUserNickName());
                }
                if (userInfoEntity.getUserSex() != null) {
                    params.put("userSex", userInfoEntity.getUserSex());
                }
                if (userInfoEntity.getUserBirth() != null) {
                    params.put("userBirth", userInfoEntity.getUserBirth());
                    Logger.e(userInfoEntity.getUserBirth() + "   -------------------- ");
                }
                if (userInfoEntity.getUserProvince() != null) {
                    params.put("userProvince", userInfoEntity.getUserProvince());
                }
                if (userInfoEntity.getUserCity() != null) {
                    params.put("userCity", userInfoEntity.getUserCity());
                }
                if (userInfoEntity.getUserBrief() != null) {
                    params.put("userBrief", userInfoEntity.getUserBrief());
                }
                mIMe.setUserInfoParams(params)
                        //更新缓存里面的数据
                        .flatMap(new Function<ResultMsgBean, ObservableSource<UserInfoEntity>>() {
                            @Override
                            public ObservableSource<UserInfoEntity> apply(ResultMsgBean result) throws Exception {
                                mvpView.onUpdateInfoResult(result.getResult());
                                return mIMe.updateUserInfo(userInfoEntity);
                            }
                        })

                        .subscribe(new ObserverCallback<UserInfoEntity>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(UserInfoEntity userInfoEntity) {
                                mvpView.onUpdateCacheResult(userInfoEntity);
                            }
                        });
            }
        });
    }


    public void setUserPhoto(final String photo) {
        onceViewAttached(new ViewAction<UserInfoEditView>() {
            @Override
            public void run(@NonNull final UserInfoEditView mvpView) {
                mIMe.setUserPhotoParam(photo)
                        .subscribe(new ObserverCallback<UserInfoEntity>
                                (mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(UserInfoEntity userInfoEntity) {
                                mvpView.onUserInfo(userInfoEntity);
                                mvpView.onUpdateInfoResult(userInfoEntity.getResult());
                            }
                        });
            }
        });
    }

    /**
     * 获取地区列表
     */
    private void getDistrictList() {
        onceViewAttached(new ViewAction<UserInfoEditView>() {
            @Override
            public void run(@NonNull final UserInfoEditView mvpView) {
                mIMe.dealWithDistrictData()
                        .subscribe(new ObserverCallback<DictrictOptionBean>
                                (mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(DictrictOptionBean result) {
                                mvpView.onDistrictListResult(result);
                            }
                        });
            }
        });
    }

    public interface UserInfoEditView extends MvpView {
        void onUserInfo(UserInfoEntity userInfoEntity);

        void onUpdateInfoResult(String resultMsg);

        void onUpdateCacheResult(UserInfoEntity userInfo);

        void onDistrictListResult(DictrictOptionBean optionBean);
    }
}

