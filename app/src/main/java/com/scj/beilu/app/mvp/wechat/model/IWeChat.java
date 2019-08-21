package com.scj.beilu.app.mvp.wechat.model;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.wechat.bean.WeChatTokenBean;

import java.util.Map;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/2/22 13:18
 */
public interface IWeChat {
    Observable<WeChatTokenBean> setCode(String code);

    Observable<ResultMsgBean> getWeChatUserInfoByToken(String access_token);

    Observable<ResultMsgBean> weChatLogin(Map<String, Object> params);
}
