package com.scj.beilu.app.mvp.news.bean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/14 16:52
 * 当前View需要加载的数据
 */
public class NewsViewContentBean {
    private List<NewsBannerBean> mNewsBannerBeans;
    private NewsInfoBean mNewsListBean;

    public NewsViewContentBean(List<NewsBannerBean> newsBannerBeans, NewsInfoBean newsListBean) {
        mNewsBannerBeans = newsBannerBeans;
        mNewsListBean = newsListBean;
    }

    public NewsViewContentBean(NewsInfoBean newsListBean) {
        mNewsListBean = newsListBean;
    }
}
