package com.scj.beilu.app.ui.merchant;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.location.LoctionNavigationUtil;
import com.scj.beilu.app.mvp.merchant.MerchantInfoPre;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoCoachBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoDetailsListBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoMemberShipListBean;
import com.scj.beilu.app.ui.merchant.adapter.MerchantInfoDetailListAdapter;
import com.scj.beilu.app.util.ToastUtils;
import com.scj.beilu.app.widget.DividerDecoration;

import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author Mingxun
 * @time on 2019/4/13 18:23
 */
@RuntimePermissions
public class MerchantDetailFrag extends BaseMvpFragment<MerchantInfoPre.MerchantInfoView, MerchantInfoPre>
        implements MerchantInfoPre.MerchantInfoView, NestedScrollView.OnScrollChangeListener, View.OnClickListener, OnItemClickListener {

    private static final String MERCHANT_ID = "merchant_id";
    private int mMerchantId;

    private AppBarLayout mAppBarLayout;
    private NestedScrollView mNestedScrollView;
    private ImageView mIvMerchantImg;
    private LinearLayout mLlMerchantImgCount;
    private TextView mTvMerchantImgCount, mTvMerchantName,
            mTvMerchantAreaTime,
            mTvMerchantContact, mTvMerchantStartNav,
            mTvMerchantAddress,
            mTvMerchantDescription;
    private RecyclerView mRvMerchantInfoList;

    private MerchantInfoDetailListAdapter mMerchantInfoDetailListAdapter;
    private int mPicHeight = 0;
    private boolean isChangeWhite = true;
    private GlideRequest<Drawable> mOriginal, mThumbnail;
    private String phoneNum;
    private String mMerchantPhone;
    private double[] mLocation;

    public static MerchantDetailFrag newInstance(int merchantId) {

        Bundle args = new Bundle();
        args.putInt(MERCHANT_ID, merchantId);
        MerchantDetailFrag fragment = new MerchantDetailFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mMerchantId = arguments.getInt(MERCHANT_ID, mMerchantId);
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getMerchantInfo(mMerchantId);
    }

    @Override
    public MerchantInfoPre createPresenter() {
        return new MerchantInfoPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_merchant_info;
    }

    @Override
    public void initView() {
        super.initView();

        ImmersionBar.with(this).statusBarView(R.id.top_view)
                .addTag("PicAndColor")
                .statusBarDarkFont(false)
                .keyboardEnable(true)
                .init();

        mLlMerchantImgCount = findViewById(R.id.ll_merchant_img);
        mAppBarLayout = findViewById(R.id.app_bar_layout_merchant);
        mNestedScrollView = findViewById(R.id.nested_scroll_view);
        mIvMerchantImg = findViewById(R.id.iv_merchant_img);
        mTvMerchantImgCount = findViewById(R.id.tv_merchant_img_count);
        mTvMerchantName = findViewById(R.id.tv_merchant_name);
        mTvMerchantAreaTime = findViewById(R.id.tv_merchant_area_time);
        mTvMerchantContact = findViewById(R.id.tv_merchant_contact);
        mTvMerchantStartNav = findViewById(R.id.tv_merchant_start_nav);
        mTvMerchantAddress = findViewById(R.id.tv_merchant_address);
        mTvMerchantDescription = findViewById(R.id.tv_merchant_description);
        mRvMerchantInfoList = findViewById(R.id.rv_merchant_info_list);

        try {
            mPicHeight = getResources().getDimensionPixelOffset(R.dimen.merchant_pic_height);
            final int actionBarSize = getResources().getDimensionPixelOffset(R.dimen.abc_action_bar_default_height_material);
            mPicHeight = mPicHeight - actionBarSize - ImmersionBar.getStatusBarHeight(mFragmentActivity);
            mNestedScrollView.setOnScrollChangeListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ViewCompat.setElevation(mAppBarLayout, 0);

        mLlMerchantImgCount.setOnClickListener(this);
        mTvMerchantContact.setOnClickListener(this);
        mTvMerchantStartNav.setOnClickListener(this);

        mMerchantInfoDetailListAdapter = new MerchantInfoDetailListAdapter(this);
        mMerchantInfoDetailListAdapter.setOnItemClickListener(this);
        mRvMerchantInfoList.setAdapter(mMerchantInfoDetailListAdapter);

        mRvMerchantInfoList.addItemDecoration(new DividerDecoration(mFragmentActivity, DividerItemDecoration.VERTICAL, getResources().getDimensionPixelOffset(R.dimen.padding_20)));
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY >= mPicHeight) {
            if (isChangeWhite) {
                mFragmentActivity.runOnUiThread(() -> {
                    ImmersionBar.with(MerchantDetailFrag.this)
                            .statusBarDarkFont(true,0.2f)
                            .init();
                    isChangeWhite = false;
                    mAppToolbar.setNavigationIcon(R.drawable.ic_back);
                    mAppBarLayout.setBackgroundColor(ContextCompat.getColor(mFragmentActivity, android.R.color.white));
                    ViewCompat.setElevation(mAppBarLayout, 10);
                });

            }
        } else {
            if (!isChangeWhite) {
                mFragmentActivity.runOnUiThread(() -> {
                    ImmersionBar.with(MerchantDetailFrag.this).statusBarDarkFont(false)
                            .init();
                    isChangeWhite = true;
                    mAppToolbar.setNavigationIcon(R.drawable.ic_back_white);
                    mAppBarLayout.setBackgroundColor(ContextCompat.getColor(mFragmentActivity, android.R.color.transparent));
                    ViewCompat.setElevation(mAppBarLayout, 0);
                });
            }
        }
    }

    @Override
    public void onMerchantInfo(MerchantInfoBean merchantInfo) {
        try {
            if (mOriginal == null) {
                mOriginal = GlideApp.with(this).asDrawable().optionalCenterCrop();
                mThumbnail = GlideApp.with(this).asDrawable().optionalCenterCrop();
            }

            mOriginal.load(merchantInfo.getMerchantPicAddr())
                    .thumbnail(mThumbnail.load(merchantInfo.getMerchantPicAddrZip()))
                    .into(mIvMerchantImg);
            mTvMerchantImgCount.setText(String.valueOf(merchantInfo.getCountMerchantAlbum()));
            mTvMerchantName.setText(merchantInfo.getMerchantName());
            String areaTime = getResources().getString(R.string.txt_merchant_area_time);
            String area = merchantInfo.getMerchantArea();
            area = (area == null) ? "无" : area + "㎡";
            String openShop = merchantInfo.getMerchantOpenShop();
            String closeShop = merchantInfo.getMerchantCloseShop();
            openShop = (openShop == null) ? "00:00" : openShop;
            closeShop = (closeShop == null) ? "00:00" : closeShop;
            mTvMerchantAreaTime.setText(String.format(areaTime, area, openShop, closeShop));
            String description = merchantInfo.getMerchantDec();
            description = (description == null) ? "无" : description;
            mTvMerchantAddress.setText(merchantInfo.getMerchantAddr());
            mTvMerchantDescription.setText(description);

            mMerchantPhone = merchantInfo.getMerchantTel();

            mLocation = new double[]{Double.parseDouble(merchantInfo.getMerchantLatitude()), Double.parseDouble(merchantInfo.getMerchantLongitude())};
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMerchantInfoList(List<MerchantInfoDetailsListBean> detailInfo) {
        mMerchantInfoDetailListAdapter.setDetailsList(detailInfo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_merchant_img:
                start(MerchantPhotoManagerFrag.newInstance(mMerchantId));
                break;
            case R.id.tv_merchant_contact:
                phoneNum = mMerchantPhone;
                MerchantDetailFragPermissionsDispatcher.needsCallPhoneWithPermissionCheck(this);
                break;
            case R.id.tv_merchant_start_nav:
                LoctionNavigationUtil.gaodeGuide(mFragmentActivity, mLocation);
                break;
        }
    }

    @Override
    public void onItemClick(int position, Object entity, View view) {
        if (entity instanceof MerchantInfoCoachBean) {
            MerchantInfoCoachBean coachInfo = (MerchantInfoCoachBean) entity;
            start(MerchantCoachInfoFrag.newInstance(coachInfo.getMerchantId(), coachInfo.getCoachId()));
        } else if ((entity instanceof MerchantInfoMemberShipListBean)) {
            //call phone
            MerchantInfoMemberShipListBean info = (MerchantInfoMemberShipListBean) entity;
            phoneNum = info.getMemberShipTel();
            MerchantDetailFragPermissionsDispatcher.needsCallPhoneWithPermissionCheck(this);
        }
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void needsCallPhone() {
        if (phoneNum != null) {
            new AlertDialog.Builder(mFragmentActivity)
                    .setPositiveButton("呼叫", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            Uri data = Uri.parse("tel:" + phoneNum);
                            intent.setData(data);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.button_cancel, (dialog, which) -> dialog.cancel())
                    .setCancelable(false)
                    .setMessage(phoneNum)
                    .show();
        } else {
            ToastUtils.showToast(mFragmentActivity, "暂时无法拨打电话～");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MerchantDetailFragPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.CALL_PHONE)
    void rationaleCallPhone(final PermissionRequest request) {
        showRationaleDialog(R.string.permission_call_phone, request);
    }
}
