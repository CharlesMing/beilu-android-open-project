package com.scj.beilu.app.mvp.news.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mingxun
 * @time on 2019/2/14 16:21
 */
public class NewsInfoBean implements Parcelable {
    /**
     * newsId : 59
     * userId : 11
     * newsTitle : asdads
     * newsAuthor : asdasd
     * newsSource : asdads
     * newsBrief : asdasd
     * newsPic : https://beilutest.oss-cn-hangzhou.aliyuncs.com/news/about/cc54a377-529f-406a-8edf-638858ca2b6020190244210743.jpg
     * newsContent : null
     * cateId : 3
     * newsDate : 2019-02-13 21:07:42
     * newsBrowseCount : 0
     * newsShareWebSite : https://beilutest.oss-cn-hangzhou.aliyuncs.com/news/article/ff7fa073-c1df-4b50-8f38-3f8844a45d5f20190244210743.html
     * newsCollectionCount : 0
     * newsShareCount : 0
     */

    private int newsId;
    private int userId;
    private String newsTitle;
    private String newsAuthor;
    private String newsSource;
    private String newsBrief;
    private String newsPic;
    private String newsContent;
    private int cateId;
    private String newsDate;
    private int newsBrowseCount;
    private String newsShareWebSite;
    private int newsCollectionCount;
    private int newsShareCount;
    private int commentCount;
    private int isCollect;
    private int isFocus;


    protected NewsInfoBean(Parcel in) {
        newsId = in.readInt();
        userId = in.readInt();
        newsTitle = in.readString();
        newsAuthor = in.readString();
        newsSource = in.readString();
        newsBrief = in.readString();
        newsPic = in.readString();
        newsContent = in.readString();
        cateId = in.readInt();
        newsDate = in.readString();
        newsBrowseCount = in.readInt();
        newsShareWebSite = in.readString();
        newsCollectionCount = in.readInt();
        newsShareCount = in.readInt();
        commentCount = in.readInt();
        isCollect = in.readInt();
        isFocus = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(newsId);
        dest.writeInt(userId);
        dest.writeString(newsTitle);
        dest.writeString(newsAuthor);
        dest.writeString(newsSource);
        dest.writeString(newsBrief);
        dest.writeString(newsPic);
        dest.writeString(newsContent);
        dest.writeInt(cateId);
        dest.writeString(newsDate);
        dest.writeInt(newsBrowseCount);
        dest.writeString(newsShareWebSite);
        dest.writeInt(newsCollectionCount);
        dest.writeInt(newsShareCount);
        dest.writeInt(commentCount);
        dest.writeInt(isCollect);
        dest.writeInt(isFocus);
    }

    public static final Creator<NewsInfoBean> CREATOR = new Creator<NewsInfoBean>() {
        @Override
        public NewsInfoBean createFromParcel(Parcel in) {
            return new NewsInfoBean(in);
        }

        @Override
        public NewsInfoBean[] newArray(int size) {
            return new NewsInfoBean[size];
        }
    };

    @Override
    public String toString() {
        return "NewsListBean{" +
                "newsId=" + newsId +
                '}';
    }


    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public void setNewsAuthor(String newsAuthor) {
        this.newsAuthor = newsAuthor;
    }

    public String getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(String newsSource) {
        this.newsSource = newsSource;
    }

    public String getNewsBrief() {
        return newsBrief;
    }

    public void setNewsBrief(String newsBrief) {
        this.newsBrief = newsBrief;
    }

    public String getNewsPic() {
        return newsPic;
    }

    public void setNewsPic(String newsPic) {
        this.newsPic = newsPic;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public int getNewsBrowseCount() {
        return newsBrowseCount;
    }

    public void setNewsBrowseCount(int newsBrowseCount) {
        this.newsBrowseCount = newsBrowseCount;
    }

    public String getNewsShareWebSite() {
        return newsShareWebSite;
    }

    public void setNewsShareWebSite(String newsShareWebSite) {
        this.newsShareWebSite = newsShareWebSite;
    }

    public int getNewsCollectionCount() {
        return newsCollectionCount;
    }

    public void setNewsCollectionCount(int newsCollectionCount) {
        this.newsCollectionCount = newsCollectionCount;
    }

    public int getNewsShareCount() {
        return newsShareCount;
    }

    public void setNewsShareCount(int newsShareCount) {
        this.newsShareCount = newsShareCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public int getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(int isFocus) {
        this.isFocus = isFocus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
