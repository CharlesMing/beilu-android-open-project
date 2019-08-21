package com.scj.beilu.app.mvp.merchant;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicTypeBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicTypeResultBean;
import com.scj.beilu.app.mvp.merchant.model.IMerchantInfo;
import com.scj.beilu.app.mvp.merchant.model.MerchantInfoImpl;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/4/15 22:32
 */
public class MerchantPicManagerPre extends BaseMvpPresenter<MerchantPicManagerPre.MerchantPicManagerView> {

    private IMerchantInfo mMerchantInfo;

    public MerchantPicManagerPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mMerchantInfo = new MerchantInfoImpl(mBuilder);
    }

    public void getMerchantPicTypeList(final int merchantId) {
        onceViewAttached(new ViewAction<MerchantPicManagerView>() {
            @Override
            public void run(@NonNull final MerchantPicManagerView mvpView) {
                mMerchantInfo.getMerchantPicTypeList(merchantId)
                        .subscribe(new BaseResponseCallback<MerchantPicTypeResultBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(MerchantPicTypeResultBean merchantPicTypeResultBean) {
                                try {
                                    ArrayList<MerchantPicTypeBean> regionList = merchantPicTypeResultBean.getRegionList();
                                    mvpView.onTypeList(regionList);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    public interface MerchantPicManagerView extends MvpView {
        void onTypeList(ArrayList<MerchantPicTypeBean> typeList);
    }
}
