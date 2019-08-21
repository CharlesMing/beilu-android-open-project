package com.scj.beilu.app.mvp.common.user;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.Map;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/2/13 15:09
 */
public interface IUser<T extends Map<String, Object>> extends IUserInfo {
    /**
     * 发送验证码
     */
    Observable<ResultMsgBean> setSendRegCodeParams(T params);

    /**
     * 发送登录验证码
     */
    Observable<ResultMsgBean> setSendLoginCodeParams(T params);

    /**
     * 注册
     */
    Observable<ResultMsgBean> setRegParams(T params);

    /**
     * 登录
     */
    Observable<ResultMsgBean> setLoginParams(T params);

}
