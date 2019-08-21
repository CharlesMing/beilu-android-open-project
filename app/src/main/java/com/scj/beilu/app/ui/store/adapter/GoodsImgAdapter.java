package com.scj.beilu.app.ui.store.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.store.bean.GoodsImgInfoBean;
import com.scj.beilu.app.ui.store.GoodsDetailFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/28 18:32
 */
public class GoodsImgAdapter extends PagerAdapter {
    private List<GoodsImgInfoBean> mFindImageBeanList;
    private GlideRequest<Drawable> mOriginalGlideRequest;
    private GlideRequest<Drawable> mThumbnailGlideRequests;

    private OnItemClickListener mItemClickListener;

    public GoodsImgAdapter(GoodsDetailFrag frag) {
        mOriginalGlideRequest = GlideApp.with(frag).asDrawable().centerCrop();
        mThumbnailGlideRequests = GlideApp.with(frag).asDrawable().centerCrop();
    }

    public void setImgList(List<GoodsImgInfoBean> findImageBeanList) {
        mFindImageBeanList = findImageBeanList;
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public int getCount() {
        return mFindImageBeanList == null ? 0 : mFindImageBeanList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setId(R.id.imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        final GoodsImgInfoBean imageBean = mFindImageBeanList.get(position);
        mOriginalGlideRequest
                .load(imageBean.getProductPicOriginalAddr())
                .thumbnail(mThumbnailGlideRequests.load(imageBean.getProductPicCompressionAddr()))
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position, imageBean, view);
                }
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        (container).removeView(container);
    }
}
