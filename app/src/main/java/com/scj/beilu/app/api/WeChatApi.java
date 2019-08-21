package com.scj.beilu.app.api;

import com.scj.beilu.app.mvp.wechat.bean.WeChatTokenBean;
import com.scj.beilu.app.mvp.wechat.bean.WeChatUserInfoBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @author Mingxun
 * @time on 2019/2/22 13:15
 */
public interface WeChatApi {
    @GET
    Observable<WeChatTokenBean> getWeChatToken(@Url String url);

    @GET("https://api.weixin.qq.com/sns/userinfo")
    Observable<WeChatUserInfoBean> getWeChatUserInfo(@Query("access_token") String access_token,
                                                     @Query("openid") String openid);

}
