package com.scj.beilu.app.mvp.course.bean;

import com.scj.beilu.app.mvp.common.bean.PageBean;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/12 16:58
 */
public class CourseListBean extends ResultMsgBean {

    private List<CourseInfoBean> courses;

    private PageBean<CourseInfoBean> page;

    public List<CourseInfoBean> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseInfoBean> courses) {
        this.courses = courses;
    }

    public PageBean<CourseInfoBean> getPage() {
        return page;
    }

    public void setPage(PageBean<CourseInfoBean> page) {
        this.page = page;
    }
}
