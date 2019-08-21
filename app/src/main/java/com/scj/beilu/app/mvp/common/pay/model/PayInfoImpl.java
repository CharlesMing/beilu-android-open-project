package com.scj.beilu.app.mvp.common.pay.model;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.mx.pro.lib.mvp.exception.UserException;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.api.Constants;
import com.scj.beilu.app.api.PayApi;
import com.scj.beilu.app.mvp.base.user.BaseLoadUserInfoDelegate;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.common.pay.bean.AliPayInfoResultBean;
import com.scj.beilu.app.mvp.common.pay.bean.WeChatPayBean;
import com.scj.beilu.app.mvp.common.pay.bean.WeChatPayResultBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mingxun
 * @time on 2019/3/23 15:37
 */
public class PayInfoImpl extends BaseLoadUserInfoDelegate implements IPayInfo {

    private PayApi mPayApi;
    private IWXAPI mIWXAPI;
    public static final int SDK_PAY_FLAG = 1;

    public PayInfoImpl(Builder builder) {
        super(builder);
        mPayApi = create(PayApi.class);
        mIWXAPI = WXAPIFactory.createWXAPI(context, Constants.WXAPPID);
    }

    @Override
    public <PayResult extends ResultMsgBean> Observable<PayResult> startCoursePay(final int payType, final int courseVideoId, final int courseId) {
        mBuilder.setLoadType(ShowConfig.LOADING_DIALOG);
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<PayResult>>() {
                    @Override
                    public ObservableSource<PayResult> apply(Map<String, String> token) throws Exception {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            Map<String, Integer> params = new HashMap<>();
                            if (courseId != -1) {
                                params.put("courseId", courseId);
                            }
                            if (courseVideoId != -1) {
                                params.put("courseVideoId", courseVideoId);
                            }

                            if (payType == 1) {//wxpay
                                return (ObservableSource<PayResult>) createObservable(mPayApi.coursePayWx(token, params));
                            } else {//alipay
                                return (ObservableSource<PayResult>) createObservable(mPayApi.coursePayAliPay(token, params));
                            }
                        }
                    }
                });
    }

    /**
     * 发起微信支付
     */
    public void setWxPayInfo(WeChatPayResultBean resultBean) {
        try {
            WeChatPayBean result = resultBean.getOrderInfo();
            PayReq payReq = new PayReq();
            payReq.appId = result.getAppid();
            payReq.partnerId = result.getPartnerid();
            payReq.prepayId = result.getPrepayid();
            payReq.nonceStr = result.getNoncestr();
            payReq.timeStamp = String.valueOf(result.getTimestamp());
            payReq.packageValue = result.getPackageX();
            payReq.sign = result.getSign();
            payReq.extData = "app data"; // optional
            mIWXAPI.sendReq(payReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发起支付宝支付
     */
    public void setAliPay(final Activity activity, final AliPayInfoResultBean orderInfo, final Handler handler) {
        final Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo.getOrderInfo(), true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public <PayResult extends ResultMsgBean> Observable<PayResult> startGoodsPay(final String orderNo, final int productId, final int productCount,
                                                                                 final int orderPayId, final String productName,
                                                                                 final int addrId, final String description) {
        mBuilder.setLoadType(ShowConfig.LOADING_DIALOG);
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<PayResult>>() {
                    @Override
                    public ObservableSource<PayResult> apply(Map<String, String> token) throws UserException {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            Map<String, Object> params = new HashMap<>();
                            if (orderNo != null) {
                                params.put("tradeNo", orderNo);
                            }
                            params.put("productId", productId);
                            params.put("productCount", productCount);
                            params.put("orderPayId", orderPayId);
                            params.put("productName", productName);
                            params.put("addrId", addrId);
                            params.put("description", description);
                            if (orderPayId == 1) {
                                return (ObservableSource<PayResult>) createObservable(mPayApi.setWxPay(token, params));
                            } else {
                                return (ObservableSource<PayResult>) createObservable(mPayApi.setAliPay(token, params));
                            }
                        }
                    }
                });
    }

}
