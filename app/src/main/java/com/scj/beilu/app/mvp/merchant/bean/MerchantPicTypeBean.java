package com.scj.beilu.app.mvp.merchant.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/4/15 22:23
 */
public class MerchantPicTypeBean implements Parcelable {

    private int regionId;
    private String regionName;
    private int typeCount;
    private ArrayList<MerchantPicInfoBean> regionPicList;
    private boolean selected;

    protected MerchantPicTypeBean(Parcel in) {
        regionId = in.readInt();
        regionName = in.readString();
        typeCount = in.readInt();
        regionPicList = in.createTypedArrayList(MerchantPicInfoBean.CREATOR);
        selected = in.readByte() != 0;
    }

    public static final Creator<MerchantPicTypeBean> CREATOR = new Creator<MerchantPicTypeBean>() {
        @Override
        public MerchantPicTypeBean createFromParcel(Parcel in) {
            return new MerchantPicTypeBean(in);
        }

        @Override
        public MerchantPicTypeBean[] newArray(int size) {
            return new MerchantPicTypeBean[size];
        }
    };

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public int getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(int typeCount) {
        this.typeCount = typeCount;
    }

    public ArrayList<MerchantPicInfoBean> getRegionPicList() {
        return regionPicList;
    }

    public void setRegionPicList(ArrayList<MerchantPicInfoBean> regionPicList) {
        this.regionPicList = regionPicList;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(regionId);
        dest.writeString(regionName);
        dest.writeInt(typeCount);
        dest.writeTypedList(regionPicList);
        dest.writeByte((byte) (selected ? 1 : 0));
    }
}
