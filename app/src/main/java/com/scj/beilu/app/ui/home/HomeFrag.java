package com.scj.beilu.app.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.mx.pro.lib.banner.Banner;
import com.mx.pro.lib.banner.loader.ImageLoader;
import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequests;
import com.scj.beilu.app.R;
import com.scj.beilu.app.banner.GlideImageLoader;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnGroupItemClickListener;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.common.bean.BannerInfoBean;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.course.bean.CourseInfoBean;
import com.scj.beilu.app.mvp.find.bean.FindImageBean;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;
import com.scj.beilu.app.mvp.find.bean.ModifyRecordBean;
import com.scj.beilu.app.mvp.home.HomePre;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoBean;
import com.scj.beilu.app.ui.act.FitRecordAct;
import com.scj.beilu.app.ui.act.MsgManagerAct;
import com.scj.beilu.app.ui.action.ActionInfoAct;
import com.scj.beilu.app.ui.course.CourseInfoAct;
import com.scj.beilu.app.ui.find.FindDetailsAct;
import com.scj.beilu.app.ui.find.FindDetailsFrag;
import com.scj.beilu.app.ui.find.adapter.FindInfoListAdapter;
import com.scj.beilu.app.ui.home.adapter.HomeButtonAdapter;
import com.scj.beilu.app.ui.home.adapter.HomeCourseListAdapter;
import com.scj.beilu.app.ui.home.adapter.HomeGoodsListAdapter;
import com.scj.beilu.app.ui.merchant.MerchantAct;
import com.scj.beilu.app.ui.news.NewsDetailsAct;
import com.scj.beilu.app.ui.news.adapter.NewsListAdapter;
import com.scj.beilu.app.ui.preview.PicImagePreviewAct;
import com.scj.beilu.app.ui.store.StoreInfoAct;
import com.scj.beilu.app.ui.user.UserInfoHomePageAct;
import com.scj.beilu.app.util.ToastUtils;
import com.scj.beilu.app.widget.DividerItemDecorationToPadding;
import com.scj.beilu.app.widget.FindDivider;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * date:2019/2/13
 * descriptin:首页
 */
@RuntimePermissions
public class HomeFrag extends BaseMvpFragment<HomePre.HomeView, HomePre>
        implements HomePre.HomeView, View.OnClickListener, OnItemClickListener,
        OnGroupItemClickListener<ArrayList<FindImageBean>> {

    private Banner mBanner;
    private RecyclerView mRvButton;
    private TextView mTvCity;
    private ImageButton mIbtnMessage;
    private TextView mTvMoreCourse, mTvMoreGoods, mTvMoreNews, mTvMoreFind;
    private RecyclerView mRvCourse, mRvGoods, mRvNews, mRvFind;
    private FrameLayout mFlSearch;
    private LinearLayout ll_content;

    private HomeCourseListAdapter mHomeCourseListAdapter;
    private HomeGoodsListAdapter mHomeGoodsListAdapter;
    private NewsListAdapter mHomeNewsListAdapter;
    private FindInfoListAdapter mHomeFindInfoListAdapter;

    private int mRecordPosition = -1;
    private static final int REQUEST_FIND_DETAILS_CODE = 0x001;
    private int mDynamicIdByIndex = -1;
    private onChangeBottomListener mOnChangeBottomListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        HomeFragPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE})
    void needsPermission() {
        getPresenter().getHomePageList();
        initLocationInfo();
    }

    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE})
    void onShowRationale(final PermissionRequest request) {
        request.proceed();
    }


    public interface onChangeBottomListener {
        void onBottomNavIndex(@IdRes int itemId);
    }

    private LocationClient mLocationClient = null;
    private LocationClientOption mLocationClientOption;

    private void initLocationInfo() {
        mLocationClient = new LocationClient(mFragmentActivity);
        mLocationClientOption = new LocationClientOption();
        mLocationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        mLocationClientOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        mLocationClientOption.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        mLocationClientOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要

        synchronized (this) {
            if (mLocationClient != null && mLocationClient.isStarted()) {
                mLocationClient.stop();
            }
        }
        mLocationClient.start();// 定位SDK
        mLocationClient.registerLocationListener(mListener);

        if (mLocationClient.isStarted())
            mLocationClient.stop();
        mLocationClient.setLocOption(mLocationClientOption);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof onChangeBottomListener) {
            mOnChangeBottomListener = (onChangeBottomListener) activity;
        }
    }

    @Override
    public HomePre createPresenter() {
        return new HomePre(mFragmentActivity);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        HomeFragPermissionsDispatcher.needsPermissionWithPermissionCheck(this);
    }

    @Override
    protected void onReLoad() {
        HomeFragPermissionsDispatcher.needsPermissionWithPermissionCheck(this);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (mHomeCourseListAdapter != null && mHomeCourseListAdapter.getItemCount() == 0) {
            getPresenter().getHomePageList();
        }
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_home;
    }

    @Override
    public void initView() {
        super.initView();

        mBanner = findViewById(R.id.banner_home);
        mRvButton = findViewById(R.id.rv_home_nav_list);
        mTvCity = findViewById(R.id.tv_city);
        mIbtnMessage = findViewById(R.id.iv_msg);
        mTvMoreCourse = findViewById(R.id.tv_home_page_list_load_more_course);
        mTvMoreGoods = findViewById(R.id.tv_home_page_list_load_more_goods);
        mTvMoreNews = findViewById(R.id.tv_home_page_list_load_more_news);
        mTvMoreFind = findViewById(R.id.tv_home_page_list_load_more_find);
        mRvCourse = findViewById(R.id.rv_home_page_course_content_list);
        mRvGoods = findViewById(R.id.rv_home_page_goods_content_list);
        mRvNews = findViewById(R.id.rv_home_page_news_content_list);
        mRvFind = findViewById(R.id.rv_home_page_find_content_list);
        mFlSearch = findViewById(R.id.fl_search);
        ll_content = findViewById(R.id.ll_content);

        mBanner.setImageLoader(new GlideImageLoader());

        HomeButtonAdapter homeButtonAdapter = new HomeButtonAdapter(GlideApp.with(this));
        homeButtonAdapter.setItemClickListener(this);
        mRvButton.setAdapter(homeButtonAdapter);

        initCourseAdapter();
        initGoodsAdapter();
        initNewsListAdapter();
        initFindInfoAdapter();

        mIbtnMessage.setOnClickListener(this);
        mTvCity.setOnClickListener(this);
        mFlSearch.setOnClickListener(this);
        mTvMoreCourse.setOnClickListener(this);
        mTvMoreGoods.setOnClickListener(this);
        mTvMoreNews.setOnClickListener(this);
        mTvMoreFind.setOnClickListener(this);
    }

    private void initCourseAdapter() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(mFragmentActivity, LinearLayoutManager.HORIZONTAL, false);
        mRvCourse.setLayoutManager(layoutManager);
        DividerItemDecorationToPadding itemDecorationToPadding = new DividerItemDecorationToPadding(mFragmentActivity, DividerItemDecoration.HORIZONTAL);
        itemDecorationToPadding.setDrawable(ContextCompat.getDrawable(mFragmentActivity, R.drawable.item_divider_course_hor));
        mRvCourse.addItemDecoration(itemDecorationToPadding);

        mHomeCourseListAdapter = new HomeCourseListAdapter(this);
        mHomeCourseListAdapter.setOnItemClickListener(this);
        mRvCourse.setAdapter(mHomeCourseListAdapter);
    }

    private void initGoodsAdapter() {

        final int offset = getResources().getDimensionPixelOffset(R.dimen.hor_divider_size_2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mFragmentActivity, LinearLayoutManager.HORIZONTAL, false);
        mRvGoods.setLayoutManager(layoutManager);
        mRvGoods.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(offset, 0, offset, 0);
            }
        });

        mHomeGoodsListAdapter = new HomeGoodsListAdapter(this);
        mHomeGoodsListAdapter.setItemClickListener(this);
        mRvGoods.setAdapter(mHomeGoodsListAdapter);

    }

    private void initNewsListAdapter() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(mFragmentActivity, LinearLayoutManager.VERTICAL, false);
        mRvNews.setLayoutManager(layoutManager);
        mRvNews.addItemDecoration(new DividerItemDecorationToPadding(mFragmentActivity, DividerItemDecoration.VERTICAL));

        mHomeNewsListAdapter = new NewsListAdapter(mFragmentActivity, GlideApp.with(this));
        mHomeNewsListAdapter.setOnItemClickListener(this);
        mRvNews.setAdapter(mHomeNewsListAdapter);
    }

    private void initFindInfoAdapter() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(mFragmentActivity, LinearLayoutManager.VERTICAL, false);
        mRvFind.setLayoutManager(layoutManager);
        mRvFind.addItemDecoration(new FindDivider(mFragmentActivity, DividerItemDecoration.VERTICAL));
        mRvFind.getItemAnimator().setChangeDuration(0);

        mHomeFindInfoListAdapter = new FindInfoListAdapter(GlideApp.with(this), mFragmentActivity);
        mHomeFindInfoListAdapter.setOnItemClickListener(this);
        mHomeFindInfoListAdapter.setOnGroupItemClickListener(this);
        mRvFind.setAdapter(mHomeFindInfoListAdapter);
    }

    @Override
    public void showError(int errorCode, String throwableContent) {
        super.showError(errorCode, throwableContent);
        if (errorCode == ShowConfig.ERROR_NET) {
            ll_content.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBanner != null)
            mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        if (mLocationClient != null && mListener != null) {
            mLocationClient.unRegisterLocationListener(mListener);//注销掉监听
            mLocationClient.stop(); //停止定位服务
        }
        super.onStop();
        if (mBanner != null)
            mBanner.stopAutoPlay();
    }

    @Override
    public void onShowContent() {
        if (ll_content.getVisibility() == View.GONE) {
            ll_content.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBannerList(List<BannerInfoBean> images) {
        final GlideRequests mGlideOriginal = GlideApp.with(HomeFrag.this);
        final GlideRequests mGlideThumbnail = GlideApp.with(HomeFrag.this);

        mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, View imageView) {
                BannerInfoBean bannerPath = (BannerInfoBean) path;
                ImageView image = imageView.findViewById(R.id.banner_image);
                mGlideOriginal
                        .load(bannerPath.getAdverPicAddr())
                        .thumbnail(mGlideThumbnail.load(bannerPath.getAdverPicZip()))
                        .dontAnimate()
                        .into(image);
            }

        });
        mBanner.setImages(images);
        mBanner.start();
    }

    @Override
    public void onCourseList(List<CourseInfoBean> courseInfoList) {
        mHomeCourseListAdapter.setCourseInfoList(courseInfoList);
    }

    @Override
    public void onGoodsList(List<GoodsInfoBean> goodsInfoList) {
        mHomeGoodsListAdapter.setGoodsInfoList(goodsInfoList);
    }

    @Override
    public void onNewsList(List<NewsInfoBean> newsInfoList) {
        mHomeNewsListAdapter.setNewsListEntityList(newsInfoList);
    }

    @Override
    public void onFindInfoList(List<FindInfoBean> findInfoList) {
        mHomeFindInfoListAdapter.setFindInfoList(findInfoList);
    }

    @Override
    public void onModifyResult(String result) {
        if (result != null) {
            ToastUtils.showToast(mFragmentActivity, result);
        }
        mHomeFindInfoListAdapter.notifyItemChanged(mRecordPosition);
        mRecordPosition = -1;
    }

    @Override
    public void onNotifyListChangeResult(String result, int position) {
        if (result != null) {
            ToastUtils.showToast(mFragmentActivity, result);
        }

        if (position != -1) {
            mHomeFindInfoListAdapter.notifyItemChanged(position);
        } else {
            mHomeFindInfoListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onFindDel() {
        getPresenter().delFind(mDynamicIdByIndex);
    }

    @Override
    public void onShareCountResult() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onFindDelResult(ResultMsgBean result) {
        mHomeFindInfoListAdapter.notifyItemChanged(mRecordPosition);
        ToastUtils.showToast(mFragmentActivity, result.getResult());
        mRecordPosition = -1;
    }

    @Subscribe
    public void onShareResult(BaseResp resp) {
        getPresenter().setFindShareCount(mDynamicIdByIndex);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_city:
//                HomeSearchCityAct.startHomeSearchCityAct(mFragmentActivity);
                break;
            case R.id.iv_msg:
                getPresenter().checkUserIsLogin(view.getId());
                break;
            case R.id.tv_home_page_list_load_more_course:
                mOnChangeBottomListener.onBottomNavIndex(R.id.nav_course);
                break;
            case R.id.tv_home_page_list_load_more_goods:
                intent = new Intent(mFragmentActivity, StoreInfoAct.class);
                startActivity(intent);
                break;
            case R.id.tv_home_page_list_load_more_news:
                mOnChangeBottomListener.onBottomNavIndex(R.id.nav_news);
                break;
            case R.id.tv_home_page_list_load_more_find:
                mOnChangeBottomListener.onBottomNavIndex(R.id.nav_find);
                break;
            case R.id.fl_search:
                intent = new Intent(mFragmentActivity, HomeSearchAct.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(int position, Object entity, View view) {
        Intent intent;
        FindInfoBean findBean = null;

        if (entity instanceof FindInfoBean) {
            findBean = (FindInfoBean) entity;
        }

        if (entity == null) {
            switch (position) {
                case 0:
                    intent = new Intent(mFragmentActivity, MerchantAct.class);
                    intent.putExtra(MerchantAct.CITY_NAME, mTvCity.getText().toString());
                    startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(mFragmentActivity, ActionInfoAct.class);
                    startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(mFragmentActivity, StoreInfoAct.class);
                    startActivity(intent);
                    break;
                case 3:
                    getPresenter().checkUserIsLogin(view.getId());
                    break;
            }
            return;
        }

        switch (view.getId()) {

            case R.id.item_news_view_id:
                NewsInfoBean newsInfoBean = null;
                if (entity instanceof NewsInfoBean) {
                    newsInfoBean = (NewsInfoBean) entity;
                }
                if (newsInfoBean == null) break;
                NewsDetailsAct.startNewsDetailsAct(mFragmentActivity, newsInfoBean);
                break;
            case R.id.ll_item_find_praise:
                getPresenter().setLike(findBean.getDynamicId(), position);
                break;
            case R.id.ll_item_find_comment:
            case R.id.tv_item_find_txt:
                mRecordPosition = position;
                GSYVideoManager.releaseAllVideos();
                intent = new Intent(mFragmentActivity, FindDetailsAct.class);
                intent.putExtra(FindDetailsFrag.FIND_ID, findBean.getDynamicId());
                startActivityForResult(intent, REQUEST_FIND_DETAILS_CODE);
                break;
            case R.id.ll_item_find_share:
                if (!EventBus.getDefault().isRegistered(this)) {
                    EventBus.getDefault().register(this);
                }
                mRecordPosition = position;
                mDynamicIdByIndex = findBean.getDynamicId();
                getPresenter().startShareFind(this, findBean);
                break;
            case R.id.tv_item_find_attention:
                getPresenter().setAttentionUser(findBean.getUserId());
                break;
            case R.id.iv_item_find_avatar:
                intent = new Intent(mFragmentActivity, UserInfoHomePageAct.class);
                int userId = findBean.getIsOwn() == 0 ? findBean.getUserId() : -1;
                intent.putExtra(UserInfoHomePageAct.EXTRA_USER_ID, userId);
                startActivityForResult(intent, REQUEST_FIND_DETAILS_CODE);
                break;
            case R.id.ll_item_course_content:
                if (entity instanceof CourseInfoBean) {
                    CourseInfoBean info = (CourseInfoBean) entity;
                    intent = new Intent(mFragmentActivity, CourseInfoAct.class);
                    intent.putExtra(CourseInfoAct.EXTRA_COURSE_ID, info.getCourseId());
                    startActivity(intent);
                }
                break;
            case R.id.card_view_goods_info:
                if (entity instanceof GoodsInfoBean) {
                    GoodsInfoBean info = (GoodsInfoBean) entity;
                    intent = new Intent(mFragmentActivity, StoreInfoAct.class);
                    intent.putExtra(StoreInfoAct.PRODUCT_ID, info.getProductId());
                    startActivity(intent);
                }
                break;
            case R.id.iv_item_find_pic:
                //查看大图
                PicImagePreviewAct.startPreview(mFragmentActivity, findBean.getDynamicPic(), position);
                break;
        }
    }

    @Override
    public void onItemClick(int groupPosition, int childPosition, ArrayList<FindImageBean> object, View view) {
        switch (view.getId()) {
            case R.id.iv_find_pic:
                //查看大图
                PicImagePreviewAct.startPreview(mFragmentActivity, object, childPosition);
                break;
        }
    }

    @Override
    protected void userStartAction(int viewId) {
        Intent intent;
        switch (viewId) {
            case R.id.iv_msg:
                MsgManagerAct.startMsgManagerAct(mFragmentActivity);
                break;
            case R.id.home_nav_button:
                intent = new Intent(mFragmentActivity, FitRecordAct.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public boolean onBackPressedSupport() {
        if (GSYVideoManager.backFromWindowFull(mFragmentActivity)) {
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            GSYVideoManager.onPause();
            mBanner.stopAutoPlay();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FIND_DETAILS_CODE && resultCode == RESULT_OK && data != null) {
            ModifyRecordBean record = data.getParcelableExtra(FindDetailsFrag.EXTRA_MODIFY_RESULT);
            getPresenter().modifyRecord(record, mRecordPosition);
        }
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (null != location && location.getLocType() != BDLocation.TypeServerError) {

                if (mTvCity != null) {

                    String city = location.getCity().replace("市", "");
                    mTvCity.setText(city);
                }

                if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    ToastUtils.showToast(mFragmentActivity, "网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    ToastUtils.showToast(mFragmentActivity, "无法获取有效定位依据，定位失败，请检查运营商网络或者WiFi网络是否正常开启，尝试重新请求定位");
                }
            }
        }

    };
}
