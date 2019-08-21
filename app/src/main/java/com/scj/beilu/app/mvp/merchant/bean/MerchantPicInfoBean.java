package com.scj.beilu.app.mvp.merchant.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.scj.beilu.app.mvp.common.bean.PicPreviewBean;

/**
 * @author Mingxun
 * @time on 2019/4/15 22:23
 */
public class MerchantPicInfoBean extends PicPreviewBean implements Parcelable {
    /**
     * regionPicId : 7
     * regionId : 1
     * merchantId : 45
     * regionPicAddr : https://beilutest.oss-cn-hangzhou.aliyuncs.com/merchant/about/537c2225-f81e-4cc3-8977-803a422540fb20190378173417.jpg
     * regionPicAddrZip : https://beilutest.oss-cn-hangzhou.aliyuncs.com/merchant/about/zip537c2225-f81e-4cc3-8977-803a422540fb20190378173417.jpg
     */

    private int regionPicId;
    private int regionId;
    private int merchantId;
    private String regionPicAddr;
    private String regionPicAddrZip;

    protected MerchantPicInfoBean(Parcel in) {
        regionPicId = in.readInt();
        regionId = in.readInt();
        merchantId = in.readInt();
        regionPicAddr = in.readString();
        regionPicAddrZip = in.readString();
    }

    public static final Creator<MerchantPicInfoBean> CREATOR = new Creator<MerchantPicInfoBean>() {
        @Override
        public MerchantPicInfoBean createFromParcel(Parcel in) {
            return new MerchantPicInfoBean(in);
        }

        @Override
        public MerchantPicInfoBean[] newArray(int size) {
            return new MerchantPicInfoBean[size];
        }
    };

    public int getRegionPicId() {
        return regionPicId;
    }

    public void setRegionPicId(int regionPicId) {
        this.regionPicId = regionPicId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getRegionPicAddr() {
        return regionPicAddr;
    }

    public void setRegionPicAddr(String regionPicAddr) {
        this.regionPicAddr = regionPicAddr;
    }

    public String getRegionPicAddrZip() {
        return regionPicAddrZip;
    }

    public void setRegionPicAddrZip(String regionPicAddrZip) {
        this.regionPicAddrZip = regionPicAddrZip;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(regionPicId);
        dest.writeInt(regionId);
        dest.writeInt(merchantId);
        dest.writeString(regionPicAddr);
        dest.writeString(regionPicAddrZip);
    }

    @Override
    public String getOriginalPicPath() {
        return regionPicAddr;
    }

    @Override
    public String getThumbnailPicPath() {
        return regionPicAddrZip;
    }
}
