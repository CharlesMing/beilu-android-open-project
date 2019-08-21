package com.scj.beilu.app.mvp.merchant;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoDetailsListBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoDetailsListResultBean;
import com.scj.beilu.app.mvp.merchant.model.IMerchantInfo;
import com.scj.beilu.app.mvp.merchant.model.MerchantInfoImpl;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/13 19:52
 */
public class MerchantInfoPre extends BaseMvpPresenter<MerchantInfoPre.MerchantInfoView> {

    private IMerchantInfo mMerchantInfo;

    public MerchantInfoPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mMerchantInfo = new MerchantInfoImpl(mBuilder);
    }

    public void getMerchantInfo(final int merchantId) {
        onceViewAttached(new ViewAction<MerchantInfoView>() {
            @Override
            public void run(@NonNull final MerchantInfoView mvpView) {
                mMerchantInfo.getMerchantInfo(merchantId)
                        .subscribe(new ObserverCallback<MerchantInfoDetailsListResultBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(MerchantInfoDetailsListResultBean detailsListBean) {
                                try {
                                    mvpView.onMerchantInfo(detailsListBean.getMerchantInfoBean());
                                    mvpView.onMerchantInfoList(detailsListBean.getDetailsListBeans());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    public interface MerchantInfoView extends MvpView {
        void onMerchantInfo(MerchantInfoBean merchantInfo);

        void onMerchantInfoList(List<MerchantInfoDetailsListBean> detailInfo);
    }
}
