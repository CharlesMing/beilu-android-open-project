package com.scj.beilu.app.mvp.mine;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.user.model.IUserDetails;
import com.scj.beilu.app.mvp.user.model.UserDetailsImpl;

/**
 * @author Mingxun
 * @time on 2019/4/16 22:23
 */
public class MinePhoneBindPre extends BaseMvpPresenter<MinePhoneBindPre.MinePhoneBindView> {

    private IUserDetails mUserDetails;

    public MinePhoneBindPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mUserDetails = new UserDetailsImpl(mBuilder);
    }

    public void startSendCode(final String phone) {
        onceViewAttached(new ViewAction<MinePhoneBindView>() {
            @Override
            public void run(@NonNull final MinePhoneBindView mvpView) {
                mUserDetails.bindPhoneSendMsg(phone)
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                mvpView.codeResult(resultMsgBean);
                            }
                        });
            }
        });
    }

    public void startBind(final String code, final String phone) {
        onceViewAttached(new ViewAction<MinePhoneBindView>() {
            @Override
            public void run(@NonNull final MinePhoneBindView mvpView) {
                mUserDetails.bindPhone(code, phone)
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                mvpView.onBindResult(resultMsgBean.getResult());
                            }
                        });
            }
        });
    }


    public void setHintNum(final String num) {
        onceViewAttached(new ViewAction<MinePhoneBindView>() {
            @Override
            public void run(@NonNull MinePhoneBindView mvpView) {
                String phoneNumber = num.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                phoneNumber = String.format(mContext.getResources().getString(R.string.msg_send_hint), phoneNumber);
                mvpView.showSendHint(phoneNumber);
            }
        });
    }

    public interface MinePhoneBindView extends MvpView {
        void showSendHint(String content);

        void codeResult(ResultMsgBean result);

        void onBindResult(String result);
    }
}
