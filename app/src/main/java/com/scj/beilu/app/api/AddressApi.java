package com.scj.beilu.app.api;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.mine.address.bean.AddressArrayBean;
import com.scj.beilu.app.mvp.mine.address.bean.AddressInfoResultBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * @author Mingxun
 * @time on 2019/4/4 11:40
 */
public interface AddressApi {
    /**
     * 查看所有地址
     */
    @POST("/apigateway/user/api/mobile/user/getAllAddrByUserId")
    Observable<AddressArrayBean> getAllAddrByUserId(@HeaderMap Map<String, String> headers);

    /**
     * 增加地址
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/addUserAddr")
    Observable<ResultMsgBean> addUserAddr(@HeaderMap Map<String, String> headers, @FieldMap Map<String, Object> map);

    /**
     * 修改地址相关信息
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/modifyUserShipAddr")
    Observable<ResultMsgBean> modifyUserShipAddr(@HeaderMap Map<String, String> headers, @FieldMap Map<String, Object> map);

    /**
     * 删除地址
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/delUserAddrById")
    Observable<ResultMsgBean> delUserAddrById(@HeaderMap Map<String, String> headers, @FieldMap Map<String, Object> map);

    /**
     * 获取默认地址
     */
    @POST("/apigateway/user/api/mobile/user/getUserDefaultAddr")
    Observable<AddressInfoResultBean> getDefAddrByToken(@HeaderMap Map<String, String> token);
}
