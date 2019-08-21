package com.scj.beilu.app.mvp.user.model;

import com.mx.pro.lib.mvp.exception.UserException;
import com.scj.beilu.app.api.UserApi;
import com.scj.beilu.app.mvp.base.user.BaseLoadUserInfoDelegate;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.user.bean.AvatarResult;
import com.scj.beilu.app.mvp.user.bean.UserDetailsBean;

import java.io.File;
import java.net.URLEncoder;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Mingxun
 * @time on 2019/3/4 20:04
 */
public class UserDetailsImpl extends BaseLoadUserInfoDelegate implements IUserDetails {


    private UserApi mUserApi;

    public UserDetailsImpl(Builder builder) {
        super(builder);
        mUserApi = create(UserApi.class);
    }


    @Override
    public Observable<UserDetailsBean> getUserDetails(final int otherUserId) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<UserDetailsBean>>() {

                    @Override
                    public ObservableSource<UserDetailsBean> apply(Map<String, String> token) throws Exception {
                        if (otherUserId != -1) {
                            return createObservable(mUserApi.getOtherDetails(token, otherUserId));
                        } else {
                            return createObservable(mUserApi.getMyDetails(token));
                        }
                    }
                });

    }

    @Override
    public Observable<AvatarResult> updateBackgroundImg(final String path) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<AvatarResult>>() {
                    @Override
                    public ObservableSource<AvatarResult> apply(Map<String, String> token) throws Exception {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            File imgFile = new File(path);
                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);
                            String fileName = URLEncoder.encode(imgFile.getName(), "UTF-8");
                            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", fileName, requestBody);
                            return createObservable(mUserApi.updateBackgroundImg(token, filePart));
                        }
                    }
                });
    }

    @Override
    public Observable<ResultMsgBean> bindWxAccount(final String openId) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(Map<String, String> token) throws Exception {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return createObservable(mUserApi.bindWxAccount(token, openId));
                        }
                    }
                })
                .flatMap(new Function<ResultMsgBean, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(ResultMsgBean resultMsgBean) throws Exception {
                        return clearAccountCache(resultMsgBean);
                    }
                });
    }

    @Override
    public Observable<ResultMsgBean> bindPhoneSendMsg(String phone) {
        return createObservable(mUserApi.bindPhoneSendMsg(phone));
    }

    @Override
    public Observable<ResultMsgBean> bindPhone(final String code, final String phone) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(Map<String, String> token) throws UserException {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return createObservable(mUserApi.bindPhone(token, code, phone));
                        }
                    }
                })
                .flatMap(new Function<ResultMsgBean, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(ResultMsgBean resultMsgBean) throws Exception {
                        return clearAccountCache(resultMsgBean);
                    }
                });
    }

    private Observable<ResultMsgBean> clearAccountCache(final ResultMsgBean result) {
        ObservableOnSubscribe onSubscribe = new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                try {
                    if (mInfoEntityDao == null) {
                        mInfoEntityDao = getDaoSession().getUserInfoEntityDao();
                    }
                    mInfoEntityDao.deleteAll();
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
