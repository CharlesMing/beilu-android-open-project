package com.scj.beilu.app.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.mx.pro.lib.mvp.MvpActivity;
import com.mx.pro.lib.mvp.MvpPresenter;
import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.R;
import com.scj.beilu.app.ui.act.LoginAndRegisterAct;
import com.scj.beilu.app.util.ToastUtils;
import com.scj.beilu.app.widget.AppToolbar;

import permissions.dispatcher.PermissionRequest;

public abstract class BaseMvpActivity<V extends MvpView, P extends MvpPresenter<V>>
        extends MvpActivity<V, P> implements MvpView, Toolbar.OnMenuItemClickListener {
    private ProgressDialog mProgressDialog;
    protected AppToolbar mAppToolbar;

    @Override
    protected void initView() {
        super.initView();
        mAppToolbar = findViewById(R.id.toolbar);

        if (mAppToolbar != null) {
            mAppToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressedSupport();
                }
            });
            mAppToolbar.setOnMenuItemClickListener(this);
        }

    }

    @Override
    public void showError(int errorCode, String throwableContent) {
        if (errorCode == ShowConfig.ERROR_TOAST) {
            ToastUtils.showToast(this, throwableContent);
        } else if (errorCode == ShowConfig.ERROR_USER) {
            Intent intent = new Intent(this, LoginAndRegisterAct.class);
            startActivityForResult(intent, LoginAndRegisterAct.LOGIN_REQUEST);
        }

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showLoading(int loading, boolean isShow) {
        switch (loading) {
            case ShowConfig.LOADING_DIALOG:
                if (isShow) {
                    if (mProgressDialog == null) {
                        mProgressDialog = ProgressDialog.show(this, "",
                                "请求中，请稍后...");
                        mProgressDialog.setCancelable(true);
                    } else {
                        mProgressDialog.show();
                    }
                } else {
                    mProgressDialog.dismiss();
                }
                break;
            case ShowConfig.LOADING_STATE:
                break;
            case ShowConfig.LOADING_REFRESH:
                break;
        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }


    protected void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }

    @Override
    public void userIsLogin(boolean login, int viewId) {
    }
}
