package com.scj.beilu.app.ui.find.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.find.bean.FindImageBean;
import com.scj.beilu.app.ui.find.FindDetailsFrag;

import java.util.List;

/**
 * date:2019/2/25
 * descriptin:动态详情的图片适配器
 * {@link FindDetailsFrag}
 */
public class FindDetailsImagePagerAdapter extends PagerAdapter {
    private List<FindImageBean> mFindImageBeanList;
    private GlideRequest<Drawable> mOriginalGlideRequest;
    private GlideRequest<Drawable> mThumbnailGlideRequests;

    private OnItemClickListener mItemClickListener;


    public FindDetailsImagePagerAdapter(GlideRequest<Drawable> originalGlideRequest,
                                        GlideRequest<Drawable> thumbnailGlideRequests) {
        mOriginalGlideRequest = originalGlideRequest;
        mThumbnailGlideRequests = thumbnailGlideRequests;
    }

    public void setFindImageBeanList(List<FindImageBean> findImageBeanList) {
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
        final FindImageBean imageBean = mFindImageBeanList.get(position);
        mOriginalGlideRequest
                .load(imageBean.getDynamicPicAddr())
                .thumbnail(mThumbnailGlideRequests.load(imageBean.getDynamicPicZip()).centerCrop())
                .centerCrop()
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
