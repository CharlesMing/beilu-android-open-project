package com.scj.beilu.app.mvp.common.search;

import com.scj.beilu.app.mvp.common.entity.SearchHistoryEntity;
import com.scj.beilu.app.mvp.common.entity.SearchType;
import com.scj.beilu.app.mvp.common.user.IUserInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/2/28 22:40
 */
public interface ISearchInfo extends IUserInfo {

    /**
     * 添加搜索历史到数据库
     */
    Observable<Long> addSearchContentToData(String tagName, @SearchType.Search int searchType);

    /**
     * 获取搜索历史列表
     */
    Observable<List<SearchHistoryEntity>> getHistoryList(@SearchType.Search int searchType);

    Observable<String> content(String val);
}
