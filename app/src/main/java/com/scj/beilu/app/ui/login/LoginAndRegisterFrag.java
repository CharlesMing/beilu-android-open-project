package com.scj.beilu.app.ui.login;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.scj.beilu.app.R;
import com.scj.beilu.app.api.Constants;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.user.UserInfoPre;
import com.scj.beilu.app.mvp.wechat.bean.WeChatTokenBean;
import com.scj.beilu.app.ui.act.LoginAndRegisterAct;
import com.scj.beilu.app.util.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author Mingxun
 * @time on 2019/2/14 09:47
 */
public class LoginAndRegisterFrag extends BaseMvpFragment<UserInfoPre.LoginAndRegisterView, UserInfoPre>
        implements UserInfoPre.LoginAndRegisterView, View.OnClickListener {

    private EditText mEtPhone;
    private TextView mTvRegister, mTvLogin;
    private Button mBtnCode;
    private ImageButton mBtnWxLogin;
    private Drawable drawableBottom;
    private boolean mIsLogin;
    private IWXAPI api;

    @Override
    public int getLayout() {
        return R.layout.act_login_register;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(mFragmentActivity, Constants.WXAPPID, false);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        super.initView();
        ImmersionBar.with(this).statusBarView(R.id.top_view)
                .addTag("PicAndColor")
                .statusBarDarkFont(false)
                .keyboardEnable(false)
                .init();
        mEtPhone = findViewById(R.id.et_phone);
        mTvRegister = findViewById(R.id.tv_register);
        mTvLogin = findViewById(R.id.tv_login);
        mBtnCode = findViewById(R.id.btn_code);
        mBtnWxLogin = findViewById(R.id.btn_wx_login);

        mTvRegister.setOnClickListener(this);
        mTvLogin.setOnClickListener(this);
        mBtnCode.setOnClickListener(this);
        mBtnWxLogin.setOnClickListener(this);
        drawableBottom = ContextCompat.getDrawable(mFragmentActivity, R.drawable.ic_triangle);
        drawableBottom.setBounds(0, 0, drawableBottom.getIntrinsicWidth(), drawableBottom.getIntrinsicHeight());

        mBtnCode.setEnabled(false);

        getPresenter().checkTxt(mEtPhone);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mEtPhone != null) {
            showSoftInput(mEtPhone);
        }

    }

    @NonNull
    @Override
    public UserInfoPre createPresenter() {
        return new UserInfoPre(mFragmentActivity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                selectLogin(0);
                break;
            case R.id.tv_login:
                selectLogin(1);
                break;
            case R.id.btn_code:
                String number = mEtPhone.getText().toString();
                start(SendVerifyCodeFrag.newInstance(number, mIsLogin));
                break;
            case R.id.btn_wx_login:
                final SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "none";
                api.sendReq(req);
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            ImmersionBar.with(this).statusBarView(R.id.top_view)
                    .addTag("PicAndColor")
                    .keyboardEnable(false)
                    .init();
        }
    }

    /**
     * Select login mode
     *
     * @param selectId def:register 1:login
     */
    private void selectLogin(int selectId) {

        if (selectId == 1) {
            mIsLogin = true;
            mTvLogin.setCompoundDrawables(null, null, null, drawableBottom);
            mTvRegister.setCompoundDrawables(null, null, null, null);
        } else {
            mIsLogin = false;
            mTvRegister.setCompoundDrawables(null, null, null, drawableBottom);
            mTvLogin.setCompoundDrawables(null, null, null, null);
        }
    }

    @Override
    public void onButtonEnable(boolean enable, String txt) {
        if (enable) {
            mBtnCode.setEnabled(true);
        } else {
            if (txt != null) {
                ToastUtils.showToast(mFragmentActivity, "手机号码格式不正确");
            }
            if (mBtnCode.isEnabled()) {
                mBtnCode.setEnabled(false);
            }
        }
    }

    @Override
    public void onLoginSuccess() {
        mFragmentActivity.setResult(LoginAndRegisterAct.LOGIN_RESULT_OK);
        mFragmentActivity.finish();
    }

    @Subscribe
    public void weChatLoginResult(WeChatTokenBean tokenBean) {
        if (tokenBean.getAccess_token() == null) {
            ToastUtils.showToast(mFragmentActivity, "用戶取消");
        } else {
            getPresenter().getWeChatUserInfo(tokenBean.getAccess_token());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
