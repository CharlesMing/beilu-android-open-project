package com.scj.beilu.app.mvp.order;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.common.pay.GoodsPayPre;
import com.scj.beilu.app.mvp.common.pay.GoodsPayView;
import com.scj.beilu.app.mvp.order.bean.OrderInfoBean;
import com.scj.beilu.app.mvp.order.bean.OrderInfoResultBean;
import com.scj.beilu.app.mvp.order.bean.OrderLogisticsInfoResultBean;
import com.scj.beilu.app.mvp.order.model.IOrderInfo;
import com.scj.beilu.app.mvp.order.model.OrderInfoImpl;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/4/4 16:19
 */
public class OrderInfoStatusPre extends GoodsPayPre<OrderInfoStatusPre.OrderInfoStatusView> {

    private IOrderInfo mOrderInfo;

    public OrderInfoStatusPre(Activity activity, String orderNo) {
        super(activity);
        mOrderInfo = new OrderInfoImpl(mBuilder, orderNo);
    }

    public void getOrderInfo() {
        onceViewAttached(new ViewAction<OrderInfoStatusView>() {
            @Override
            public void run(@NonNull final OrderInfoStatusView mvpView) {
                Observable.merge(mOrderInfo.getOrderInfoByOrderNo(), mOrderInfo.getLogisticsInfo())
                        .subscribe(new ObserverCallback<Object>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(Object result) {
                                if (result instanceof OrderInfoResultBean) {
                                    OrderInfoResultBean orderInfoResultBean = (OrderInfoResultBean) result;
                                    mvpView.onOrderInfoResult(orderInfoResultBean.getOrder());
                                } else if (result instanceof OrderLogisticsInfoResultBean) {
                                    OrderLogisticsInfoResultBean logisticsInfo = (OrderLogisticsInfoResultBean) result;
                                    mvpView.onLogisticsInfo(logisticsInfo);
                                }
                            }


                        });
            }
        });
    }

    public void setCancelOrder() {
        onceViewAttached(new ViewAction<OrderInfoStatusView>() {
            @Override
            public void run(@NonNull final OrderInfoStatusView mvpView) {
                mOrderInfo.setCancelOrder()
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                mvpView.onCancelResult(resultMsgBean);
                            }
                        });
            }
        });
    }

    public void applyRefund(final String content) {
        onceViewAttached(new ViewAction<OrderInfoStatusView>() {
            @Override
            public void run(@NonNull final OrderInfoStatusView mvpView) {
                mOrderInfo.applyRefund(content)
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                mvpView.onRefundResult(resultMsgBean);
                            }
                        });
            }
        });
    }

    public interface OrderInfoStatusView extends GoodsPayView {

        void onOrderInfoResult(OrderInfoBean orderInfo);

        void onLogisticsInfo(OrderLogisticsInfoResultBean logisticsInfo);

        void onCancelResult(ResultMsgBean result);

        void onRefundResult(ResultMsgBean result);
    }
}
