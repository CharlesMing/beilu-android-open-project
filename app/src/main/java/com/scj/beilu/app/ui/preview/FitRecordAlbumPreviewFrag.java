package com.scj.beilu.app.ui.preview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.mx.pro.lib.fragmentation.support.SupportFragment;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.mvp.fit.bean.FitRecordImgInfoBean;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * @author Mingxun
 * @time on 2019/3/15 17:14
 */
public class FitRecordAlbumPreviewFrag extends SupportFragment {

    private onViewClickListener mListener;
    private FitRecordImgInfoBean mImgInfoBean;
    private static final String IMG_INFO = "IMG_INFO";

    public interface onViewClickListener {
        void onClick(int position);
    }

    public static FitRecordAlbumPreviewFrag newInstance(FitRecordImgInfoBean imgInfo) {

        Bundle args = new Bundle();
        args.putParcelable(IMG_INFO, imgInfo);
        FitRecordAlbumPreviewFrag fragment = new FitRecordAlbumPreviewFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onViewClickListener) {
            mListener = (onViewClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onViewClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mImgInfoBean = arguments.getParcelable(IMG_INFO);
        }
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_fit_record_album_preview;
    }

    @Override
    public void initView() {
        super.initView();

        ImageViewTouch image = findViewById(R.id.image_view);
        image.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

        if (mImgInfoBean == null) return;
        image.setSingleTapListener(new ImageViewTouch.OnImageViewTouchSingleTapListener() {
            @Override
            public void onSingleTapConfirmed() {
                if (mListener != null) {
                    mListener.onClick(-1);
                }
            }
        });

        GlideRequest<Bitmap> thumbnail = GlideApp.with(this)
                .asBitmap();
        GlideRequest<Bitmap> original = GlideApp.with(this)
                .asBitmap();

        original
                .load(mImgInfoBean.getPicOrgAddr())
                .thumbnail(thumbnail.load(mImgInfoBean.getPicComAddr()))
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
