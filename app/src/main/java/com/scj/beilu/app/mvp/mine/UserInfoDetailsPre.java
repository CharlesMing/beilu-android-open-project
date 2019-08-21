package com.scj.beilu.app.mvp.mine;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.mine.model.IMine;
import com.scj.beilu.app.mvp.mine.model.MineImpl;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * author:SunGuiLan
 * date:2019/2/21
 * descriptin:
 */
public class UserInfoDetailsPre extends BaseMvpPresenter<UserInfoDetailsPre.UserInfoDetailsView> {
    private IMine mIMe;

    public UserInfoDetailsPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mIMe = new MineImpl(mBuilder);
    }

    public void modifyUserInfo(final UserInfoEntity userInfoEntity) {
        onceViewAttached(new ViewAction<UserInfoDetailsView>() {
            @Override
            public void run(@NonNull final UserInfoDetailsView mvpView) {
                Map<String, Object> params = new HashMap<>();
                if (userInfoEntity.getUserNickName() != null) {
                    params.put("nickName", userInfoEntity.getUserNickName());
                }
                if (userInfoEntity.getUserSex() != null) {
                    params.put("userSex", userInfoEntity.getUserSex());
                }
                if (userInfoEntity.getUserBirth() != null) {
                    params.put("userBirth", userInfoEntity.getUserBirth());
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


    public interface UserInfoDetailsView extends MvpView {
        void onUpdateInfoResult(String resultMsg);

        void onUpdateCacheResult(UserInfoEntity userInfo);
    }
}
