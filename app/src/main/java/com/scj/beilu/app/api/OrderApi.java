package com.scj.beilu.app.api;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.order.bean.OrderInfoResultBean;
import com.scj.beilu.app.mvp.order.bean.OrderListInfoResultBean;
import com.scj.beilu.app.mvp.order.bean.OrderLogisticsInfoResultBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * @author Mingxun
 * @time on 2019/4/4 16:22
 */
public interface OrderApi {

    /**
     * 获取当前用户所有订单列表
     */
    @FormUrlEncoded
    @POST("/apigateway/order/api/mobile/order/getAllOrderPage")
    Observable<OrderListInfoResultBean> getAllOrderList(@HeaderMap Map<String, String> token,
                                                        @Field("currentPage") int currentPage);

    /**
     * 根据状态查看订单列表
     */
    @FormUrlEncoded
    @POST("/apigateway/order/api/mobile/order/getOrderStatusView")
    Observable<OrderListInfoResultBean> getOrderListByStatus(@HeaderMap Map<String, String> token,
                                                        @Field("orderStatusId") int orderStatusId,
                                                        @Field("currentPage") int currentPage);

    /**
     * 根据订单查看物流信息
     */
    @FormUrlEncoded
    @POST("/apigateway/order/api/mobile/order/getLogistics")
    Observable<OrderLogisticsInfoResultBean> getOrderLogisticsInfoByOrderNo(@HeaderMap Map<String, String> token,
                                                                            @Field("orderNo") String orderNo);


    /**
     * 申请退款
     */
    @FormUrlEncoded
    @POST("/apigateway/order/api/mobile/order/applyRefund")
    Observable<ResultMsgBean> applyOrderRefund(@HeaderMap Map<String, String> token,
                                               @Field("orderNo") String orderNo,
                                               @Field("userApplyReason") String userApplyReason);

    /**
     * 订单详情
     */
    @FormUrlEncoded
    @POST("/apigateway/order/api/mobile/order/getOrderDetails")
    Observable<OrderInfoResultBean> getOrderInfoByOrderNo(@HeaderMap Map<String, String> token,
                                                          @Field("orderNo") String orderNo);

    /**
     * 取消订单
     */
    @FormUrlEncoded
    @POST("/apigateway/order/api/mobile/order/cancelOrder")
    Observable<ResultMsgBean> cancelOrder(@HeaderMap Map<String, String> token, @Field("orderNo") String orderNo);

}
