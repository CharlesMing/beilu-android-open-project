package com.scj.beilu.app.mvp.merchant;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicInfoBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicInfoResultBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicTypeBean;
import com.scj.beilu.app.mvp.merchant.model.MerchantInfoImpl;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/4/15 22:32
 */
public class MerchantPicPreviewManagerPre extends BaseMvpPresenter<MerchantPicPreviewManagerPre.MerchantPicPreViewManagerView> {

    private MerchantInfoImpl mMerchantInfo;

    public MerchantPicPreviewManagerPre(Context context) {
        super(context, ShowConfig.NONE, ShowConfig.ERROR_TOAST);
        mMerchantInfo = new MerchantInfoImpl(mBuilder);
    }

    public void getMerchantPicTypeList(final int position, final boolean isAuto) {
        onceViewAttached(new ViewAction<MerchantPicPreViewManagerView>() {
            @Override
            public void run(@NonNull final MerchantPicPreViewManagerView mvpView) {
                mMerchantInfo.setSelectedByIndex(position)
                        .subscribe(new ObserverCallback<ArrayList<MerchantPicTypeBean>>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(ArrayList<MerchantPicTypeBean> merchantPicTypeBeans) {
                                if (isAuto) {
                                    mvpView.onTypeList(merchantPicTypeBeans);
                                } else {
                                    mvpView.onSetSelectResult();
                                }
                            }
                        });
            }
        });
    }

    public void getPicList(final int merchantId, final int regionId) {
        onceViewAttached(new ViewAction<MerchantPicPreViewManagerView>() {
            @Override
            public void run(@NonNull final MerchantPicPreViewManagerView mvpView) {
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

    public interface MerchantPicPreViewManagerView extends MvpView {
        void onTypeList(ArrayList<MerchantPicTypeBean> typeList);

        void onSetSelectResult();

        void onPicListResult(ArrayList<MerchantPicInfoBean> picInfoList);
    }
}
