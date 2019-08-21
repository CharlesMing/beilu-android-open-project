package com.scj.beilu.app.mvp.common.pay;

import com.mx.pro.lib.mvp.MvpView;

/**
 * @author Mingxun
 * @time on 2019/3/23 15:33
 */
public interface WXPayView extends MvpView {
    //    void onStartWeChatPay(WeChatPayBean result);
    void onAliPaySuccess();
}
