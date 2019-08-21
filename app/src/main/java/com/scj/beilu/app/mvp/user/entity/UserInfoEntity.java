package com.scj.beilu.app.mvp.user.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author Mingxun
 * @time on 2019/2/13 11:56
 */
@Entity
public class UserInfoEntity implements Parcelable {

    /**
     * userId : 12
     * userNickName : Mingxun
     * userPassword : null
     * userSex : null
     * userBirth : 2019-02-14 10:53:32
     * userTel : 13883448332
     * userBrief : null
     * userProvince : null
     * userCity : null
     * userType : 0
     * userOriginalHead : https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/987654321-20190214104729.png
     * userCompressionHead : https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/987654321-20190214104729.png
     * userAppKey : null
     * userTargetValue : null
     * userBackImg : null
     * openId : null
     */
    @Id
    private String token;
    private long userId;
    private String userNickName;
    private String userPassword;
    private String userSex;
    private String userBirth;
    private String userTel;
    private String userBrief;
    private String userProvince;
    private String userCity;
    private int userType;
    private String userOriginalHead;
    private String userCompressionHead;
    private String userAppKey;
    private String userTargetValue;
    private String userBackImg;
    private String userBackImgZip;
    private String openId;
    private String result;

    @Generated(hash = 1883466132)
    public UserInfoEntity(String token, long userId, String userNickName, String userPassword, String userSex,
                          String userBirth, String userTel, String userBrief, String userProvince, String userCity,
                          int userType, String userOriginalHead, String userCompressionHead, String userAppKey,
                          String userTargetValue, String userBackImg, String userBackImgZip, String openId, String result) {
        this.token = token;
        this.userId = userId;
        this.userNickName = userNickName;
        this.userPassword = userPassword;
        this.userSex = userSex;
        this.userBirth = userBirth;
        this.userTel = userTel;
        this.userBrief = userBrief;
        this.userProvince = userProvince;
        this.userCity = userCity;
        this.userType = userType;
        this.userOriginalHead = userOriginalHead;
        this.userCompressionHead = userCompressionHead;
        this.userAppKey = userAppKey;
        this.userTargetValue = userTargetValue;
        this.userBackImg = userBackImg;
        this.userBackImgZip = userBackImgZip;
        this.openId = openId;
        this.result = result;
    }

    @Generated(hash = 2042969639)
    public UserInfoEntity() {
    }

    protected UserInfoEntity(Parcel in) {
        token = in.readString();
        userId = in.readLong();
        userNickName = in.readString();
        userPassword = in.readString();
        userSex = in.readString();
        userBirth = in.readString();
        userTel = in.readString();
        userBrief = in.readString();
        userProvince = in.readString();
        userCity = in.readString();
        userType = in.readInt();
        userOriginalHead = in.readString();
        userCompressionHead = in.readString();
        userAppKey = in.readString();
        userTargetValue = in.readString();
        userBackImg = in.readString();
        userBackImgZip = in.readString();
        openId = in.readString();
        result = in.readString();
    }

    public static final Creator<UserInfoEntity> CREATOR = new Creator<UserInfoEntity>() {
        @Override
        public UserInfoEntity createFromParcel(Parcel in) {
            return new UserInfoEntity(in);
        }

        @Override
        public UserInfoEntity[] newArray(int size) {
            return new UserInfoEntity[size];
        }
    };

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return this.userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserSex() {
        return this.userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserBirth() {
        return this.userBirth;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    public String getUserTel() {
        return this.userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserBrief() {
        return this.userBrief;
    }

    public void setUserBrief(String userBrief) {
        this.userBrief = userBrief;
    }

    public String getUserProvince() {
        return this.userProvince;
    }

    public void setUserProvince(String userProvince) {
        this.userProvince = userProvince;
    }

    public String getUserCity() {
        return this.userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public int getUserType() {
        return this.userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUserOriginalHead() {
        return this.userOriginalHead;
    }

    public void setUserOriginalHead(String userOriginalHead) {
        this.userOriginalHead = userOriginalHead;
    }

    public String getUserCompressionHead() {
        return this.userCompressionHead;
    }

    public void setUserCompressionHead(String userCompressionHead) {
        this.userCompressionHead = userCompressionHead;
    }

    public String getUserAppKey() {
        return this.userAppKey;
    }

    public void setUserAppKey(String userAppKey) {
        this.userAppKey = userAppKey;
    }

    public String getUserTargetValue() {
        return this.userTargetValue;
    }

    public void setUserTargetValue(String userTargetValue) {
        this.userTargetValue = userTargetValue;
    }

    public String getUserBackImg() {
        return this.userBackImg;
    }

    public void setUserBackImg(String userBackImg) {
        this.userBackImg = userBackImg;
    }

    public String getUserBackImgZip() {
        return this.userBackImgZip;
    }

    public void setUserBackImgZip(String userBackImgZip) {
        this.userBackImgZip = userBackImgZip;
    }

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeLong(userId);
        dest.writeString(userNickName);
        dest.writeString(userPassword);
        dest.writeString(userSex);
        dest.writeString(userBirth);
        dest.writeString(userTel);
        dest.writeString(userBrief);
        dest.writeString(userProvince);
        dest.writeString(userCity);
        dest.writeInt(userType);
        dest.writeString(userOriginalHead);
        dest.writeString(userCompressionHead);
        dest.writeString(userAppKey);
        dest.writeString(userTargetValue);
        dest.writeString(userBackImg);
        dest.writeString(userBackImgZip);
        dest.writeString(openId);
        dest.writeString(result);
    }
}
