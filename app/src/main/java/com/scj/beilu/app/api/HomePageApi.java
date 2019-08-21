package com.scj.beilu.app.api;

import com.scj.beilu.app.mvp.common.bean.BannerListBean;
import com.scj.beilu.app.mvp.course.bean.CourseListBean;
import com.scj.beilu.app.mvp.find.bean.FindListBean;
import com.scj.beilu.app.mvp.home.bean.HomePageFindListBean;
import com.scj.beilu.app.mvp.home.bean.HomePageNewsListBean;
import com.scj.beilu.app.mvp.mine.bean.MeFocusListBean;
import com.scj.beilu.app.mvp.store.bean.GoodsListBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * @author Mingxun
 * @time on 2019/3/12 16:49
 */
public interface HomePageApi {

    /**
     * 获取首页Banner
     */
    @POST("/apigateway/adver/api/mobile/adver/getIndexBanner")
    Observable<BannerListBean> getBannerList();

    /**
     * 获取首页课程
     */
    @POST("/apigateway/course/api/mobile/course/getFeaturedCourse")
    Observable<CourseListBean> getHomeCourseList();

    /**
     * 获取首页商品
     */
    @POST("/apigateway/product/api/mobile/product/selectedProduct")
    Observable<GoodsListBean> getHomeGoodsList();

    /**
     * 获取首页资讯
     */
    @POST("/apigateway/news/api/mobile/news/getFeaturedNews")
    Observable<HomePageNewsListBean> getHomeNewsList();

    /**
     * 获取首页动态
     */
    @POST("/apigateway/dynamic/api/mobile/dynamic/getFeaturedDynamic")
    Observable<HomePageFindListBean> getHomeFindList(@HeaderMap Map<String, String> header);

    /**
     * 动态搜索
     * 搜索类型，1：动态，2：资讯，3：商品，4：用户
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/getHomeSearch")
    Observable<FindListBean> searchFind(@HeaderMap Map<String, String> header, @Field("searchType") int searchType,
                                        @Field("keyName") String keyName, @Field("currentPage") int currentPage);

    /**
     * 动态搜索
     * 搜索类型，1：动态，2：资讯，3：商品，4：用户
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/getHomeSearch")
    Observable<MeFocusListBean> searchUser(@HeaderMap Map<String, String> header, @Field("searchType") int searchType,
                                           @Field("keyName") String keyName, @Field("currentPage") int currentPage);

}
