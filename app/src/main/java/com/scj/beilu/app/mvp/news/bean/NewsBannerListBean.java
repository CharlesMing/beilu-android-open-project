package com.scj.beilu.app.mvp.news.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/14 16:47
 */
public class NewsBannerListBean extends ResultMsgBean {

    private List<NewsBannerBean> banner;

    public List<NewsBannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<NewsBannerBean> banner) {
        this.banner = banner;
    }

}
