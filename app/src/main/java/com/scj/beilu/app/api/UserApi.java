package com.scj.beilu.app.api;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.mine.bean.AboutUsBean;
import com.scj.beilu.app.mvp.mine.bean.MeFansListBean;
import com.scj.beilu.app.mvp.mine.bean.MeFocusListBean;
import com.scj.beilu.app.mvp.mine.bean.TotalCountBean;
import com.scj.beilu.app.mvp.mine.bean.UpdateAvatarResultBean;
import com.scj.beilu.app.mvp.user.bean.AvatarResult;
import com.scj.beilu.app.mvp.user.bean.UserDetailsBean;
import com.scj.beilu.app.mvp.user.bean.UserInfoBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * @author Mingxun
 * @time on 2019/1/28 19:01
 */
public interface UserApi {
    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/userRegister")
    Observable<ResultMsgBean> register(@FieldMap Map<String, Object> params);

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/userLogin")
    Observable<ResultMsgBean> login(@FieldMap Map<String, Object> params);

    /**
     * 查看个人信息
     */
    @POST("/apigateway/user/api/mobile/user/getUserById")
    Observable<UserInfoBean> getUserInfo(@HeaderMap Map<String, String> token);

    /**
     * 个人中心统计
     */
    @POST("/apigateway/user/api/mobile/user/getTotalCount")
    Observable<TotalCountBean> getTotalCount(@HeaderMap Map<String, String> token);

    /**
     * 修改用户信息
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/modifyUserInfo")
    Observable<ResultMsgBean> modifyUserInfo(@HeaderMap Map<String, String> token,
                                             @FieldMap Map<String, Object> map);

    /**
     * 修改头像
     */
    @Multipart
    @POST("/apigateway/user/api/mobile/user/modifyUserHead")
    Observable<UpdateAvatarResultBean> modifyUserHead(@HeaderMap Map<String, String> token,
                                                      @PartMap Map<String, RequestBody> map);

    /**
     * 微信登录
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/WeChatLogin")
    Observable<ResultMsgBean> weChatLogin(@FieldMap Map<String, Object> params);

    /**
     * 我关注的人
     */
    @POST("/apigateway/user/api/mobile/user/getMyFocusNumber")
    @FormUrlEncoded
    Observable<MeFocusListBean> GetMyFocusNumber(@HeaderMap Map<String, String> token, @Field("currentPage") int currentPage);

    /**
     * 获取我的主页信息
     */
    @POST("/apigateway/user/api/mobile/user/getMyUserPage")
    Observable<UserDetailsBean> getMyDetails(@HeaderMap Map<String, String> token);

    /**
     * 获取别人的主页信息
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/getOtherIndexPage")
    Observable<UserDetailsBean> getOtherDetails(@HeaderMap Map<String, String> token, @Field("otherUserId") int otherUserId);

    /**
     * 设置关注好友
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/focusOtherUser")
    Observable<ResultMsgBean> setUserFocus(@HeaderMap Map<String, String> token, @Field("otherUserId") int otherUserId);

    /**
     * 查看我的粉丝
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/getMyFans")
    Observable<MeFansListBean> getMyFans(@HeaderMap Map<String, String> token, @Field("currentPage") int currentPage);

    /**
     * 查看关于我们的信息
     */
    @POST("/apigateway/adver/api/mobile/adver/getAboutUsInfo")
    Observable<AboutUsBean> getAboutUsInfo();

    /**
     * 意见反馈
     */
    @FormUrlEncoded
    @POST("/apigateway/adver/api/mobile/adver/addFeedBack")
    Observable<ResultMsgBean> addFeedBack(@HeaderMap Map<String, String> token, @FieldMap Map<String, String> map);

    /**
     * 修改背景
     */
    @Multipart
    @POST("/apigateway/user/api/mobile/user/updateBackgroundImg")
    Observable<AvatarResult> updateBackgroundImg(@HeaderMap Map<String, String> token, @Part MultipartBody.Part file);

    /**
     * 绑定微信号
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/telBindingweChat")
    Observable<ResultMsgBean> bindWxAccount(@HeaderMap Map<String, String> token,
                                            @Field("openId") String openId);

    /**
     * 绑定手机号 发送验证码
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/sendBindingTelMsg")
    Observable<ResultMsgBean> bindPhoneSendMsg(@Field("weChatTel") String weChatTel);

    /**
     * 绑定手机号
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/weChatBindingTel")
    Observable<ResultMsgBean> bindPhone(@HeaderMap Map<String, String> token,
                                        @Field("code") String code,
                                        @Field("weChatTel") String weChatTel);


}
