package com.scj.beilu.app.mvp.merchant;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicInfoBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicInfoResultBean;
import com.scj.beilu.app.mvp.merchant.model.IMerchantInfo;
import com.scj.beilu.app.mvp.merchant.model.MerchantInfoImpl;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/4/15 23:37
 */
public class MerchantPicListPre extends BaseMvpPresenter<MerchantPicListPre.MerchantPicListView> {
    private IMerchantInfo mMerchantInfo;

    public MerchantPicListPre(Context context) {
        super(context, ShowConfig.LOADING_STATE, ShowConfig.ERROR_TOAST);
        mMerchantInfo = new MerchantInfoImpl(mBuilder);
    }

    public void getPicList(final int merchantId, final int regionId) {
        onceViewAttached(new ViewAction<MerchantPicListView>() {
            @Override
            public void run(@NonNull final MerchantPicListView mvpView) {
                mMerchantInfo.getMerchantPicListById(merchantId, regionId)
                        .subscribe(new BaseResponseCallback<MerchantPicInfoResultBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(MerchantPicInfoResultBean merchantPicInfoResultBean) {
                                try {
                                    ArrayList<MerchantPicInfoBean> regionPicList = merchantPicInfoResultBean.getRegionList();
                                    mvpView.onPicListResult(regionPicList);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    public interface MerchantPicListView extends MvpView {
        void onPicListResult(ArrayList<MerchantPicInfoBean> picInfoList);
    }
}
