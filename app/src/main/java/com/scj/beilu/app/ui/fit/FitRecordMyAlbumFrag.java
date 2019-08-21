package com.scj.beilu.app.ui.fit;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

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
import com.scj.beilu.app.mvp.fit.FitRecordMyAlbumPre;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgInfoBean;
import com.scj.beilu.app.ui.dialog.SelectPictureDialog;
import com.scj.beilu.app.ui.fit.adapter.FitRecordMyAlbumListAdapter;
import com.scj.beilu.app.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static com.scj.beilu.app.api.Constants.DIRECTORY;

/**
 * @author Mingxun
 * @time on 2019/3/14 21:10
 */
public class FitRecordMyAlbumFrag extends BaseMvpFragment<FitRecordMyAlbumPre.FitRecordMyAlbumView, FitRecordMyAlbumPre>
        implements FitRecordMyAlbumPre.FitRecordMyAlbumView, View.OnClickListener, SelectPictureDialog.onSelectPhoto,
        OnItemClickListener<FitRecordImgInfoBean> {

    private FrameLayout mFlSelectPicture;
    private TextView mTvCustomPhoto;
    private TextView mTvPictureRange;
    private MediaStoreCompat mMediaStoreCompat;
    private static final int REQUEST_CODE_CAPTURE = 0x01;
    private static final int REQUEST_CODE_ALBUM = 0x02;
    private static final int REQUEST_CODE_PREVIEW = 0x03;
    private static final int REQUEST_CODE_GENERATE = 0x04;

    private FitRecordMyAlbumListAdapter mFitRecordMyAlbumListAdapter;

    private int mRectSize = 0;
    private boolean isShowSelector = false;
    private boolean isClickBack = false;
    private final List<String> mGeneratePic = new ArrayList<>();

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_fit_record_my_album;
    }

    @Override
    public void initView() {
        super.initView();
        mFlSelectPicture = findViewById(R.id.fl_add_picture);
        mTvCustomPhoto = findViewById(R.id.tv_custom_photo);
        mTvPictureRange = findViewById(R.id.tv_picture_range);

        if (isAdded()) {
            mRectSize = getResources().getDimensionPixelSize(R.dimen.padding_15);
        }

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, mRectSize, mRectSize, 0);
            }
        });

        mFlSelectPicture.setOnClickListener(this);
        mTvCustomPhoto.setOnClickListener(this);
        mFitRecordMyAlbumListAdapter = new FitRecordMyAlbumListAdapter(GlideApp.with(this), mFragmentActivity);
        mFitRecordMyAlbumListAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mFitRecordMyAlbumListAdapter);
        mRecyclerView.getItemAnimator().setChangeDuration(0);

        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        getPresenter().getAlbumList(mCurrentPage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().getAlbumList(mCurrentPage);
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public FitRecordMyAlbumPre createPresenter() {
        return new FitRecordMyAlbumPre(mFragmentActivity);
    }

    @Override
    public void onAlbumListResult(List<FitRecordImgInfoBean> albumList) {
        mFitRecordMyAlbumListAdapter.setFitRecordImgInfoBeans(albumList);
    }

    @Override
    public void onAddForResult(String result) {
        isShowSelector = false;
        isClickBack = true;
        ToastUtils.showToast(mFragmentActivity, result);
        mRecyclerView.scrollToPosition(0);
        mFitRecordMyAlbumListAdapter.notifyItemInserted(0);
        setFragmentResult(RESULT_OK, null);
    }

    @Override
    public void onShowSelector(List<String> selectedImg, int position, String hint) {
        if (mTvPictureRange.getVisibility() == View.GONE) {
            mTvPictureRange.setVisibility(View.VISIBLE);
        }
        if (position == -1) {
            mFitRecordMyAlbumListAdapter.notifyDataSetChanged();
            if (isShowSelector) {
                mTvCustomPhoto.setText("完成（0/2）张图");
                mTvCustomPhoto.setEnabled(false);
            } else {
                if (!isClickBack) {
                    startForResult(FitRecordGeneratePictureFrag.newInstance(mGeneratePic), REQUEST_CODE_GENERATE);
                }
                mTvPictureRange.setVisibility(View.GONE);
                mTvCustomPhoto.setText("制作对比照");
                mTvCustomPhoto.setEnabled(true);
            }
        } else {
            isClickBack = false;
            mGeneratePic.clear();
            mGeneratePic.addAll(selectedImg);
            mFitRecordMyAlbumListAdapter.notifyItemChanged(position);
            mTvCustomPhoto.setEnabled(mGeneratePic.size() == 2);
            mTvCustomPhoto.setText("完成（" + mGeneratePic.size() + "/2）张图");
        }

        if (!TextUtils.isEmpty(hint)) {
            ToastUtils.showToast(mFragmentActivity, hint);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_add_picture:
                SelectPictureDialog dialog = new SelectPictureDialog();
                dialog.setOnSelectPhoto(this);
                dialog.show(getChildFragmentManager(), dialog.getClass().getName());
                break;
            case R.id.tv_custom_photo://制作对比照
                isShowSelector = !isShowSelector;
                getPresenter().showSelector(isShowSelector);
                break;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_ALBUM) {
            List<String> imgList = AlbumManager.obtainPathResult(data);
            String imgPath = imgList.get(0);
            getPresenter().addFitRecordImg(imgPath);
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CAPTURE) {
            String imgPath = mMediaStoreCompat.getCurrentPhotoPath();
            getPresenter().addFitRecordImg(imgPath);
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_PREVIEW) {
            setFragmentResult(RESULT_OK, null);
            mRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (REQUEST_CODE_GENERATE == requestCode) {
            mRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void onItemClick(int position, FitRecordImgInfoBean entity, View view) {
        switch (view.getId()) {
            case R.id.iv_album_selector:
                getPresenter().setSelector(position);
                break;
            case R.id.iv_fit_record_my_album:
                Intent intent = new Intent(mFragmentActivity, FitRecordAlbumPreviewAct.class);
                intent.putParcelableArrayListExtra(FitRecordAlbumPreviewAct.EXTRA_IMAGE_LIST, getPresenter().getImgList());
                intent.putExtra(FitRecordAlbumPreviewAct.EXTRA_IMAGE_POSITION, position);
                startActivityForResult(intent, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    protected void onToolbarNavClick() {
        if (isShowSelector) {
            isShowSelector = false;
            isClickBack = true;
            getPresenter().showSelector(isShowSelector);
        } else {
            mFragmentActivity.onBackPressed();
        }
    }

    @Override
    public boolean onBackPressedSupport() {
        if (isShowSelector) {
            isShowSelector = false;
            isClickBack = true;
            getPresenter().showSelector(isShowSelector);
            return true;
        } else {
            return super.onBackPressedSupport();
        }
    }


}
