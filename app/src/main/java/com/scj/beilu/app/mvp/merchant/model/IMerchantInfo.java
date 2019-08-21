package com.scj.beilu.app.mvp.merchant.model;

import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoCoachPicInfoResultBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoCoachResultBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoDetailsListResultBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoListBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicInfoResultBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicTypeResultBean;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/4/13 18:53
 */
public interface IMerchantInfo {
    /**
     * 获取当前城市商家列表
     *
     * @param currentPage
     * @param keyName
     * @param cityName
     * @return
     */
    Observable<MerchantInfoListBean> getMerchantList(int currentPage, String keyName, String cityName);

    /**
     * 获取商家详情
     */
    Observable<MerchantInfoDetailsListResultBean> getMerchantInfo(int merchantId);

    Observable<String> setContent(String content);

    /**
     * 教练详情
     */
    Observable<MerchantInfoCoachResultBean> getCoachInfo(int merchantId, int coachId);

    /**
     * 获取商家图片分类
     */
    Observable<MerchantPicTypeResultBean> getMerchantPicTypeList(int merchantId);

    /**
     * 通过分类查看图片
     *
     * @param merchantId
     * @param regionId
     * @return
     */
    Observable<MerchantPicInfoResultBean> getMerchantPicListById(int merchantId, int regionId);

    /**
     * 教练个人相册
     */
    Observable<MerchantInfoCoachPicInfoResultBean> getCoachAlbumList(int merchantId, int coachId);
}
