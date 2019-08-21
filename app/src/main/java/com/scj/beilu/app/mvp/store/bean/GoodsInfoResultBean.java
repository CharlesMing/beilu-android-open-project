package com.scj.beilu.app.mvp.store.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/3/28 18:11
 */
public class GoodsInfoResultBean extends ResultMsgBean {

    private GoodsInfoBean productInfo;

    public GoodsInfoBean getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(GoodsInfoBean productInfo) {
        this.productInfo = productInfo;
    }
}