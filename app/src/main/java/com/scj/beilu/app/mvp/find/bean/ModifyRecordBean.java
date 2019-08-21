package com.scj.beilu.app.mvp.find.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mingxun
 * @time on 2019/2/28 18:10
 * 修改动态详情记录
 */
public class ModifyRecordBean implements Parcelable {
    public int mLikeCount;//传递正负1 ,默认为0
    public int mCommentCount;//评论总数量
    public int mFocusUserId;

    public ModifyRecordBean(int likeCount, int commentCount, int focusUserId) {
        mLikeCount = likeCount;
        mCommentCount = commentCount;
        mFocusUserId = focusUserId;
    }

    protected ModifyRecordBean(Parcel in) {
        mLikeCount = in.readInt();
        mCommentCount = in.readInt();
        mFocusUserId = in.readInt();
    }

    public static final Creator<ModifyRecordBean> CREATOR = new Creator<ModifyRecordBean>() {
        @Override
        public ModifyRecordBean createFromParcel(Parcel in) {
            return new ModifyRecordBean(in);
        }

        @Override
        public ModifyRecordBean[] newArray(int size) {
            return new ModifyRecordBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mLikeCount);
        dest.writeInt(mCommentCount);
        dest.writeInt(mFocusUserId);
    }

    @Override
    public String toString() {
        return "ModifyRecordBean{" +
                "mLikeCount=" + mLikeCount +
                ", mCommentCount=" + mCommentCount +
                ", mFocusUserId=" + mFocusUserId +
                '}';
    }
}
