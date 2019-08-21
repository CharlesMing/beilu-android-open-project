package com.scj.beilu.app.mvp.course.bean;

/**
 * @author Mingxun
 * @time on 2019/3/20 15:50
 */
public class CourseTypeInfoBean {
    /**
     * courseCateId : 1
     * courseCateName : 健身入门系列
     */

    private int courseCateId;
    private String courseCateName;
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
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
}
