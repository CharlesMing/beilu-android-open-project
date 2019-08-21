package com.scj.beilu.app.mvp.order.model;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.order.bean.OrderInfoResultBean;
import com.scj.beilu.app.mvp.order.bean.OrderListInfoResultBean;
import com.scj.beilu.app.mvp.order.bean.OrderLogisticsInfoResultBean;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/4/4 16:34
 */
public interface IOrderInfo {
    /**
     * 获取所有订单列表
     *
     * @param currentPage
     * @return
     */
    Observable<OrderListInfoResultBean> getAllOrderList(int currentPage);

    /**
     * 根据订单状态查看订单列表
     */
    Observable<OrderListInfoResultBean> getOrderListByStatusId(int orderStatusId, int currentPage);

    /**
     * 获取物流信息
     */
    Observable<OrderLogisticsInfoResultBean> getLogisticsInfo();

    /**
     * 申请退款
     */
    Observable<ResultMsgBean> applyRefund(String userApplyReason);

    /**
     * 订单详情
     */
    Observable<OrderInfoResultBean> getOrderInfoByOrderNo();

    /**
     * 订单取消
     */
    Observable<ResultMsgBean> setCancelOrder();
}
