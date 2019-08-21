package com.scj.beilu.app.mvp.fit.model;

import android.view.View;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.common.user.IUserInfo;
import com.scj.beilu.app.mvp.fit.bean.FitRecordGirthInfoBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordGirthListBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgListBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgResultBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordInfoResultBean;
import com.scj.beilu.app.mvp.fit.bean.FitUserSexBean;

import java.io.File;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/3/11 14:48
 */
public interface IFitRecord extends IUserInfo {

    /**
     * 设置健身者性别
     *
     * @param sex
     * @return
     */
    Observable<ResultMsgBean> setSex(int sex);

    /**
     * 获取健身者性别
     *
     * @return
     */
    Observable<FitUserSexBean> getSexInfo();

    /**
     * 获取健身记录
     *
     * @return
     */
    Observable<FitRecordGirthListBean> getUserRecord();

    /**
     * 添加健身记录
     */
    Observable<ResultMsgBean> addFitRecord(FitRecordGirthInfoBean girthInfoBean, String recordKey, float recordVal);

    /**
     * 获取最新健身记录
     */
    Observable<FitRecordInfoResultBean> getRecentFitRecord();

    /**
     * 添加健身记录-图片记录
     */
    Observable<FitRecordImgResultBean> addFitRecordImg(File filePath);

    /**
     * 查看健身记录-查看图片
     */
    Observable<FitRecordImgListBean> getFitRecordImgList(int currentPage);

    /**
     * 生成对比照
     */
    Observable<FitRecordImgResultBean> generatePicture(View view, boolean isWaterMark);

    /**
     * 分享图片
     */
    Observable<FitRecordImgResultBean> shareToImg(View view, int scene);

    /**
     * 删除图片
     */
    Observable<ResultMsgBean> deleteImg(long recordPicId);


}
