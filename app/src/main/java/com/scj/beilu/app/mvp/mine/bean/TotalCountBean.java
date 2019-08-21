package com.scj.beilu.app.mvp.mine.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * author:SunGuiLan
 * date:2019/2/20
 * descriptin:
 */
public class TotalCountBean extends ResultMsgBean {
    /**
     * userFansCount : 0
     * userFocusCount : 0
     * userDynamicCount : 0
     * collectionCount : 0
     * userLikeCount : 0
     */

    private int userFansCount;
    private int userFocusCount;
    private int userDynamicCount;
    private int collectionCount;
    private int userLikeCount;
//    private UserInfoEntity mUserInfoEntity;

//    public UserInfoEntity getUserInfoEntity() {
//        return mUserInfoEntity;
//    }
//
//    public void setUserInfoEntity(UserInfoEntity userInfoEntity) {
//        mUserInfoEntity = userInfoEntity;
//    }

    public int getUserFansCount() {
        return userFansCount;
    }

    public void setUserFansCount(int userFansCount) {
        this.userFansCount = userFansCount;
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

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public int getUserLikeCount() {
        return userLikeCount;
    }

    public void setUserLikeCount(int userLikeCount) {
        this.userLikeCount = userLikeCount;
    }
}
