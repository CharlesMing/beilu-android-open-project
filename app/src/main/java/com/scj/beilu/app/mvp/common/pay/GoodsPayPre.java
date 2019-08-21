package com.scj.beilu.app.mvp.common.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.common.pay.bean.AliPayInfoResultBean;
import com.scj.beilu.app.mvp.common.pay.bean.PayResult;
import com.scj.beilu.app.mvp.common.pay.bean.WeChatPayResultBean;
import com.scj.beilu.app.mvp.common.pay.model.PayInfoImpl;
import com.scj.beilu.app.util.ToastUtils;

import java.util.Map;

import static com.scj.beilu.app.mvp.common.pay.model.PayInfoImpl.SDK_PAY_FLAG;

/**
 * @author Mingxun
 * @time on 2019/4/9 12:38
 */
public class GoodsPayPre<View extends GoodsPayView> extends BaseMvpPresenter<View> {

    private PayInfoImpl mPayInfo;
    private Activity mActivity;

    public GoodsPayPre(Activity activity) {
        super(activity, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mActivity = activity;
        mPayInfo = new PayInfoImpl(mBuilder);
    }

    public void startPay(final int productId, final int productCount, final int orderPayId, final String productName,
                         final int addrId, final String description) {
        startPay(null, productId, productCount, orderPayId, productName, addrId, description);
    }


    public void startPay(final String orderNo, final int productId, final int productCount, final int orderPayId, final String productName,
                         final int addrId, final String description) {
        mBuilder.setLoadType(ShowConfig.LOADING_DIALOG);
        onceViewAttached(new ViewAction<View>() {
            @Override
            public void run(@NonNull final View mvpView) {
                mPayInfo.startGoodsPay(orderNo, productId, productCount, orderPayId, productName, addrId, description)
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                if (resultMsgBean instanceof AliPayInfoResultBean) {
                                    try {
                                        AliPayInfoResultBean aliPayInfo = (AliPayInfoResultBean) resultMsgBean;
                                        mvpView.onOrderNoResult(aliPayInfo.getOut_trade_no());
                                        mPayInfo.setAliPay(mActivity, aliPayInfo, mHandler);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else if (resultMsgBean instanceof WeChatPayResultBean) {
                                    try {
                                        WeChatPayResultBean wxPayInfo = (WeChatPayResultBean) resultMsgBean;
                                        mvpView.onOrderNoResult(wxPayInfo.getOut_trade_no());
                                        mPayInfo.setWxPayInfo(wxPayInfo);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
            }
        });
    }


    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    onceViewAttached(new ViewAction<View>() {
                        @Override
                        public void run(@NonNull View mvpView) {
                            @SuppressWarnings("unchecked")
                            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                            /**
                             * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                             */
                            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                            String resultStatus = payResult.getResultStatus();
                            // 判断resultStatus 为9000则代表支付成功
                            if (TextUtils.equals(resultStatus, "9000")) {
                                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                                mvpView.onAliPaySuccess();
                            } else {
                                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                                ToastUtils.showToast(mActivity, "支付失败");
                            }
                        }
                    });
                    break;
                }

                default:
                    break;
            }
        }
    };
}
