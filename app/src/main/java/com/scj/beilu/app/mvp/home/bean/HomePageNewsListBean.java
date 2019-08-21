package com.scj.beilu.app.mvp.home.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/12 17:49
 */
public class HomePageNewsListBean extends ResultMsgBean {

    private List<NewsInfoBean> news;

    public List<NewsInfoBean> getNews() {
        return news;
    }

    public void setNews(List<NewsInfoBean> news) {
        this.news = news;
    }
}
