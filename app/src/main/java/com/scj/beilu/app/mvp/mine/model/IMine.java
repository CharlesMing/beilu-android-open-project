package com.scj.beilu.app.mvp.mine.model;

import com.scj.beilu.app.mvp.common.bean.DictrictOptionBean;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.common.user.IUserInfo;
import com.scj.beilu.app.mvp.mine.bean.AboutUsBean;
import com.scj.beilu.app.mvp.mine.bean.FocusUserInfoBean;
import com.scj.beilu.app.mvp.mine.bean.MeFansInfoBean;
import com.scj.beilu.app.mvp.mine.bean.MeFansListBean;
import com.scj.beilu.app.mvp.mine.bean.MeFocusListBean;
import com.scj.beilu.app.mvp.mine.bean.TotalCountBean;
import com.scj.beilu.app.mvp.user.entity.UserInfoEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/2/20 11:04
 */
public interface IMine extends IUserInfo {
    /**
     * 关注相关信息
     */
    Observable<TotalCountBean> getTotalCount();

    /**
     * 修改个人信息
     */
    Observable<ResultMsgBean> setUserInfoParams(Map<String, Object> params);

    /**
     * 更新个人信息
     */
    Observable<UserInfoEntity> updateUserInfo(UserInfoEntity userInfoEntity);

    /**
     * 修改头像
     */
    Observable<UserInfoEntity> setUserPhotoParam(String filePath);

    /**
     * 获取城市
     *
     * @return
     */
    Observable<DictrictOptionBean> dealWithDistrictData();

    /**
     * 关注的人
     */
    Observable<MeFocusListBean> GetMyFocusNumberList(int currentPage);

    /**
     * 设置 关注的人 --关注
     */
    Observable<ResultMsgBean> setAttentionParams(final List<FocusUserInfoBean> list, int position, int userId);


    /**
     * 设置 粉丝关注
     */
    Observable<ResultMsgBean> setFansAttentionParams(final List<MeFansInfoBean> beanList, int position, int userId);

    /**
     * 查看我的粉丝
     */
    Observable<MeFansListBean> getMyFans(int currentPage);

    /**
     * 查看关于我们
     */
    Observable<AboutUsBean> getAboutUsInfo();

    /**
     * 提交反馈信息
     */
    Observable<ResultMsgBean> addFeedBack(Map<String, String> params);

    /**
     * 获取缓存大小
     */
    Observable<String> getCacheSize();

    /**
     * 清理缓存
     */
    Observable<String> clearCache();
}
