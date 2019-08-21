package com.scj.beilu.app.ui.merchant.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoCoachPicInfoBean;
import com.scj.beilu.app.ui.merchant.MerchantCoachInfoFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/16 16:36
 */
public class MerchantCoachInfoPicListPagerAdapter extends PagerAdapter {

    private List<MerchantInfoCoachPicInfoBean> mInfoCoachPicInfoBeans;

    private GlideRequest<Drawable> mOriginalGlideRequest, mThumbnailGlideRequests;

    public MerchantCoachInfoPicListPagerAdapter(MerchantCoachInfoFrag frag) {
        mOriginalGlideRequest = GlideApp.with(frag).asDrawable().optionalCenterCrop();
        mThumbnailGlideRequests = GlideApp.with(frag).asDrawable().optionalCenterCrop();

    }

    public void setInfoCoachPicInfoBeans(List<MerchantInfoCoachPicInfoBean> infoCoachPicInfoBeans) {
        mInfoCoachPicInfoBeans = infoCoachPicInfoBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mInfoCoachPicInfoBeans == null ? 0 : mInfoCoachPicInfoBeans.size();
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
        final MerchantInfoCoachPicInfoBean imageBean = mInfoCoachPicInfoBeans.get(position);
        mOriginalGlideRequest
                .load(imageBean.getCoachPicAddr())
                .thumbnail(mThumbnailGlideRequests.load(imageBean.getCoachPicAddrZip()))
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mItemClickListener != null) {
//                    mItemClickListener.onItemClick(position, imageBean, view);
//                }
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
