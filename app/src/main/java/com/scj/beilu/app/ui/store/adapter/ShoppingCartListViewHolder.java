package com.scj.beilu.app.ui.store.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.store.entity.GoodsShoppingCarInfoBean;
import com.scj.beilu.app.ui.store.ShoppingCarFrag;

/**
 * @author Mingxun
 * @time on 2019/4/1 17:24
 */
public class ShoppingCartListViewHolder extends BaseViewHolder {

    private ImageView mIvSelect;
    private ImageView mIvGoodsImg;
    private TextView mTvGoodsName, mTvGoodsSpecification, mTvGoodsPrice;
    private ImageButton mBtnMinus, mBtnAdd;
    private TextView mTvGoodsNum;

    private GlideRequest<Drawable> mOriginalRequest;
    private GlideRequest<Drawable> mThumbnailRequest;

    private String mRMB;
    private OnItemClickListener<GoodsShoppingCarInfoBean> mOnItemClickListener;

    public ShoppingCartListViewHolder(View itemView, ShoppingCarFrag frag,
                                      OnItemClickListener<GoodsShoppingCarInfoBean> onItemClickListener) {
        super(itemView);
        mOnItemClickListener = onItemClickListener;

        mIvSelect = findViewById(R.id.iv_cart_selector);
        mIvGoodsImg = findViewById(R.id.iv_goods_img);
        mTvGoodsName = findViewById(R.id.tv_goods_name);
        mTvGoodsSpecification = findViewById(R.id.tv_goods_specification);
        mTvGoodsPrice = findViewById(R.id.tv_goods_unit_price);
        mBtnMinus = findViewById(R.id.btn_cart_minus);
        mBtnAdd = findViewById(R.id.btn_cart_add);
        mTvGoodsNum = findViewById(R.id.tv_cart_goods_num);

        mOriginalRequest = GlideApp.with(frag).asDrawable().optionalFitCenter();
        mThumbnailRequest = GlideApp.with(frag).asDrawable().optionalFitCenter();
        if (mRMB == null) {
            mRMB = frag.getResources().getString(R.string.txt_rmb);
        }

    }

    public void bindData(GoodsShoppingCarInfoBean carInfoBean) {
        try {
            mIvSelect.setSelected(carInfoBean.getSelect());
            mOriginalRequest.load(carInfoBean.getGoodsOriginalImg())
                    .thumbnail(mThumbnailRequest.load(carInfoBean.getGoodsThumbnailImg()))
                    .into(mIvGoodsImg);
            mTvGoodsName.setText(carInfoBean.getGoodsName());
            mTvGoodsSpecification.setText(carInfoBean.getGoodsSpecification());
            mTvGoodsPrice.setText(String.format(mRMB, carInfoBean.getGoodsPrice()));
            int goodsNum = carInfoBean.getGoodsNum();
            mTvGoodsNum.setText(String.valueOf(goodsNum));
            mBtnMinus.setEnabled(goodsNum > 1);
            mIvSelect.setOnClickListener(onClick(carInfoBean));
            mBtnMinus.setOnClickListener(onClick(carInfoBean));
            mBtnAdd.setOnClickListener(onClick(carInfoBean));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final View.OnClickListener onClick(final GoodsShoppingCarInfoBean carInfoBean) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(getAdapterPosition(), carInfoBean, v);
                }
            }
        };
    }
}
