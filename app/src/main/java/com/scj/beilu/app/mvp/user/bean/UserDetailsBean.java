package com.scj.beilu.app.mvp.user.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;

/**
 * @author Mingxun
 * @time on 2019/3/4 21:55
 */
public class UserDetailsBean extends ResultMsgBean {


    /**
     * userFansCount : 2
     * isFocus : 0
     * userFocusCount : 1
     * userDynamicCount : 2
     * user : {"userId":61,"userNickName":"小明45","userPassword":null,"userSex":"man","userBirth":"1907-01-01 00:00:00","userTel":"18875096171","userBrief":"2445了55","userProvince":"山西省","userCity":"太原市","userType":0,"userOriginalHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/5afc7bc0-3da7-42c6-b1bd-cf329f52737120190360221606.jpg","userCompressionHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/zip5afc7bc0-3da7-42c6-b1bd-cf329f52737120190360221606.jpg","userAppKey":null,"userTargetValue":null,"userBackImg":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/dynamic/bgp/987654321_20190214120537.png","openId":null}
     * userLikeCount : 5
     */
    private int userFansCount;//关注我的
    private int isFocus;
    private int userFocusCount;//我关注的
    private int userDynamicCount;
    private int userLikeCount;
    private UserInfoEntity user;

    public int getUserFansCount() {
        return userFansCount;
    }

    public void setUserFansCount(int userFansCount) {
        this.userFansCount = userFansCount;
    }

    public int getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(int isFocus) {
        this.isFocus = isFocus;
    }

    public int getUserFocusCount() {
        return userFocusCount;
    }

    public void setUserFocusCount(int userFocusCount) {
        this.userFocusCount = userFocusCount;
    }

    public int getUserDynamicCount() {
        return userDynamicCount;
    }

    public void setUserDynamicCount(int userDynamicCount) {
        this.userDynamicCount = userDynamicCount;
    }

    public UserInfoEntity getUser() {
        return user;
    }

    public void setUser(UserInfoEntity user) {
        this.user = user;
    }

    public int getUserLikeCount() {
        return userLikeCount;
    }

    public void setUserLikeCount(int userLikeCount) {
        this.userLikeCount = userLikeCount;
    }


}
