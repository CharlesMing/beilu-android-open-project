package com.scj.beilu.app.mvp.news.bean;

import com.scj.beilu.app.mvp.common.bean.PageBean;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/5/11 17:59
 */
public class NewsAuthorListBean extends ResultMsgBean {

    private PageBean<NewsAuthorInfoBean> page;

    public PageBean<NewsAuthorInfoBean> getPage() {
        return page;
    }

    public void setPage(PageBean<NewsAuthorInfoBean> page) {
        this.page = page;
    }
}
