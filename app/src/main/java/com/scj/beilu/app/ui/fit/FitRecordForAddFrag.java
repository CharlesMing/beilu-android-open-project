package com.scj.beilu.app.ui.fit;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.album.AlbumManager;
import com.mx.pro.lib.album.GlideEngine;
import com.mx.pro.lib.album.MimeType;
import com.mx.pro.lib.album.internal.entity.CaptureStrategy;
import com.mx.pro.lib.album.utils.MediaStoreCompat;
import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.fit.FitRecordForAddPre;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgInfoBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordInfoBean;
import com.scj.beilu.app.ui.dialog.SelectPictureDialog;
import com.scj.beilu.app.ui.fit.adapter.FitRecordImgListAdapter;
import com.scj.beilu.app.util.ToastUtils;

import java.io.File;
import java.util.List;

import static com.scj.beilu.app.api.Constants.DIRECTORY;

/**
 * @author Mingxun
 * @time on 2019/3/11 12:09
 * 添加健身记录
 */
public class FitRecordForAddFrag extends BaseMvpFragment<FitRecordForAddPre.FitRecordForAddView, FitRecordForAddPre>
        implements FitRecordForAddPre.FitRecordForAddView, View.OnClickListener, SelectPictureDialog.onSelectPhoto, OnItemClickListener<FitRecordImgInfoBean> {

    private TextView mTvWeight, mTvBodyFat, mTvBoneMass;
    private ViewStub mViewMan;
    private ViewStub mViewWoman;
    private TextView mTvRecordPicture;
    private ImageView mIvRecordPictureSmall;
    private TextView mTvLeftArm, mTvRightArm, mTvBust,
            mTvWaistline, mTvHipline, mTvLeftThigh, mTvRightThigh;


    private MediaStoreCompat mMediaStoreCompat;
    private static final int REQUEST_CODE_CAPTURE = 0 * 024;
    private static final int REQUEST_CODE_ALBUM = 0x025;
    private static final int REQUEST_CODE_PREVIEW = 0x021;


    private static final String SEX_VAL = "sex_val";

    private int mSexVal;
    private final String mOrangeTxtVal = "%s <font color='#FDBD2C'>%scm</font >  ";

    private FitRecordImgListAdapter mImgListAdapter;

    public static FitRecordForAddFrag newInstance(int sex) {

        Bundle args = new Bundle();
        args.putInt(SEX_VAL, sex);
        FitRecordForAddFrag fragment = new FitRecordForAddFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mSexVal = arguments.getInt(SEX_VAL);
        }
    }

    @Override
    public FitRecordForAddPre createPresenter() {
        return new FitRecordForAddPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_fit_record_add;
    }

    @Override
    public void initView() {
        super.initView();
        ImmersionBar.with(this)
                .titleBar(mAppToolbar)
                .statusBarDarkFont(true, 0.2f)
                .init();
        mTvWeight = findViewById(R.id.tv_weight);
        mTvBodyFat = findViewById(R.id.tv_body_fat);
        mTvBoneMass = findViewById(R.id.tv_bone_mass);
        mTvRecordPicture = findViewById(R.id.tv_picture_record);
        mIvRecordPictureSmall = findViewById(R.id.iv_picture_record_small);
        mRecyclerView = findViewById(R.id.rv_fit_record_album);

        mRecyclerView.setVisibility(View.GONE);
        mTvRecordPicture.setVisibility(View.GONE);
        mIvRecordPictureSmall.setVisibility(View.GONE);

        View showView;

        if (mSexVal == 0) {
            mViewWoman = findViewById(R.id.view_woman);
            showView = mViewWoman.inflate();
        } else {
            mViewMan = findViewById(R.id.view_man);
            showView = mViewMan.inflate();
        }
        initShowView(showView);
        mRefreshLayout.autoRefresh();

        mTvRecordPicture.setOnClickListener(this);
        mIvRecordPictureSmall.setOnClickListener(this);
    }

    private void initShowView(View view) {
        mTvLeftArm = view.findViewById(R.id.tv_left_arm);
        mTvRightArm = view.findViewById(R.id.tv_right_arm);
        mTvBust = view.findViewById(R.id.tv_bust);
        mTvWaistline = view.findViewById(R.id.tv_waistline);
        mTvHipline = view.findViewById(R.id.tv_hipline);
        mTvLeftThigh = view.findViewById(R.id.tv_left_thigh);
        mTvRightThigh = view.findViewById(R.id.tv_right_thigh);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        getPresenter().getFitRecordInfo();
        getPresenter().getFitRecordImgList(mCurrentPage);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        startForResult(new FitRecordGirthFrag(), FitRecordGirthFrag.GIRTH_REQUEST);
        return super.onMenuItemClick(item);
    }

    @Override
    public void onFitRecordInfoResult(FitRecordInfoBean fitRecordInfo) {
        mRefreshLayout.finishRefresh(true);

        try {
            mTvWeight.setText(Float.toString(fitRecordInfo.getWeight()));
            mTvBodyFat.setText(Float.toString(fitRecordInfo.getFatRate()));
            mTvBoneMass.setText(Float.toString(fitRecordInfo.getBone()));

            mTvLeftArm.setText(Html.fromHtml(String.format(mOrangeTxtVal, "左上臂", fitRecordInfo.getLeftArm())));
            mTvRightArm.setText(Html.fromHtml(String.format(mOrangeTxtVal, "右上臂", fitRecordInfo.getRightArm())));
            mTvBust.setText(Html.fromHtml(String.format(mOrangeTxtVal, "胸围", fitRecordInfo.getBust())));
            mTvWaistline.setText(Html.fromHtml(String.format(mOrangeTxtVal, "腰围", fitRecordInfo.getWaistline())));
            mTvHipline.setText(Html.fromHtml(String.format(mOrangeTxtVal, "臀围", fitRecordInfo.getHips())));
            mTvLeftThigh.setText(Html.fromHtml(String.format(mOrangeTxtVal, "左大腿", fitRecordInfo.getLeftThigh())));
            mTvRightThigh.setText(Html.fromHtml(String.format(mOrangeTxtVal, " 右大腿", fitRecordInfo.getRightThigh())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAddForResult(String result) {
        ToastUtils.showToast(mFragmentActivity, result);
    }

    @Override
    public void onFitRecordImgList(List<FitRecordImgInfoBean> fitRecordImgInfoList, int totalCount) {
        if (fitRecordImgInfoList.size() == 0) {
            if (mRecyclerView.getVisibility() == View.VISIBLE) {
                mRecyclerView.setVisibility(View.GONE);
                mIvRecordPictureSmall.setVisibility(View.GONE);
                mTvRecordPicture.setVisibility(View.VISIBLE);
            }
        } else {

            if (mIvRecordPictureSmall.getVisibility() == View.GONE) {
                mTvRecordPicture.setVisibility(View.GONE);
                mIvRecordPictureSmall.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }

            if (mImgListAdapter == null) {
                final int dividerSize = getResources().getDimensionPixelSize(R.dimen.hor_divider_size_2);
                mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.set(dividerSize, dividerSize, dividerSize, dividerSize);
                    }
                });
                mImgListAdapter = new FitRecordImgListAdapter(GlideApp.with(this), mFragmentActivity);
                mImgListAdapter.setOnItemClickListener(this);
                mRecyclerView.setAdapter(mImgListAdapter);
            }
            mImgListAdapter.setTotalCount(totalCount);
            mImgListAdapter.setFitRecordImgInfoList(fitRecordImgInfoList);

        }
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_picture_record_small:
            case R.id.tv_picture_record:
                SelectPictureDialog dialog = new SelectPictureDialog();
                dialog.setOnSelectPhoto(this);
                dialog.show(getChildFragmentManager(), dialog.getClass().getName());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String imgPath = null;
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_ALBUM) {
            List<String> imgList = AlbumManager.obtainPathResult(data);
            imgPath = imgList.get(0);
        } else if (requestCode == REQUEST_CODE_CAPTURE) {
            imgPath = mMediaStoreCompat.getCurrentPhotoPath();
        }
        if (imgPath != null) {
            File imgFile = new File(imgPath);
            if (imgFile.exists()) {
                getPresenter().addFitRecordImg(imgFile);
            }
        }
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_PREVIEW) {
            getPresenter().getFitRecordImgList(mCurrentPage);
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_PREVIEW) {
            getPresenter().getFitRecordImgList(mCurrentPage);
        }
        if (requestCode == FitRecordGirthFrag.GIRTH_REQUEST && resultCode == FitRecordGirthFrag.GIRTH_ADD_RESULT_OK) {
            getPresenter().getFitRecordInfo();
        }
    }

    @Override
    public void selectPhoto(boolean isTakePhoto) {

        if (isTakePhoto) {
            if (mMediaStoreCompat == null) {
                mMediaStoreCompat = new MediaStoreCompat(mFragmentActivity, this);
                mMediaStoreCompat.setCaptureStrategy(new CaptureStrategy(true,
                        mFragmentActivity.getPackageName() + ".fileprovider", DIRECTORY));
            }
            if (!MediaStoreCompat.hasCameraFeature(getContext())) {
                return;
            }
            mMediaStoreCompat.dispatchCaptureIntent(mFragmentActivity, REQUEST_CODE_CAPTURE);
        } else {
            selectAlbum();
        }
    }

    private void selectAlbum() {
        AlbumManager.from(this)
                .choose(MimeType.ofImage(), false)
                .showSingleMediaType(true)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(false,
                                mFragmentActivity.getPackageName() + ".fileprovider", "test"))
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())  // for glide-V4
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .forResult(REQUEST_CODE_ALBUM);
    }


    @Override
    public void onItemClick(int position, FitRecordImgInfoBean entity, View view) {
        if (view == null) {//start to My album
            startForResult(new FitRecordMyAlbumFrag(), REQUEST_CODE_PREVIEW);
        } else {//image preview
            Intent intent = new Intent(mFragmentActivity, FitRecordAlbumPreviewAct.class);
            intent.putParcelableArrayListExtra(FitRecordAlbumPreviewAct.EXTRA_IMAGE_LIST, getPresenter().getImgList());
            intent.putExtra(FitRecordAlbumPreviewAct.EXTRA_IMAGE_POSITION, position);
            startActivityForResult(intent, REQUEST_CODE_PREVIEW);
        }
    }
}
