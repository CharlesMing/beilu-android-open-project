package com.scj.beilu.app.api;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.course.bean.CourseBoughtListResult;
import com.scj.beilu.app.mvp.course.bean.CourseCommentListBean;
import com.scj.beilu.app.mvp.course.bean.CourseDetailsBean;
import com.scj.beilu.app.mvp.course.bean.CourseListBean;
import com.scj.beilu.app.mvp.course.bean.CourseTeacherInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseTypeListBean;
import com.scj.beilu.app.mvp.course.bean.CourseVideoListBean;
import com.scj.beilu.app.mvp.course.bean.CourseVideoResultBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * @author Mingxun
 * @time on 2019/3/20 15:50
 * 课程相关
 */
public interface CourseApi {

    /**
     * 获取课程所有分类
     *
     * @return
     */
    @POST("/apigateway/course/api/mobile/course/getCourseCate")
    Observable<CourseTypeListBean> getCourseTypeList();

    /**
     * 根据分类查看课程列表
     */
    @FormUrlEncoded
    @POST("/apigateway/course/api/mobile/course/getCourseListByCate")
    Observable<CourseListBean> getCourseList(@Field("courseCateId") int courseCateId, @Field("currentPage") int currentPage);

    /**
     * 查看课程详情
     */
    @FormUrlEncoded
    @POST("/apigateway/course/api/mobile/course/getCourseDetailById")
    Observable<CourseDetailsBean> getCourseInfoById(@HeaderMap Map<String, String> header, @Field("courseId") int courseId);

    /**
     * 老师简介
     */
    @FormUrlEncoded
    @POST("/apigateway/course/api/mobile/course/getCourseTeacherDetail")
    Observable<CourseTeacherInfoBean> getCourseTeacherInfo(@Field("courseId") int courseId);

    /**
     * 获取课程视频
     */
    @FormUrlEncoded
    @POST("/apigateway/course/api/mobile/course/getCourseVideo")
    Observable<CourseVideoListBean> getCourseVideoListById(@HeaderMap Map<String, String> header, @Field("courseId") int courseId);

    /**
     * 创建课程评论
     *
     * @param token
     * @param courseId
     * @param comContent
     * @return
     */
    @FormUrlEncoded
    @POST("/apigateway/course/api/mobile/course/commentCourse")
    Observable<ResultMsgBean> createCourseComment(@HeaderMap Map<String, String> token, @Field("courseId") int courseId,
                                                  @Field("comContent") String comContent);

    /**
     * 查询课程评论
     */
    @FormUrlEncoded
    @POST("/apigateway/course/api/mobile/course/getCourseComments")
    Observable<CourseCommentListBean> getCommentList(@Field("courseId") int courseId, @Field("currentPage") int currentPage);

    /**
     * 获取视频地址
     */
    @FormUrlEncoded
    @POST("/apigateway/course/api/mobile/course/getVideoDetail")
    Observable<CourseVideoResultBean> getVideoInfo(@HeaderMap Map<String, String> token, @Field("courseVideoId") int courseVideoId);

    /**
     * 已购课程
     */
    @FormUrlEncoded
    @POST("/apigateway/course/api/mobile/course/getMyPurchaseVideo")
    Observable<CourseBoughtListResult> getBoughtList(@HeaderMap Map<String, String> token, @Field("currentPage") int currentPage);
}
