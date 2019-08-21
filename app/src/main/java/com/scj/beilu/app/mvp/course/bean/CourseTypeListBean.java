package com.scj.beilu.app.mvp.course.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/20 15:50
 */
public class CourseTypeListBean extends ResultMsgBean {

    private List<CourseTypeInfoBean> cates;

    public List<CourseTypeInfoBean> getCates() {
        return cates;
    }

    public void setCates(List<CourseTypeInfoBean> cates) {
        this.cates = cates;
    }
}
