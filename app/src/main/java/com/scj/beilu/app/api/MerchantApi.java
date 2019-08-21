package com.scj.beilu.app.api;

import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoCoachPicInfoResultBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoCoachResultBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoListBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoResultBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicInfoResultBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicTypeResultBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author Mingxun
 * @time on 2019/4/13 18:46
 */
public interface MerchantApi {

    /**
     * 根据位置获取商家列表
     */
    @FormUrlEncoded
    @POST("/apigateway/merchant/api/mobile/merchant/getMerchantIndex")
    Observable<MerchantInfoListBean> getLocationByMerchantList(@FieldMap Map<String, Object> params);

    /**
     * 查看商家基本信息
     */
    @FormUrlEncoded
    @POST("/apigateway/merchant/api/mobile/merchant/getMerchantInfoById")
    Observable<MerchantInfoResultBean> getMerchantInfoById(@Field("merchantId") int merchantId);

    /**
     * 查询教练详情
     */
    @FormUrlEncoded
    @POST("/apigateway/merchant/api/mobile/merchant/getMerchantCoachInfoById")
    Observable<MerchantInfoCoachResultBean> getCoachInfo(@Field("merchantId") int merchantId, @Field("coachId") int coachId);

    /**
     * 查看商家图片分类
     */
    @FormUrlEncoded
    @POST("/apigateway/merchant/api/mobile/merchant/getAllMerchantRegionName")
    Observable<MerchantPicTypeResultBean> getMerchantPicTypeList(@Field("merchantId") int merchantId);

    /**
     * 根据分类查看图片
     */
    @FormUrlEncoded
    @POST("/apigateway/merchant/api/mobile/merchant/getMerchantRegionPicById")
    Observable<MerchantPicInfoResultBean> getMerchantPicListById(@Field("merchantId") int merchantId, @Field("regionId") int regionId);

    /**
     * 查看教练相册
     */
    @FormUrlEncoded
    @POST("/apigateway/merchant/api/mobile/merchant/getMerchantCoachAlbumById")
    Observable<MerchantInfoCoachPicInfoResultBean> getCoachAlbum(@Field("merchantId") int merchantId, @Field("coachId") int coachId);
}
