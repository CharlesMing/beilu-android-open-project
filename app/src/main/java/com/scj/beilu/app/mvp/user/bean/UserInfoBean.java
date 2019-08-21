package com.scj.beilu.app.mvp.user.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;

/**
 * @author Mingxun
 * @time on 2019/2/14 11:18
 */
public class UserInfoBean extends ResultMsgBean {
    private UserInfoEntity user;

    public UserInfoEntity getUser() {
        return user;
    }

    public void setUser(UserInfoEntity user) {
        this.user = user;
    }
}
