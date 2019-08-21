package com.scj.beilu.app.ui.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mx.pro.lib.mvp.network.config.AppConfig;
import com.scj.beilu.app.R;
import com.scj.beilu.app.api.Constants;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.mine.MineAccountManagerPre;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;
import com.scj.beilu.app.mvp.wechat.bean.WeChatTokenBean;
import com.scj.beilu.app.ui.act.SetSystemAct;
import com.scj.beilu.app.util.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * @author Mingxun
 * @time on 2019/4/12 20:49
 */
public class MineAccountManagerFrag extends BaseMvpFragment<MineAccountManagerPre.MineAccountManagerView,
        MineAccountManagerPre> implements MineAccountManagerPre.MineAccountManagerView, View.OnClickListener {

    private TextView mTvAccountTel, mTvWxName;
    private Button mButton, mButton1;
    private TextView mTvLogout;
    private boolean bindTel, bindWx;

    private IWXAPI api;
    public static final int REQUEST_CODE = 1010;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public MineAccountManagerPre createPresenter() {
        return new MineAccountManagerPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_account_manager;
    }

    @Override
    public void initView() {
        super.initView();
        mTvAccountTel = findViewById(R.id.tv_account_name);
        mTvWxName = findViewById(R.id.tv_account_name1);
        mButton = findViewById(R.id.button);
        mButton1 = findViewById(R.id.button1);
        mTvLogout = findViewById(R.id.tv_log_out);

        mButton.setOnClickListener(this);
        mButton1.setOnClickListener(this);
        mTvLogout.setOnClickListener(this);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getUserInfo();
    }

    @Override
    public void onUserInfo(UserInfoEntity userInfo) {
        String userTel = userInfo.getUserTel();
        if (userTel != null && userTel.length() != 0) {
            mTvAccountTel.setText(userTel);
            bindTel = true;
            mButton.setText("已绑定");
        } else {
            mTvAccountTel.setText("未绑定");
            mButton.setText("立即绑定");
            bindTel = false;
        }
        String openId = userInfo.getOpenId();
        if (openId != null && openId.length() != 0) {
            mTvWxName.setText(userInfo.getUserNickName());
            bindWx = true;
            mButton1.setText("已绑定");
        } else {
            mTvWxName.setText("未绑定");
            mButton1.setText("立即绑定");
            bindWx = false;
        }
    }

    @Override
    public void onBindResult(String result) {
        ToastUtils.showToast(mFragmentActivity, result);
        getPresenter().getUserInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                if (bindTel) {

                } else {
                    Intent intent = new Intent(mFragmentActivity, MineBindPhoneAct.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                break;
            case R.id.button1:

                if (bindWx) {

                } else {

                    if (api == null) {
                        api = WXAPIFactory.createWXAPI(mFragmentActivity, Constants.WXAPPID, false);
                    }

                    final SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "none";
                    api.sendReq(req);
                }
                break;
            case R.id.tv_log_out:
                new AlertDialog.Builder(mFragmentActivity)
                        .setPositiveButton(R.string.button_sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(@NonNull DialogInterface dialog, int which) {
                                SharedPreferences sharedPreferences = mFragmentActivity.getSharedPreferences(AppConfig.PREFS_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor clear = sharedPreferences.edit().clear();
                                clear.apply();
                                mFragmentActivity.setResult(SetSystemAct.LOGIN_OUT);
                                mFragmentActivity.finish();
                            }
                        })
                        .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(@NonNull DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setCancelable(false)
                        .setMessage("确认退出登录？")
                        .show();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void startBindingWxInfo(WeChatTokenBean tokenBean) {
        getPresenter().bindWxAccount(tokenBean.getOpenid());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == requestCode) {
            getPresenter().getUserInfo();
        }
    }
}
