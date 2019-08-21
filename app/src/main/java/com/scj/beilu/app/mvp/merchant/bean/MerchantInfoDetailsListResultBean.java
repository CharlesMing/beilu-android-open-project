package com.scj.beilu.app.mvp.merchant.bean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/15 17:13
 */
public class MerchantInfoDetailsListResultBean {
    private List<MerchantInfoDetailsListBean> mDetailsListBeans;
    private MerchantInfoBean mMerchantInfoBean;

    public MerchantInfoBean getMerchantInfoBean() {
        return mMerchantInfoBean;
    }

    public void setMerchantInfoBean(MerchantInfoBean merchantInfoBean) {
        mMerchantInfoBean = merchantInfoBean;
    }

    public List<MerchantInfoDetailsListBean> getDetailsListBeans() {
        return mDetailsListBeans;
    }

    public void setDetailsListBeans(List<MerchantInfoDetailsListBean> detailsListBeans) {
        mDetailsListBeans = detailsListBeans;
    }
}
