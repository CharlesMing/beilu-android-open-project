package com.scj.beilu.app.mvp.course;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.pay.WXPayPre;
import com.scj.beilu.app.mvp.common.pay.WXPayView;
import com.scj.beilu.app.mvp.course.bean.CourseCommentInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseDetailsBean;
import com.scj.beilu.app.mvp.course.bean.CourseInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseVideoListBean;
import com.scj.beilu.app.mvp.course.model.CourseInfoImpl;
import com.scj.beilu.app.mvp.course.model.ICourseInfo;

/**
 * @author Mingxun
 * @time on 2019/3/20 19:42
 */
public class CourseDetailsPre extends WXPayPre<CourseDetailsPre.CourseDetailsView> {
    private ICourseInfo mCourseInfo;

    public CourseDetailsPre(Activity activity) {
        super(activity);
        mCourseInfo = new CourseInfoImpl(mBuilder);
    }

    public void getCourseDetails(final int courseId) {
        mBuilder.setLoadType(ShowConfig.LOADING_REFRESH);
        onceViewAttached(new ViewAction<CourseDetailsView>() {
            @Override
            public void run(@NonNull final CourseDetailsView mvpView) {
                mCourseInfo.getCourseInfoById(courseId)
                        .subscribe(new BaseResponseCallback<CourseDetailsBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(CourseDetailsBean courseInfoBean) {
                                mvpView.onCourseInfoResult(courseInfoBean.getCourse());
                            }
                        });
            }
        });
    }

    public void getCourseVideoList(final int courseId) {
        mBuilder.setLoadType(ShowConfig.NONE);
        onceViewAttached(new ViewAction<CourseDetailsView>() {
            @Override
            public void run(@NonNull final CourseDetailsView mvpView) {
                mCourseInfo.getCourseVideoById(courseId)
                        .subscribe(new BaseResponseCallback<CourseVideoListBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(CourseVideoListBean courseVideoListBean) {
                                mvpView.onVideoList(courseVideoListBean);
                            }
                        });
            }
        });
    }

    public void createComment(final int courseId, final String content) {
        mBuilder.setLoadType(ShowConfig.LOADING_DIALOG);
        onceViewAttached(new ViewAction<CourseDetailsView>() {
            @Override
            public void run(@NonNull final CourseDetailsView mvpView) {
                mCourseInfo.createCourseComment(courseId, content)
                        .subscribe(new BaseResponseCallback<CourseCommentInfoBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(CourseCommentInfoBean resultMsgBean) {
                                mvpView.onCreateCommentResult(resultMsgBean);
                            }
                        });
            }
        });
    }

    public interface CourseDetailsView extends WXPayView {
        void onCourseInfoResult(CourseInfoBean courseInfo);

        void onVideoList(CourseVideoListBean courseVideoList);

        void onCreateCommentResult(CourseCommentInfoBean result);

    }
}
