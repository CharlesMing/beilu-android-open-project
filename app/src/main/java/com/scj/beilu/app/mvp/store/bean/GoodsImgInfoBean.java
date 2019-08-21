package com.scj.beilu.app.mvp.store.bean;

/**
 * @author Mingxun
 * @time on 2019/3/28 18:21
 */
public class GoodsImgInfoBean {

    private int productPicId;
    private int productId;
    private String productPicOriginalAddr;
    private String productPicCompressionAddr;

    public int getProductPicId() {
        return productPicId;
    }

    public void setProductPicId(int productPicId) {
        this.productPicId = productPicId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductPicOriginalAddr() {
        return productPicOriginalAddr;
    }

    public void setProductPicOriginalAddr(String productPicOriginalAddr) {
        this.productPicOriginalAddr = productPicOriginalAddr;
    }

    public String getProductPicCompressionAddr() {
        return productPicCompressionAddr;
    }

    public void setProductPicCompressionAddr(String productPicCompressionAddr) {
        this.productPicCompressionAddr = productPicCompressionAddr;
    }
}
