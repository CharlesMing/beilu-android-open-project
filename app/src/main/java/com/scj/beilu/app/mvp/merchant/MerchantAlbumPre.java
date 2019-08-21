package com.scj.beilu.app.mvp.merchant;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoCoachPicInfoBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoCoachPicInfoResultBean;
import com.scj.beilu.app.mvp.merchant.model.IMerchantInfo;
import com.scj.beilu.app.mvp.merchant.model.MerchantInfoImpl;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/4/16 17:21
 */
public class MerchantAlbumPre extends BaseMvpPresenter<MerchantAlbumPre.MerchantAlbumView> {
    private IMerchantInfo mMerchantInfo;

    public MerchantAlbumPre(Context context) {
        super(context, ShowConfig.LOADING_STATE, ShowConfig.ERROR_TOAST);
        mMerchantInfo = new MerchantInfoImpl(mBuilder);
    }

    public void getAlbumList(final int merchantId, final int coachId) {
        onceViewAttached(new ViewAction<MerchantAlbumView>() {
            @Override
            public void run(@NonNull final MerchantAlbumView mvpView) {
                mMerchantInfo.getCoachAlbumList(merchantId, coachId)
                        .subscribe(new BaseResponseCallback<MerchantInfoCoachPicInfoResultBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(MerchantInfoCoachPicInfoResultBean merchantInfoCoachPicInfoResultBean) {
                                try {
                                    ArrayList<MerchantInfoCoachPicInfoBean> coachPicList = merchantInfoCoachPicInfoResultBean.getCoachPicList();
                                    mvpView.onAlbumList(coachPicList);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    public interface MerchantAlbumView extends MvpView {
        void onAlbumList(ArrayList<MerchantInfoCoachPicInfoBean> albumList);
    }
}
