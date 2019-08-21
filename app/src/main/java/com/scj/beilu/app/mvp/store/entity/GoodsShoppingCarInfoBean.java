package com.scj.beilu.app.mvp.store.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Mingxun
 * @time on 2019/4/1 11:35
 */
@Entity
public class GoodsShoppingCarInfoBean {
    @Id
    private Long goodsId;
    private String token;
    private String goodsName;
    private String goodsSpecification;
    private String goodsPrice;
    private String goodsOriginalPrice;
    private int goodsNum;
    private String goodsOriginalImg;
    private String goodsThumbnailImg;
    private boolean select = true;
    @Generated(hash = 885914583)
    public GoodsShoppingCarInfoBean(Long goodsId, String token, String goodsName,
            String goodsSpecification, String goodsPrice, String goodsOriginalPrice,
            int goodsNum, String goodsOriginalImg, String goodsThumbnailImg,
            boolean select) {
        this.goodsId = goodsId;
        this.token = token;
        this.goodsName = goodsName;
        this.goodsSpecification = goodsSpecification;
        this.goodsPrice = goodsPrice;
        this.goodsOriginalPrice = goodsOriginalPrice;
        this.goodsNum = goodsNum;
        this.goodsOriginalImg = goodsOriginalImg;
        this.goodsThumbnailImg = goodsThumbnailImg;
        this.select = select;
    }
    @Generated(hash = 2109027320)
    public GoodsShoppingCarInfoBean() {
    }
    public Long getGoodsId() {
        return this.goodsId;
    }
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getGoodsName() {
        return this.goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public String getGoodsSpecification() {
        return this.goodsSpecification;
    }
    public void setGoodsSpecification(String goodsSpecification) {
        this.goodsSpecification = goodsSpecification;
    }
    public String getGoodsPrice() {
        return this.goodsPrice;
    }
    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
    public String getGoodsOriginalPrice() {
        return this.goodsOriginalPrice;
    }
    public void setGoodsOriginalPrice(String goodsOriginalPrice) {
        this.goodsOriginalPrice = goodsOriginalPrice;
    }
    public int getGoodsNum() {
        return this.goodsNum;
    }
    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }
    public String getGoodsOriginalImg() {
        return this.goodsOriginalImg;
    }
    public void setGoodsOriginalImg(String goodsOriginalImg) {
        this.goodsOriginalImg = goodsOriginalImg;
    }
    public String getGoodsThumbnailImg() {
        return this.goodsThumbnailImg;
    }
    public void setGoodsThumbnailImg(String goodsThumbnailImg) {
        this.goodsThumbnailImg = goodsThumbnailImg;
    }
    public boolean getSelect() {
        return this.select;
    }
    public void setSelect(boolean select) {
        this.select = select;
    }

    @Override
    public String toString() {
        return "GoodsShoppingCarInfoBean{" +
                "goodsId=" + goodsId +
                ", token='" + token + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsSpecification='" + goodsSpecification + '\'' +
                ", goodsPrice='" + goodsPrice + '\'' +
                ", goodsOriginalPrice='" + goodsOriginalPrice + '\'' +
                ", goodsNum=" + goodsNum +
                ", goodsOriginalImg='" + goodsOriginalImg + '\'' +
                ", goodsThumbnailImg='" + goodsThumbnailImg + '\'' +
                ", select=" + select +
                '}';
    }
}
