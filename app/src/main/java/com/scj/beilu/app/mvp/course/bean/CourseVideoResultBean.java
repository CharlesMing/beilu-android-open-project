package com.scj.beilu.app.mvp.course.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/3/26 00:21
 */
public class CourseVideoResultBean extends ResultMsgBean {


    /**
     * video : {"courseVideoAddr":"https://beilucourse.oss-cn-beijing.aliyuncs.com/WeChat_20190222105025.mp4","courseVideoPic":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/course/video/pic/000be002-247f-4f00-bff9-d236f91548ca20190258221936.jpg","courseVideoPicZip":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/course/video/pic/6754eb05-7ecd-434f-8be7-f40b3e4d2a0720190258221423.jpg"}
     */

    private CourseVideoInfoBean video;

    public CourseVideoInfoBean getVideo() {
        return video;
    }

    public void setVideo(CourseVideoInfoBean video) {
        this.video = video;
    }
}
