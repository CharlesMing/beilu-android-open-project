package com.scj.beilu.app.mvp.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.common.user.IUser;
import com.scj.beilu.app.mvp.common.user.UserImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mingxun
 * @time on 2019/2/13 15:13
 */
public class UserInfoPre extends BaseMvpPresenter<UserInfoPre.VerifyCodeView> {
    private IUser mIUser;

    public UserInfoPre(Context context) {
        super(context);
        initLoad(ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mIUser = new UserImpl(mBuilder);
    }


    public void setHintNum(final String num) {
        onceViewAttached(new ViewAction<VerifyCodeView>() {
            @Override
            public void run(@NonNull VerifyCodeView mvpView) {
                String phoneNumber = num.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                phoneNumber = String.format(mContext.getResources().getString(R.string.msg_send_hint), phoneNumber);
                mvpView.showSendHint(phoneNumber);
            }
        });
    }

    public void checkTxt(final EditText editText) {
        onceViewAttached(new ViewAction<VerifyCodeView>() {
            @Override
            public void run(@NonNull final VerifyCodeView mvpView) {
                final TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 6) {
                            mvpView.onButtonEnable(true, s.toString());
                        } else {
                            mvpView.onButtonEnable(false, null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };
                editText.addTextChangedListener(textWatcher);


            }

        });
    }

    public void startRegSend(final String num) {
        onceViewAttached(new ViewAction<VerifyCodeView>() {
            @Override
            public void run(@NonNull final VerifyCodeView mvpView) {
                Map<String, Object> params = new HashMap<>();
                params.put("userTel", num);
                mIUser.setSendRegCodeParams(params)
                        .subscribe(new BaseResponseCallback(
                                mBuilder.build(mvpView)
                        ) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                mvpView.codeResult(resultMsgBean);
                            }
                        });

            }
        });
    }

    public void startLoginSend(final String num) {
        onceViewAttached(new ViewAction<VerifyCodeView>() {
            @Override
            public void run(@NonNull final VerifyCodeView mvpView) {
                Map<String, Object> params = new HashMap<>();
                params.put("userTel", num);
                mIUser.setSendLoginCodeParams(params)
                        .subscribe(new BaseResponseCallback(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                mvpView.codeResult(resultMsgBean);
                            }
                        });
            }
        });
    }

    public void startReg(final String number, final String code) {
        onceViewAttached(new ViewAction<VerifyCodeView>() {
            @Override
            public void run(@NonNull final VerifyCodeView mvpView) {
                Map<String, Object> params = new HashMap<>();
                params.put("userTel", Encrypt(number));
                params.put("userPassword", Encrypt("000000"));
                params.put("code", code);
                params.put("userAppKey", "25307800");
                params.put("userTargetValue", Encrypt(PushServiceFactory.getCloudPushService().getDeviceId()));
                mIUser.setRegParams(params)
                        .subscribe(new BaseResponseCallback(
                                mBuilder.build(mvpView)
                        ) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                mvpView.onLoginSuccess();
                            }
                        });
            }
        });
    }

    public void startLogin(final String number, final String code) {

        onceViewAttached(new ViewAction<VerifyCodeView>() {
            @Override
            public void run(@NonNull VerifyCodeView mvpView) {
                Map<String, Object> params = new HashMap<>();
                params.put("userTel", Encrypt(number));
                params.put("code", code);
                params.put("userAppKey", "25307800");
                params.put("targetValue", Encrypt(PushServiceFactory.getCloudPushService().getDeviceId()));
                mIUser.setLoginParams(params)
                        .subscribe(new BaseResponseCallback(
                                mBuilder.build(mvpView)
                        ) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                onceViewAttached(new ViewAction<VerifyCodeView>() {
                                    @Override
                                    public void run(@NonNull VerifyCodeView mvpView) {
                                        mvpView.onLoginSuccess();
                                    }
                                });
                            }
                        });
            }
        });
    }


    public interface VerifyCodeView extends MvpView {
        void showSendHint(String content);

        void codeResult(ResultMsgBean result);

        void onButtonEnable(boolean enable, String txt);

        void onLoginSuccess();
    }
}
