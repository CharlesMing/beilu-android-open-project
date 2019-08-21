package com.scj.beilu.app.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.mvp.MvpFragment;
import com.mx.pro.lib.mvp.MvpPresenter;
import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.mx.pro.lib.smartrefresh.layout.SmartRefreshLayout;
import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.mx.pro.lib.smartrefresh.layout.listener.OnLoadMoreListener;
import com.mx.pro.lib.smartrefresh.layout.listener.OnRefreshListener;
import com.scj.beilu.app.R;
import com.scj.beilu.app.ui.act.LoginAndRegisterAct;
import com.scj.beilu.app.util.ToastUtils;
import com.scj.beilu.app.widget.AppToolbar;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.PermissionRequest;

public abstract class BaseMvpFragment<V extends MvpView, P extends MvpPresenter<V>>
        extends MvpFragment<V, P> implements MvpView, Toolbar.OnMenuItemClickListener, OnRefreshListener, OnLoadMoreListener {
    protected AppToolbar mAppToolbar;
    protected SmartRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    private ViewStub mViewLoadError;
    private ProgressDialog mProgressDialog;
    protected int mCurrentPage = 0;
    private boolean mHaveData = false;


    @Override
    public void initView() {
        super.initView();

        mAppToolbar = findViewById(R.id.toolbar);
        ImmersionBar.with(this)
                .titleBar(mAppToolbar)
                .statusBarDarkFont(true,0.2f)
                .keyboardEnable(true)
                .init();
        mRefreshLayout = findViewById(R.id.refresh_layout);
        mRecyclerView = findViewById(R.id.recycler_view_id);
        if (mAppToolbar != null) {
            mAppToolbar.setNavigationOnClickListener(v -> onToolbarNavClick());
            mAppToolbar.setOnMenuItemClickListener(this);
        }

        if (mRefreshLayout != null) {
            mRefreshLayout.setOnRefreshListener(this);
            mRefreshLayout.setOnLoadMoreListener(this);
        }
    }

    protected void onToolbarNavClick() {
        mFragmentActivity.onBackPressed();
    }

    @Override
    public void showError(int errorCode, String throwableContent) {
        if (errorCode == ShowConfig.ERROR_TOAST) {
            ToastUtils.showToast(mFragmentActivity, throwableContent);
        } else if (errorCode == ShowConfig.ERROR_USER) {
            gotoLogin();
        } else if (errorCode == ShowConfig.ERROR_NET) {
            if (mHaveData) {
                if (mRefreshLayout == null) return;
                if (mCurrentPage == 0) {
                    mRefreshLayout.finishRefresh(true);
                } else {
                    mRefreshLayout.finishLoadMore(true);
                }
            } else {
                checkIsLoadMore(new ArrayList<>());
                showContentView(false);
            }

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
                ToastUtils.showToast(mFragmentActivity, throwableContent);
            }
        }

        if (mRefreshLayout != null) {
            mRefreshLayout.finishRefresh(true);
        }

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public void showLoading(int loading, boolean isShow) {

        switch (loading) {
            case ShowConfig.LOADING_DIALOG:
                if (isShow) {
                    if (mProgressDialog == null) {
                        mProgressDialog = ProgressDialog.show(mFragmentActivity, "",
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
            case ShowConfig.NONE:
                break;
        }

        if (isShow) {
            showContentView(true);
        }
    }

    /**
     * 是否显示网络错误按钮
     *
     * @param success
     */
    private void showContentView(boolean success) {
        if (success) {
            if (mViewLoadError != null) {
                mViewLoadError.setVisibility(View.GONE);
            }
            if (mRecyclerView != null) {
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        } else {
            if (mViewLoadError == null) {
                mViewLoadError = findViewById(R.id.view_load_error);
                if (mViewLoadError == null) return;
                View inflate = mViewLoadError.inflate();
                Button btnReStartLoad = inflate.findViewById(R.id.btn_restart_load);
                btnReStartLoad.setOnClickListener(v -> onReLoad());
            } else {
                mViewLoadError.setVisibility(View.VISIBLE);
            }

            if (mRecyclerView != null) {
                mRecyclerView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 重新加载
     */
    protected void onReLoad() {
        if (mCurrentPage > 0) {
            mRefreshLayout.autoLoadMore();
        } else {
            if (mRefreshLayout != null) {
                mRefreshLayout.autoRefresh();
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }


    protected void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(mFragmentActivity)
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
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 0;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
    }

    /**
     * 看代码逻辑
     */
    protected void checkIsLoadMore(List<?> list) {
        mHaveData = list.size() > 0;
        if (mRefreshLayout == null) return;
        if (mCurrentPage == 0) {
            mRefreshLayout.finishRefresh(list.size() == 0);
            mRefreshLayout.resetNoMoreData();
        } else {
            if (list.size() == 0) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            } else {
                mRefreshLayout.finishLoadMore(true);
            }
        }
    }

    @Override
    public void userIsLogin(boolean login, int id) {
        if (login) {
            userStartAction(id);
        } else {
            gotoLogin();
        }
    }

    protected void userStartAction(int viewId) {

    }

    private void gotoLogin() {
        Intent intent = new Intent(mFragmentActivity, LoginAndRegisterAct.class);
        startActivityForResult(intent, LoginAndRegisterAct.LOGIN_REQUEST);
    }
}
