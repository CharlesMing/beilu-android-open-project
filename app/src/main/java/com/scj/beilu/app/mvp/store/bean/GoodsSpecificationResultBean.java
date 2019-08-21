package com.scj.beilu.app.mvp.store.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/28 21:24
 */
public class GoodsSpecificationResultBean extends ResultMsgBean {

    private List<GoodsSpecificationListBean> format;

    public List<GoodsSpecificationListBean> getFormat() {
        return format;
    }

    public void setFormat(List<GoodsSpecificationListBean> format) {
        this.format = format;
    }

}
