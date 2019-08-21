package com.scj.beilu.app.api;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.find.bean.FindCommentListBean;
import com.scj.beilu.app.mvp.find.bean.FindDetailsBean;
import com.scj.beilu.app.mvp.find.bean.FindDetailsRecommendBean;
import com.scj.beilu.app.mvp.find.bean.FindListBean;
import com.scj.beilu.app.mvp.find.bean.LikeResultBean;
import com.scj.beilu.app.mvp.find.bean.OrganizationListBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * @author Mingxun
 * @time on 2019/2/19 10:28
 * 动态相关API
 */
public interface FindApi {

    /**
     * 创建动态
     */
    @Multipart
    @POST("/apigateway/dynamic/api/mobile/dynamic/releaseDynamic")
    Observable<ResultMsgBean> createDynamic(@HeaderMap Map<String, String> headers,
                                            @PartMap Map<String, RequestBody> map);

    /**
     * 获取热门动态
     */
    @FormUrlEncoded
    @POST("/apigateway/dynamic/api/mobile/dynamic/getHotDynamicNext")
    Observable<FindListBean> getHotDynamicList(@HeaderMap Map<String, String> headers, @Field("currentPage") int currentPage);

    /**
     * 获取关注动态
     */
    @FormUrlEncoded
    @POST("/apigateway/dynamic/api/mobile/dynamic/getFocusDynamicNext")
    Observable<FindListBean> getAttentionDynamicList(@HeaderMap Map<String, String> headers, @Field("currentPage") int currentPage);

    /**
     * 设置关注好友
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/focusOtherUser")
    Observable<ResultMsgBean> setUserFocus(@HeaderMap Map<String, String> header, @Field("otherUserId") int otherUserId);

    /**
     * 动态点赞
     */
    @FormUrlEncoded
    @POST("/apigateway/dynamic/api/mobile/dynamic/likeDynamic")
    Observable<LikeResultBean> setLike(@HeaderMap Map<String, String> header, @Field("dynamicId") int dynamicId);

    /**
     * 查看动态详情
     */
    @FormUrlEncoded
    @POST("/apigateway/dynamic/api/mobile/dynamic/getDynamicDetailById")
    Observable<FindDetailsBean> getFindDetailsById(@HeaderMap Map<String, String> header, @Field("dynamicId") int dynamicId);

    /**
     * 获取动态评论列表
     */
    @FormUrlEncoded
    @POST("/apigateway/dynamic/api/mobile/dynamic/getCommentsNextByDynamicId")
    Observable<FindCommentListBean> getFindCommentList(@Field("dynamicId") int dynamicId, @Field("currentPage") int currentPage);

    /**
     * 发布动态
     *
     * @param header
     * @param dynamicId
     * @param comContent
     * @return
     */
    @FormUrlEncoded
    @POST("/apigateway/dynamic/api/mobile/dynamic/commentDynamic")
    Observable<ResultMsgBean> createFindComment(@HeaderMap Map<String, String> header, @Field("dynamicId") int dynamicId,
                                                @Field("comContent") String comContent);

    /**
     * 动态评论回复
     */
    @FormUrlEncoded
    @POST("/apigateway/dynamic/api/mobile/dynamic/dynamicCommentReply")
    Observable<ResultMsgBean> createReplyFidnComment(@HeaderMap Map<String, String> header, @FieldMap Map<String, Object> params);

    /**
     * 获取组织列表
     */
    @FormUrlEncoded
    @POST("/apigateway/organ/api/mobile/organ/getOrganIndex")
    Observable<OrganizationListBean> getOrganizationList(@Field("currentPage") int currentPage);

    /**
     * 获取我的动态
     */
    @FormUrlEncoded
    @POST("/apigateway/dynamic/api/mobile/dynamic/getMyDynamicPage")
    Observable<FindListBean> getMyDynamicByToken(@HeaderMap Map<String, String> header, @Field("currentPage") int currentPage);

    /**
     * 获取其他人的动态
     */
    @FormUrlEncoded
    @POST("/apigateway/dynamic/api/mobile/dynamic/getOtherDynamicPage")
    Observable<FindListBean> getOtherDynamicListByOtherUserId(@HeaderMap Map<String, String> header,
                                                              @Field("userId") int otherUserId, @Field("currentPage") int currentPage);

    /**
     * 统计分享数量
     */
    @FormUrlEncoded
    @POST("/apigateway/dynamic/api/mobile/dynamic/shareDynamic")
    Observable<ResultMsgBean> setShareCount(@Field("dynamicId") int dynamicId);

    /**
     * 删除动态
     */
    @FormUrlEncoded
    @POST("/apigateway/dynamic/api/mobile/dynamic/deleteDynamic")
    Observable<ResultMsgBean> delWithFind(@HeaderMap Map<String, String> header, @Field("dynamicId") int dynamicId);

    /**
     * 获取我的最新动态 第一条
     */
    @POST("/apigateway/dynamic/api/mobile/dynamic/getMyNewDynamic")
    Observable<FindListBean> getMyFindInfo(@HeaderMap Map<String, String> header);

    /**
     * 获取推荐用户
     */
    @POST("/apigateway/user/api/mobile/user/getRecomUser")
    Observable<FindDetailsRecommendBean> getRecommendUserList(@HeaderMap Map<String, String> header);
}
