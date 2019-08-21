package com.scj.beilu.app.ui.mine;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.mine.MinePhoneBindPre;
import com.scj.beilu.app.util.CheckUtils;
import com.scj.beilu.app.util.ToastUtils;

/**
 * @author Mingxun
 * @time on 2019/4/16 22:26
 */
public class MineStartBindPhoneFrag extends BaseMvpFragment<MinePhoneBindPre.MinePhoneBindView, MinePhoneBindPre>
        implements MinePhoneBindPre.MinePhoneBindView, View.OnClickListener {

    private static final String PHONE = "phone";
    private String mPhone;
    private TextView mTvSendHint, mTvCountdown;
    private EditText mEtVerifyCode;
    private Button mBtnStartVerify, mBtnRestartSend;

    private String mCode;

    public static MineStartBindPhoneFrag newInstance(String phone) {

        Bundle args = new Bundle();
        args.putString(PHONE, phone);
        MineStartBindPhoneFrag fragment = new MineStartBindPhoneFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mPhone = arguments.getString(PHONE);
        }
    }

    @Override
    public MinePhoneBindPre createPresenter() {
        return new MinePhoneBindPre(mFragmentActivity);
    }

    @Nullable
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
            CheckUtils.checkStringIsNull(mPhone, "");

            getPresenter().setHintNum(mPhone);
            getPresenter().startSendCode(mPhone);

            mEtVerifyCode.addTextChangedListener(textWatcher);
            mBtnStartVerify.setEnabled(false);
            mBtnRestartSend.setEnabled(false);
        } catch (RuntimeException e) {

        }
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 6) {
                onButtonEnable(true, s.toString());
            } else {
                onButtonEnable(false, null);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_verify:
                getPresenter().startBind(mCode, mPhone);
                break;
            case R.id.btn_restart_send:
                getPresenter().startSendCode(mPhone);
                break;
        }
    }

    private void onButtonEnable(boolean enable, String text) {
        mCode = text;
        mBtnStartVerify.setEnabled(enable);
    }

    @Override
    public void showSendHint(String content) {
        mTvSendHint.setText(content);
    }

    @Override
    public void codeResult(ResultMsgBean result) {
        mTimer.start();
        ToastUtils.showToast(mFragmentActivity, result.getResult());
    }

    @Override
    public void onBindResult(String result) {
        mFragmentActivity.setResult(MineAccountManagerFrag.REQUEST_CODE);
        ToastUtils.showToast(mFragmentActivity, result);
        mFragmentActivity.finish();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null)
            mTimer.cancel();
    }


}
