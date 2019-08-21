package com.scj.beilu.app.ui.merchant;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.common.bean.PicPreviewBean;
import com.scj.beilu.app.mvp.merchant.MerchantPicPreviewManagerPre;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicInfoBean;
import com.scj.beilu.app.mvp.merchant.bean.MerchantPicTypeBean;
import com.scj.beilu.app.ui.merchant.adapter.MerchantPicPreviewManagerListAdapter;
import com.scj.beilu.app.ui.preview.adapter.PicPreviewPagerAdapter;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/4/13 18:26
 */
public class MerchantPhotoPreviewManagerFrag extends BaseMvpFragment<MerchantPicPreviewManagerPre.MerchantPicPreViewManagerView, MerchantPicPreviewManagerPre>
        implements MerchantPicPreviewManagerPre.MerchantPicPreViewManagerView, OnItemClickListener, ViewPager.OnPageChangeListener {

    private ViewPager view_pager_merchant_photo_preview;

    private int mMerchantId;
    private int mParentPosition;
    private int mChildPosition;
    private ArrayList<PicPreviewBean> mImagePathList;
    private MerchantPicPreviewManagerListAdapter mMerchantPicPreviewManagerListAdapter;
    private PicPreviewPagerAdapter mPicPreviewPagerAdapter;

    public static MerchantPhotoPreviewManagerFrag newInstance(int parentPosition,
                                                              int childPosition,
                                                              ArrayList<? extends PicPreviewBean> imageList,
                                                              int merchantId) {
        Bundle args = new Bundle();
        args.putInt(MerchantPicPreviewAct.PARENT_POSITION, parentPosition);
        args.putInt(MerchantPicPreviewAct.CHILD_POSITION, childPosition);
        args.putParcelableArrayList(MerchantPicPreviewAct.EXTRA_DATA, imageList);
        args.putInt(MerchantPicPreviewAct.MERCHANT_ID, merchantId);
        MerchantPhotoPreviewManagerFrag fragment = new MerchantPhotoPreviewManagerFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mParentPosition = arguments.getInt(MerchantPicPreviewAct.PARENT_POSITION);
            mChildPosition = arguments.getInt(MerchantPicPreviewAct.CHILD_POSITION);
            mImagePathList = arguments.getParcelableArrayList(MerchantPicPreviewAct.EXTRA_DATA);
            mMerchantId = arguments.getInt(MerchantPicPreviewAct.MERCHANT_ID);
        }
    }

    @Override
    public MerchantPicPreviewManagerPre createPresenter() {
        return new MerchantPicPreviewManagerPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_merchant_photo_preview;
    }

    @Override
    public void initView() {
        super.initView();
        ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
                .init();
        mAppToolbar = findViewById(R.id.merchant_toolbar);
        view_pager_merchant_photo_preview = findViewById(R.id.view_pager_merchant_photo_preview);

        mMerchantPicPreviewManagerListAdapter = new MerchantPicPreviewManagerListAdapter();
        mMerchantPicPreviewManagerListAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mMerchantPicPreviewManagerListAdapter);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(5, 0, 5, 0);
            }
        });

        mPicPreviewPagerAdapter = new PicPreviewPagerAdapter(getChildFragmentManager());
        mPicPreviewPagerAdapter.setImagePathList(mImagePathList);
        view_pager_merchant_photo_preview.setAdapter(mPicPreviewPagerAdapter);
        view_pager_merchant_photo_preview.setCurrentItem(mChildPosition, true);

        view_pager_merchant_photo_preview.addOnPageChangeListener(this);

        setTitle(mChildPosition);

        mAppToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToolbarNavClick();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getMerchantPicTypeList(mParentPosition, true);
    }

    @Override
    public void onTypeList(ArrayList<MerchantPicTypeBean> typeList) {
        mMerchantPicPreviewManagerListAdapter.setMerchantPicTypeBeans(typeList);
        mRecyclerView.smoothScrollToPosition(mParentPosition);
    }

    @Override
    public void onSetSelectResult() {
        mMerchantPicPreviewManagerListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPicListResult(ArrayList<MerchantPicInfoBean> picInfoList) {
        mImagePathList.clear();
        mImagePathList.addAll(picInfoList);
        mPicPreviewPagerAdapter.setImagePathList(mImagePathList);
        setTitle(0);
    }

    @Override
    public void onItemClick(int position, Object entity, View view) {

        if (entity instanceof MerchantPicTypeBean) {
            getPresenter().getMerchantPicTypeList(position, false);
            MerchantPicTypeBean picTypeBean = (MerchantPicTypeBean) entity;
            getPresenter().getPicList(mMerchantId, picTypeBean.getRegionId());
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setTitle(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setTitle(int position) {
        String title = (position + 1) + "/" + mImagePathList.size();
        mAppToolbar.setToolbarTitle(title);
    }
}
