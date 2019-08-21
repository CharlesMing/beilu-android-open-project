package com.scj.beilu.app.mvp.course.bean;

/**
 * @author Mingxun
 * @time on 2019/3/21 22:00
 */
public class CourseTeacherImgBean {
    /**
     * courseTeacherPicId : 11
     * courseTeacherId : 0
     * courseTeacherPicAddr : https://beilutest.oss-cn-hangzhou.aliyuncs.com/course/teacher/show/94567662-3ddd-4358-a5e8-0bb355aeb44720190372114640.png
     */

    private int courseTeacherPicId;
    private int courseTeacherId;
    private String courseTeacherPicAddr;
    private String courseTeacherPicAddrZip;

    public String getCourseTeacherPicAddrZip() {
        return courseTeacherPicAddrZip;
    }

    public void setCourseTeacherPicAddrZip(String courseTeacherPicAddrZip) {
        this.courseTeacherPicAddrZip = courseTeacherPicAddrZip;
    }

    public int getCourseTeacherPicId() {
        return courseTeacherPicId;
    }

    public void setCourseTeacherPicId(int courseTeacherPicId) {
        this.courseTeacherPicId = courseTeacherPicId;
    }

    public int getCourseTeacherId() {
        return courseTeacherId;
    }

    public void setCourseTeacherId(int courseTeacherId) {
        this.courseTeacherId = courseTeacherId;
    }

    public String getCourseTeacherPicAddr() {
        return courseTeacherPicAddr;
    }

    public void setCourseTeacherPicAddr(String courseTeacherPicAddr) {
        this.courseTeacherPicAddr = courseTeacherPicAddr;
    }
}
