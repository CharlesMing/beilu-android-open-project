package com.mx.pro.lib.mvp.network.callback;

import com.mx.pro.lib.BuildConfig;
import com.mx.pro.lib.mvp.delegate.LoadDelegate;
import com.mx.pro.lib.mvp.exception.UserException;
import com.mx.pro.lib.mvp.network.config.ShowConfig;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * @author Mingxun
 * @time on 2018/12/25 15:32
 */
public abstract class ObserverCallback<T> implements Observer<T> {
    protected final LoadDelegate mLoadDelegate;

    public ObserverCallback(LoadDelegate loadDelegate) {
        mLoadDelegate = loadDelegate;
    }

    @Override
    public void onSubscribe(Disposable d) {
        mLoadDelegate.mCompositeDisposable.add(d);
        loading(true);
    }

    @Override
    public void onError(Throwable e) {
//        loading(false);
        e.printStackTrace();
        error(e);
    }

    @Override
    public void onComplete() {
        loading(false);
    }

    private void loading(boolean isShow) {
        if (mLoadDelegate.mMvpView == null) return;
        mLoadDelegate.mMvpView.showLoading(mLoadDelegate.mLoadType, isShow);
    }

    protected void onError(String errorMsg) {

    }

    private void error(Throwable e) {
        if (BuildConfig.LOG_REQUEST) {
            e.printStackTrace();
        }

        String errorMsg = "未知错误";
        if (e instanceof UnknownHostException) {
            errorMsg = "网络不可用";
            showNetError(errorMsg);
            return;
        } else if (e instanceof SocketTimeoutException) {
            errorMsg = "请求网络超时";
            showNetError(errorMsg);
            return;
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            errorMsg = convertStatusCode(httpException);
            showNetError(errorMsg);
            return;
        } else if (e instanceof ParseException || e instanceof JSONException
                || e instanceof JSONException) {
            errorMsg = "数据解析错误";
        } else if (e instanceof UserException) {
            if (mLoadDelegate.mMvpView != null) {
                mLoadDelegate.mMvpView.showError(ShowConfig.ERROR_USER, errorMsg);
            }
            return;
        } else if (e instanceof ConnectException) {
            errorMsg = "网络不给力";
            showNetError(errorMsg);
            return;
        }
        if (mLoadDelegate.mMvpView != null) {
            mLoadDelegate.mMvpView.showError(mLoadDelegate.mLoadErrType, errorMsg);
        }
        onError(errorMsg);
    }

    private void showNetError(String errorMsg) {
        if (mLoadDelegate.mMvpView != null) {
            mLoadDelegate.mMvpView.showError(ShowConfig.ERROR_NET, errorMsg);
        }
    }

    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() >= 500 && httpException.code() < 600) {
            msg = "服务器处理请求出错";
        } else if (httpException.code() >= 400 && httpException.code() < 500) {
            msg = "服务器无法处理请求";
        } else if (httpException.code() >= 300 && httpException.code() < 400) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}
