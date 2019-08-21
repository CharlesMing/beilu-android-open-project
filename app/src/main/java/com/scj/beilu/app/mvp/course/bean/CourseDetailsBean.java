package com.scj.beilu.app.mvp.course.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/3/21 23:52
 */
public class CourseDetailsBean extends ResultMsgBean {

    /**
     * course : {"courseId":21,"courseName":"321","courseTitle":"思考京东方","coursePic":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/course/pic/9cb00eca-d690-4e23-8c42-68549cc261ba20190364162211.jpg","coursePicZip":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/course/pic/9cb00eca-d690-4e23-8c42-68549cc261ba20190364162211.jpg","courseBackPic":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/course/pic/9cb00eca-d690-4e23-8c42-68549cc261ba20190364162211.jpg","courseBackPicZip":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/course/pic/9cb00eca-d690-4e23-8c42-68549cc261ba20190364162211.jpg","courseBrief":"321","courseDec":"规范化渝和堂 ","courseLabel":"萨菲加厚款,点附近可垃圾,卡萨积分","courseTotalPrice":321,"courseSinglePrice":312,"courseNotice":"<p>是的法规刚发的发过的给对方给对方<\/p>","courseAdvisoryTel":"13698369491","courseCateId":0,"courseCateName":null,"courseBrowseCount":0,"courseVideoCount":4,"courseVideoTime":110,"teacher":null,"qas":[{"courseQaId":0,"courseId":0,"courseQ":"而 而","courseA":" 二哥 寡妇 个人广告费"},{"courseQaId":0,"courseId":0,"courseQ":"发 付广告费大锅饭","courseA":"放电个梵蒂冈伐哥"}]}
     */

    private CourseInfoBean course;

    public CourseInfoBean getCourse() {
        return course;
    }

    public void setCourse(CourseInfoBean course) {
        this.course = course;
    }

}
