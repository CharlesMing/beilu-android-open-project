package com.scj.beilu.app.ui.preview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.album.listener.OnFragmentInteractionListener;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpActivity;
import com.scj.beilu.app.mvp.common.PicImagePreviewPre;
import com.scj.beilu.app.mvp.common.bean.PicPreviewBean;
import com.scj.beilu.app.ui.act.adapter.ImageSelectedListAdapter;
import com.scj.beilu.app.ui.preview.adapter.PicPreviewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/26 15:13
 */
public class PicImagePreviewAct extends BaseMvpActivity<PicImagePreviewPre.PicImagePreviewView, PicImagePreviewPre>
        implements PicImagePreviewPre.PicImagePreviewView, OnFragmentInteractionListener {

    private ViewPager mViewPager;
    private RecyclerView mRvImgSelected;
    private ImageButton mBtnDownload;

    private ArrayList<PicPreviewBean> mImagePathList;
    private int mPosition = -1;

    private ImageSelectedListAdapter mImageSelectedListAdapter;//用于显示底部圆点
    private PicPreviewPagerAdapter mPicPreviewPagerAdapter;//显示ImageView

    public static final String EXTRA_IMAGE_LIST = "image_list";
    public static final String EXTRA_IMAGE_POSITION = "images_position";


    public static void startPreview(Activity activity, ArrayList<? extends PicPreviewBean> imageList, int position) {
        Intent intent = new Intent(activity, PicImagePreviewAct.class);
        intent.putParcelableArrayListExtra(PicImagePreviewAct.EXTRA_IMAGE_LIST, imageList);
        intent.putExtra(PicImagePreviewAct.EXTRA_IMAGE_POSITION, position);
        activity.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.act_pic_preview;
    }

    @Override
    protected void initView() {
        super.initView();
        ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
                .init();

        Intent intent = getIntent();
        if (intent == null) return;

        mViewPager = findViewById(R.id.view_pager);
        mRvImgSelected = findViewById(R.id.rv_image_selected_list);

        mBtnDownload = findViewById(R.id.iv_download_pic);

        final int size = getResources().getDimensionPixelSize(R.dimen.rect_5);

        mRvImgSelected.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(size, 0, size, 0);
            }
        });

        mImageSelectedListAdapter = new ImageSelectedListAdapter();
        mRvImgSelected.setAdapter(mImageSelectedListAdapter);

        mPicPreviewPagerAdapter = new PicPreviewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPicPreviewPagerAdapter);

        mImagePathList = intent.getParcelableArrayListExtra(EXTRA_IMAGE_LIST);
        mPosition = intent.getIntExtra(EXTRA_IMAGE_POSITION, mPosition);
        mPicPreviewPagerAdapter.setImagePathList(mImagePathList);
        getPresenter().initSelectorList(mImagePathList);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getPresenter().setSelectedByPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(mPosition, true);

        mBtnDownload.setOnClickListener(v -> getPresenter().downloadImg(mImagePathList.get(mViewPager.getCurrentItem()).getOriginalPicPath()));
    }

    @NonNull
    @Override
    public PicImagePreviewPre createPresenter() {
        return new PicImagePreviewPre(this);
    }

    @Override
    public void onImgSelectedListResult(List<Boolean> imageSelectorList) {
        mImageSelectedListAdapter.setImageSelectedList(imageSelectorList);
        getPresenter().setSelectedByPosition(mPosition);
    }

    @Override
    public void onNotify(int position) {
        mImageSelectedListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick() {
        onBackPressedSupport();
    }
}
