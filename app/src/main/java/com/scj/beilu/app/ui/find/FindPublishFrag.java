package com.scj.beilu.app.ui.find;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mx.pro.lib.album.AlbumManager;
import com.mx.pro.lib.album.GlideEngine;
import com.mx.pro.lib.album.MimeType;
import com.mx.pro.lib.album.filter.Filter;
import com.mx.pro.lib.album.internal.entity.CaptureStrategy;
import com.mx.pro.lib.album.internal.ui.widget.MediaGridInset;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequests;
import com.scj.beilu.app.R;
import com.scj.beilu.app.api.Constants;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.find.FindPublishPre;
import com.scj.beilu.app.mvp.find.GifSizeFilter;
import com.scj.beilu.app.service.UploadService;
import com.scj.beilu.app.ui.act.AddedPreviewActivity;
import com.scj.beilu.app.ui.find.adapter.EditPhotoAdapter;

import java.util.ArrayList;

import static com.mx.pro.lib.album.ui.AlbumActivity.EXTRA_RESULT_SELECTION_PATH;
import static com.scj.beilu.app.api.Constants.DIRECTORY;
import static com.scj.beilu.app.ui.act.AddedPreviewActivity.IMAGE_LIST;

/**
 * @author Mingxun
 * @time on 2019/2/19 16:24
 */
public class FindPublishFrag extends BaseMvpFragment<FindPublishPre.FindPublishView, FindPublishPre>
        implements FindPublishPre.FindPublishView, OnItemClickListener, EditPhotoAdapter.onNotifyListener {

    private EditText mEtDescription;
    private TextView mTvPublish;
    private RecyclerView mRvImageList;
    public static final String FILE_DATA = "file_date";
    public static final String MIME_TYPE = "mine_type";
    public static final String DESCRIPTION = "description";
    private Intent mData = null;
    private EditPhotoAdapter mEditPhotoAdapter;
    private final ArrayList<Uri> mUriList = new ArrayList<>();
    private final ArrayList<String> mPathList = new ArrayList<>();
    private int mPublishType = 1;
    private boolean ofImage = true;
    private final int REQUEST_SELECT = 0x0101;

    public static FindPublishFrag newInstance(Intent data, int publishType) {

        Bundle args = new Bundle();
        args.putParcelable(FILE_DATA, data);
        args.putInt(MIME_TYPE, publishType);
        FindPublishFrag fragment = new FindPublishFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mPublishType = arguments.getInt(MIME_TYPE);
            mData = arguments.getParcelable(FILE_DATA);
            ArrayList<Uri> uriList = (ArrayList<Uri>) AlbumManager.obtainResult(mData);
            ArrayList<String> pathList = (ArrayList<String>) AlbumManager.obtainPathResult(mData);
            if (uriList != null && pathList != null) {
                mUriList.addAll(uriList);
                mPathList.addAll(pathList);
            }
        }
    }

    @Override
    public FindPublishPre createPresenter() {
        return new FindPublishPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_publish_edit;
    }

    @Override
    public void initView() {
        super.initView();
        mEtDescription = findViewById(R.id.et_description);
        mRvImageList = findViewById(R.id.rv_editPhoto);
        mTvPublish = findViewById(R.id.tv_publish);

        int spacing = getResources().getDimensionPixelSize(R.dimen.media_grid_spacing);
        mRvImageList.addItemDecoration(new MediaGridInset(3, spacing, false));
        int count = 9;
        if (mPublishType == 0) {
            count= 1;
            ofImage = false;
        }
        GlideRequests requests = GlideApp.with(this);
        mEditPhotoAdapter = new EditPhotoAdapter(this, requests, count);
        mEditPhotoAdapter.setOnItemClickListener(this);
        mRvImageList.setAdapter(mEditPhotoAdapter);
        mEditPhotoAdapter.setImagePath(mUriList);
        mTvPublish.setOnClickListener(v -> {
            String content = mEtDescription.getText().toString();
            //通过Service 上传
            Intent intent = new Intent(mFragmentActivity, UploadService.class);
            intent.putExtra(FILE_DATA, mData);
            intent.putExtra(MIME_TYPE, ofImage);
            intent.putExtra(DESCRIPTION, content);
            mFragmentActivity.startService(intent);
            mFragmentActivity.setResult(RESULT_OK, intent);
            mFragmentActivity.onBackPressed();
        });
        mTvPublish.setEnabled(mPathList.size() > 0);

        mEtDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mPathList.size() > 0 || s.length() > 0) {
                    mTvPublish.setEnabled(true);
                } else {
                    mTvPublish.setEnabled(false);
                }
            }
        });

    }

    @Override
    public void onItemClick(int position, Object entity, View view) {
        switch (view.getId()) {
            case R.id.iv_del_image:
                mPathList.remove(position);
                mUriList.remove(position);
                mEditPhotoAdapter.notifyItemRemoved(position);

                onImageSize(mPathList.size());
                break;
            case R.id.iv_publish_image:
                selectSource(position, entity);
                break;
        }
    }

    private void selectSource(int position, Object entity) {
        if (entity == null) {
            int maxSize;
            if (ofImage) {
                maxSize = 9 - mUriList.size();
            } else {
                maxSize = 1;
            }

            AlbumManager.from(this)
                    .choose(ofImage ? MimeType.ofImage() : MimeType.ofVideo(), false)
                    .showSingleMediaType(true)
                    .countable(true)
                    .capture(true)
                    .captureStrategy(
                            new CaptureStrategy(true, mFragmentActivity.getPackageName() + ".fileprovider", DIRECTORY))
                    .maxSelectable(maxSize)
                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    .thumbnailScale(0.85f)
                    .imageEngine(new GlideEngine())  // for glide-V4
                    .maxOriginalSize(10
                    )
                    .autoHideToolbarOnSingleTap(true)
                    .forResult(REQUEST_SELECT);
        } else {
            Intent intent = new Intent(mFragmentActivity, AddedPreviewActivity.class);
            intent.putParcelableArrayListExtra(AddedPreviewActivity.IMAGE_LIST, mUriList);
            intent.putStringArrayListExtra(AddedPreviewActivity.IMAGE_PATH_LIST, mPathList);
            intent.putExtra(AddedPreviewActivity.POSITION, position);
            startActivityForResult(intent, Constants.REQUEST_CODE_CHOOSE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_CODE_CHOOSE) {//点击预览后，返回的数据

            mUriList.clear();
            mPathList.clear();

            ArrayList<Uri> uriList = data.getParcelableArrayListExtra(IMAGE_LIST);
            mUriList.addAll(uriList);
            ArrayList<String> pathList = data.getStringArrayListExtra(AddedPreviewActivity.IMAGE_PATH_LIST);
            mPathList.addAll(pathList);

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_SELECT) {

            ArrayList<Uri> uris = (ArrayList<Uri>) AlbumManager.obtainResult(data);
            ArrayList<String> pathList = (ArrayList<String>) AlbumManager.obtainPathResult(data);
            mUriList.addAll(uris);
            mPathList.addAll(pathList);
        }
        mData.putStringArrayListExtra(EXTRA_RESULT_SELECTION_PATH, mPathList);
        mEditPhotoAdapter.setImagePath(mUriList);
    }

    @Override
    public void onImageSize(int size) {
        if (size > 0 || mEtDescription.getText().toString().length() > 0) {
            mTvPublish.setEnabled(true);
        } else {
            mTvPublish.setEnabled(false);
        }
    }
}
