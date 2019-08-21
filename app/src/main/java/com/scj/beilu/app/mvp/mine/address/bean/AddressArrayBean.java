package com.scj.beilu.app.mvp.mine.address.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * author:SunGuiLan
 * date:2019/2/28
 * descriptin:
 */
public class AddressArrayBean extends ResultMsgBean {

    private List<AddressInfoBean> addr;

    public List<AddressInfoBean> getAddr() {
        return addr;
    }

    public void setAddr(List<AddressInfoBean> addr) {
        this.addr = addr;
    }

}
