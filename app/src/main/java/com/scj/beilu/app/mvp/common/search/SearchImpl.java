package com.scj.beilu.app.mvp.common.search;

import com.scj.beilu.app.dao.SearchHistoryEntityDao;
import com.scj.beilu.app.mvp.base.user.BaseLoadUserInfoDelegate;
import com.scj.beilu.app.mvp.common.entity.SearchHistoryEntity;
import com.scj.beilu.app.mvp.common.entity.SearchType;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author Mingxun
 * @time on 2019/2/28 22:42
 * 搜索记录操作：
 * 添加
 * 修改
 * 删除
 * 查询
 */
public class SearchImpl extends BaseLoadUserInfoDelegate implements ISearchInfo {

    private SearchHistoryEntityDao mSearchHistoryEntityDao;

    public SearchImpl(Builder builder) {
        super(builder);
        mSearchHistoryEntityDao = getDaoSession().getSearchHistoryEntityDao();
    }

    @Override
    public Observable<Long> addSearchContentToData(String tagName, @SearchType.Search int searchType) {
        return addTagNameToLocal(tagName, searchType);
    }

    @Override
    public Observable<List<SearchHistoryEntity>> getHistoryList(@SearchType.Search int searchType) {
        return getLocalHistoryList(searchType);
    }

    /**
     * 添加数据到本地缓存中
     *
     * @param tagName
     * @param searchType
     * @return
     */
    private Observable<Long> addTagNameToLocal(final String tagName, final int searchType) {
        ObservableOnSubscribe<Long> addSub =
                new ObservableOnSubscribe<Long>() {
                    @Override
                    public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                        long insert = -1;
                        try {
                            List<SearchHistoryEntity> searchList = mSearchHistoryEntityDao
                                    .queryBuilder()
                                    .where(SearchHistoryEntityDao.Properties.TagName.eq(tagName),
                                            SearchHistoryEntityDao.Properties.SearchType.eq(searchType))
                                    .list();

                            //用户未登录时，设置默认token  登录后，需要修改默认token为用户登录的token
                            String token = getToken() == null ? VAL_TOKEN : getToken();

                            SearchHistoryEntity historyEntity;
                            if (searchList.size() == 0) {
                                historyEntity = new SearchHistoryEntity();
                                historyEntity.setToken(token);
                                historyEntity.setTagName(tagName);
                                historyEntity.setSearchType(searchType);
                            } else {//有且只有一个时
                                historyEntity = searchList.get(0);
                            }
                            historyEntity.setCreateTime(new Date());
                            insert = mSearchHistoryEntityDao.insertOrReplace(historyEntity);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            emitter.onNext(insert);
                            emitter.onComplete();
                        }
                    }
                };
        return createObservableOnSubscribe(addSub);
    }

    private Observable<List<SearchHistoryEntity>> getLocalHistoryList(final int searchType) {
        ObservableOnSubscribe<List<SearchHistoryEntity>> searchListSub =
                new ObservableOnSubscribe<List<SearchHistoryEntity>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<SearchHistoryEntity>> emitter) throws Exception {
                        //用户未登录时，设置默认token  登录后，需要修改默认token为用户登录的token
                        String token = getToken() == null ? VAL_TOKEN : getToken();
                        List<SearchHistoryEntity> searchList = mSearchHistoryEntityDao
                                .queryBuilder()
                                .where(SearchHistoryEntityDao.Properties.Token.eq(token),
                                        SearchHistoryEntityDao.Properties.SearchType.eq(searchType))
                                .orderDesc(SearchHistoryEntityDao.Properties.CreateTime)
                                .limit(searchType == 1 ? 3 : 10)//searchType==1 为动态则显示3条数据
                                .list();
                        //开始修改token
                        if ((!token.equals(VAL_TOKEN)) && token.equals(getToken())) {
                            int size = searchList.size();
                            for (int i = 0; i < size; i++) {
                                try {
                                    SearchHistoryEntity entity = searchList.get(i);
                                    entity.setToken(token);
                                    mSearchHistoryEntityDao.update(entity);
                                } catch (Exception e) {
                                    emitter.onError(e);
                                }
                            }
                        }
                        try {
                            emitter.onNext(searchList);
                        } catch (Exception e) {
                            emitter.onError(e);
                        } finally {
                            emitter.onComplete();
                        }
                    }
                };
        return createObservableOnSubscribe(searchListSub);
    }

    @Override
    public Observable<String> content(final String s) {
        ObservableOnSubscribe<String> onSubscribe =
                new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        try {
                            emitter.onNext(s);
                            emitter.onComplete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }


}
