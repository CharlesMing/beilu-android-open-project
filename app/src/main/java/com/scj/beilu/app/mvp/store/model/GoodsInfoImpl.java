package com.scj.beilu.app.mvp.store.model;

import com.mx.pro.lib.mvp.exception.UserException;
import com.scj.beilu.app.api.GoodsApi;
import com.scj.beilu.app.mvp.base.user.BaseLoadUserInfoDelegate;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.store.bean.CollectGoodsListResultBean;
import com.scj.beilu.app.mvp.store.bean.GoodsBannerResultBean;
import com.scj.beilu.app.mvp.store.bean.GoodsCategoryListBean;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoResultBean;
import com.scj.beilu.app.mvp.store.bean.GoodsListResultBean;
import com.scj.beilu.app.mvp.store.bean.GoodsSpecificationInfoBean;
import com.scj.beilu.app.mvp.store.bean.GoodsSpecificationListBean;
import com.scj.beilu.app.mvp.store.bean.GoodsSpecificationResultBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mingxun
 * @time on 2019/3/26 03:31
 */
public class GoodsInfoImpl extends BaseLoadUserInfoDelegate implements IGoodsInfo {

    private GoodsApi mGoodsApi;

    public GoodsInfoImpl(Builder builder) {
        super(builder);
        mGoodsApi = create(GoodsApi.class);
    }

    @Override
    public Observable<GoodsBannerResultBean> getBannerList() {
        return createObservable(mGoodsApi.getStoreBannerList());
    }

    @Override
    public Observable<GoodsCategoryListBean> getGoodsCategoryList() {
        return createObservable(mGoodsApi.getGoodsCateGoryList());
    }

    @Override
    public Observable<GoodsListResultBean> getGoodsListByCategoryId(int productCateId, int currentPage) {
        return createObservable(mGoodsApi.getGoodsListByCategoryId(productCateId, currentPage));
    }

    @Override
    public Observable<GoodsListResultBean> getGoodsList(int currentPage) {
        return createObservable(mGoodsApi.getGoodsList(currentPage));
    }

    @Override
    public Observable<GoodsInfoResultBean> getGoodsInfoById(final int productId) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<GoodsInfoResultBean>>() {
                    @Override
                    public ObservableSource<GoodsInfoResultBean> apply(Map<String, String> token) throws Exception {
                        return createObservable(mGoodsApi.getGoodsInfoById(token, productId));
                    }
                });
    }

    @Override
    public Observable<ResultMsgBean> setCollect(final int productId) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<ResultMsgBean>>() {
                    @Override
                    public ObservableSource<ResultMsgBean> apply(Map<String, String> token) throws Exception {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return createObservable(mGoodsApi.setCollect(token, productId));
                        }
                    }
                });
    }

    @Override
    public Observable<List<GoodsSpecificationListBean>> getSpecification(int productId) {
        return createObservable(mGoodsApi.getGoodsSpecificationInfo(productId))
                .flatMap(new Function<GoodsSpecificationResultBean, ObservableSource<List<GoodsSpecificationListBean>>>() {
                    @Override
                    public ObservableSource<List<GoodsSpecificationListBean>> apply(GoodsSpecificationResultBean goodsSpecificationResultBean) throws Exception {
                        return setSelect(goodsSpecificationResultBean.getFormat(), -1, 0);
                    }
                });
    }

    @Override
    public Observable<GoodsListResultBean> searchGoodsList(String searchContent, int currentPage) {
        return createObservable(mGoodsApi.searchGoodsList(searchContent, currentPage));
    }

    @Override
    public Observable<CollectGoodsListResultBean> getMyCollectGoodsList(final int currentPage) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<CollectGoodsListResultBean>>() {
                    @Override
                    public ObservableSource<CollectGoodsListResultBean> apply(Map<String, String> token) throws UserException {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return createObservable(mGoodsApi.getMyCollectGoods(token, currentPage));
                        }
                    }
                });
    }

    public Observable<List<GoodsSpecificationListBean>> setSelect(final List<GoodsSpecificationListBean> groupList,
                                                                  final int groupPosition, final int childPosition) {
        ObservableOnSubscribe<List<GoodsSpecificationListBean>> onSubscribe =
                new ObservableOnSubscribe<List<GoodsSpecificationListBean>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<GoodsSpecificationListBean>> emitter) throws Exception {
                        try {
                            if (groupPosition == -1) {//設置默認選中
                                int groupSize = groupList.size();
                                for (int g = 0; g < groupSize; g++) {
                                    GoodsSpecificationListBean groupInfo = groupList.get(g);
                                    List<GoodsSpecificationInfoBean> childInfo = groupInfo.getProductFors();
                                    int childSize = childInfo.size();
                                    for (int c = 0; c < childSize; c++) {
                                        GoodsSpecificationInfoBean child = childInfo.get(c);
                                        if (c == childPosition) {
                                            child.setSelect(true);
                                        } else {
                                            child.setSelect(false);
                                        }
                                        childInfo.set(c, child);
                                    }

                                    groupInfo.setProductFors(childInfo);
                                    groupList.set(g, groupInfo);

                                }
                            } else {//用戶點擊
                                GoodsSpecificationListBean groupInfo = groupList.get(groupPosition);

                                List<GoodsSpecificationInfoBean> childList = groupInfo.getProductFors();

                                GoodsSpecificationInfoBean child = childList.get(childPosition);
                                if (!child.isSelect()) {//未选择的情况下
                                    try {
                                        int childSize = childList.size();

                                        for (int i = 0; i < childSize; i++) {
                                            GoodsSpecificationInfoBean childInfoIndex = childList.get(i);
                                            if (i == childPosition) {
                                                childInfoIndex.setSelect(true);
                                            } else {
                                                childInfoIndex.setSelect(false);
                                            }
                                            childList.set(i, childInfoIndex);
                                        }
                                        groupInfo.setProductFors(childList);
                                        groupList.set(groupPosition, groupInfo);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            emitter.onNext(groupList);
                            emitter.onComplete();
                        }
                    }
                };

        return createObservableOnSubscribe(onSubscribe);
    }

    public Observable<StringBuilder> getSelectVal(final List<GoodsSpecificationListBean> groupList) {
        ObservableOnSubscribe<StringBuilder> onSubscribe =
                new ObservableOnSubscribe<StringBuilder>() {
                    @Override
                    public void subscribe(ObservableEmitter<StringBuilder> emitter) throws Exception {
                        HashMap<Integer, String> hashMap = new HashMap<>();
                        try {
                            int groupSize = groupList.size();
                            for (int g = 0; g < groupSize; g++) {
                                GoodsSpecificationListBean groupInfo = groupList.get(g);
                                List<GoodsSpecificationInfoBean> childList = groupInfo.getProductFors();
                                int childSize = childList.size();
                                for (int c = 0; c < childSize; c++) {
                                    GoodsSpecificationInfoBean childInfo = childList.get(c);
                                    if (childInfo.isSelect()) {
                                        hashMap.put(g, childInfo.getObjValue());
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                        } finally {
                            StringBuilder sb = new StringBuilder();
                            for (Integer key : hashMap.keySet()) {
                                sb.append("\"")
                                        .append(hashMap.get(key))
                                        .append("\"");
                            }
                            emitter.onNext(sb);
                            emitter.onComplete();
                        }
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }
}
