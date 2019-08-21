package com.scj.beilu.app.mvp.common.pay.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/4/2 22:49
 */
public class PayInfoResultBean extends ResultMsgBean {

    private PayOrderInfoResultBean orderInfo;
    private int state;

    public PayOrderInfoResultBean getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(PayOrderInfoResultBean orderInfo) {
        this.orderInfo = orderInfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
