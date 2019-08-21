package com.scj.beilu.app.mvp.common.pay.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/3/23 16:17
 */
public class WeChatPayResultBean extends ResultMsgBean {

    /**
     * orderInfo : {"appid":"wxec4fad924ee5fd9e","noncestr":"TAa0D8oMoEjiK8BO","package":"Sign=WXPay","partnerid":"1526389851","prepayid":"wx231616401407151a6dea6b311390237169","sign":"BEBC587449F98A12A01CD52AE76DF30C","timestamp":1553329002}
     * state : 1
     */

    private WeChatPayBean orderInfo;
    private String out_trade_no;
    private int state;

    public WeChatPayBean getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(WeChatPayBean orderInfo) {
        this.orderInfo = orderInfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
}
