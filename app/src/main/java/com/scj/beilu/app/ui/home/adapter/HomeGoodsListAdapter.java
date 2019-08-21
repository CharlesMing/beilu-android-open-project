package com.scj.beilu.app.ui.home.adapter;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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
 * @time on 2019/3/12 19:39
 */
public class HomeGoodsListAdapter extends RecyclerView.Adapter<HomeGoodsListAdapter.GoodsViewHolder> {
    private GlideRequest<Drawable> mOriginalGlideRequest;
    private GlideRequest<Drawable> mThumbnailGlideRequest;
    private List<GoodsInfoBean> mGoodsInfoList;
    private OnItemClickListener<GoodsInfoBean> mItemClickListener;

    public void setItemClickListener(OnItemClickListener<GoodsInfoBean> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public HomeGoodsListAdapter(SupportFragment fragment) {
        mOriginalGlideRequest = GlideApp.with(fragment).asDrawable().optionalFitCenter();
        mThumbnailGlideRequest = GlideApp.with(fragment).asDrawable().optionalFitCenter();
    }

    public void setGoodsInfoList(List<GoodsInfoBean> goodsInfoList) {
        mGoodsInfoList = goodsInfoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_page_goods_content, parent, false);
        return new GoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsViewHolder holder, int position) {

        try {
            GoodsInfoBean goodsInfo = mGoodsInfoList.get(position);
            mOriginalGlideRequest
                    .load(goodsInfo.getProductPicOriginalAddr())
                    .thumbnail(mThumbnailGlideRequest.load(goodsInfo.getProductPicCompressionAddr()))
                    .into(holder.mIvGoodsImg);

            holder.mTvTitle.setText(goodsInfo.getProductName());
            holder.mTvPrice.setText("¥" + goodsInfo.getProductDiscountPrice());
            holder.mTvOldPrice.setText("原价 " + goodsInfo.getProductOriginalPrice());
            holder.mTvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mGoodsInfoList == null ? 0 : mGoodsInfoList.size();
    }

    class GoodsViewHolder extends BaseViewHolder {
        private ImageView mIvGoodsImg;
        private TextView mTvTitle;
        private TextView mTvPrice, mTvOldPrice;
        private CardView mCardView;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            mIvGoodsImg = findViewById(R.id.iv_home_page_goods_img);
            mTvTitle = findViewById(R.id.tv_home_page_goods_title);
            mTvPrice = findViewById(R.id.tv_home_page_goods_price);
            mTvOldPrice = findViewById(R.id.tv_home_page_goods_old_price);
            mCardView = findViewById(R.id.card_view_goods_info);
            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(getAdapterPosition(), mGoodsInfoList.get(getAdapterPosition()), v);
                    }
                }
            });
        }
    }

}
