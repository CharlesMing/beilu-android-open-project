package com.scj.beilu.app.mvp.store.model;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.store.bean.CollectGoodsListResultBean;
import com.scj.beilu.app.mvp.store.bean.GoodsBannerResultBean;
import com.scj.beilu.app.mvp.store.bean.GoodsCategoryListBean;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoResultBean;
import com.scj.beilu.app.mvp.store.bean.GoodsListResultBean;
import com.scj.beilu.app.mvp.store.bean.GoodsSpecificationListBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/3/26 03:31
 */
public interface IGoodsInfo {
    /**
     * 获取banner
     *
     * @return
     */
    Observable<GoodsBannerResultBean> getBannerList();

    /**
     * 获取首页商品分类
     */
    Observable<GoodsCategoryListBean> getGoodsCategoryList();

    /**
     * 根据商品分类id 获取商品列表
     */
    Observable<GoodsListResultBean> getGoodsListByCategoryId(int productCateId, int currentPage);

    /**
     * 商品列表
     *
     * @return
     */
    Observable<GoodsListResultBean> getGoodsList(int currentPage);

    /**
     * 商品详情
     */
    Observable<GoodsInfoResultBean> getGoodsInfoById(int productId);

    /**
     * 商品收藏
     */
    Observable<ResultMsgBean> setCollect(int productId);

    /**
     * 商品規格
     */
    Observable<List<GoodsSpecificationListBean>> getSpecification(int productId);

    /**
     * 商品搜索
     */
    Observable<GoodsListResultBean> searchGoodsList(String searchContent, int currentPage);

    /**
     * 收藏的商品
     */
    Observable<CollectGoodsListResultBean> getMyCollectGoodsList(int currentPage);
}
