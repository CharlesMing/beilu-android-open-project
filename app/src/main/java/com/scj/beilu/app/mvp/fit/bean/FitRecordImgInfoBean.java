package com.scj.beilu.app.mvp.fit.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mingxun
 * @time on 2019/3/14 17:15
 */
public class FitRecordImgInfoBean implements Parcelable {
    /**
     * recordPicId : 2
     * userId : 13
     * picOrgAddr : https://beilutest.oss-cn-hangzhou.aliyuncs.com/record/pic/ba703c5a-66f3-4658-b69b-b7391a49440220190251194742.jpg
     * picComAddr : https://beilutest.oss-cn-hangzhou.aliyuncs.com/record/pic/zipba703c5a-66f3-4658-b69b-b7391a49440220190251194742.jpg
     */

    private int recordPicId;
    private int userId;
    private String picOrgAddr;
    private String picComAddr;
    private String recordDate;
    private boolean isSelected;
    private boolean isShow;

    public FitRecordImgInfoBean() {
    }

    protected FitRecordImgInfoBean(Parcel in) {
        recordPicId = in.readInt();
        userId = in.readInt();
        picOrgAddr = in.readString();
        picComAddr = in.readString();
        recordDate = in.readString();
        isSelected = in.readByte() != 0;
        isShow = in.readByte() != 0;
    }

    public static final Creator<FitRecordImgInfoBean> CREATOR = new Creator<FitRecordImgInfoBean>() {
        @Override
        public FitRecordImgInfoBean createFromParcel(Parcel in) {
            return new FitRecordImgInfoBean(in);
        }

        @Override
        public FitRecordImgInfoBean[] newArray(int size) {
            return new FitRecordImgInfoBean[size];
        }
    };

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getRecordPicId() {
        return recordPicId;
    }

    public void setRecordPicId(int recordPicId) {
        this.recordPicId = recordPicId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPicOrgAddr() {
        return picOrgAddr;
    }

    public void setPicOrgAddr(String picOrgAddr) {
        this.picOrgAddr = picOrgAddr;
    }

    public String getPicComAddr() {
        return picComAddr;
    }

    public void setPicComAddr(String picComAddr) {
        this.picComAddr = picComAddr;
    }

    public String getRecordDate() {

        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recordPicId);
        dest.writeInt(userId);
        dest.writeString(picOrgAddr);
        dest.writeString(picComAddr);
        dest.writeString(recordDate);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeByte((byte) (isShow ? 1 : 0));
    }
}
