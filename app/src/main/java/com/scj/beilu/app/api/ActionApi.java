package com.scj.beilu.app.api;

import com.scj.beilu.app.mvp.action.bean.ActionInfoListBean;
import com.scj.beilu.app.mvp.action.bean.ActionInfoResultBean;
import com.scj.beilu.app.mvp.action.bean.ActionPayPhotoBean;
import com.scj.beilu.app.mvp.action.bean.ActionThirdTypeListBean;
import com.scj.beilu.app.mvp.action.bean.ActionTopListTypeBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * @author Mingxun
 * @time on 2019/3/16 15:44
 * 动作相关
 */
public interface ActionApi {

    /**
     * 获取一层分类和二层分类数据
     */
    @POST("/apigateway/action/api/mobile/action/getAllDes")
    Observable<ActionTopListTypeBean> getActionType();

    /**
     * 获取第三层的分类
     */
    @POST("/apigateway/action/api/mobile/action/getAllCate")
    Observable<ActionThirdTypeListBean> getThirdType();

    /**
     * 根据分类获取动作列表
     */
    @FormUrlEncoded
    @POST("/apigateway/action/api/mobile/action/getAction")
    Observable<ActionInfoListBean> getActionListByTypeId(@Field("desId") int desId, @Field("cateId") int cateId, @Field("partId") int partId);

    /**
     * 获取动作详情
     */
    @FormUrlEncoded
    @POST("/apigateway/action/api/mobile/action/getActionDetail")
    Observable<ActionInfoResultBean> getActionInfoById(@Field("actionId") int actionId);

    /**
     * 获取图片
     */
    @POST("/apigateway/action/api/mobile/action/getActionPoster")
    Observable<ActionPayPhotoBean> getActionPayPhoto(@HeaderMap Map<String, String> header);
}
