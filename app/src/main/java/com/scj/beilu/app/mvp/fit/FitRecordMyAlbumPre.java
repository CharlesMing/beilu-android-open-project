package com.scj.beilu.app.mvp.fit;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.base.BaseCheckArrayView;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgInfoBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgListBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgResultBean;
import com.scj.beilu.app.mvp.fit.model.FitRecordImpl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/14 21:26
 */
public class FitRecordMyAlbumPre extends BaseMvpPresenter<FitRecordMyAlbumPre.FitRecordMyAlbumView> {

    private FitRecordImpl mFitRecord;

    private final List<FitRecordImgInfoBean> mFitRecordImgInfoList = new ArrayList<>();
    private final List<String> mAddedImgList = new ArrayList<>();

    public FitRecordMyAlbumPre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mFitRecord = new FitRecordImpl(mBuilder);
    }

    /**
     * 添加记录
     *
     * @param imgPath
     */
    public void addFitRecordImg(String imgPath) {
        mBuilder.setLoadType(ShowConfig.LOADING_DIALOG);
        File imgFile = new File(imgPath);
        if (imgFile.exists()) {
            onceViewAttached(mvpView -> mFitRecord.addFitRecordImg(imgFile)
                    .subscribe(new BaseResponseCallback<FitRecordImgResultBean>(mBuilder.build(mvpView)) {
                        @Override
                        public void onRequestResult(FitRecordImgResultBean fitRecordImgResultBean) {

                            showSelector(false);

                            FitRecordImgInfoBean imgInfoBean = new FitRecordImgInfoBean();
                            imgInfoBean.setPicComAddr(fitRecordImgResultBean.getComFile());
                            imgInfoBean.setPicOrgAddr(fitRecordImgResultBean.getOrgFile());
                            final String pattern = "yyyy-MM-dd HH:mm:ss";
                            final SimpleDateFormat df = new SimpleDateFormat(pattern);
                            String format = df.format(new Date());
                            imgInfoBean.setRecordDate(format);
                            mFitRecordImgInfoList.add(0, imgInfoBean);
                            mvpView.onAddForResult(fitRecordImgResultBean.getResult());
                        }
                    }));
        }

    }

    /**
     * 获取我的相册
     *
     * @param currentPage
     */
    public void getAlbumList(final int currentPage) {
        mBuilder.setLoadType(ShowConfig.LOADING_REFRESH);
        onceViewAttached(new ViewAction<FitRecordMyAlbumView>() {
            @Override
            public void run(@NonNull final FitRecordMyAlbumView mvpView) {
                mFitRecord.getFitRecordImgList(currentPage)
                        .subscribe(new BaseResponseCallback<FitRecordImgListBean>(mBuilder.build(mvpView)) {
                            @Override
                            public void onRequestResult(FitRecordImgListBean fitRecordImgListBean) {

                                try {
                                    List<FitRecordImgInfoBean> list = fitRecordImgListBean.getPage().getList();

                                    if (currentPage == 0) {
                                        mFitRecordImgInfoList.clear();
                                    }
                                    mFitRecordImgInfoList.addAll(list);

                                    mvpView.onCheckLoadMore(list);
                                    mvpView.onAlbumListResult(mFitRecordImgInfoList);

                                } catch (Exception e) {
                                    e.printStackTrace();

                                }
                            }
                        });
            }
        });

    }

    /**
     * 是否操作生成对比照
     *
     * @param isSelect
     */
    public void showSelector(final boolean isSelect) {
        if (!isSelect) {
            mAddedImgList.clear();
        }
        onceViewAttached(new ViewAction<FitRecordMyAlbumView>() {
            @Override
            public void run(@NonNull final FitRecordMyAlbumView mvpView) {
                mFitRecord.startShowSelector(mFitRecordImgInfoList, isSelect)
                        .subscribe(new ObserverCallback<List<FitRecordImgInfoBean>>(mBuilder.build(mvpView)) {

                            @Override
                            public void onNext(List<FitRecordImgInfoBean> fitRecordImgInfoBeans) {
                                mvpView.onShowSelector(mAddedImgList, -1, null);
                            }
                        });
            }
        });
    }


    /**
     * 设置对比照选择状态
     */
    public void setSelector(final int position) {
        onceViewAttached(new ViewAction<FitRecordMyAlbumView>() {
            @Override
            public void run(@NonNull final FitRecordMyAlbumView mvpView) {
                mFitRecord.setSelector(mAddedImgList, mFitRecordImgInfoList, position)
                        .subscribe(new ObserverCallback<String>(mBuilder.build(mvpView)) {
                            @Override
                            public void onNext(String result) {
                                mvpView.onShowSelector(mAddedImgList, position, result);
                            }
                        });
            }
        });
    }

    public ArrayList<FitRecordImgInfoBean> getImgList() {
        return (ArrayList<FitRecordImgInfoBean>) mFitRecordImgInfoList;
    }

    public interface FitRecordMyAlbumView extends BaseCheckArrayView {
        void onAlbumListResult(List<FitRecordImgInfoBean> albumList);

        void onAddForResult(String result);

        void onShowSelector(List<String> selectedImg, int position, String hint);
    }
}
