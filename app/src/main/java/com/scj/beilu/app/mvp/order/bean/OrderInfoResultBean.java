package com.scj.beilu.app.mvp.order.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/4/4 16:21
 */
public class OrderInfoResultBean extends ResultMsgBean {
    private OrderInfoBean order;

    public OrderInfoBean getOrder() {
        return order;
    }

    public void setOrder(OrderInfoBean order) {
        this.order = order;
    }

}
