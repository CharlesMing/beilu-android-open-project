package com.scj.beilu.app.mvp.user.model;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.common.user.IUserInfo;
import com.scj.beilu.app.mvp.user.bean.AvatarResult;
import com.scj.beilu.app.mvp.user.bean.UserDetailsBean;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/3/4 20:00
 * 用户详情
 * 用户主页详情
 */
public interface IUserDetails extends IUserInfo {

    /**
     * 获取我的主页详情/获取其他人的主页详情
     */
    Observable<UserDetailsBean> getUserDetails(int otherUserId);

    /**
     * 修改背景
     *
     * @param path
     * @return
     */
    Observable<AvatarResult> updateBackgroundImg(String path);

    /**
     * 绑定微信号
     */
    Observable<ResultMsgBean> bindWxAccount(String openId);

    /**
     * 发送验证码
     */
    Observable<ResultMsgBean> bindPhoneSendMsg(String phone);

    /**
     * 开始绑定
     */
    Observable<ResultMsgBean> bindPhone(String code, String phone);
}
