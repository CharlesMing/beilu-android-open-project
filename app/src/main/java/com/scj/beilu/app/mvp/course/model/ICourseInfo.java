package com.scj.beilu.app.mvp.course.model;

import com.scj.beilu.app.mvp.course.bean.CourseBoughtListResult;
import com.scj.beilu.app.mvp.course.bean.CourseCommentInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseCommentListBean;
import com.scj.beilu.app.mvp.course.bean.CourseDetailsBean;
import com.scj.beilu.app.mvp.course.bean.CourseListBean;
import com.scj.beilu.app.mvp.course.bean.CourseTeacherInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseTypeListBean;
import com.scj.beilu.app.mvp.course.bean.CourseVideoListBean;
import com.scj.beilu.app.mvp.course.bean.CourseVideoResultBean;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/3/20 15:54
 */
public interface ICourseInfo {
    /**
     * 获取所有课程分类
     *
     * @return
     */
    Observable<CourseTypeListBean> getCourseTypeList();

    /**
     * 根据分类获取课程列表
     *
     * @param courseCateId
     * @param currentPage
     * @return
     */
    Observable<CourseListBean> getCourseList(int courseCateId, int currentPage);

    /**
     * 获取课程详情
     *
     * @param courseId
     * @return
     */
    Observable<CourseDetailsBean> getCourseInfoById(int courseId);

    /**
     * 获取课程教师信息
     */
    Observable<CourseTeacherInfoBean> getCourseTeacherInfo(int courseId);

    /**
     * 获取课程视频
     */
    Observable<CourseVideoListBean> getCourseVideoById(int courseId);

    /**
     * 创建评论
     */
    Observable<CourseCommentInfoBean> createCourseComment(int courseId, String content);

    /**
     * 回复评论
     */
    /**
     * 获取评论列表
     */
    Observable<CourseCommentListBean> getCommentList(int courseId, int currentPage);

    /**
     * 获取视频信息
     */
    Observable<CourseVideoResultBean> getVideoInfo(int courseVideoId);

    /**
     * 已购课程
     */
    Observable<CourseBoughtListResult> getBoughtList(int currentPage);
}
