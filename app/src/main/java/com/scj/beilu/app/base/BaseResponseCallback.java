package com.scj.beilu.app.base;

import com.mx.pro.lib.mvp.delegate.LoadDelegate;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/13 17:55
 */
public abstract class BaseResponseCallback<T extends ResultMsgBean> extends ObserverCallback<T> {

    private static final Integer[] resultCode = {-1, 1001, 1003, 1006, 1007, 1009, 1011, 5000};
    private static final List<Integer> codes = Arrays.asList(resultCode);

    public BaseResponseCallback(LoadDelegate loadDelegate) {
        super(loadDelegate);
    }

    @Override
    public void onNext(T t) {

        if (codes.contains(t.getCode())) {
            mLoadDelegate.mMvpView.showError(mLoadDelegate.mLoadErrType, t.getResult());
        } else {
            onRequestResult(t);
        }
    }

    public abstract void onRequestResult(T t);
}
