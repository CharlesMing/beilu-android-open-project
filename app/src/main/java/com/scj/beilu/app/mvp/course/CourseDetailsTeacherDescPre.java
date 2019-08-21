package com.scj.beilu.app.mvp.course;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.course.bean.CourseTeacherBean;
import com.scj.beilu.app.mvp.course.bean.CourseTeacherInfoBean;
import com.scj.beilu.app.mvp.course.model.CourseInfoImpl;
import com.scj.beilu.app.mvp.course.model.ICourseInfo;

/**
 * @author Mingxun
 * @time on 2019/3/20 23:22
 */
public class CourseDetailsTeacherDescPre extends BaseMvpPresenter<CourseDetailsTeacherDescPre.CourseDetailsTeacherDescView> {

    private ICourseInfo mCourseInfo;

    public CourseDetailsTeacherDescPre(Context context) {
        super(context, ShowConfig.NONE, ShowConfig.ERROR_TOAST);
        mCourseInfo = new CourseInfoImpl(mBuilder);
    }

    public void getTeacherInfo(final int courseId) {
        onceViewAttached(new ViewAction<CourseDetailsTeacherDescView>() {
            @Override
            public void run(@NonNull final CourseDetailsTeacherDescView mvpView) {
                mCourseInfo.getCourseTeacherInfo(courseId)
                        .subscribe(new BaseResponseCallback<CourseTeacherInfoBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(CourseTeacherInfoBean courseTeacherInfoBean) {
                                mvpView.onTeacherInfo(courseTeacherInfoBean.getCourseTeacher());
                            }
                        });
            }
        });
    }

    public interface CourseDetailsTeacherDescView extends MvpView {
        void onTeacherInfo(CourseTeacherBean teacherBean);
    }
}
