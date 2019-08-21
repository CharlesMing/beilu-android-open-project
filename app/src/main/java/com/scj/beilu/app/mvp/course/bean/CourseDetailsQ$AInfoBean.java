package com.scj.beilu.app.mvp.course.bean;

/**
 * @author Mingxun
 * @time on 2019/3/21 23:54
 */
public class CourseDetailsQ$AInfoBean {
    /**
     * courseQaId : 0
     * courseId : 0
     * courseQ : 123
     * courseA : 123
     */

    private int courseQaId;
    private int courseId;
    private String courseQ;
    private String courseA;

    public int getCourseQaId() {
        return courseQaId;
    }

    public void setCourseQaId(int courseQaId) {
        this.courseQaId = courseQaId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseQ() {
        return courseQ;
    }

    public void setCourseQ(String courseQ) {
        this.courseQ = courseQ;
    }

    public String getCourseA() {
        return courseA;
    }

    public void setCourseA(String courseA) {
        this.courseA = courseA;
    }
}
