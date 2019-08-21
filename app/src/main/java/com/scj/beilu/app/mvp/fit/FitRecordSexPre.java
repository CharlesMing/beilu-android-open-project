package com.scj.beilu.app.mvp.fit;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.fit.model.FitRecordImpl;
import com.scj.beilu.app.mvp.fit.model.IFitRecord;

/**
 * @author Mingxun
 * @time on 2019/3/8 13:44
 */
public class FitRecordSexPre extends BaseMvpPresenter<FitRecordSexPre.FitRecordSexView> {

    private IFitRecord mFitRecord;

    public FitRecordSexPre(Context context) {
        super(context, ShowConfig.LOADING_DIALOG, ShowConfig.ERROR_TOAST);
        mFitRecord = new FitRecordImpl(mBuilder);
    }

    public void setSex(final int sex) {
        onceViewAttached(new ViewAction<FitRecordSexView>() {
            @Override
            public void run(@NonNull final FitRecordSexView mvpView) {
                mFitRecord.setSex(sex)
                        .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {

                            @Override
                            public void onRequestResult(ResultMsgBean resultMsgBean) {
                                mvpView.setResult(resultMsgBean);
                            }
                        });
            }
        });
    }

    public interface FitRecordSexView extends MvpView {
        void setResult(ResultMsgBean result);
    }

}
