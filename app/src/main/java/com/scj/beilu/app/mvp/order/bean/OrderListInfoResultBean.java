package com.scj.beilu.app.mvp.order.bean;

import com.scj.beilu.app.mvp.common.bean.PageBean;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/4/4 16:21
 */
public class OrderListInfoResultBean extends ResultMsgBean {

    private PageBean<OrderInfoBean> page;

    public PageBean<OrderInfoBean> getPage() {
        return page;
    }

    public void setPage(PageBean<OrderInfoBean> page) {
        this.page = page;
    }
}
