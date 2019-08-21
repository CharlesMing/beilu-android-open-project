package com.scj.beilu.app.mvp.order.model;

import com.mx.pro.lib.mvp.exception.UserException;
import com.scj.beilu.app.api.OrderApi;
import com.scj.beilu.app.mvp.base.user.BaseLoadUserInfoDelegate;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.order.bean.OrderInfoResultBean;
import com.scj.beilu.app.mvp.order.bean.OrderListInfoResultBean;
import com.scj.beilu.app.mvp.order.bean.OrderLogisticsInfoResultBean;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mingxun
 * @time on 2019/4/4 16:37
 */
public class OrderInfoImpl extends BaseLoadUserInfoDelegate implements IOrderInfo {

    private OrderApi mOrderApi;
    private String mOrderNo;

    public OrderInfoImpl(Builder builder, String orderNo) {
        super(builder);
        mOrderApi = create(OrderApi.class);
        mOrderNo = orderNo;
    }

    public OrderInfoImpl(Builder builder) {
        this(builder, null);
    }

    @Override
    public Observable<OrderListInfoResultBean> getAllOrderList(final int currentPage) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<OrderListInfoResultBean>>() {

                    @Override
                    public ObservableSource<OrderListInfoResultBean> apply(Map<String, String> token) throws Exception {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return createObservable(mOrderApi.getAllOrderList(token, currentPage));
                        }
                    }
                });
    }

    @Override
    public Observable<OrderListInfoResultBean> getOrderListByStatusId(final int orderStatusId, final int currentPage) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<OrderListInfoResultBean>>() {
                    @Override
                    public ObservableSource<OrderListInfoResultBean> apply(Map<String, String> token) throws Exception {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return createObservable(mOrderApi.getOrderListByStatus(token, orderStatusId, currentPage));
                        }
                    }
                });
    }

    @Override
    public Observable<OrderLogisticsInfoResultBean> getLogisticsInfo() {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<OrderLogisticsInfoResultBean>>() {
                    @Override
                    public ObservableSource<OrderLogisticsInfoResultBean> apply(Map<String, String> token) throws Exception {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return createObservable(mOrderApi.getOrderLogisticsInfoByOrderNo(token, mOrderNo));
                        }
                    }
                });
    }

    @Override
    public Observable<ResultMsgBean> applyRefund(final String userApplyReason) {
        return
                getHeader().flatMap(new Function<Map<String, String>, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(Map<String, String> token) throws UserException {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return createObservable(mOrderApi.applyOrderRefund(token, mOrderNo, userApplyReason));
                        }
                    }
                });
    }

    @Override
    public Observable<OrderInfoResultBean> getOrderInfoByOrderNo() {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<OrderInfoResultBean>>() {
                    @Override
                    public ObservableSource<OrderInfoResultBean> apply(Map<String, String> token) throws UserException {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return createObservable(mOrderApi.getOrderInfoByOrderNo(token, mOrderNo));
                        }
                    }
                });
    }

    @Override
    public Observable<ResultMsgBean> setCancelOrder() {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(Map<String, String> token) throws UserException {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return createObservable(mOrderApi.cancelOrder(token, mOrderNo));
                        }
                    }
                });
    }
}
