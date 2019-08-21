package com.scj.beilu.app.mvp.course.bean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/21 21:59
 */
public class CourseTeacherBean {
    /**
     * courseTeacherId : 23
     * courseId : 25
     * courseTeacherName : 123
     * courseTeacherBrief : 123
     * courseTeacherWords : 123
     * courseTeacherHead : https://beilutest.oss-cn-hangzhou.aliyuncs.com/course/teacher/head/c8f872d3-6310-4aa1-b61f-1d42c94f338020190372114640.png
     * courseTeacherLabel : 123
     * pics : [{"courseTeacherPicId":11,"courseTeacherId":0,"courseTeacherPicAddr":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/course/teacher/show/94567662-3ddd-4358-a5e8-0bb355aeb44720190372114640.png"}]
     */

    private int courseTeacherId;
    private int courseId;
    private String courseTeacherName;
    private String courseTeacherBrief;
    private String courseTeacherWords;
    private String courseTeacherHead;
    private String courseTeacherHeadZip;
    private String courseTeacherLabel;
    private List<CourseTeacherImgBean> pics;

    public String getCourseTeacherHeadZip() {
        return courseTeacherHeadZip;
    }

    public void setCourseTeacherHeadZip(String courseTeacherHeadZip) {
        this.courseTeacherHeadZip = courseTeacherHeadZip;
    }

    public int getCourseTeacherId() {
        return courseTeacherId;
    }

    public void setCourseTeacherId(int courseTeacherId) {
        this.courseTeacherId = courseTeacherId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseTeacherName() {
        return courseTeacherName;
    }

    public void setCourseTeacherName(String courseTeacherName) {
        this.courseTeacherName = courseTeacherName;
    }

    public String getCourseTeacherBrief() {
        return courseTeacherBrief;
    }

    public void setCourseTeacherBrief(String courseTeacherBrief) {
        this.courseTeacherBrief = courseTeacherBrief;
    }

    public String getCourseTeacherWords() {
        return courseTeacherWords;
    }

    public void setCourseTeacherWords(String courseTeacherWords) {
        this.courseTeacherWords = courseTeacherWords;
    }

    public String getCourseTeacherHead() {
        return courseTeacherHead;
    }

    public void setCourseTeacherHead(String courseTeacherHead) {
        this.courseTeacherHead = courseTeacherHead;
    }

    public String getCourseTeacherLabel() {
        return courseTeacherLabel;
    }

    public void setCourseTeacherLabel(String courseTeacherLabel) {
        this.courseTeacherLabel = courseTeacherLabel;
    }

    public List<CourseTeacherImgBean> getPics() {
        return pics;
    }

    public void setPics(List<CourseTeacherImgBean> pics) {
        this.pics = pics;
    }


}
