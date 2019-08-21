package com.scj.beilu.app.mvp.store.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/12 16:56
 */
public class GoodsInfoBean implements Parcelable {


    /**
     * productId : 1
     * productName : 虽然他还是分公司
     * productOriginalPrice : 20.00
     * productDiscountPrice : 10.00
     * productFormat : []
     * productPicOriginalAddr : https://beilutest.oss-cn-hangzhou.aliyuncs.com/product/pic/f7ebd81c-8cf7-4e7b-9c56-157ca348d40c20190256114304..jpg
     * productPicCompressionAddr : https://beilutest.oss-cn-hangzhou.aliyuncs.com/product/pic/Zipf7ebd81c-8cf7-4e7b-9c56-157ca348d40c20190256114304..jpg
     */

    private int productId;
    private String productName;
    private String productOriginalPrice;
    private double productDiscountPrice;
    private String productPicOriginalAddr;
    private String productPicCompressionAddr;
    private List<?> productFormat;
    private String productSynopsis;
    private int productCateId;
    private int productSalesVolume;
    private int productRepertory;
    private String productDec;
    private int productStatus;
    private int isCollection;
    private List<GoodsImgInfoBean> productPic;
    private int goodsCount;
    private String mGoodsSpecification;

    protected GoodsInfoBean(Parcel in) {
        productId = in.readInt();
        productName = in.readString();
        productOriginalPrice = in.readString();
        productDiscountPrice = in.readDouble();
        productPicOriginalAddr = in.readString();
        productPicCompressionAddr = in.readString();
        productSynopsis = in.readString();
        productCateId = in.readInt();
        productSalesVolume = in.readInt();
        productRepertory = in.readInt();
        productDec = in.readString();
        productStatus = in.readInt();
        isCollection = in.readInt();
        goodsCount = in.readInt();
        mGoodsSpecification = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productId);
        dest.writeString(productName);
        dest.writeString(productOriginalPrice);
        dest.writeDouble(productDiscountPrice);
        dest.writeString(productPicOriginalAddr);
        dest.writeString(productPicCompressionAddr);
        dest.writeString(productSynopsis);
        dest.writeInt(productCateId);
        dest.writeInt(productSalesVolume);
        dest.writeInt(productRepertory);
        dest.writeString(productDec);
        dest.writeInt(productStatus);
        dest.writeInt(isCollection);
        dest.writeInt(goodsCount);
        dest.writeString(mGoodsSpecification);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GoodsInfoBean> CREATOR = new Creator<GoodsInfoBean>() {
        @Override
        public GoodsInfoBean createFromParcel(Parcel in) {
            return new GoodsInfoBean(in);
        }

        @Override
        public GoodsInfoBean[] newArray(int size) {
            return new GoodsInfoBean[size];
        }
    };

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductOriginalPrice() {
        return productOriginalPrice;
    }

    public void setProductOriginalPrice(String productOriginalPrice) {
        this.productOriginalPrice = productOriginalPrice;
    }

    public double getProductDiscountPrice() {
        return productDiscountPrice;
    }

    public void setProductDiscountPrice(double productDiscountPrice) {
        this.productDiscountPrice = productDiscountPrice;
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

    public List<?> getProductFormat() {
        return productFormat;
    }

    public void setProductFormat(List<?> productFormat) {
        this.productFormat = productFormat;
    }

    public String getProductSynopsis() {
        return productSynopsis;
    }

    public void setProductSynopsis(String productSynopsis) {
        this.productSynopsis = productSynopsis;
    }

    public int getProductCateId() {
        return productCateId;
    }

    public void setProductCateId(int productCateId) {
        this.productCateId = productCateId;
    }

    public int getProductSalesVolume() {
        return productSalesVolume;
    }

    public void setProductSalesVolume(int productSalesVolume) {
        this.productSalesVolume = productSalesVolume;
    }

    public int getProductRepertory() {
        return productRepertory;
    }

    public void setProductRepertory(int productRepertory) {
        this.productRepertory = productRepertory;
    }

    public String getProductDec() {
        return productDec;
    }

    public void setProductDec(String productDec) {
        this.productDec = productDec;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }

    public int getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(int isCollection) {
        this.isCollection = isCollection;
    }

    public List<GoodsImgInfoBean> getProductPic() {
        return productPic;
    }

    public void setProductPic(List<GoodsImgInfoBean> productPic) {
        this.productPic = productPic;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getGoodsSpecification() {
        return mGoodsSpecification;
    }

    public void setGoodsSpecification(String goodsSpecification) {
        mGoodsSpecification = goodsSpecification;
    }
}
