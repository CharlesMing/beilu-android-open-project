package com.scj.beilu.app.mvp.action.model;

import com.scj.beilu.app.mvp.action.bean.ActionInfoListBean;
import com.scj.beilu.app.mvp.action.bean.ActionInfoResultBean;
import com.scj.beilu.app.mvp.action.bean.ActionPayPhotoBean;
import com.scj.beilu.app.mvp.action.bean.ActionTypeInfoBean;

import java.util.Map;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/3/16 15:53
 */
public interface IActionInfo {

    /**
     * 获取所有分类的数据
     */
    Observable<ActionTypeInfoBean> getAllType();

    /**
     * 通过分类Id获取动作列表
     */
    Observable<ActionInfoListBean> getActionList(int desId, int cateId, int partId);

    /**
     * 查看动作详情
     */
    Observable<ActionInfoResultBean> getActionInfo(int actionId);

    /**
     * 获取动作库分享图片
     */
    Observable<ActionPayPhotoBean> getActionPayPhoto();


}
