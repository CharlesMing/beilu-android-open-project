package com.scj.beilu.app.mvp.store.model;

import com.scj.beilu.app.mvp.store.bean.GoodsInfoBean;
import com.scj.beilu.app.mvp.store.entity.GoodsShoppingCarInfoBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/4/1 11:45
 */
public interface IShoppingCar {
    /**
     * 加入购物车
     *
     * @param goodsInfo
     * @param goodsNum
     * @param goodsSpecification
     * @return Integer 购物车数量
     */
    Observable<Integer> addCart(GoodsInfoBean goodsInfo, int goodsNum, String goodsSpecification);

    /**
     * 获取当前用户购物车列表
     *
     * @return
     */
    Observable<List<GoodsShoppingCarInfoBean>> getCartListByToken();

    /**
     * 获取购物车数量
     *
     * @return 商品总数量
     */
    Observable<Integer> getCartCount();

    /**
     * 编辑购物车商品（修改数量）
     *
     * @return Integer none
     */
    Observable<Integer> modifyCartNum(int num, int position);
}
