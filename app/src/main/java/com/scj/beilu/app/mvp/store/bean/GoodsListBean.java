package com.scj.beilu.app.mvp.store.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/12 16:58
 */
public class GoodsListBean extends ResultMsgBean {

    private List<GoodsInfoBean> productInfo;

    public List<GoodsInfoBean> getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(List<GoodsInfoBean> productInfo) {
        this.productInfo = productInfo;
    }

}
