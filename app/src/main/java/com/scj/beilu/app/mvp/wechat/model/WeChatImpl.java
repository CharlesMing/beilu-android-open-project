package com.scj.beilu.app.mvp.wechat.model;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.scj.beilu.app.api.Constants;
import com.scj.beilu.app.api.UserApi;
import com.scj.beilu.app.api.WeChatApi;
import com.scj.beilu.app.mvp.base.user.BaseLoadUserInfoDelegate;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.wechat.bean.WeChatTokenBean;
import com.scj.beilu.app.mvp.wechat.bean.WeChatUserInfoBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.mx.pro.lib.mvp.network.config.BaseMvpPresenter.Encrypt;

/**
 * @author Mingxun
 * @time on 2019/2/22 13:21
 */
public class WeChatImpl extends BaseLoadUserInfoDelegate implements IWeChat {
    private WeChatApi mWeChatApi;
    private UserApi mUserApi;

    public WeChatImpl(Builder builder) {
        super(builder);
        mWeChatApi = create(WeChatApi.class);
        mUserApi = create(UserApi.class);
    }

    @Override
    public Observable<WeChatTokenBean> setCode(String code) {
        String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?" +
                        "appid=%s&secret=%s&code=%s&grant_type=authorization_code", Constants.WXAPPID,
                "9d9e55c041e8a9b95c2b2c7df7afc3cb", code);
        return createObservable(mWeChatApi.getWeChatToken(url));
    }

    @Override
    public Observable<ResultMsgBean> getWeChatUserInfoByToken(String access_token) {
        return createObservable(mWeChatApi.getWeChatUserInfo(access_token, Constants.WXAPPID))
                .flatMap((Function<WeChatUserInfoBean, ObservableSource<ResultMsgBean>>) weChatUserInfo -> {
                    Map<String, Object> params = new HashMap<>();
                    params.put("openid", Encrypt(weChatUserInfo.getOpenid()));
                    params.put("nickname", weChatUserInfo.getNickname());
                    params.put("sex", weChatUserInfo.getSex());
                    params.put("headimgurl", weChatUserInfo.getHeadimgurl());
                    params.put("userAppKey", "25307800");
                    params.put("userTargetValue", Encrypt(PushServiceFactory.getCloudPushService().getDeviceId()));
                    return weChatLogin(params);
                });
    }

    @Override
    public Observable<ResultMsgBean> weChatLogin(Map<String, Object> params) {
        return createObservable(mUserApi.weChatLogin(params))
                .flatMap((Function<ResultMsgBean, ObservableSource<ResultMsgBean>>) resultMsgBean -> saveToken(resultMsgBean));
    }
}
