package com.scj.beilu.app.mvp.user.bean;

import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;

/**
 * @author Mingxun
 * @time on 2019/4/11 19:36
 */
public class RecommendUserInfoBean extends UserInfoEntity {

    private boolean isAttention;

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean attention) {
        isAttention = attention;
    }
}
