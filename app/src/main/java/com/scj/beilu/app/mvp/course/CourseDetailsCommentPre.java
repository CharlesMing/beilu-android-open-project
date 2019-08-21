package com.scj.beilu.app.mvp.course;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.course.bean.CourseCommentInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseCommentListBean;
import com.scj.beilu.app.mvp.course.model.CourseInfoImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/20 23:22
 */
public class CourseDetailsCommentPre extends BaseMvpPresenter<CourseDetailsCommentPre.CourseDetailsCommentView> {

    private CourseInfoImpl mCourseInfo;
    private final List<CourseCommentInfoBean> mCourseCommentInfoList = new ArrayList<>();

    public CourseDetailsCommentPre(Context context) {
        super(context, ShowConfig.NONE, ShowConfig.ERROR_TOAST);
        mCourseInfo = new CourseInfoImpl(mBuilder);
    }

    public void getCommentList(final int currentId, final int currentPage) {
        onceViewAttached(new ViewAction<CourseDetailsCommentView>() {
            @Override
            public void run(@NonNull final CourseDetailsCommentView mvpView) {
                mCourseInfo.getCommentList(currentId, currentPage)
                        .subscribe(new BaseResponseCallback<CourseCommentListBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(CourseCommentListBean courseCommentListBean) {
                                try {
                                    List<CourseCommentInfoBean> list = courseCommentListBean.getPage().getList();
                                    if (currentPage == 0) {
                                        mCourseCommentInfoList.clear();
                                    }
                                    mvpView.onCheckLoadMore(list);
                                    mCourseCommentInfoList.addAll(list);
                                    mvpView.onCommentList(mCourseCommentInfoList);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    public void createCommentNotify(final CourseCommentInfoBean result) {
        onceViewAttached(new ViewAction<CourseDetailsCommentView>() {
            @Override
            public void run(@NonNull CourseDetailsCommentView mvpView) {
                mCourseCommentInfoList.add(0, result);
                if (mCourseCommentInfoList.size() == 1) {
                    mvpView.onCommentList(mCourseCommentInfoList);
                } else {
                    mvpView.onCreateNotifyResult();
                }
            }
        });
    }

    public interface CourseDetailsCommentView extends BaseCheckArrayView {
        void onCommentList(List<CourseCommentInfoBean> courseCommentInfoList);

        void onCreateNotifyResult();
    }
}
