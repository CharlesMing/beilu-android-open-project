package com.scj.beilu.app.api;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.store.bean.CollectGoodsListResultBean;
import com.scj.beilu.app.mvp.store.bean.GoodsBannerResultBean;
import com.scj.beilu.app.mvp.store.bean.GoodsCategoryListBean;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoResultBean;
import com.scj.beilu.app.mvp.store.bean.GoodsListResultBean;
import com.scj.beilu.app.mvp.store.bean.GoodsSpecificationResultBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * @author Mingxun
 * @time on 2019/3/26 03:30
 */
public interface GoodsApi {
    /**
     * 获取首页Banner
     *
     * @return
     */
    @POST("/apigateway/product/api/mobile/product/getProductBanner")
    Observable<GoodsBannerResultBean> getStoreBannerList();

    /**
     * 获取商城首页分类
     */
    @POST("/apigateway/product/api/mobile/product/getAllProductCate")
    Observable<GoodsCategoryListBean> getGoodsCateGoryList();

    /**
     * 根据分类id获取商品列表
     */
    @FormUrlEncoded
    @POST("/apigateway/product/api/mobile/product/getProductCateView")
    Observable<GoodsListResultBean> getGoodsListByCategoryId(@Field("productCateId") int productCateId,
                                                             @Field("currentPage") int currentPage);

    /**
     * 获取商品列表
     */
    @FormUrlEncoded
    @POST("/apigateway/product/api/mobile/product/getAllProductPage")
    Observable<GoodsListResultBean> getGoodsList(@Field("currentPage") int currentPage);

    /**
     * 获取商品详情
     */
    @FormUrlEncoded
    @POST("/apigateway/product/api/mobile/product/getProductDetailsById")
    Observable<GoodsInfoResultBean> getGoodsInfoById(@HeaderMap Map<String, String> token,
                                                     @Field("productId") int productId);

    /**
     * 收藏商品
     */
    @FormUrlEncoded
    @POST("/apigateway/product/api/mobile/product/collectionOrcancelProduct")
    Observable<ResultMsgBean> setCollect(@HeaderMap Map<String, String> token,
                                         @Field("productId") int productId);

    /**
     * 商品規格
     */
    @FormUrlEncoded
    @POST("/apigateway/product/api/mobile/product/getProductFormatById")
    Observable<GoodsSpecificationResultBean> getGoodsSpecificationInfo(@Field("productId") int productId);

    /**
     * 搜索商品
     */
    @FormUrlEncoded
    @POST("/apigateway/product/api/mobile/product/searchByKeyName")
    Observable<GoodsListResultBean> searchGoodsList(@Field("keyName") String keyName, @Field("currentPage") int currentPage);

    /**
     * 获取收藏的商品
     */
    @FormUrlEncoded
    @POST("/apigateway/product/api/mobile/product/getMyProductCollection")
    Observable<CollectGoodsListResultBean> getMyCollectGoods(@HeaderMap Map<String, String> token,
                                                             @Field("currentPage") int currentPage);
}
