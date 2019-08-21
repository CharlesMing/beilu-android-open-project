package com.scj.beilu.app.mvp.mine.model;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.mine.address.bean.AddressArrayBean;
import com.scj.beilu.app.mvp.mine.address.bean.AddressInfoResultBean;

import java.util.Map;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/4/4 11:44
 */
public interface IAddressInfo {

    /**
     * 获取用户所有收货地址相关信息
     */
    Observable<AddressArrayBean> getAllAddrByUserId();

    /**
     * 添加收货地址相关信息
     */
    Observable<ResultMsgBean> addUserAddr(Map<String, Object> params);

    /**
     * 修改收货地址相关信息
     */
    Observable<ResultMsgBean> modifyUserShipAddr(Map<String, Object> params);

    /**
     * 删除地址
     */
    Observable<ResultMsgBean> delUserAddrById(Map<String, Object> params);

    /**
     * 获取默认地址
     */
    Observable<AddressInfoResultBean> getDefAddressInfo();
}
