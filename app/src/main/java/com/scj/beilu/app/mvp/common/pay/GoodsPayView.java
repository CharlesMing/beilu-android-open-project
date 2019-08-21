package com.scj.beilu.app.mvp.common.pay;

import com.mx.pro.lib.mvp.MvpView;

/**
 * @author Mingxun
 * @time on 2019/4/9 12:38
 */
public interface GoodsPayView extends MvpView {
    void onAliPaySuccess();

    void onOrderNoResult(String orderNo);
}
