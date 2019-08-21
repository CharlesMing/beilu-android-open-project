package com.scj.beilu.app.mvp.mine;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;
import com.scj.beilu.app.mvp.user.model.IUserDetails;
import com.scj.beilu.app.mvp.user.model.UserDetailsImpl;

/**
 * @author Mingxun
 * @time on 2019/4/12 20:26
 */
public class MineAccountManagerPre extends BaseMvpPresenter<MineAccountManagerPre.MineAccountManagerView> {

    private IUserDetails mUserDetails;

    public MineAccountManagerPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mUserDetails = new UserDetailsImpl(mBuilder);
    }

    public void getUserInfo() {
        onceViewAttached(new ViewAction<MineAccountManagerView>() {
            @Override
            public void run(@NonNull final MineAccountManagerView mvpView) {
                mUserDetails.getUserInfoByToken()
                        .subscribe(new ObserverCallback<UserInfoEntity>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(UserInfoEntity userInfoEntity) {
                                mvpView.onUserInfo(userInfoEntity);
                            }
                        });
            }
        });
    }

    public void bindWxAccount(final String openId) {
        onceViewAttached(new ViewAction<MineAccountManagerView>() {
            @Override
            public void run(@NonNull final MineAccountManagerView mvpView) {
                mUserDetails.bindWxAccount(openId)
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                mvpView.onBindResult(resultMsgBean.getResult());
                            }
                        });
            }
        });
    }

    public interface MineAccountManagerView extends MvpView {
        void onUserInfo(UserInfoEntity userInfo);

        void onBindResult(String result);
    }
}

