package com.scj.beilu.app.ui.preview;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.mx.pro.lib.album.listener.OnFragmentInteractionListener;
import com.mx.pro.lib.album.utils.PhotoMetadataUtils;
import com.mx.pro.lib.fragmentation.support.SupportFragment;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * @author Mingxun
 * @time on 2019/2/20 18:07
 */
public class AddedPreviewItemFrag extends SupportFragment {

    private Uri mPath;
    private static final String PATH_URL = "path";
    private OnFragmentInteractionListener mListener;

    public static AddedPreviewItemFrag newInstance(Uri path) {

        Bundle args = new Bundle();
        args.putParcelable(PATH_URL, path);
        AddedPreviewItemFrag fragment = new AddedPreviewItemFrag();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mPath = arguments.getParcelable(PATH_URL);
        }
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.fragment_preview_item;
    }

    @Override
    public void initView() {
        super.initView();

        findViewById(com.mx.pro.lib.R.id.video_play_button).setVisibility(View.GONE);
        ImageViewTouch image = findViewById(com.mx.pro.lib.R.id.image_view);
        image.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

        image.setSingleTapListener(() -> {
            if (mListener != null) {
                mListener.onClick();
            }
        });
        Point size = PhotoMetadataUtils.getBitmapSize(mPath, getActivity());
        GlideApp.with(this)
                .load(mPath)
                .apply(new RequestOptions()
                        .override(size.x, size.y)
                        .priority(Priority.HIGH)
                        .fitCenter())
                .into(image);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
