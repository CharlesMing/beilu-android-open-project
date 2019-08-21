package com.scj.beilu.app.mvp.course.model;

import com.mx.pro.lib.mvp.exception.UserException;
import com.scj.beilu.app.api.CourseApi;
import com.scj.beilu.app.mvp.base.user.BaseLoadUserInfoDelegate;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.course.bean.CourseBoughtListResult;
import com.scj.beilu.app.mvp.course.bean.CourseCommentInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseCommentListBean;
import com.scj.beilu.app.mvp.course.bean.CourseDetailsBean;
import com.scj.beilu.app.mvp.course.bean.CourseListBean;
import com.scj.beilu.app.mvp.course.bean.CourseTeacherInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseTypeInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseTypeListBean;
import com.scj.beilu.app.mvp.course.bean.CourseVideoListBean;
import com.scj.beilu.app.mvp.course.bean.CourseVideoResultBean;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;
import com.scj.beilu.app.util.TimeUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mingxun
 * @time on 2019/3/20 15:56
 */
public class CourseInfoImpl extends BaseLoadUserInfoDelegate implements ICourseInfo {

    private final CourseApi mCourseApi;

    public CourseInfoImpl(Builder builder) {
        super(builder);
        mCourseApi = create(CourseApi.class);
    }

    @Override
    public Observable<CourseTypeListBean> getCourseTypeList() {
        return createObservable(mCourseApi.getCourseTypeList());
    }

    @Override
    public Observable<CourseListBean> getCourseList(int courseCateId, int currentPage) {
        return createObservable(mCourseApi.getCourseList(courseCateId, currentPage));
    }

    @Override
    public Observable<CourseDetailsBean> getCourseInfoById(final int courseId) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<CourseDetailsBean>>() {
                    @Override
                    public ObservableSource<CourseDetailsBean> apply(Map<String, String> token) throws Exception {
                        return createObservable(mCourseApi.getCourseInfoById(token, courseId));
                    }
                });
    }

    @Override
    public Observable<CourseTeacherInfoBean> getCourseTeacherInfo(int courseId) {
        return createObservable(mCourseApi.getCourseTeacherInfo(courseId));
    }

    @Override
    public Observable<CourseVideoListBean> getCourseVideoById(final int courseId) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<CourseVideoListBean>>() {
                    @Override
                    public ObservableSource<CourseVideoListBean> apply(Map<String, String> token) throws Exception {
                        return createObservable(mCourseApi.getCourseVideoListById(token, courseId));
                    }
                });
    }

    @Override
    public Observable<CourseCommentInfoBean> createCourseComment(final int courseId, final String content) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(Map<String, String> token) throws Exception {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return createObservable(mCourseApi.createCourseComment(token, courseId, content));
                        }
                    }
                })
                .flatMap(new Function<ResultMsgBean, ObservableSource<CourseCommentInfoBean>>() {
                    @Override
                    public ObservableSource<CourseCommentInfoBean> apply(final ResultMsgBean resultMsgBean) throws Exception {
                        return getUserInfoByToken()
                                .flatMap(new Function<UserInfoEntity, ObservableSource<CourseCommentInfoBean>>() {

                                    @Override
                                    public ObservableSource<CourseCommentInfoBean> apply(UserInfoEntity userInfoEntity) throws Exception {
                                        return addLocalList(content, resultMsgBean, userInfoEntity);
                                    }
                                });
                    }
                });
    }

    public Observable<CourseCommentInfoBean> addLocalList(final String content, final ResultMsgBean result, final UserInfoEntity userInfo) {
        ObservableOnSubscribe<CourseCommentInfoBean> subscribeOn = new
                ObservableOnSubscribe<CourseCommentInfoBean>() {
                    @Override
                    public void subscribe(ObservableEmitter<CourseCommentInfoBean> emitter) throws Exception {

                        CourseCommentInfoBean infoBean = new CourseCommentInfoBean();
                        try {
                            final String pattern = "yyyy-MM-dd HH:mm:ss";
                            infoBean.setFormatDate(TimeUtil.showTime(new Date(), pattern));
                            infoBean.setUserName(userInfo.getUserNickName());
                            infoBean.setUserId((int) userInfo.getUserId());
                            infoBean.setComContent(content);
                            infoBean.setUserHead(userInfo.getUserOriginalHead());
                            infoBean.setUserHeadCompression(userInfo.getUserCompressionHead());
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            infoBean.setResult(result.getResult());
                            infoBean.setCode(result.getCode());
                            emitter.onNext(infoBean);
                            emitter.onComplete();
                        }
                    }
                };
        return createObservableOnSubscribe(subscribeOn);
    }

    @Override
    public Observable<CourseCommentListBean> getCommentList(int courseId, int currentPage) {
        return createObservable(mCourseApi.getCommentList(courseId, currentPage))
                .flatMap(new Function<CourseCommentListBean, ObservableSource<CourseCommentListBean>>() {
                    @Override
                    public ObservableSource<CourseCommentListBean> apply(CourseCommentListBean courseCommentListBean) throws Exception {
                        return dealWithTime(courseCommentListBean.getPage().getList(), courseCommentListBean);
                    }
                });
    }

    @Override
    public Observable<CourseVideoResultBean> getVideoInfo(final int courseVideoId) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<CourseVideoResultBean>>() {

                    @Override
                    public ObservableSource<CourseVideoResultBean> apply(Map<String, String> token) throws Exception {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return createObservable(mCourseApi.getVideoInfo(token, courseVideoId));
                        }
                    }
                });
    }

    @Override
    public Observable<CourseBoughtListResult> getBoughtList(final int currentPage) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<CourseBoughtListResult>>() {

                    @Override
                    public ObservableSource<CourseBoughtListResult> apply(Map<String, String> token) throws Exception {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return createObservable(mCourseApi.getBoughtList(token, currentPage));
                        }
                    }
                });
    }

    /**
     * 设置课程分类选中
     *
     * @param courseTypeList
     * @param position
     * @return
     */
    public Observable<CourseTypeInfoBean> setSelect(final List<CourseTypeInfoBean> courseTypeList, final int position) {
        ObservableOnSubscribe<CourseTypeInfoBean> onSubscribe =
                new ObservableOnSubscribe<CourseTypeInfoBean>() {
                    @Override
                    public void subscribe(ObservableEmitter<CourseTypeInfoBean> emitter) throws Exception {
                        CourseTypeInfoBean result = null;
                        try {
                            result = courseTypeList.get(position);
                            if (!result.isSelect()) {
                                int size = courseTypeList.size();
                                for (int i = 0; i < size; i++) {
                                    CourseTypeInfoBean typeInfo = courseTypeList.get(i);
                                    if (i == position) {
                                        typeInfo.setSelect(true);
                                    } else {
                                        typeInfo.setSelect(false);
                                    }
                                    courseTypeList.set(i, typeInfo);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            emitter.onNext(result);
                            emitter.onComplete();
                        }
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }
}
