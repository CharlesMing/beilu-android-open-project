package com.scj.beilu.app.mvp.common.share.model;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.scj.beilu.app.api.Constants;
import com.scj.beilu.app.api.FindApi;
import com.scj.beilu.app.mvp.base.user.BaseLoadUserInfoDelegate;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/4/10 11:11
 */
public class ShareInfoImpl extends BaseLoadUserInfoDelegate implements IShareInfo {

    private IWXAPI mIWXAPI;
    private FindApi mFindApi;

    public ShareInfoImpl(Builder builder) {
        super(builder);
        mIWXAPI = WXAPIFactory.createWXAPI(context, Constants.WXAPPID);
        mFindApi = create(FindApi.class);
    }

    @Override
    public void WXShareWebPage(final String url, final String title, final String description, final int scene) {
        getUserInfoByToken()
                .subscribe(new ObserverCallback<UserInfoEntity>(mBuilder.build()) {
                    @Override
                    public void onNext(final UserInfoEntity userInfoEntity) {

                        WXWebpageObject webpage = new WXWebpageObject();
                        webpage.webpageUrl = url;
                        WXMediaMessage msg = new WXMediaMessage(webpage);
                        msg.title = title == null ? "" : title;
                        msg.description = description == null ? "" : description;
                        SendMessageToWX.Req req = new SendMessageToWX.Req();
                        req.transaction = "webpage" + System.currentTimeMillis();
                        req.message = msg;
                        req.scene = scene;
                        req.openId = userInfoEntity.getOpenId();
                        mIWXAPI.sendReq(req);

                    }
                });

    }

    @Override
    public Observable<ResultMsgBean> setShareFindCount(int dynamicId) {
        return createObservable(mFindApi.setShareCount(dynamicId));
    }
}
