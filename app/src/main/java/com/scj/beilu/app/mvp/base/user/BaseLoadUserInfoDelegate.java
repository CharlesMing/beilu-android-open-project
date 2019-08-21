package com.scj.beilu.app.mvp.base.user;

import android.content.Context;
import android.content.SharedPreferences;

import com.mx.pro.lib.mvp.delegate.LoadDelegate;
import com.mx.pro.lib.mvp.exception.UserException;
import com.mx.pro.lib.mvp.network.config.AppConfig;
import com.scj.beilu.app.MyApplication;
import com.scj.beilu.app.api.UserApi;
import com.scj.beilu.app.dao.DaoSession;
import com.scj.beilu.app.dao.UserInfoEntityDao;
import com.scj.beilu.app.mvp.common.bean.FormatResult;
import com.scj.beilu.app.mvp.common.bean.FormatTimeBean;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.common.user.IUserInfo;
import com.scj.beilu.app.mvp.user.bean.UserInfoBean;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;
import com.scj.beilu.app.util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mingxun
 * @time on 2019/2/17 18:38
 */
public class BaseLoadUserInfoDelegate extends LoadDelegate implements IUserInfo {

    private DaoSession mDaoSession;
    protected UserInfoEntityDao mInfoEntityDao;
    private final String VAL_COUNT = "count";

    public BaseLoadUserInfoDelegate(Builder builder) {
        super(builder);
    }

    protected DaoSession getDaoSession() {
        if (mDaoSession == null) {
            mDaoSession = ((MyApplication) (context.getApplicationContext())).getDaoSession();
        }
        return mDaoSession;
    }

    protected String getToken() {
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(AppConfig.PREFS_NAME, Context.MODE_PRIVATE);
        String token = preferences.getString(AppConfig.USER_TOKEN, null);
        return token;
    }

    protected Observable<ResultMsgBean> saveToken(final ResultMsgBean token) {
        ObservableOnSubscribe<ResultMsgBean>
                save = new ObservableOnSubscribe<ResultMsgBean>() {
            @Override
            public void subscribe(ObservableEmitter<ResultMsgBean> emitter) throws Exception {
                try {
                    String tokenVal = token.getToken();
                    SharedPreferences sharedPreferences = context
                            .getApplicationContext()
                            .getSharedPreferences(AppConfig.PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(AppConfig.USER_TOKEN, tokenVal);
                    editor.apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                emitter.onNext(token);
                emitter.onComplete();
            }
        };
        return createObservableOnSubscribe(save);
    }

    @Override
    public Observable<UserInfoEntity> getUserInfoByToken() {
        if (mInfoEntityDao == null) {
            mInfoEntityDao = getDaoSession().getUserInfoEntityDao();
        }
        Observable<UserInfoEntity> userInfoEntityObservable = getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<Map<String, Object>>>() {
                    @Override
                    public ObservableSource<Map<String, Object>> apply(Map<String, String> token) throws Exception {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return checkIsData(getToken());
                        }
                    }
                })
                .flatMap(new Function<Map<String, Object>, ObservableSource<UserInfoEntity>>() {
                    @Override
                    public ObservableSource<UserInfoEntity> apply(Map<String, Object> value) throws Exception {
                        Long count = (Long) value.get(VAL_COUNT);
                        String token = (String) value.get(VAL_TOKEN);
                        if (count > 0) {//获取本地缓存用户信息
                            return getUserInfoByCacheToken(token);
                        } else {//通过接口获取
                            return getUserInfoByNetToken(token);
                        }
                    }
                });

        return userInfoEntityObservable;
    }


    /**
     * 查询本地是否有数据
     */
    private Observable<Map<String, Object>> checkIsData(final String token) {
        if (mInfoEntityDao == null) {
            mInfoEntityDao = getDaoSession().getUserInfoEntityDao();
        }
        ObservableOnSubscribe<Map<String, Object>> userInfoObser = new ObservableOnSubscribe<Map<String, Object>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<String, Object>> emitter) throws Exception {
                Map<String, Object> saveValue = new HashMap<>();
                long count = -1;
                try {
                    count = mInfoEntityDao.queryBuilder().where(UserInfoEntityDao.Properties.Token.eq(token))
                            .count();
                } catch (Exception e) {
                    emitter.onError(e);
                } finally {
                    saveValue.put(VAL_COUNT, count);
                    saveValue.put(VAL_TOKEN, token);
                    emitter.onNext(saveValue);
                    emitter.onComplete();
                }
            }
        };
        return createObservableOnSubscribe(userInfoObser);
    }


    /**
     * 获取用户信息 通过 本地缓存
     */
    private Observable<UserInfoEntity> getUserInfoByCacheToken(final String token) {
        ObservableOnSubscribe<UserInfoEntity> userInfoObservable = new
                ObservableOnSubscribe<UserInfoEntity>() {
                    @Override
                    public void subscribe(ObservableEmitter<UserInfoEntity> emitter) throws Exception {
                        try {
                            UserInfoEntity infoEntity = mInfoEntityDao.load(token);
                            emitter.onNext(infoEntity);
                        } catch (Exception e) {
                            emitter.onError(e);
                            e.printStackTrace();
                        }
                        emitter.onComplete();
                    }
                };
        return createObservableOnSubscribe(userInfoObservable);
    }

    /**
     * 获取用户信息 通过 网络
     */
    private Observable<UserInfoEntity> getUserInfoByNetToken(final String token) {
        Map<String, String> header = new HashMap<>();
        header.put(VAL_TOKEN, token);
        Observable<UserInfoEntity> userInfoEntityObservable = createObservable(create(UserApi.class).getUserInfo(header))
                .flatMap(new Function<UserInfoBean, ObservableSource<UserInfoEntity>>() {
                    @Override
                    public ObservableSource<UserInfoEntity> apply(UserInfoBean userInfoBean) throws Exception {
                        //将获取到的用户信息 保存至本地
                        if (userInfoBean == null) {
                            throw new UserException();
                        } else {
                            return saveUserInfoToLocal(userInfoBean.getUser(), token);
                        }
                    }
                });
        return userInfoEntityObservable;
    }

    /**
     * 保存用户信息到本地
     */
    private Observable<UserInfoEntity> saveUserInfoToLocal(final UserInfoEntity userInfoEntity, final String token) {

        ObservableOnSubscribe<UserInfoEntity> userInfo = new ObservableOnSubscribe<UserInfoEntity>() {
            @Override
            public void subscribe(ObservableEmitter<UserInfoEntity> emitter) throws Exception {
                if (userInfoEntity != null) {
                    userInfoEntity.setToken(token);
                    try {
                        long count = mInfoEntityDao.queryBuilder().where(UserInfoEntityDao.Properties.UserId.eq(userInfoEntity.getUserId()))
                                .count();
                        if (count == 0) {
                            mInfoEntityDao.insert(userInfoEntity);
                        }
                        emitter.onNext(userInfoEntity);
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                } else {
                    emitter.onError(new Throwable("数据处理失败"));
                }
                emitter.onComplete();
            }
        };
        return createObservableOnSubscribe(userInfo);
    }

    /**
     * 时间格式处理
     *
     * @param dataList 要处理的数组
     * @param result   返回的结果 需要在实体类继承FormatResult<FormatResult>
     * @param <T>      T extends FormatTimeBean
     * @param <Result> Result extends FormatResult<T>
     * @return
     */
    public <T extends FormatTimeBean, Result extends FormatResult<T>>
    Observable<Result> dealWithTime(final List<T> dataList, final Result result) {

        ObservableOnSubscribe<Result> formatTimeSub =
                new ObservableOnSubscribe<Result>() {
                    @Override
                    public void subscribe(ObservableEmitter<Result> emitter) throws Exception {
                        try {
                            int size = dataList.size();
                            final String pattern = "yyyy-MM-dd HH:mm:ss";
                            for (int i = 0; i < size; i++) {
                                FormatTimeBean timeBean = dataList.get(i);
                                try {
                                    SimpleDateFormat df = new SimpleDateFormat(pattern);
                                    timeBean.setFormatDate(TimeUtil.showTime(df.parse(timeBean.getFormatDate()), pattern));
                                    dataList.set(i, (T) timeBean);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            result.setList(dataList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            emitter.onNext(result);
                            emitter.onComplete();
                        }
                    }
                };

        return createObservableOnSubscribe(formatTimeSub);
    }
}
