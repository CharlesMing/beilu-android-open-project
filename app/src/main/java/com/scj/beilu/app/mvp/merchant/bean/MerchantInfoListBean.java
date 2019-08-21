package com.scj.beilu.app.mvp.merchant.bean;

import com.scj.beilu.app.mvp.common.bean.PageBean;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/4/13 18:49
 */
public class MerchantInfoListBean extends ResultMsgBean {

    private PageBean<MerchantInfoBean> page;

    public PageBean<MerchantInfoBean> getPage() {
        return page;
    }

    public void setPage(PageBean<MerchantInfoBean> page) {
        this.page = page;
    }
}
