package com.scj.beilu.app.mvp.course.bean;

import com.scj.beilu.app.mvp.common.bean.PageBean;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/3/26 02:10
 */
public class CourseBoughtListResult extends ResultMsgBean {

    /**
     * page : {"currentPage":1,"list":[{"courseVideoId":1,"courseId":21,"courseVideoName":"test1","courseVideoBrief":"testContent","courseVideoAddr":null,"courseVideoPic":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/course/video/pic/000be002-247f-4f00-bff9-d236f91548ca20190258221936.jpg","courseVideoPicZip":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/course/video/pic/6754eb05-7ecd-434f-8be7-f40b3e4d2a0720190258221423.jpg","courseVideoPrice":null,"courseVideoBrowseCount":16,"courseVideoTime":23,"courseVideoIsPurchase":0}],"startCount":0,"nextPage":1,"totalCount":0}
     */
    private PageBean<CourseVideoInfoBean> page;
    public PageBean<CourseVideoInfoBean> getPage() {
        return page;
    }

    public void setPage(PageBean<CourseVideoInfoBean> page) {
        this.page = page;
    }
}
