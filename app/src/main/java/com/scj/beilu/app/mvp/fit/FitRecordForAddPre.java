package com.scj.beilu.app.mvp.fit;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgInfoBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgListBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgResultBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordInfoBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordInfoResultBean;
import com.scj.beilu.app.mvp.fit.model.FitRecordImpl;
import com.scj.beilu.app.mvp.fit.model.IFitRecord;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/11 13:01
 */
public class FitRecordForAddPre extends BaseMvpPresenter<FitRecordForAddPre.FitRecordForAddView> {

    private IFitRecord mFitRecord;
    private final List<FitRecordImgInfoBean> mFitRecordImgInfoList = new ArrayList<>();

    public FitRecordForAddPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mFitRecord = new FitRecordImpl(mBuilder);
    }


    public void getFitRecordInfo() {
        mBuilder.setLoadType(ShowConfig.LOADING_REFRESH);
        onceViewAttached(new ViewAction<FitRecordForAddView>() {
            @Override
            public void run(@NonNull final FitRecordForAddView mvpView) {
                mFitRecord.getRecentFitRecord()
                        .subscribe(new BaseResponseCallback<FitRecordInfoResultBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(FitRecordInfoResultBean fitRecordInfoResultBean) {
                                mvpView.onFitRecordInfoResult(fitRecordInfoResultBean.getRecord());
                            }
                        });
            }
        });
    }

    public void addFitRecordImg(File imgPath) {
        mBuilder.setLoadType(ShowConfig.LOADING_DIALOG);
        onceViewAttached(mvpView -> mFitRecord.addFitRecordImg(imgPath)
                .subscribe(new BaseResponseCallback<FitRecordImgResultBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(FitRecordImgResultBean fitRecordImgResultBean) {
                        //添加完成 重新请求接口
                        getFitRecordImgList(0);
                        mvpView.onAddForResult(fitRecordImgResultBean.getResult());
                    }
                }));
    }

    public void getFitRecordImgList(final int currentPage) {
        mBuilder.setLoadType(ShowConfig.NONE);
        onceViewAttached(new ViewAction<FitRecordForAddView>() {
            @Override
            public void run(@NonNull final FitRecordForAddView mvpView) {
                mFitRecord.getFitRecordImgList(currentPage)
                        .subscribe(new BaseResponseCallback<FitRecordImgListBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(FitRecordImgListBean fitRecordImgListBean) {
                                int totalCount = fitRecordImgListBean.getPage().getTotalCount();
                                List<FitRecordImgInfoBean> list = fitRecordImgListBean.getPage().getList();
                                if (currentPage == 0) {
                                    mFitRecordImgInfoList.clear();
                                }
                                mFitRecordImgInfoList.addAll(list);

                                mvpView.onCheckLoadMore(list);
                                mvpView.onFitRecordImgList(mFitRecordImgInfoList, totalCount);

                            }
                        });
            }
        });
    }

    public ArrayList<FitRecordImgInfoBean> getImgList() {
        return (ArrayList<FitRecordImgInfoBean>) mFitRecordImgInfoList;
    }

    public interface FitRecordForAddView extends BaseCheckArrayView {
        void onFitRecordInfoResult(FitRecordInfoBean fitRecordInfo);

        void onAddForResult(String result);

        void onFitRecordImgList(List<FitRecordImgInfoBean> fitRecordImgInfoList, int totalCount);
    }
}
