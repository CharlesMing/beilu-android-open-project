package com.scj.beilu.app.api;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author Mingxun
 * @time on 2019/2/13 15:02
 */
public interface VerifyApi {
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/sendMessageToMobile")
    Observable<ResultMsgBean> sendRegisterVerifyCode(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("apigateway/user/api/mobile/user/sendLoginMessageToMobile")
    Observable<ResultMsgBean> sendLoginVerifyCode(@FieldMap Map<String, Object> params);

}
