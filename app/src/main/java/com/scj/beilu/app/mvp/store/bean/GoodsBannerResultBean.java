package com.scj.beilu.app.mvp.store.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/26 03:30
 */
public class GoodsBannerResultBean extends ResultMsgBean {

    private List<GoodsBannerInfoBean> banner;

    public List<GoodsBannerInfoBean> getBanner() {
        return banner;
    }

    public void setBanner(List<GoodsBannerInfoBean> banner) {
        this.banner = banner;
    }

}
