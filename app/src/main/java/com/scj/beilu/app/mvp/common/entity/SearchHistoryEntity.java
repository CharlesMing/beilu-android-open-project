package com.scj.beilu.app.mvp.common.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * @author Mingxun
 * @time on 2019/2/18 16:29
 */
@Entity
public class SearchHistoryEntity {
    @Id(autoincrement = true)
    private Long id;
    private String token;
    private String tagName;
    private int searchType;//搜索类型 //0:资讯 1:动态
    private Date createTime;

    @Generated(hash = 1115596339)
    public SearchHistoryEntity(Long id, String token, String tagName,
                               int searchType, Date createTime) {
        this.id = id;
        this.token = token;
        this.tagName = tagName;
        this.searchType = searchType;
        this.createTime = createTime;
    }

    @Generated(hash = 691068747)
    public SearchHistoryEntity() {
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getSearchType() {
        return this.searchType;
    }


    public void setSearchType(@SearchType.Search int searchType) {
        this.searchType = searchType;
    }
}
