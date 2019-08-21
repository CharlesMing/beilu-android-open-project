package com.scj.beilu.app.mvp.mine.order;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.pay.GoodsPayPre;
import com.scj.beilu.app.mvp.common.pay.GoodsPayView;
import com.scj.beilu.app.mvp.order.bean.OrderInfoBean;
import com.scj.beilu.app.mvp.order.bean.OrderListInfoResultBean;
import com.scj.beilu.app.mvp.order.model.IOrderInfo;
import com.scj.beilu.app.mvp.order.model.OrderInfoImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/1/15 21:15
 */
public class OrderListPre extends GoodsPayPre<OrderListPre.OrderListView> {

    private IOrderInfo mOrderInfo;

    private final List<OrderInfoBean> mOrderList = new ArrayList<>();

    public OrderListPre(Activity activity) {
        super(activity);
        mOrderInfo = new OrderInfoImpl(mBuilder);
    }

    public void getOrderStatusList(final int index, final int currentPage) {
        mBuilder.setLoadType(ShowConfig.LOADING_REFRESH);

        switch (index) {
            case 0:
                getAllOrderList(currentPage);
                break;
            case 1:
                getOrderListByStatus(1, currentPage);
                break;
            case 2:
                getOrderListByStatus(6, currentPage);
                break;
            case 3:
                getOrderListByStatus(9, currentPage);
                break;
        }
    }

    private void getAllOrderList(final int currentPage) {
        onceViewAttached(new ViewAction<OrderListView>() {
            @Override
            public void run(@NonNull final OrderListView mvpView) {

                mOrderInfo.getAllOrderList(currentPage)
                        .subscribe(new BaseResponseCallback<OrderListInfoResultBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(OrderListInfoResultBean orderListInfoResultBean) {
                                try {
                                    List<OrderInfoBean> list = orderListInfoResultBean.getPage().getList();
                                    if (currentPage == 0) {
                                        mOrderList.clear();
                                    }
                                    mOrderList.addAll(list);
                                    mvpView.onCheckLoadMore(list);
                                    mvpView.onOrderList(mOrderList);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });

    }

    private void getOrderListByStatus(final int statusId, final int currentPage) {
        onceViewAttached(new ViewAction<OrderListView>() {
            @Override
            public void run(@NonNull final OrderListView mvpView) {
                mOrderInfo.getOrderListByStatusId(statusId, currentPage)
                        .subscribe(new BaseResponseCallback<OrderListInfoResultBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(OrderListInfoResultBean orderListInfoResultBean) {
                                try {
                                    List<OrderInfoBean> list = orderListInfoResultBean.getPage().getList();
                                    if (currentPage == 0) {
                                        mOrderList.clear();
                                    }
                                    mOrderList.addAll(list);
                                    mvpView.onCheckLoadMore(list);
                                    mvpView.onOrderList(mOrderList);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    public interface OrderListView extends GoodsPayView {
        void onOrderList(List<OrderInfoBean> orderInfoBeanList);

        void onCheckLoadMore(List<?> list);
    }
}
