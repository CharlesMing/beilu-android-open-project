package com.scj.beilu.app.mvp.store.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/28 11:38
 */
public class GoodsCategoryListBean extends ResultMsgBean {

    private List<GoodsCategoryInfoBean> cates;

    public List<GoodsCategoryInfoBean> getCates() {
        return cates;
    }

    public void setCates(List<GoodsCategoryInfoBean> cates) {
        this.cates = cates;
    }


}
