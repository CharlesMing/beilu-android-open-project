package com.scj.beilu.app.mvp.store.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mingxun
 * @time on 2019/3/28 11:38
 */
public class GoodsCategoryInfoBean implements Parcelable {
    /**
     * productCateId : 1
     * productCateName : 训练器材
     * productInfo : null
     */
    private int productCateId;
    private String productCateName;
    private String productInfo;
    private String productCatePicOriginalAddr;
    private String productCatePicCompressadAddr;

    protected GoodsCategoryInfoBean(Parcel in) {
        productCateId = in.readInt();
        productCateName = in.readString();
        productInfo = in.readString();
        productCatePicOriginalAddr = in.readString();
        productCatePicCompressadAddr = in.readString();
    }

    public static final Creator<GoodsCategoryInfoBean> CREATOR = new Creator<GoodsCategoryInfoBean>() {
        @Override
        public GoodsCategoryInfoBean createFromParcel(Parcel in) {
            return new GoodsCategoryInfoBean(in);
        }

        @Override
        public GoodsCategoryInfoBean[] newArray(int size) {
            return new GoodsCategoryInfoBean[size];
        }
    };

    public String getProductCatePicOriginalAddr() {
        return productCatePicOriginalAddr;
    }

    public void setProductCatePicOriginalAddr(String productCatePicOriginalAddr) {
        this.productCatePicOriginalAddr = productCatePicOriginalAddr;
    }

    public String getProductCatePicCompressadAddr() {
        return productCatePicCompressadAddr;
    }

    public void setProductCatePicCompressadAddr(String productCatePicCompressadAddr) {
        this.productCatePicCompressadAddr = productCatePicCompressadAddr;
    }

    public int getProductCateId() {
        return productCateId;
    }

    public void setProductCateId(int productCateId) {
        this.productCateId = productCateId;
    }

    public String getProductCateName() {
        return productCateName;
    }

    public void setProductCateName(String productCateName) {
        this.productCateName = productCateName;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productCateId);
        dest.writeString(productCateName);
        dest.writeString(productInfo);
        dest.writeString(productCatePicOriginalAddr);
        dest.writeString(productCatePicCompressadAddr);
    }
}
