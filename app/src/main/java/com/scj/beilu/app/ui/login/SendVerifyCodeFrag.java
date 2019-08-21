package com.scj.beilu.app.ui.login;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.common.UserInfoPre;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.util.CheckUtils;
import com.scj.beilu.app.util.ToastUtils;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author Mingxun
 * @time on 2019/2/13 15:08
 */
@RuntimePermissions
public class SendVerifyCodeFrag extends BaseMvpFragment<UserInfoPre.VerifyCodeView, UserInfoPre>
        implements UserInfoPre.VerifyCodeView, View.OnClickListener {
    private TextView mTvSendHint, mTvCountdown;
    private EditText mEtVerifyCode;
    private Button mBtnStartVerify, mBtnRestartSend;

    private static final String EXTRA_PHONE_NUMBER = "number";
    private static final String EXTRA_USE_TYPE = "login_register";

    private String mNumber = null;
    private boolean mIsLogin = true;
    private String mCode;

    public static SendVerifyCodeFrag newInstance(String number, boolean isLogin) {

        Bundle args = new Bundle();
        args.putString(EXTRA_PHONE_NUMBER, number);
        args.putBoolean(EXTRA_USE_TYPE, isLogin);
        SendVerifyCodeFrag fragment = new SendVerifyCodeFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mNumber = arguments.getString(EXTRA_PHONE_NUMBER);
            mIsLogin = arguments.getBoolean(EXTRA_USE_TYPE);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.frag_input_code;
    }

    @Override
    public void initView() {
        super.initView();

        mTvSendHint = findViewById(R.id.tv_send_hint);
        mTvCountdown = findViewById(R.id.tv_countdown);
        mEtVerifyCode = findViewById(R.id.et_verify_code);
        mBtnStartVerify = findViewById(R.id.btn_start_verify);
        mBtnRestartSend = findViewById(R.id.btn_restart_send);

        mBtnStartVerify.setOnClickListener(this);
        mBtnRestartSend.setOnClickListener(this);
        hideSoftInput();

        try {
            CheckUtils.checkStringIsNull(mNumber, "");

            getPresenter().setHintNum(mNumber);
            if (mIsLogin) {
                getPresenter().startLoginSend(mNumber);
            } else {
                getPresenter().startRegSend(mNumber);
            }
            getPresenter().checkTxt(mEtVerifyCode);
            mBtnStartVerify.setEnabled(false);
            mBtnRestartSend.setEnabled(false);
        } catch (RuntimeException e) {

        }

    }


    private final CountDownTimer mTimer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            String hint = String.format(getString(R.string.countdown_hint), (millisUntilFinished / 1000) + "s");
            mTvCountdown.setText(hint);
        }

        @Override
        public void onFinish() {
            String hint = String.format(getString(R.string.countdown_hint), "0s");
            mTvCountdown.setText(hint);
            mBtnRestartSend.setEnabled(true);
            mBtnRestartSend.setText("重新发送");
        }
    };

    @NonNull
    @Override
    public UserInfoPre createPresenter() {
        return new UserInfoPre(mFragmentActivity);
    }

    @Override
    public void showSendHint(String content) {
        mTvSendHint.setText(content);
    }

    @Override
    public void codeResult(ResultMsgBean result) {
        if (result.getCode() == 1004) {
            new AlertDialog.Builder(mFragmentActivity)
                    .setPositiveButton("切换", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            mIsLogin = true;
                            getPresenter().startLoginSend(mNumber);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setCancelable(false)
                    .setMessage(result.getResult() + ",是否切换至登录?")
                    .show();
        } else {
            mTimer.start();
            ToastUtils.showToast(mFragmentActivity, result.getResult());
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        ImmersionBar.with(this).statusBarDarkFont(true,0.2f)
                .statusBarColor(android.R.color.white)
                .fitsSystemWindows(true)
                .init();
    }

    @Override
    public void onButtonEnable(boolean enable, String txt) {
        mCode = txt;
        mBtnStartVerify.setEnabled(enable);
    }

    @Override
    public void onLoginSuccess() {
        mFragmentActivity.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_verify:
                SendVerifyCodeFragPermissionsDispatcher.NeedsPhoneStateWithPermissionCheck(this);
                break;
            case R.id.btn_restart_send:

                if (mIsLogin) {
                    getPresenter().startLoginSend(mNumber);
                } else {
                    getPresenter().startRegSend(mNumber);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    void NeedsPhoneState() {
        if (mIsLogin) {
            getPresenter().startLogin(mNumber, mCode);
        } else {
            getPresenter().startReg(mNumber, mCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SendVerifyCodeFragPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.READ_PHONE_STATE)
    void OnShowRationalePhoneState(final PermissionRequest request) {
        showRationaleDialog(R.string.permission_phone_state_rationale, request);
    }


}
