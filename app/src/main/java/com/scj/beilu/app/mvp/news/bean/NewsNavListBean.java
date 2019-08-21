package com.scj.beilu.app.mvp.news.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/14 15:20
 */
public class NewsNavListBean extends ResultMsgBean {

    private List<NewsNavBean> cates;

    public List<NewsNavBean> getCates() {
        return cates;
    }

    public void setCates(List<NewsNavBean> cates) {
        this.cates = cates;
    }

}
