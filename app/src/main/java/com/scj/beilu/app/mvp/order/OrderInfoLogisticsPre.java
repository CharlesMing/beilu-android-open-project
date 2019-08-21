package com.scj.beilu.app.mvp.order;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.mvp.order.bean.OrderLogisticsInfoResultBean;
import com.scj.beilu.app.mvp.order.model.IOrderInfo;
import com.scj.beilu.app.mvp.order.model.OrderInfoImpl;

/**
 * @author Mingxun
 * @time on 2019/4/8 22:10
 */
public class OrderInfoLogisticsPre extends BaseMvpPresenter<OrderInfoLogisticsPre.OrderInfoLogisticsView> {

    private IOrderInfo mOrderInfo;

    public OrderInfoLogisticsPre(Context context, String orderNo) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mOrderInfo = new OrderInfoImpl(mBuilder, orderNo);
    }

    public void getLogisticsList() {
        onceViewAttached(new ViewAction<OrderInfoLogisticsView>() {
            @Override
            public void run(@NonNull final OrderInfoLogisticsView mvpView) {
                mOrderInfo.getLogisticsInfo()
                        .subscribe(new ObserverCallback<OrderLogisticsInfoResultBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(OrderLogisticsInfoResultBean orderLogisticsInfoResultBean) {
                                mvpView.onLogisticsList(orderLogisticsInfoResultBean);
                            }
                        });
            }
        });
    }

    public interface OrderInfoLogisticsView extends MvpView {
        void onLogisticsList(OrderLogisticsInfoResultBean logisticsInfo);
    }
}
