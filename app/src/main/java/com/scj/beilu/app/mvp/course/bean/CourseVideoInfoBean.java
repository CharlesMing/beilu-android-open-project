package com.scj.beilu.app.mvp.course.bean;

import java.text.DecimalFormat;

/**
 * @author Mingxun
 * @time on 2019/3/22 16:29
 */
public class CourseVideoInfoBean {
    /**
     * courseVideoId : 1
     * courseId : 21
     * courseVideoName : test1
     * courseVideoBrief : testContent
     * courseVideoAddr : null
     * courseVideoPic : https://beilutest.oss-cn-hangzhou.aliyuncs.com/course/video/pic/6754eb05-7ecd-434f-8be7-f40b3e4d2a0720190258221423.jpg
     * courseVideoPrice : 123.0
     * courseVideoBrowseCount : 0
     * courseVideoTime : 23
     */

    private int courseVideoId;
    private int courseId;
    private String courseVideoName;
    private String courseVideoBrief;
    private String courseVideoAddr;
    private String courseVideoPic;
    private String courseVideoPicZip;
    private double courseVideoPrice;
    private int courseVideoBrowseCount;
    private int courseVideoTime;
    private int courseVideoIsPurchase;


    public int getCourseVideoIsPurchase() {
        return courseVideoIsPurchase;
    }

    public void setCourseVideoIsPurchase(int courseVideoIsPurchase) {
        this.courseVideoIsPurchase = courseVideoIsPurchase;
    }

    public String getCourseVideoPicZip() {
        return courseVideoPicZip;
    }

    public void setCourseVideoPicZip(String courseVideoPicZip) {
        this.courseVideoPicZip = courseVideoPicZip;
    }

    public int getCourseVideoId() {
        return courseVideoId;
    }

    public void setCourseVideoId(int courseVideoId) {
        this.courseVideoId = courseVideoId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseVideoName() {
        return courseVideoName;
    }

    public void setCourseVideoName(String courseVideoName) {
        this.courseVideoName = courseVideoName;
    }

    public String getCourseVideoBrief() {
        return courseVideoBrief;
    }

    public void setCourseVideoBrief(String courseVideoBrief) {
        this.courseVideoBrief = courseVideoBrief;
    }

    public String getCourseVideoAddr() {
        return courseVideoAddr;
    }

    public void setCourseVideoAddr(String courseVideoAddr) {
        this.courseVideoAddr = courseVideoAddr;
    }

    public String getCourseVideoPic() {
        return courseVideoPic;
    }

    public void setCourseVideoPic(String courseVideoPic) {
        this.courseVideoPic = courseVideoPic;
    }

    public String getCourseVideoPrice() {
        if (courseVideoPrice > 0) {
            DecimalFormat df2 = new DecimalFormat("#.##");
            return df2.format(courseVideoPrice);
        } else {
            return null;
        }
    }

    public void setCourseVideoPrice(double courseVideoPrice) {
        this.courseVideoPrice = courseVideoPrice;
    }

    public int getCourseVideoBrowseCount() {
        return courseVideoBrowseCount;
    }

    public void setCourseVideoBrowseCount(int courseVideoBrowseCount) {
        this.courseVideoBrowseCount = courseVideoBrowseCount;
    }

    public int getCourseVideoTime() {
        return courseVideoTime;
    }

    public void setCourseVideoTime(int courseVideoTime) {
        this.courseVideoTime = courseVideoTime;
    }
}
