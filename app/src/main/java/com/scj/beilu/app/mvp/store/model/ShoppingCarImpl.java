package com.scj.beilu.app.mvp.store.model;

import com.scj.beilu.app.dao.GoodsShoppingCarInfoBeanDao;
import com.scj.beilu.app.mvp.base.user.BaseLoadUserInfoDelegate;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoBean;
import com.scj.beilu.app.mvp.store.entity.GoodsShoppingCarInfoBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mingxun
 * @time on 2019/4/1 11:58
 */
public class ShoppingCarImpl extends BaseLoadUserInfoDelegate implements IShoppingCar {

    private GoodsShoppingCarInfoBeanDao mShoppingCarInfoDao;

    private final List<GoodsShoppingCarInfoBean> mShoppingCartList = new ArrayList<>();
    private final Set<Long> mRecordGoodsId = new HashSet<>();//记录已选中的商品id
    private int mCartCount = 0;//购物车总数量

    public ShoppingCarImpl(Builder builder) {
        super(builder);
        mShoppingCarInfoDao = getDaoSession().getGoodsShoppingCarInfoBeanDao();
    }

    @Override
    public Observable<Integer> addCart(final GoodsInfoBean goodsInfo, final int goodsNum, final String goodsSpecification) {

        ObservableOnSubscribe<Integer> subscribeOn =
                new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        Integer goodsCount = 0;
                        try {
                            //用户未登录时，设置默认token  登录后，需要修改默认token为用户登录的token
                            String token = getToken() == null ? VAL_TOKEN : getToken();

                            //通过默认token查找
                            List<GoodsShoppingCarInfoBean> defTokenList = mShoppingCarInfoDao.queryBuilder()
                                    .where(GoodsShoppingCarInfoBeanDao.Properties.Token.eq(VAL_TOKEN))
                                    .list();

                            //修改token
                            if ((defTokenList != null && defTokenList.size() > 0) && getToken() != null) {
                                int defSize = defTokenList.size();
                                token = getToken();
                                for (int i = 0; i < defSize; i++) {
                                    try {
                                        GoodsShoppingCarInfoBean carInfoBean = defTokenList.get(i);
                                        carInfoBean.setToken(token);
                                        mShoppingCarInfoDao.update(carInfoBean);
                                    } catch (Exception e) {
                                        emitter.onError(e);
                                    }
                                }

                            }

                            //通过token 查询本地是否有数据
                            List<GoodsShoppingCarInfoBean> list = mShoppingCarInfoDao.queryBuilder()
                                    .where(GoodsShoppingCarInfoBeanDao.Properties.Token.eq(token),
                                            GoodsShoppingCarInfoBeanDao.Properties.GoodsId.eq((long) goodsInfo.getProductId()))
                                    .list();
                            GoodsShoppingCarInfoBean carInfo;
                            if (list != null && list.size() > 0) {
                                carInfo = list.get(0);
                                int goodsNum = carInfo.getGoodsNum() + 1;

                                carInfo.setGoodsNum(goodsNum);

                                mShoppingCarInfoDao.update(carInfo);

                            } else {//新增
                                carInfo = new GoodsShoppingCarInfoBean();
                                carInfo.setGoodsId((long) goodsInfo.getProductId());
                                carInfo.setGoodsName(goodsInfo.getProductName());
                                carInfo.setGoodsOriginalImg(goodsInfo.getProductPicOriginalAddr());
                                carInfo.setGoodsThumbnailImg(goodsInfo.getProductPicCompressionAddr());
                                carInfo.setGoodsNum(goodsNum);
                                carInfo.setGoodsSpecification(goodsSpecification);
                                carInfo.setToken(token);
                                carInfo.setGoodsPrice(String.valueOf(goodsInfo.getProductDiscountPrice()));
                                carInfo.setGoodsOriginalPrice(goodsInfo.getProductOriginalPrice());
                                mShoppingCarInfoDao.insertOrReplace(carInfo);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            emitter.onNext(goodsCount);
                            emitter.onComplete();
                        }
                    }
                };

        return createObservableOnSubscribe(subscribeOn)
                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        return getCartCount();
                    }
                });
    }

    @Override
    public Observable<List<GoodsShoppingCarInfoBean>> getCartListByToken() {
        ObservableOnSubscribe<List<GoodsShoppingCarInfoBean>> onSubscribe =
                new ObservableOnSubscribe<List<GoodsShoppingCarInfoBean>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<GoodsShoppingCarInfoBean>> emitter) throws Exception {

                        try {
                            String token = getToken() == null ? VAL_TOKEN : getToken();
                            //通过默认token查找
                            List<GoodsShoppingCarInfoBean> defTokenList = mShoppingCarInfoDao.queryBuilder()
                                    .where(GoodsShoppingCarInfoBeanDao.Properties.Token.eq(VAL_TOKEN))
                                    .list();

                            //修改token
                            if ((defTokenList != null && defTokenList.size() > 0) && getToken() != null) {
                                int defSize = defTokenList.size();
                                token = getToken();
                                for (int i = 0; i < defSize; i++) {
                                    try {
                                        GoodsShoppingCarInfoBean carInfoBean = defTokenList.get(i);
                                        carInfoBean.setToken(token);
                                        mShoppingCarInfoDao.update(carInfoBean);
                                    } catch (Exception e) {
                                        emitter.onError(e);
                                    }
                                }

                            }

                            List<GoodsShoppingCarInfoBean> list = mShoppingCarInfoDao.queryBuilder()
                                    .where(GoodsShoppingCarInfoBeanDao.Properties.Token.eq(token))
                                    .list();
                            mShoppingCartList.clear();
                            mShoppingCartList.addAll(list);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            emitter.onNext(mShoppingCartList);
                            emitter.onComplete();
                        }

                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }

    @Override
    public Observable<Integer> getCartCount() {
        ObservableOnSubscribe<Integer> onSubscribe =
                new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        Integer goodsCount = 0;
                        try {
                            String token = getToken() == null ? VAL_TOKEN : getToken();

                            //通过默认token查找
                            List<GoodsShoppingCarInfoBean> defTokenList = mShoppingCarInfoDao.queryBuilder()
                                    .where(GoodsShoppingCarInfoBeanDao.Properties.Token.eq(VAL_TOKEN))
                                    .list();

                            //修改token
                            if ((defTokenList != null && defTokenList.size() > 0) && getToken() != null) {
                                int defSize = defTokenList.size();
                                token = getToken();
                                for (int i = 0; i < defSize; i++) {
                                    try {
                                        GoodsShoppingCarInfoBean carInfoBean = defTokenList.get(i);
                                        carInfoBean.setToken(token);
                                        mShoppingCarInfoDao.update(carInfoBean);
                                    } catch (Exception e) {
                                        emitter.onError(e);
                                    }
                                }
                            }
                            goodsCount = (int) mShoppingCarInfoDao.queryBuilder()
                                    .where(GoodsShoppingCarInfoBeanDao.Properties.Token.eq(token))
                                    .count();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            emitter.onNext(goodsCount);
                            emitter.onComplete();
                        }
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }

    /**
     * 设置全选
     *
     * @return Integer 选中商品的总数量
     */
    public Observable<Integer> setSelectAll(final boolean isSelectAll) {
        ObservableOnSubscribe<Integer> onSubscribe =
                new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        int count = 0;
                        try {
                            int size = mShoppingCartList.size();
                            for (int i = 0; i < size; i++) {
                                GoodsShoppingCarInfoBean carInfoBean = mShoppingCartList.get(i);
                                carInfoBean.setSelect(isSelectAll);
                                Long goodsId = carInfoBean.getGoodsId();
                                if (isSelectAll) {
                                    mRecordGoodsId.add(goodsId);
                                    count = count + carInfoBean.getGoodsNum();
                                } else {
                                    mRecordGoodsId.remove(goodsId);
                                }
                                mShoppingCartList.set(i, carInfoBean);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            emitter.onNext(count);
                            emitter.onComplete();
                        }
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }


    /**
     * 设置单选
     */
    public Observable<Integer> setSelectSingle(final int position) {
        ObservableOnSubscribe<Integer> onSubscribe =
                new ObservableOnSubscribe<Integer>() {

                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        try {
                            GoodsShoppingCarInfoBean carInfoBean = mShoppingCartList.get(position);
                            Long goodsId = carInfoBean.getGoodsId();
                            if (mRecordGoodsId.contains(goodsId)) {
                                mRecordGoodsId.remove(goodsId);
                                mCartCount = mCartCount - carInfoBean.getGoodsNum();
                            } else {
                                mRecordGoodsId.add(goodsId);
                                mCartCount = mCartCount + carInfoBean.getGoodsNum();
                            }
                            carInfoBean.setSelect(mRecordGoodsId.contains(goodsId));

                            mShoppingCartList.set(position, carInfoBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            emitter.onNext(mCartCount);
                            emitter.onComplete();
                        }
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }

    /**
     * 删除购物车商品
     */
    public Observable<Integer> delCart() {
        ObservableOnSubscribe<Integer> onSubscribe =
                new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        try {
                            Iterator<Long> goodsIds = mRecordGoodsId.iterator();
                            while (goodsIds.hasNext()) {
                                Long nextId = goodsIds.next();
                                for (int i = 0; i < mShoppingCartList.size(); i++) {
                                    GoodsShoppingCarInfoBean carInfoBean = mShoppingCartList.get(i);
                                    if (nextId == carInfoBean.getGoodsId()) {
                                        goodsIds.remove();
                                        mShoppingCartList.remove(i);
                                        mShoppingCarInfoDao.deleteByKey(nextId);
                                    }
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            emitter.onNext(-1);
                            emitter.onComplete();
                        }
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }

    /**
     * 获取选中的商品总数量
     *
     * @return Integer 选中商品的总数量
     */
    public Observable<Integer> getSelectNum() {
        ObservableOnSubscribe<Integer> onSubscribe =
                new ObservableOnSubscribe<Integer>() {

                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        int count = 0;
                        try {
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            emitter.onNext(count);
                            emitter.onComplete();
                        }
                    }
                };
        return createObservableOnSubscribe(onSubscribe);
    }

    @Override
    public Observable<Integer> modifyCartNum(final int num, final int position) {
        ObservableOnSubscribe<Integer> modifySub =
                new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        try {
                            GoodsShoppingCarInfoBean carInfoBean = mShoppingCartList.get(position);
                            carInfoBean.setGoodsNum(num);
                            mShoppingCartList.set(position, carInfoBean);
                            mShoppingCarInfoDao.update(carInfoBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            emitter.onNext(0);
                            emitter.onComplete();
                        }
                    }
                };
        return createObservableOnSubscribe(modifySub);
    }


}
