package com.scj.beilu.app.api;

import com.scj.beilu.app.mvp.common.pay.bean.AliPayInfoResultBean;
import com.scj.beilu.app.mvp.common.pay.bean.WeChatPayResultBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * @author Mingxun
 * @time on 2019/3/23 15:38
 */
public interface PayApi {

    /**
     * 微信支付课程
     */
    @FormUrlEncoded
    @POST("/apigateway/pay/api/WxPay/coursePay")
    Observable<WeChatPayResultBean> coursePayWx(@HeaderMap Map<String, String> token, @FieldMap Map<String, Integer> params);

    /**
     * 支付宝支付课程
     */
    @FormUrlEncoded
    @POST("/apigateway/pay/api/alipay/coursePay")
    Observable<AliPayInfoResultBean> coursePayAliPay(@HeaderMap Map<String, String> token, @FieldMap Map<String, Integer> params);


    /**
     * 支付宝支付商品
     */
    @FormUrlEncoded
    @POST("/apigateway/pay/api/alipay/productPay")
    Observable<AliPayInfoResultBean> setAliPay(@HeaderMap Map<String, String> token,
                                               @FieldMap Map<String, Object> params);

    /**
     * 微信支付商品
     */
    @FormUrlEncoded
    @POST("/apigateway/pay/api/WxPay/productPay")
    Observable<WeChatPayResultBean> setWxPay(@HeaderMap Map<String, String> token,
                                             @FieldMap Map<String, Object> params);


}
