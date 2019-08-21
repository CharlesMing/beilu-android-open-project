package com.scj.beilu.app.mvp.common.user;

import com.scj.beilu.app.api.UserApi;
import com.scj.beilu.app.api.VerifyApi;
import com.scj.beilu.app.mvp.base.user.BaseLoadUserInfoDelegate;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mingxun
 * @time on 2019/2/13 15:11
 */
public class UserImpl extends BaseLoadUserInfoDelegate implements IUser<Map<String, Object>> {

    private UserApi mUserApi;
    private VerifyApi mVerifyApi;

    public UserImpl(Builder builder) {
        super(builder);
        if (mUserApi == null) {
            mUserApi = create(UserApi.class);
        }
        if (mVerifyApi == null) {
            mVerifyApi = create(VerifyApi.class);
        }

    }

    @Override
    public Observable<UserInfoEntity> getUserInfoByToken() {
        return super.getUserInfoByToken();
    }

    @Override
    public Observable<ResultMsgBean> setSendRegCodeParams(Map<String, Object> params) {
        return createObservable(mVerifyApi.sendRegisterVerifyCode(params));
    }

    @Override
    public Observable<ResultMsgBean> setSendLoginCodeParams(Map<String, Object> params) {
        return createObservable(mVerifyApi.sendLoginVerifyCode(params));
    }

    @Override
    public Observable<ResultMsgBean> setRegParams(Map<String, Object> params) {
        return createObservable(mUserApi.register(params))
                .flatMap(new Function<ResultMsgBean, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(ResultMsgBean resultMsgBean) throws Exception {
                        return saveToken(resultMsgBean);
                    }
                });
    }

    @Override
    public Observable<ResultMsgBean> setLoginParams(Map<String, Object> params) {
        return createObservable(mUserApi.login(params))
                .flatMap(new Function<ResultMsgBean, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(ResultMsgBean resultMsgBean) throws Exception {
                        return saveToken(resultMsgBean);
                    }
                });
    }


}
