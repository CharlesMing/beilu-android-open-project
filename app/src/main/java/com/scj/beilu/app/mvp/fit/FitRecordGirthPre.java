package com.scj.beilu.app.mvp.fit;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordGirthInfoBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordGirthListBean;
import com.scj.beilu.app.mvp.fit.model.FitRecordImpl;
import com.scj.beilu.app.mvp.fit.model.IFitRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/11 20:18
 */
public class FitRecordGirthPre extends BaseMvpPresenter<FitRecordGirthPre.FitRecordGirthView> {
    private IFitRecord mFitRecord;

    private List<FitRecordGirthInfoBean> mFitRecordGirthInfoList = new ArrayList<>();

    public FitRecordGirthPre(Context context) {
        super(context);
        mFitRecord = new FitRecordImpl(mBuilder);
    }


    public void getFitRecordInfo() {
        mBuilder.setLoadType(ShowConfig.LOADING_REFRESH);
        onceViewAttached(new ViewAction<FitRecordGirthView>() {
            @Override
            public void run(@NonNull final FitRecordGirthView mvpView) {
                mFitRecord.getUserRecord()
                        .subscribe(new BaseResponseCallback<FitRecordGirthListBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(FitRecordGirthListBean fitRecordInfoBean) {
                                mFitRecordGirthInfoList.clear();
                                List<FitRecordGirthInfoBean> girthInfoBeans = fitRecordInfoBean.getRecordCommons();
                                mFitRecordGirthInfoList.addAll(girthInfoBeans);

                                mvpView.onCheckLoadMore(mFitRecordGirthInfoList);
                                mvpView.onGirthListResult(mFitRecordGirthInfoList);
                            }
                        });
            }
        });
    }

    public void addFitRecord(final int position, final String recordKey, final float recordVal) {
        mBuilder.setLoadType(ShowConfig.LOADING_DIALOG);
        onceViewAttached(new ViewAction<FitRecordGirthView>() {
            @Override
            public void run(@NonNull final FitRecordGirthView mvpView) {
                mFitRecord.addFitRecord(getChartValList(position), recordKey, recordVal)
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                mvpView.onAddResult(resultMsgBean.getResult());
                            }
                        });
            }
        });
    }

    private FitRecordGirthInfoBean getChartValList(int position) {
        return mFitRecordGirthInfoList.get(position);

    }

    public interface FitRecordGirthView extends BaseCheckArrayView {
        void onGirthListResult(List<FitRecordGirthInfoBean> girthInfoBeans);

        void onAddResult(String result);
    }
}
