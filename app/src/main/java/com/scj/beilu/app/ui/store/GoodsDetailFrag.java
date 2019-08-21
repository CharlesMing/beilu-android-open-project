package com.scj.beilu.app.ui.store;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.store.GoodsDetailPre;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoBean;
import com.scj.beilu.app.ui.act.LoginAndRegisterAct;
import com.scj.beilu.app.ui.order.OrderInfoConfirmationFrag;
import com.scj.beilu.app.ui.store.adapter.GoodsImgAdapter;
import com.scj.beilu.app.util.ToastUtils;
import com.scj.beilu.app.widget.DragScrollDetailsLayout;
import com.scj.beilu.app.widget.MyWebView;

import static com.scj.beilu.app.widget.DragScrollDetailsLayout.CurrentTargetIndex.DOWNSTAIRS;

/**
 * @author Mingxun
 * @time on 2019/3/28 13:36
 */
public class GoodsDetailFrag extends BaseMvpFragment<GoodsDetailPre.GoodsDetailView, GoodsDetailPre>
        implements GoodsDetailPre.GoodsDetailView, DragScrollDetailsLayout.OnSlideFinishListener,
        ViewPager.OnPageChangeListener, View.OnClickListener, GoodsSpecificationDialog.onAddCartListener {

    private DragScrollDetailsLayout mDragScrollDetailsLayout;
    private ViewPager mViewPagerImgDetail;
    private TextView mTvImgNum;
    private TextView mTvGoodsName, mTvGoodsDescription, mTvGoodsDiscountPrice,
            mTvGoodsSaleNum, mTvOriginalPrice;
    private TextView mTvGoodsCartCount;
    private MyWebView mWebImgTxtContent;
    private FrameLayout mFlWebContent;
    private View mCollectView;
    private FrameLayout mFlShoppingCar;
    private TextView mTvAddCar, mTvGoodsBuy;
    private GoodsInfoBean mGoodsInfo;

    private final static String GOODS_ID = "goods_id";

    private int mProductId;
    private String mRMB;
    private int mImgSize;
    private double mDiscountPrice;
    private int mGoodsNum = 1;
    private GoodsSpecificationDialog mGoodsSpecificationDialog;
    private String mPic1, mPic2;
    private boolean mCollectStatus;

    public static GoodsDetailFrag newInstance(int productId) {

        Bundle args = new Bundle();
        args.putInt(GOODS_ID, productId);
        GoodsDetailFrag fragment = new GoodsDetailFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mProductId = getArguments().getInt(GOODS_ID);
        }
    }

    @Override
    public GoodsDetailPre createPresenter() {
        return new GoodsDetailPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_goods_detail;
    }

    @Override
    public void initView() {
        super.initView();

        mDragScrollDetailsLayout = findViewById(R.id.scroll_detail_layout_id);
        mViewPagerImgDetail = findViewById(R.id.view_pager_goods_detail_img);
        mTvImgNum = findViewById(R.id.tv_goods_img_num);
        mTvGoodsName = findViewById(R.id.tv_goods_name);
        mTvGoodsDescription = findViewById(R.id.tv_goods_description);
        mTvGoodsDiscountPrice = findViewById(R.id.tv_goods_discount_price);
        mTvGoodsSaleNum = findViewById(R.id.tv_goods_sale_num);
        mTvOriginalPrice = findViewById(R.id.tv_goods_original_price);
        mWebImgTxtContent = findViewById(R.id.web_view_goods_detail);
        mFlWebContent = findViewById(R.id.fl_web_content);
        mFlShoppingCar = findViewById(R.id.fl_goods_shopping_car);
        mTvAddCar = findViewById(R.id.tv_goods_detail_add_car);
        mTvGoodsBuy = findViewById(R.id.tv_goods_detail_buy);
        mTvGoodsCartCount = findViewById(R.id.tv_store_shopping_car_count);

        mDragScrollDetailsLayout.setOnSlideDetailsListener(this);

        getPresenter().getCartCount();
        getPresenter().getGoodsInfoById(mProductId);

        if (isAdded()) {
            mRMB = getResources().getString(R.string.txt_rmb);
        }

        mViewPagerImgDetail.addOnPageChangeListener(this);
        mFlShoppingCar.setOnClickListener(this);
        mTvAddCar.setOnClickListener(this);
        mTvGoodsBuy.setOnClickListener(this);
    }

    @Override
    public void onStatueChanged(DragScrollDetailsLayout.CurrentTargetIndex status) {
//        mAppToolbar.getMenu().clear();

        int toolbarColor;
        if (status == DOWNSTAIRS) {
            toolbarColor = ContextCompat.getColor(mFragmentActivity, android.R.color.white);
            mAppToolbar.setNavigationIcon(R.drawable.ic_back);
            mAppToolbar.setToolbarTitle("图文详情");
            if (mCollectView != null) {
                mCollectView.setVisibility(View.GONE);
            }
        } else {
            toolbarColor = ContextCompat.getColor(mFragmentActivity, android.R.color.transparent);
            mAppToolbar.setNavigationIcon(R.drawable.ic_goods_detail_back);
            mAppToolbar.setToolbarTitle("");
            if (mCollectView != null) {
                mCollectView.setVisibility(View.VISIBLE);
            }

        }
        mAppToolbar.setBackgroundColor(toolbarColor);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        position = position + 1;
        setImgNum(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onGoodsInfoResult(GoodsInfoBean goodsInfo) {
        try {

            mPic1 = goodsInfo.getProductPicOriginalAddr();
            mPic2 = goodsInfo.getProductPicCompressionAddr();
            mDiscountPrice = goodsInfo.getProductDiscountPrice();
            mGoodsInfo = goodsInfo;
            GoodsImgAdapter goodsImgAdapter = new GoodsImgAdapter(this);
            goodsImgAdapter.setImgList(goodsInfo.getProductPic());
            mViewPagerImgDetail.setAdapter(goodsImgAdapter);

            mTvGoodsName.setText(goodsInfo.getProductName());
            mTvGoodsDescription.setText(goodsInfo.getProductSynopsis());
//            String.format(mRMB, mDiscountPrice)
            mTvGoodsDiscountPrice.setText(String.valueOf(mDiscountPrice));
            mTvGoodsSaleNum.setText(String.format(getString(R.string.txt_sales_num), goodsInfo.getProductSalesVolume()));
            mTvOriginalPrice.setText(String.format(mRMB, goodsInfo.getProductOriginalPrice()));
            mTvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            mWebImgTxtContent.loadDataWithBaseURL(null, goodsInfo.getProductDec(), "text/html", "utf-8", null);
            mImgSize = goodsInfo.getProductPic().size();
            setImgNum(1);
            mFlWebContent.setPadding(0, mAppToolbar.getHeight(), 0, 0);

            if (mCollectView == null) {
                mCollectView = mAppToolbar.findViewById(R.id.menu_collect);
            }
            mCollectStatus = goodsInfo.getIsCollection() == 1;
            mCollectView.setSelected(mCollectStatus);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setImgNum(int position) {
        if (mImgSize > 1) {
            String num = position + "/" + mImgSize;
            mTvImgNum.setText(num);
        } else {
            mTvImgNum.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCollectResult(ResultMsgBean result) {
        ToastUtils.showToast(mFragmentActivity, result.getResult());
        mFragmentActivity.setResult(Activity.RESULT_OK);
        if (mCollectView != null) {
            mCollectStatus = !mCollectView.isSelected();
            mCollectView.setSelected(mCollectStatus);
        }
    }

    @Override
    public void onCartCount(int count) {
        mTvGoodsCartCount.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
        mTvGoodsCartCount.setText(String.valueOf(count));

        if (mGoodsSpecificationDialog != null) {
            mGoodsSpecificationDialog.dismiss();
            setFragmentResult(RESULT_OK, null);
            ToastUtils.showToast(mFragmentActivity, "已添加至购物车中");
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menu_collect) {
            getPresenter().setCollect(mProductId);
        }
        return super.onMenuItemClick(item);
    }


    @Override
    public void onClick(View v) {
        getPresenter().checkUserIsLogin(v.getId());
    }

    @Override
    protected void userStartAction(int viewId) {
        switch (viewId) {
            case R.id.fl_goods_shopping_car:
                break;
            case R.id.tv_goods_detail_add_car:
            case R.id.tv_goods_detail_buy:
                setGoodsSpecification();
                break;
        }
    }

    private void setGoodsSpecification() {
        if (mGoodsSpecificationDialog != null) {
            mGoodsSpecificationDialog = null;
        }
        mGoodsSpecificationDialog = GoodsSpecificationDialog.newInstance(mProductId, String.valueOf(mDiscountPrice), mGoodsNum, mPic1, mPic2);
        mGoodsSpecificationDialog.setOnAddCartListener(this);
        mGoodsSpecificationDialog.show(getChildFragmentManager(), mGoodsSpecificationDialog.getClass().getName());
    }

    @Override
    public void onEditCart(int goodsNum, String specification) {

        mGoodsNum = goodsNum;
        mGoodsSpecificationDialog.dismiss();
        mGoodsInfo.setGoodsCount(goodsNum);
        mGoodsInfo.setGoodsSpecification(specification);
        start(OrderInfoConfirmationFrag.newInstance(mGoodsInfo));

        //        if (mGoodsInfo != null) {
        //            getPresenter().addToCart(mGoodsInfo, goodsNum, specification);
        //        }
        //        Intent intent = new Intent(mFragmentActivity, OrderInfoAct.class);
        //        intent.putExtra(OrderInfoAct.PAY_PRICE, "212");
        //        intent.putExtra(OrderInfoAct.ORDER_NO, String.valueOf(mProductId));
        //        startActivity(intent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginAndRegisterAct.LOGIN_REQUEST && resultCode == LoginAndRegisterAct.LOGIN_RESULT_OK) {
            getPresenter().getGoodsInfoById(mProductId);
        }
    }
}
