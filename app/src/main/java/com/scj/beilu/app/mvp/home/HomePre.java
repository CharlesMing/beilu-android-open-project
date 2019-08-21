package com.scj.beilu.app.mvp.home;

import android.content.Context;

import com.mx.pro.lib.mvp.network.callback.ObserverCallback;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.base.BaseResponseCallback;
import com.scj.beilu.app.mvp.common.bean.BannerInfoBean;
import com.scj.beilu.app.mvp.common.bean.BannerListBean;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.common.share.ShareInfoPre;
import com.scj.beilu.app.mvp.common.share.ShareInfoView;
import com.scj.beilu.app.mvp.course.bean.CourseInfoBean;
import com.scj.beilu.app.mvp.course.bean.CourseListBean;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;
import com.scj.beilu.app.mvp.find.bean.ModifyRecordBean;
import com.scj.beilu.app.mvp.find.model.FindImpl;
import com.scj.beilu.app.mvp.find.model.IFindInfo;
import com.scj.beilu.app.mvp.home.bean.HomePageFindListBean;
import com.scj.beilu.app.mvp.home.bean.HomePageNewsListBean;
import com.scj.beilu.app.mvp.home.model.HomeInfoImpl;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoBean;
import com.scj.beilu.app.mvp.store.bean.GoodsListBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;


/**
 * @author Mingxun
 * @time on 2019/1/11 15:38
 */
public class HomePre extends ShareInfoPre<HomePre.HomeView> {

    private HomeInfoImpl mHome;
    private IFindInfo mFind;

    private final List<FindInfoBean> mFindInfoList = new ArrayList<>();

    public HomePre(Context context) {
        super(context, ShowConfig.LOADING_REFRESH, ShowConfig.ERROR_TOAST);
        mHome = new HomeInfoImpl(mBuilder);
        mFind = new FindImpl(mBuilder);
    }

    /**
     * 合并请求当前页所有接口
     */
    public void getHomePageList() {
        onceViewAttached(mvpView -> Observable.mergeArray(mHome.getBannerList(), mHome.getCourseList(),
                mHome.getGoodsList(), mHome.getNewsList(), mHome.getFindList())
                .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(ResultMsgBean result) {
                        if (result instanceof BannerListBean) {
                            BannerListBean bannerListBean = (BannerListBean) result;
                            mvpView.onBannerList(bannerListBean.getAdvers());
                        } else if (result instanceof HomePageNewsListBean) {
                            HomePageNewsListBean homePageNewsList = (HomePageNewsListBean) result;
                            List<NewsInfoBean> newsList = homePageNewsList.getNews();
                            mvpView.onNewsList(newsList);
                        } else if (result instanceof CourseListBean) {
                            CourseListBean courseList = (CourseListBean) result;
                            List<CourseInfoBean> coursesList = courseList.getCourses();
                            mvpView.onCourseList(coursesList);
                        } else if (result instanceof GoodsListBean) {
                            GoodsListBean goodsList = (GoodsListBean) result;
                            List<GoodsInfoBean> goodsInfoList = goodsList.getProductInfo();
                            mvpView.onGoodsList(goodsInfoList);
                        } else if (result instanceof HomePageFindListBean) {
                            HomePageFindListBean homePageFindListBean = (HomePageFindListBean) result;
                            List<FindInfoBean> findList = homePageFindListBean.getDynamics();
                            mFindInfoList.clear();
                            mFindInfoList.addAll(findList);
                            mvpView.onFindInfoList(mFindInfoList);
                        }
                        mvpView.onShowContent();
                    }
                }));
    }


    public void setLike(final int dynamicId, final int position) {

        onceViewAttached(mvpView -> mFind.setLikeParamsByList(getFindList(), dynamicId, position)
                .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(ResultMsgBean resultMsgBean) {
                        mvpView.onNotifyListChangeResult(resultMsgBean.getResult(), position);
                    }
                }));
    }


    public void setAttentionUser(final int focusUser) {

        onceViewAttached(mvpView -> mFind.setAttentionParams(getFindList(), focusUser)
                .subscribe(new BaseResponseCallback<ResultMsgBean>(mBuilder.build(mvpView)) {
                    @Override
                    public void onRequestResult(final ResultMsgBean resultMsgBean) {
                        mvpView.onModifyResult(resultMsgBean.getResult());
                    }
                }));
    }

    public void modifyRecord(final ModifyRecordBean record, final int position) {
        onceViewAttached(mvpView -> mFind.updateFindDetailsResult(getFindList(), position, record)
                .subscribe(new ObserverCallback<Integer>(mBuilder.build(mvpView)) {
                    @Override
                    public void onNext(Integer position1) {
                        mvpView.onModifyResult(null);
                    }
                }));
    }

    private List<FindInfoBean> getFindList() {
        return mFindInfoList;
    }

    public void delFind(int dynamicId) {
        super.delFind(getFindList(), dynamicId);
    }

    public interface HomeView extends ShareInfoView {
        void onBannerList(List<BannerInfoBean> images);

        void onCourseList(List<CourseInfoBean> courseInfoList);

        void onGoodsList(List<GoodsInfoBean> goodsInfoList);

        void onNewsList(List<NewsInfoBean> newsInfoList);

        void onFindInfoList(List<FindInfoBean> findInfoList);

        void onModifyResult(String result);

        void onNotifyListChangeResult(String result, int position);

        void onShowContent();

    }
}
