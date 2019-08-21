package com.scj.beilu.app.mvp.common.bean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/13 16:52
 */
public class BannerListBean extends ResultMsgBean {

    private List<BannerInfoBean> advers;

    public List<BannerInfoBean> getAdvers() {
        return advers;
    }

    public void setAdvers(List<BannerInfoBean> advers) {
        this.advers = advers;
    }
}
