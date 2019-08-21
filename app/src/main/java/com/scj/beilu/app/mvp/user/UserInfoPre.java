package com.scj.beilu.app.mvp.user;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.wechat.model.IWeChat;
import com.scj.beilu.app.mvp.wechat.model.WeChatImpl;
import com.scj.beilu.app.util.InputValidate;

/**
 * @author Mingxun
 * @time on 2019/1/22 15:33
 */
public class UserInfoPre extends BaseMvpPresenter<UserInfoPre.LoginAndRegisterView> {

    private IWeChat mWeChat;

    public UserInfoPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mWeChat = new WeChatImpl(mBuilder);
    }

    /**
     * 检查
     *
     * @param editText
     */
    public void checkTxt(final EditText editText) {
        onceViewAttached(mvpView -> {
            final TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 11) {
                        mvpView.onButtonEnable(InputValidate.mobileFormat(s.toString()), s.toString());
                    } else {
                        mvpView.onButtonEnable(false, null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
            editText.addTextChangedListener(textWatcher);


        });
    }

    public void getWeChatUserInfo(final String access_token) {
        onceViewAttached(mvpView -> mWeChat.getWeChatUserInfoByToken(access_token)
                .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(ResultMsgBean resultMsgBean) {
                        mvpView.onLoginSuccess();
                    }
                }));
    }

    public interface LoginAndRegisterView extends MvpView {
        void onButtonEnable(boolean enable, String txt);

        void onLoginSuccess();
    }

}
