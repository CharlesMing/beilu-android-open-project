package com.scj.beilu.app.mvp.common.share.model;

import android.support.annotation.IntDef;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/4/10 11:07
 */
public interface IShareInfo {
    void WXShareWebPage(String url, String title, String description, @shareType int scene);

    @IntDef({SendMessageToWX.Req.WXSceneSession, SendMessageToWX.Req.WXSceneTimeline})
    @Retention(RetentionPolicy.SOURCE)
    @interface shareType {
    }

    Observable<ResultMsgBean> setShareFindCount(int dynamicId);
}
