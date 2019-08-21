package com.scj.beilu.app.mvp.course.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/22 16:29
 */
public class CourseVideoListBean extends ResultMsgBean {

    private List<CourseVideoInfoBean> courseVideos;

    public List<CourseVideoInfoBean> getCourseVideos() {
        return courseVideos;
    }

    public void setCourseVideos(List<CourseVideoInfoBean> courseVideos) {
        this.courseVideos = courseVideos;
    }
}
