package com.scj.beilu.app.mvp.mine.bean;

import com.scj.beilu.app.mvp.common.bean.PageBean;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

public class MeFocusListBean extends ResultMsgBean {

    private PageBean<FocusUserInfoBean> page;

    public PageBean<FocusUserInfoBean> getPage() {
        return page;
    }

    public void setPage(PageBean<FocusUserInfoBean> page) {
        this.page = page;
    }
}
