package com.scj.beilu.app.mvp.course.bean;


import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/12 16:55
 */
public class CourseInfoBean extends ResultMsgBean {
    /**
     * courseId : 21
     * courseName : 321
     * courseTitle : 思考京东方
     * coursePic : https://beilutest.oss-cn-hangzhou.aliyuncs.com/course/pic/9cb00eca-d690-4e23-8c42-68549cc261ba20190364162211.jpg
     * coursePicZip : https://beilutest.oss-cn-hangzhou.aliyuncs.com/course/pic/9cb00eca-d690-4e23-8c42-68549cc261ba20190364162211.jpg
     * courseBackPic : https://beilutest.oss-cn-hangzhou.aliyuncs.com/course/pic/9cb00eca-d690-4e23-8c42-68549cc261ba20190364162211.jpg
     * courseBackPicZip : https://beilutest.oss-cn-hangzhou.aliyuncs.com/course/pic/9cb00eca-d690-4e23-8c42-68549cc261ba20190364162211.jpg
     * courseBrief : 321
     * courseDec : 规范化渝和堂
     * courseLabel : 萨菲加厚款,点附近可垃圾,卡萨积分
     * courseTotalPrice : 321.0
     * courseSinglePrice : 312.0
     * courseNotice : <p>是的法规刚发的发过的给对方给对方</p>
     * courseAdvisoryTel : 13698369491
     * courseCateId : 0
     * courseCateName : null
     * courseBrowseCount : 0
     * courseVideoCount : 4
     * courseVideoTime : 110
     * teacher : null
     * qas : [{"courseQaId":0,"courseId":0,"courseQ":"而 而","courseA":" 二哥 寡妇 个人广告费"},{"courseQaId":0,"courseId":0,"courseQ":"发 付广告费大锅饭","courseA":"放电个梵蒂冈伐哥"}]
     */


    private int courseId;
    private String courseName;
    private String coursePic;
    private String courseBackPic;
    private String courseBackPicZip;
    private String coursePicZip;
    private String courseBrief;
    private String courseLabel;
    private double courseTotalPrice;
    private String courseNotice;
    private String courseAdvisoryTel;
    private int courseCateId;
    private String courseCateName;
    private int courseBrowseCount;
    private int courseVideoCount;
    private String courseVideoTime;
    private String teacher;
    private List<CourseDetailsQ$AInfoBean> qas;
    private String courseDec;
    private double courseSinglePrice;
    private String courseTitle;
    private int courseSaleCount;
    private int courseIsPurchase;

    public int getCourseSaleCount() {
        return courseSaleCount;
    }

    public void setCourseSaleCount(int courseSaleCount) {
        this.courseSaleCount = courseSaleCount;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDec() {
        return courseDec;
    }

    public void setCourseDec(String courseDec) {
        this.courseDec = courseDec;
    }

    public String getCourseTotalPrice() {
        DecimalFormat df2 = new DecimalFormat("#.##");
        return df2.format(courseTotalPrice);
    }

    public void setCourseTotalPrice(double courseTotalPrice) {
        this.courseTotalPrice = courseTotalPrice;
    }

    public String getCourseSinglePrice() {
        if (courseSinglePrice > 0) {
            DecimalFormat df2 = new DecimalFormat("#.##");
            return df2.format(courseSinglePrice);
        } else return null;
    }

    public void setCourseSinglePrice(double courseSinglePrice) {
        this.courseSinglePrice = courseSinglePrice;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoursePic() {
        return coursePic;
    }

    public String getCoursePicZip() {
        return coursePicZip;
    }

    public void setCoursePicZip(String coursePicZip) {
        this.coursePicZip = coursePicZip;
    }

    public void setCoursePic(String coursePic) {
        this.coursePic = coursePic;
    }

    public String getCourseBackPic() {
        return courseBackPic;
    }

    public void setCourseBackPic(String courseBackPic) {
        this.courseBackPic = courseBackPic;
    }

    public String getCourseBrief() {
        return courseBrief;
    }

    public void setCourseBrief(String courseBrief) {
        this.courseBrief = courseBrief;
    }

    public String getCourseLabel() {
        return courseLabel;
    }

    public void setCourseLabel(String courseLabel) {
        this.courseLabel = courseLabel;
    }


    public String getCourseNotice() {
        return courseNotice;
    }

    public void setCourseNotice(String courseNotice) {
        this.courseNotice = courseNotice;
    }

    public String getCourseAdvisoryTel() {
        return courseAdvisoryTel;
    }

    public void setCourseAdvisoryTel(String courseAdvisoryTel) {
        this.courseAdvisoryTel = courseAdvisoryTel;
    }

    public int getCourseCateId() {
        return courseCateId;
    }

    public void setCourseCateId(int courseCateId) {
        this.courseCateId = courseCateId;
    }

    public String getCourseCateName() {
        return courseCateName;
    }

    public void setCourseCateName(String courseCateName) {
        this.courseCateName = courseCateName;
    }

    public int getCourseBrowseCount() {
        return courseBrowseCount;
    }

    public void setCourseBrowseCount(int courseBrowseCount) {
        this.courseBrowseCount = courseBrowseCount;
    }

    public int getCourseVideoCount() {
        return courseVideoCount;
    }

    public void setCourseVideoCount(int courseVideoCount) {
        this.courseVideoCount = courseVideoCount;
    }

    public String getCourseVideoTime() {
        return courseVideoTime;
    }

    public void setCourseVideoTime(String courseVideoTime) {
        this.courseVideoTime = courseVideoTime;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public List<CourseDetailsQ$AInfoBean> getQas() {
        return qas;
    }

    public void setQas(List<CourseDetailsQ$AInfoBean> qas) {
        this.qas = qas;
    }

    public String getCourseBackPicZip() {
        return courseBackPicZip;
    }

    public void setCourseBackPicZip(String courseBackPicZip) {
        this.courseBackPicZip = courseBackPicZip;
    }
    public int getCourseIsPurchase() {
        return courseIsPurchase;
    }

    public void setCourseIsPurchase(int courseIsPurchase) {
        this.courseIsPurchase = courseIsPurchase;
    }
}
