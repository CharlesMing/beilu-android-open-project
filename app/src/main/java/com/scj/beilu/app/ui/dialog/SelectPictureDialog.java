package com.scj.beilu.app.ui.dialog;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.scj.beilu.app.R;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author Mingxun
 * @time on 2019/3/14 15:24
 */
@RuntimePermissions
public class SelectPictureDialog extends AppCompatDialogFragment implements View.OnClickListener {

    private TextView mTvTakePhoto;
    private TextView mTvAlbumSelect;

    private boolean mIsTakePhoto;
    private onSelectPhoto mOnSelectPhoto;

    public interface onSelectPhoto {
        void selectPhoto(boolean isTakePhoto);
    }

    public void setOnSelectPhoto(onSelectPhoto onSelectPhoto) {
        mOnSelectPhoto = onSelectPhoto;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_picture, container);
        mTvTakePhoto = view.findViewById(R.id.tv_take_photo);
        mTvAlbumSelect = view.findViewById(R.id.tv_album_select);
        mTvTakePhoto.setOnClickListener(this);
        mTvAlbumSelect.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            Window window = getDialog().getWindow();
            if (window != null) {
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_take_photo:
                mIsTakePhoto = true;
                SelectPictureDialogPermissionsDispatcher.needsPermissionWithPermissionCheck(this);
                dismiss();
                break;
            case R.id.tv_album_select:
                mIsTakePhoto = false;
                SelectPictureDialogPermissionsDispatcher.needsPermissionWithPermissionCheck(this);
                dismiss();
                break;
        }
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void needsPermission() {
        if (mOnSelectPhoto != null) {
            mOnSelectPhoto.selectPhoto(mIsTakePhoto);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SelectPictureDialogPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationale(final PermissionRequest request) {
        request.proceed();
    }

}
