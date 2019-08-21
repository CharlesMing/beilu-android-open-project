package com.scj.beilu.app.mvp.course.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/3/21 21:41
 */
public class CourseTeacherInfoBean extends ResultMsgBean {
    /**
     * courseTeacher : {"courseTeacherId":23,"courseId":25,"courseTeacherName":"123","courseTeacherBrief":"123","courseTeacherWords":"123","courseTeacherHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/course/teacher/head/c8f872d3-6310-4aa1-b61f-1d42c94f338020190372114640.png","courseTeacherLabel":"123","pics":[{"courseTeacherPicId":11,"courseTeacherId":0,"courseTeacherPicAddr":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/course/teacher/show/94567662-3ddd-4358-a5e8-0bb355aeb44720190372114640.png"}]}
     */

    private CourseTeacherBean courseTeacher;

    public CourseTeacherBean getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(CourseTeacherBean courseTeacher) {
        this.courseTeacher = courseTeacher;
    }
}
