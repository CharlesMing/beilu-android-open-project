package com.scj.beilu.app.ui.merchant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mx.pro.lib.mvp.network.config.ShowConfig;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.merchant.MerchantAlbumPre;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoCoachPicInfoBean;
import com.scj.beilu.app.ui.preview.PicImagePreviewAct;
import com.scj.beilu.app.ui.merchant.adapter.MerchantCoachAlbumListAdapter;
import com.scj.beilu.app.util.ToastUtils;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/4/16 17:23
 */
public class MerchantCoachAlbumListFrag extends BaseMvpFragment<MerchantAlbumPre.MerchantAlbumView, MerchantAlbumPre>
        implements MerchantAlbumPre.MerchantAlbumView, OnItemClickListener<MerchantInfoCoachPicInfoBean> {

    private ViewStub mLoading;
    private ViewStub mViewEmpty;
    private LinearLayout mLLEmpty;
    private TextView mTvEmptyHint;

    private MerchantCoachAlbumListAdapter mMerchantCoachAlbumListAdapter;

    private static final String MERCHANT_ID = "merchant_id";
    private static final String COACH_ID = "coach_id";
    private int mMerchantId;
    private int mCoachId;
    private final ArrayList<MerchantInfoCoachPicInfoBean> mAlbumList = new ArrayList<>();

    public static MerchantCoachAlbumListFrag newInstance(int merchantId, int coachId) {
        Bundle args = new Bundle();
        args.putInt(MERCHANT_ID, merchantId);
        args.putInt(COACH_ID, coachId);
        MerchantCoachAlbumListFrag fragment = new MerchantCoachAlbumListFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mMerchantId = arguments.getInt(MERCHANT_ID);
            mCoachId = arguments.getInt(COACH_ID);
        }
    }

    @Override
    public MerchantAlbumPre createPresenter() {
        return new MerchantAlbumPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_merchant_coach_album_list;
    }

    @Override
    public void initView() {
        super.initView();

        mLLEmpty = findViewById(R.id.ll_load_empty_view);

        mMerchantCoachAlbumListAdapter = new MerchantCoachAlbumListAdapter(this);
        mMerchantCoachAlbumListAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mMerchantCoachAlbumListAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getAlbumList(mMerchantId, mCoachId);
    }

    @Override
    public void onAlbumList(ArrayList<MerchantInfoCoachPicInfoBean> albumList) {
        mAlbumList.clear();
        if (albumList.size() == 0) {

            mRecyclerView.setVisibility(View.GONE);
            if (mViewEmpty == null) {
                mViewEmpty = findViewById(R.id.view_stu_empty);
                View inflate = mViewEmpty.inflate();
                TextView tvEmptyHint = inflate.findViewById(R.id.tv_empty_hint);
                tvEmptyHint.setText("暂无数据哦～");
            }
            mViewEmpty.setVisibility(View.VISIBLE);
        } else {
            if (mViewEmpty != null) {
                mViewEmpty.setVisibility(View.GONE);
            }
            mAlbumList.addAll(albumList);
            mRecyclerView.setVisibility(View.VISIBLE);
            mMerchantCoachAlbumListAdapter.setPicInfoBeans(mAlbumList);
        }
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
    public void showError(int errorCode, String throwableContent) {
        if (mLoading != null) {
            mLoading.setVisibility(View.GONE);
        }
        ToastUtils.showToast(mFragmentActivity, throwableContent);
    }

    @Override
    public void onItemClick(int position, MerchantInfoCoachPicInfoBean entity, View view) {
        //查看大图
        PicImagePreviewAct.startPreview(mFragmentActivity, mAlbumList, position);
    }
}
