package com.scj.beilu.app.mvp.merchant.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.scj.beilu.app.mvp.common.bean.PicPreviewBean;

/**
 * @author Mingxun
 * @time on 2019/4/16 16:25
 */
public class MerchantInfoCoachPicInfoBean extends PicPreviewBean implements Parcelable {
    /**
     * merchantId : 45
     * coachId : 28
     * coachPicId : 0
     * coachPicAddr : https://beilutest.oss-cn-hangzhou.aliyuncs.com/merchant/about/295b8b57-2a8e-4ec8-bfbb-58728ad0bbfa20190374111547.jpg
     * coachPicAddrZip : https://beilutest.oss-cn-hangzhou.aliyuncs.com/merchant/about/zip295b8b57-2a8e-4ec8-bfbb-58728ad0bbfa20190374111547.jpg
     */

    private int merchantId;
    private int coachId;
    private int coachPicId;
    private String coachPicAddr;
    private String coachPicAddrZip;

    protected MerchantInfoCoachPicInfoBean(Parcel in) {
        merchantId = in.readInt();
        coachId = in.readInt();
        coachPicId = in.readInt();
        coachPicAddr = in.readString();
        coachPicAddrZip = in.readString();
    }

    public static final Creator<MerchantInfoCoachPicInfoBean> CREATOR = new Creator<MerchantInfoCoachPicInfoBean>() {
        @Override
        public MerchantInfoCoachPicInfoBean createFromParcel(Parcel in) {
            return new MerchantInfoCoachPicInfoBean(in);
        }

        @Override
        public MerchantInfoCoachPicInfoBean[] newArray(int size) {
            return new MerchantInfoCoachPicInfoBean[size];
        }
    };

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

    public int getCoachPicId() {
        return coachPicId;
    }

    public void setCoachPicId(int coachPicId) {
        this.coachPicId = coachPicId;
    }

    public String getCoachPicAddr() {
        return coachPicAddr;
    }

    public void setCoachPicAddr(String coachPicAddr) {
        this.coachPicAddr = coachPicAddr;
    }

    public String getCoachPicAddrZip() {
        return coachPicAddrZip;
    }

    public void setCoachPicAddrZip(String coachPicAddrZip) {
        this.coachPicAddrZip = coachPicAddrZip;
    }

    @Override
    public String getOriginalPicPath() {
        return coachPicAddr;
    }

    @Override
    public String getThumbnailPicPath() {
        return coachPicAddrZip;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(merchantId);
        dest.writeInt(coachId);
        dest.writeInt(coachPicId);
        dest.writeString(coachPicAddr);
        dest.writeString(coachPicAddrZip);
    }
}
