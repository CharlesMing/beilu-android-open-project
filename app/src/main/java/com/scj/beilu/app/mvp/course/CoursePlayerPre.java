package com.scj.beilu.app.mvp.course;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.course.bean.CourseCommentInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseVideoInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseVideoResultBean;
import com.scj.beilu.app.mvp.course.model.CourseInfoImpl;
import com.scj.beilu.app.mvp.course.model.ICourseInfo;

/**
 * @author Mingxun
 * @time on 2019/3/25 22:45
 */
public class CoursePlayerPre extends BaseMvpPresenter<CoursePlayerPre.CoursePlayerView> {

    private ICourseInfo mCourseInfo;

    public CoursePlayerPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mCourseInfo = new CourseInfoImpl(mBuilder);
    }

    public void getVideoInfo(final int courseVideoId) {
        onceViewAttached(new ViewAction<CoursePlayerView>() {
            @Override
            public void run(@NonNull final CoursePlayerView mvpView) {
                mCourseInfo.getVideoInfo(courseVideoId)
                        .subscribe(new BaseResponseCallback<CourseVideoResultBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(CourseVideoResultBean courseVideoResultBean) {
                                mvpView.onVideoInfo(courseVideoResultBean.getVideo());
                            }
                        });
            }
        });
    }

    public void createComment(final int courseId, final String content) {
        mBuilder.setLoadType(ShowConfig.LOADING_DIALOG);
        onceViewAttached(new ViewAction<CoursePlayerView>() {
            @Override
            public void run(@NonNull final CoursePlayerView mvpView) {
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

    public interface CoursePlayerView extends MvpView {
        void onCreateCommentResult(CourseCommentInfoBean result);

        void onVideoInfo(CourseVideoInfoBean videoInfo);

    }
}
