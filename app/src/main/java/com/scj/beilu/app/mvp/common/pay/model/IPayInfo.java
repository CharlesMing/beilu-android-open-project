package com.scj.beilu.app.mvp.common.pay.model;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/3/23 15:36
 */
public interface IPayInfo {
    <PayResult extends ResultMsgBean> Observable<PayResult> startCoursePay(int payType, int courseVideoId, int courseId);

    <PayResult extends ResultMsgBean> Observable<PayResult> startGoodsPay
            (String orderNo, int productId, int productCount,
             int orderPayId, String productName,
             int addrId, String description);
}
