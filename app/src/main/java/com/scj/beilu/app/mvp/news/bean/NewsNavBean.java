package com.scj.beilu.app.mvp.news.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mingxun
 * @time on 2019/2/14 15:32
 */
public class NewsNavBean implements Parcelable {
    /**
     * newsCateId : 1
     * newsCateName : 推荐
     */

    private int newsCateId;
    private String newsCateName;

    protected NewsNavBean(Parcel in) {
        newsCateId = in.readInt();
        newsCateName = in.readString();
    }

    public static final Creator<NewsNavBean> CREATOR = new Creator<NewsNavBean>() {
        @Override
        public NewsNavBean createFromParcel(Parcel in) {
            return new NewsNavBean(in);
        }

        @Override
        public NewsNavBean[] newArray(int size) {
            return new NewsNavBean[size];
        }
    };

    public int getNewsCateId() {
        return newsCateId;
    }

    public void setNewsCateId(int newsCateId) {
        this.newsCateId = newsCateId;
    }

    public String getNewsCateName() {
        return newsCateName;
    }

    public void setNewsCateName(String newsCateName) {
        this.newsCateName = newsCateName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(newsCateId);
        dest.writeString(newsCateName);
    }
}
