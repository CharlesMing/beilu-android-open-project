package com.scj.beilu.app.api;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordGirthListBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgListBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgResultBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordInfoResultBean;
import com.scj.beilu.app.mvp.fit.bean.FitUserSexBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author Mingxun
 * @time on 2019/3/11 14:36
 */
public interface FitApi {
    /**
     * 设置性别
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/insertRecordSex")
    Observable<ResultMsgBean> setSexOfFitRecord(@HeaderMap Map<String, String> header, @Field("userSex") int sex);

    /**
     * 查看性别
     */
    @POST("/apigateway/user/api/mobile/user/getUserRecordSex")
    Observable<FitUserSexBean> getFitSexInfo(@HeaderMap Map<String, String> header);

    /**
     * 获取用户健身记录
     */
    @POST("/apigateway/user/api/mobile/user/getAllFitnessRecord")
    Observable<FitRecordGirthListBean> getUserFitRecord(@HeaderMap Map<String, String> header);

    /**
     * 添加健身记录
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/addFitnessRecord")
    Observable<ResultMsgBean> addFitRecord(@HeaderMap Map<String, String> header, @FieldMap Map<String, Float> params);

    /**
     * 获取最新健身记录
     */
    @POST("/apigateway/user/api/mobile/user/getNewRecord")
    Observable<FitRecordInfoResultBean> getRecentFitRecord(@HeaderMap Map<String, String> header);

    /**
     * 添加健身记录-添加图片
     */
    @Multipart
    @POST("/apigateway/user/api/mobile/user/addRecordPic")
    Observable<FitRecordImgResultBean> addFitRecordForImg(@HeaderMap Map<String, String> header, @Part MultipartBody.Part file);

    /**
     * 查看健身记录-查看图片
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/getRecordPic")
    Observable<FitRecordImgListBean> getFitRecordImgList(@HeaderMap Map<String, String> header, @Field("currentPage") int currentPage);

    /**
     * 删除健身记录图片
     */
    @FormUrlEncoded
    @POST("/apigateway/user/api/mobile/user/delRecordPic")
    Observable<ResultMsgBean> deleteImg(@HeaderMap Map<String, String> header, @Field("recordPicId") long recordPicId);

}
