package com.scj.beilu.app.mvp.mine.address.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/4/4 12:26
 */
public class AddressInfoResultBean extends ResultMsgBean {
    private AddressInfoBean userAddr;

    public AddressInfoBean getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(AddressInfoBean userAddr) {
        this.userAddr = userAddr;
    }
}
