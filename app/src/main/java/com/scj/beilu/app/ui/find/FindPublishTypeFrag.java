package com.scj.beilu.app.ui.find;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.scj.beilu.app.R;
import com.scj.beilu.app.api.Constants;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.find.FindPublishTypePre;


import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author Mingxun
 * @time on 2019/2/19 15:36
 */
@RuntimePermissions
public class FindPublishTypeFrag extends BaseMvpFragment<FindPublishTypePre.FindPublishTypeView, FindPublishTypePre>
        implements FindPublishTypePre.FindPublishTypeView, View.OnClickListener {

    private ImageView im_video;
    private ImageView mPublishImg;
    private ImageView mIvPublishTxt;
    private int mPublishType = 0;//0 video 1 image 2 txt
    private LinearLayout mLlBack;

    @Override
    public FindPublishTypePre createPresenter() {
        return new FindPublishTypePre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_publish_type;
    }

    @Override
    public void initView() {
        super.initView();
        mLlBack = findViewById(R.id.ll_liner);
        im_video = findViewById(R.id.iv_video);
        mPublishImg = findViewById(R.id.iv_photo);
        mIvPublishTxt = findViewById(R.id.iv_write);
        im_video.setOnClickListener(this);
        mPublishImg.setOnClickListener(this);
        mLlBack.setOnClickListener(this);
        mIvPublishTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_video:
                mPublishType = 0;
                FindPublishTypeFragPermissionsDispatcher.needsPermissionWithPermissionCheck(this);
                break;
            case R.id.iv_photo:
                mPublishType = 1;
                FindPublishTypeFragPermissionsDispatcher.needsPermissionWithPermissionCheck(this);
                break;
            case R.id.iv_write:
                mPublishType = 2;
                FindPublishTypeFragPermissionsDispatcher.needsPermissionWithPermissionCheck(this);
                break;
            case R.id.ll_liner:
                mFragmentActivity.onBackPressed();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_CODE_CHOOSE) {
            start(FindPublishFrag.newInstance(data, mPublishType));
        }
    }


    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void needsPermission() {
        if (mPublishType == 2) {
            start(FindPublishFrag.newInstance(new Intent(), mPublishType));
        } else {
            getPresenter().startSelectImg(this, mPublishType);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FindPublishTypeFragPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onShowRationale(final PermissionRequest request) {
        showRationaleDialog(R.string.permission_camera_rationale, request);
    }
}
