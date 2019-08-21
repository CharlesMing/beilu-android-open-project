package com.scj.beilu.app.ui.merchant;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;

import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.merchant.MerchantPicListPre;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicInfoBean;
import com.scj.beilu.app.ui.merchant.adapter.MerchantPhotoListAdapter;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/4/13 18:26
 */
public class MerchantPhotoListFrag extends BaseMvpFragment<MerchantPicListPre.MerchantPicListView, MerchantPicListPre>
        implements MerchantPicListPre.MerchantPicListView, OnItemClickListener<MerchantPicInfoBean> {

    private ViewStub mLoading;

    private static final String MERCHANT_ID = "merchant_id";
    private static final String REGION_ID = "regionId";
    private static final String POSITION = "position";
    private int mMerchantId;
    private int mRegionId;
    private int mParentPosition;

    private MerchantPhotoListAdapter mMerchantPhotoListAdapter;
    private final ArrayList<MerchantPicInfoBean> mPicInfoList = new ArrayList<>();

    public static MerchantPhotoListFrag newInstance(int merchantId, int regionId, int position) {

        Bundle args = new Bundle();
        args.putInt(MERCHANT_ID, merchantId);
        args.putInt(REGION_ID, regionId);
        args.putInt(POSITION, position);
        MerchantPhotoListFrag fragment = new MerchantPhotoListFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mMerchantId = arguments.getInt(MERCHANT_ID);
            mRegionId = arguments.getInt(REGION_ID);
            mParentPosition = arguments.getInt(POSITION);
        }
    }

    @Override
    public MerchantPicListPre createPresenter() {
        return new MerchantPicListPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_merchant_photo_list;
    }

    @Override
    public void initView() {
        super.initView();

        mMerchantPhotoListAdapter = new MerchantPhotoListAdapter(this);
        mMerchantPhotoListAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mMerchantPhotoListAdapter);

        final int rectSize = getResources().getDimensionPixelOffset(R.dimen.hor_divider_size_7);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(rectSize, rectSize, rectSize, rectSize);
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getPicList(mMerchantId, mRegionId);
    }

    @Override
    public void showLoading(int loading, boolean isShow) {
        if (loading == ShowConfig.LOADING_STATE) {
            if (isShow) {
                mRecyclerView.setVisibility(View.GONE);
                if (mLoading == null) {
                    mLoading = findViewById(R.id.view_stu_loading);
                    mLoading.inflate();
                }
                mLoading.setVisibility(View.VISIBLE);
            } else {
                if (mLoading != null) {
                    mLoading.setVisibility(View.GONE);
                }
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onPicListResult(ArrayList<MerchantPicInfoBean> picInfoList) {
        mPicInfoList.clear();
        mPicInfoList.addAll(picInfoList);
        mMerchantPhotoListAdapter.setPicInfoBeans(mPicInfoList);
    }

    @Override
    public void onItemClick(int position, MerchantPicInfoBean entity, View view) {
        //传递当前分类数组
        //当前分类数组下标，用于默认选中
        //当前图片数组
        //当前点击图片下标，图片显示定位
        Intent intent = new Intent(mFragmentActivity, MerchantPicPreviewAct.class);
        intent.putExtra(MerchantPicPreviewAct.PARENT_POSITION, mParentPosition);
        intent.putExtra(MerchantPicPreviewAct.CHILD_POSITION, position);
        intent.putExtra(MerchantPicPreviewAct.EXTRA_DATA, mPicInfoList);
        intent.putExtra(MerchantPicPreviewAct.MERCHANT_ID, mMerchantId);
        startActivity(intent);
    }
}
