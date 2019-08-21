package com.scj.beilu.app.mvp.home.model;

import com.scj.beilu.app.mvp.common.bean.BannerListBean;
import com.scj.beilu.app.mvp.course.bean.CourseListBean;
import com.scj.beilu.app.mvp.home.bean.HomePageFindListBean;
import com.scj.beilu.app.mvp.home.bean.HomePageNewsListBean;
import com.scj.beilu.app.mvp.mine.bean.MeFocusListBean;
import com.scj.beilu.app.mvp.store.bean.GoodsListBean;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/2/11 21:08
 */
public interface IHomeInfo {

    /**
     * 首页Banner
     */
    Observable<BannerListBean> getBannerList();

    /**
     * 首页课程
     */
    Observable<CourseListBean> getCourseList();

    /**
     * 首页商品
     */
    Observable<GoodsListBean> getGoodsList();

    /**
     * 首页资讯
     */
    Observable<HomePageNewsListBean> getNewsList();

    /**
     * 首页动态
     */
    Observable<HomePageFindListBean> getFindList();

    /**
     * 搜索用户
     */
    Observable<MeFocusListBean> startSearchUser(int currentPage, String keyName);
}
