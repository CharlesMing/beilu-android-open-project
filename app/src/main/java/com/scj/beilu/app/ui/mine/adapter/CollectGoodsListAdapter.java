package com.scj.beilu.app.ui.mine.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoBean;
import com.scj.beilu.app.ui.mine.MineCollectFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/9 17:20
 */
public class CollectGoodsListAdapter extends RecyclerView.Adapter<CollectGoodsListAdapter.GoodsViewHolder> {

    private List<GoodsInfoBean> mGoodsInfoList;
    private GlideRequest<Drawable> mOriginal;
    private GlideRequest<Drawable> mThumbnail;
    private final String mRMB;
    private OnItemClickListener mOnItemClickListener;

    public CollectGoodsListAdapter(MineCollectFrag frag) {
        mOriginal = GlideApp.with(frag).asDrawable().centerCrop();
        mThumbnail = GlideApp.with(frag).asDrawable().centerCrop();
        mRMB = frag.getString(R.string.txt_rmb);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setGoodsInfoList(List<GoodsInfoBean> goodsInfoList) {
        mGoodsInfoList = goodsInfoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect_goods_content, parent, false);
        return new GoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsViewHolder holder, int position) {
        try {
            GoodsInfoBean goodsInfoBean = mGoodsInfoList.get(position);
            holder.mTvGoodsName.setText(goodsInfoBean.getProductName());
            holder.mTvGoodsPrice.setText(String.format(mRMB, goodsInfoBean.getProductDiscountPrice()));
            mOriginal.load(goodsInfoBean.getProductPicOriginalAddr())
                    .thumbnail(mThumbnail.load(goodsInfoBean.getProductPicCompressionAddr()))
                    .into(holder.mIvGoodsImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mGoodsInfoList == null ? 0 : mGoodsInfoList.size();
    }

    public class GoodsViewHolder extends BaseViewHolder {
        private TextView mTvGoodsName, mTvGoodsPrice;
        private ImageView mIvGoodsImg;
        private ConstraintLayout mConstraintLayout;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            mTvGoodsName = findViewById(R.id.tv_goods_name);
            mTvGoodsPrice = findViewById(R.id.tv_goods_price);
            mIvGoodsImg = findViewById(R.id.iv_goods_img);
            mConstraintLayout = findViewById(R.id.cl_parent_content);
            mConstraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), mGoodsInfoList.get(getAdapterPosition()), v);
                    }
                }
            });
        }
    }

}
