package com.scj.beilu.app.mvp.mine.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * author:SunGuiLan
 * date:2019/2/23
 * descriptin:
 */
public class UpdateAvatarResultBean extends ResultMsgBean {
    private String userHead;

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }
}
