package com.scj.beilu.app.mvp.find.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.scj.beilu.app.mvp.common.bean.PicPreviewBean;

/**
 * @author Mingxun
 * @time on 2019/2/21 13:12
 */
public class FindImageBean extends PicPreviewBean implements Parcelable  {
    /**
     * dynamicPicId : 0
     * dynamicId : 0
     * dynamicPicAddr : https://beilutest.oss-cn-hangzhou.aliyuncs.com/dynamic/videoPic/12323453465678765.jpg
     * dynamicPicZip : https://beilutest.oss-cn-hangzhou.aliyuncs.com/dynamic/videoPic/12323453465678765.jpg
     */

    private int dynamicPicId;
    private int dynamicId;
    private String dynamicPicAddr;
    private String dynamicPicZip;

    public FindImageBean(String dynamicPicAddr, String dynamicPicZip) {
        this.dynamicPicAddr = dynamicPicAddr;
        this.dynamicPicZip = dynamicPicZip;
    }

    public FindImageBean() {
    }

    protected FindImageBean(Parcel in) {
        dynamicPicId = in.readInt();
        dynamicId = in.readInt();
        dynamicPicAddr = in.readString();
        dynamicPicZip = in.readString();
    }

    public static final Creator<FindImageBean> CREATOR = new Creator<FindImageBean>() {
        @Override
        public FindImageBean createFromParcel(Parcel in) {
            return new FindImageBean(in);
        }

        @Override
        public FindImageBean[] newArray(int size) {
            return new FindImageBean[size];
        }
    };

    public int getDynamicPicId() {
        return dynamicPicId;
    }

    public void setDynamicPicId(int dynamicPicId) {
        this.dynamicPicId = dynamicPicId;
    }

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getDynamicPicAddr() {
        return dynamicPicAddr;
    }

    public void setDynamicPicAddr(String dynamicPicAddr) {
        this.dynamicPicAddr = dynamicPicAddr;
    }

    public String getDynamicPicZip() {
        return dynamicPicZip;
    }

    public void setDynamicPicZip(String dynamicPicZip) {
        this.dynamicPicZip = dynamicPicZip;
    }



    @Override
    public String getThumbnailPicPath() {
        return dynamicPicZip;
    }

    @Override
    public String getOriginalPicPath() {
        return dynamicPicAddr;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dynamicPicId);
        dest.writeInt(dynamicId);
        dest.writeString(dynamicPicAddr);
        dest.writeString(dynamicPicZip);
    }
}
