package com.scj.beilu.app.ui.store.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.pro.lib.fragmentation.support.SupportFragment;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.store.bean.GoodsCategoryInfoBean;

import java.util.List;

/**
 * author:SunGuiLan
 * date:2019/2/13
 * descriptin: 首页--4个按钮
 */
public class GoodsCategoryListAdapter extends RecyclerView.Adapter<GoodsCategoryListAdapter.HomeButtonViewHolder> {

    private List<GoodsCategoryInfoBean> mGoodsCategoryInfoBeans;

    private OnItemClickListener mItemClickListener;

    private GlideRequest<Drawable> mOriginalRequest;
    private GlideRequest<Drawable> mThumbnailRequest;

    public GoodsCategoryListAdapter(SupportFragment fragment) {
        mOriginalRequest = GlideApp.with(fragment).asDrawable().fitCenter();
        mThumbnailRequest = GlideApp.with(fragment).asDrawable().fitCenter();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setGoodsCategoryInfoBeans(List<GoodsCategoryInfoBean> goodsCategoryInfoBeans) {
        mGoodsCategoryInfoBeans = goodsCategoryInfoBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_button, parent, false);
        return new HomeButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeButtonViewHolder holder, final int position) {
        GoodsCategoryInfoBean categoryInfoBean = mGoodsCategoryInfoBeans.get(position);
        mOriginalRequest
                .load(categoryInfoBean.getProductCatePicOriginalAddr())
                .thumbnail(mThumbnailRequest.load(categoryInfoBean.getProductCatePicCompressadAddr()))
                .into(holder.iv_photo);

        holder.tv_name.setText(categoryInfoBean.getProductCateName());

    }

    @Override
    public int getItemCount() {
        return mGoodsCategoryInfoBeans == null ? 0 : mGoodsCategoryInfoBeans.size();
    }

    class HomeButtonViewHolder extends BaseViewHolder {
        private ImageView iv_photo;
        private TextView tv_name;

        public HomeButtonViewHolder(View itemView) {
            super(itemView);
            iv_photo = findViewById(R.id.iv_photo);
            tv_name = findViewById(R.id.tv_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoodsCategoryInfoBean categoryInfoBean = mGoodsCategoryInfoBeans.get(getAdapterPosition());
                    if (mItemClickListener != null && categoryInfoBean != null) {
                        mItemClickListener.onItemClick(getAdapterPosition(), categoryInfoBean, v);
                    }
                }
            });
        }
    }
}
