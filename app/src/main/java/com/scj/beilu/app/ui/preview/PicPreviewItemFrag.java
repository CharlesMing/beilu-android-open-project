package com.scj.beilu.app.ui.preview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.mx.pro.lib.album.listener.OnFragmentInteractionListener;
import com.mx.pro.lib.fragmentation.support.SupportFragment;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.mvp.common.bean.PicPreviewBean;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * @author Mingxun
 * @time on 2019/2/20 18:07
 */
public class PicPreviewItemFrag extends SupportFragment {

    private PicPreviewBean mPicInfo;
    private static final String PATH_URL = "path";
    private OnFragmentInteractionListener mListener;

    public static PicPreviewItemFrag newInstance(PicPreviewBean picInfo) {

        Bundle args = new Bundle();
        args.putParcelable(PATH_URL, picInfo);
        PicPreviewItemFrag fragment = new PicPreviewItemFrag();
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
            mPicInfo = arguments.getParcelable(PATH_URL);
        }
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_image_preview;
    }

    @Override
    public void initView() {
        super.initView();
        ImageViewTouch image = findViewById(R.id.image_view);
        image.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

        image.setSingleTapListener(() -> {
            if (mListener != null) {
                mListener.onClick();
            }
        });

        GlideRequest<Bitmap> thumbnail = GlideApp.with(this)
                .asBitmap();
        GlideRequest<Bitmap> original = GlideApp.with(this)
                .asBitmap();

        original
                .load(mPicInfo.getOriginalPicPath())
                .thumbnail(thumbnail.load(mPicInfo.getThumbnailPicPath()))
                .apply(new RequestOptions()
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
