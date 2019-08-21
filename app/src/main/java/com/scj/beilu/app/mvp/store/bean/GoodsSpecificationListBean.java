package com.scj.beilu.app.mvp.store.bean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/28 21:24
 */
public class GoodsSpecificationListBean {
    /**
     * propId : 2
     * propName : 颜色
     * propCompany : null
     * productFors : [{"formatId":10,"propProductId":0,"propId":2,"objValue":"黄色"}]
     */

    private int propId;
    private String propName;
    private String propCompany;
    private List<GoodsSpecificationInfoBean> productFors;

    public int getPropId() {
        return propId;
    }

    public void setPropId(int propId) {
        this.propId = propId;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getPropCompany() {
        return propCompany;
    }

    public void setPropCompany(String propCompany) {
        this.propCompany = propCompany;
    }

    public List<GoodsSpecificationInfoBean> getProductFors() {
        return productFors;
    }

    public void setProductFors(List<GoodsSpecificationInfoBean> productFors) {
        this.productFors = productFors;
    }
}
