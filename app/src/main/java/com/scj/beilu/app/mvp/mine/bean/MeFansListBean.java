package com.scj.beilu.app.mvp.mine.bean;

import com.scj.beilu.app.mvp.common.bean.PageBean;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * author:SunGuiLan
 * date:2019/3/4
 * descriptin:
 */
public class MeFansListBean extends ResultMsgBean {
    private PageBean<MeFansInfoBean> page;

    public PageBean<MeFansInfoBean> getPage() {
        return page;
    }

    public void setPage(PageBean<MeFansInfoBean> page) {
        this.page = page;
    }
}
