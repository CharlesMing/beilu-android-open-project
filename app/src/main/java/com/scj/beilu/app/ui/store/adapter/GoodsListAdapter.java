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
import com.scj.beilu.app.mvp.store.bean.GoodsInfoBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/28 12:21
 */
public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.GoodsViewHolder> {

    private List<GoodsInfoBean> mGoodsInfoBeans;
    private GlideRequest<Drawable> mOriginalRequest;
    private GlideRequest<Drawable> mThumbnailRequest;
    private String mRMB;
    private OnItemClickListener mOnItemClickListener;

    public GoodsListAdapter(SupportFragment fragment) {
        mOriginalRequest = GlideApp.with(fragment).asDrawable().fitCenter();
        mThumbnailRequest = GlideApp.with(fragment).asDrawable().fitCenter();
        mRMB = fragment.getResources().getString(R.string.txt_rmb);
    }

    public void setGoodsInfoBeans(List<GoodsInfoBean> goodsInfoBeans) {
        mGoodsInfoBeans = goodsInfoBeans;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public GoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_content, parent, false);
        return new GoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsViewHolder holder, int position) {
        try {
            GoodsInfoBean goodsInfoBean = mGoodsInfoBeans.get(position);

            mOriginalRequest.load(goodsInfoBean.getProductPicOriginalAddr())
                    .thumbnail(mThumbnailRequest.load(goodsInfoBean.getProductPicCompressionAddr()))
                    .into(holder.mIvGoodsImg);

            holder.mTvGoodsName.setText(goodsInfoBean.getProductName());
            holder.mTvGoodsPrice.setText(String.format(mRMB, goodsInfoBean.getProductDiscountPrice()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mGoodsInfoBeans == null ? 0 : mGoodsInfoBeans.size();
    }

    public class GoodsViewHolder extends BaseViewHolder {
        private ImageView mIvGoodsImg;
        private TextView mTvGoodsName;
        private TextView mTvGoodsPrice;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            mIvGoodsImg = findViewById(R.id.iv_commodity_img);
            mTvGoodsName = findViewById(R.id.tv_commodity_name);
            mTvGoodsPrice = findViewById(R.id.tv_commodity_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoodsInfoBean infoBean = mGoodsInfoBeans.get(getAdapterPosition());
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), infoBean, v);
                    }
                }
            });
        }
    }
}
