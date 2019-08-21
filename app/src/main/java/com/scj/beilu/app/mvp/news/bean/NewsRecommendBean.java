package com.scj.beilu.app.mvp.news.bean;

/**
 * @author Mingxun
 * @time on 2019/5/13 13:59
 */
public class NewsRecommendBean {
    public final NewsAuthorListBean mNewsAuthorListBean;
    public final NewsListBean mNewsListBean;

    public NewsRecommendBean(NewsAuthorListBean newsAuthorListBean, NewsListBean newsListBean) {
        mNewsAuthorListBean = newsAuthorListBean;
        mNewsListBean = newsListBean;
    }
}
