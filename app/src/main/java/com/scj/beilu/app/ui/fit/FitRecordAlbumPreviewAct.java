package com.scj.beilu.app.ui.fit;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpActivity;
import com.scj.beilu.app.mvp.fit.FitRecordImgPreviewPre;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgInfoBean;
import com.scj.beilu.app.ui.preview.FitRecordAlbumPreviewFrag;
import com.scj.beilu.app.ui.preview.adapter.FitRecordAlbumPreviewPagerAdapter;
import com.scj.beilu.app.util.TimeUtil;
import com.scj.beilu.app.util.ToastUtils;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/3/15 17:35
 */
public class FitRecordAlbumPreviewAct extends BaseMvpActivity<FitRecordImgPreviewPre.FitRecordImgPreviewView, FitRecordImgPreviewPre>
        implements FitRecordAlbumPreviewFrag.onViewClickListener, FitRecordImgPreviewPre.FitRecordImgPreviewView, ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private FitRecordAlbumPreviewPagerAdapter mFitRecordAlbumPreviewPagerAdapter;
    public static final String EXTRA_IMAGE_LIST = "image_list";
    public static final String EXTRA_IMAGE_POSITION = "images_position";
    private ArrayList<FitRecordImgInfoBean> mImgInfoBeans;
    private boolean mIsToolbarHide;

    @Override
    public int getLayout() {
        return R.layout.act_fit_record_album_preview;
    }

    @Override
    protected void initView() {
        super.initView();
        mViewPager = findViewById(R.id.view_pager);
        ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
                .statusBarDarkFont(true,0.2f)
                .init();
        Intent intent = getIntent();
        if (intent == null) return;
        mImgInfoBeans = intent.getParcelableArrayListExtra(EXTRA_IMAGE_LIST);
        int position = intent.getIntExtra(EXTRA_IMAGE_POSITION, 0);
        mAppToolbar.setToolbarTitle(TimeUtil.getDate(mImgInfoBeans.get(position).getRecordDate(), "yyyy-MM-dd"));

        mFitRecordAlbumPreviewPagerAdapter = new FitRecordAlbumPreviewPagerAdapter(getSupportFragmentManager());
        mFitRecordAlbumPreviewPagerAdapter.setImagePathList(mImgInfoBeans);
        mViewPager.setAdapter(mFitRecordAlbumPreviewPagerAdapter);

        mViewPager.setCurrentItem(position, false);
        mViewPager.addOnPageChangeListener(this);
        mAppToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @NonNull
    @Override
    public FitRecordImgPreviewPre createPresenter() {
        return new FitRecordImgPreviewPre(this);
    }

    @Override
    public void onClick(int position) {
        if (mIsToolbarHide) {
            mAppToolbar.animate()
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .translationYBy(mAppToolbar.getMeasuredHeight())
                    .start();

        } else {
            mAppToolbar.animate()
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .translationYBy(-mAppToolbar.getMeasuredHeight())
                    .start();

        }

        mIsToolbarHide = !mIsToolbarHide;
    }



    @Override
    public void onDelResult(String result) {
        setResult(RESULT_OK);
        ToastUtils.showToast(this, result);
        try {
            int position = mViewPager.getCurrentItem();
            mImgInfoBeans.remove(position);
            mFitRecordAlbumPreviewPagerAdapter.notifyDataSetChanged();
            if (mFitRecordAlbumPreviewPagerAdapter.getCount() == 0) {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mAppToolbar.setToolbarTitle(TimeUtil.getDate(mImgInfoBeans.get(position).getRecordDate(), "yyyy-MM-dd"));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.button_sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        getPresenter().deleteImg(mImgInfoBeans.get(mViewPager.getCurrentItem()).getRecordPicId());
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("确定删除当前图片吗？")
                .show();
        return super.onMenuItemClick(item);
    }

}
