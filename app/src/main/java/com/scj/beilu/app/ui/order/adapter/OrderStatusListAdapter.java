package com.scj.beilu.app.ui.order.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mx.pro.lib.fragmentation.support.SupportFragment;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.order.bean.OrderInfoBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/1/17 16:04
 */
public class OrderStatusListAdapter extends RecyclerView.Adapter<OrderStatusListAdapter.OrderStatusViewHolder> {

    private List<OrderInfoBean> mOrderInfoList;
    private GlideRequest<Drawable> mOriginalRequest;
    private GlideRequest<Drawable> mThumbnailRequest;
    private final String mRMB;
    private OnItemClickListener<OrderInfoBean> mOnItemClickListener;

    public OrderStatusListAdapter(SupportFragment frag) {
        mOriginalRequest = GlideApp.with(frag).asDrawable().centerCrop();
        mThumbnailRequest = GlideApp.with(frag).asDrawable().centerCrop();
        mRMB = frag.getString(R.string.txt_rmb);
    }

    public void setOnItemClickListener(OnItemClickListener<OrderInfoBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOrderInfoList(List<OrderInfoBean> orderInfoList) {
        mOrderInfoList = orderInfoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_status, parent, false);
        return new OrderStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderStatusViewHolder holder, int position) {
        try {
            OrderInfoBean orderInfoBean = mOrderInfoList.get(position);
            holder.mTvGoodsName.setText(orderInfoBean.getProductName());
            mOriginalRequest.load(orderInfoBean.getProductPicOriginalAddr())
                    .thumbnail(mThumbnailRequest.load(orderInfoBean.getProductPicCompressionAddr()))
                    .into(holder.mIvGoodsImg);
            holder.mTvGoodsUnitPrice.setText(String.format(mRMB, orderInfoBean.getProductDiscountPrice()));
            holder.mTvGoodsNum.setText("x" + orderInfoBean.getProductCount());
            holder.mTvGoodsNumDesc.setText(String.format("共%d件商品", orderInfoBean.getProductCount()));
            holder.mTvGoodsTotalPrice.setText("合计: " + String.format(mRMB, orderInfoBean.getTotalPrice()));
            int orderStatusId = orderInfoBean.getOrderStatusId();
            holder.mTvStatus.setText(orderStatus(orderStatusId));
            holder.mBtnOrderInfo.setText(clickTxt(orderStatusId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String orderStatus(int orderStatus) {
        switch (orderStatus) {
            case 1:
                return "待付款";
            case 2:
                return "支付成功";
            case 3:
                return "待发货";
            case 4:
                return "已发货";
            case 6:
            case 7:
                return "退款中";
            case 8:
                return "同意退款";
            case 9:
                return "已完成";
            default:
                return "";
        }
    }

    private String clickTxt(int orderStatus) {
        switch (orderStatus) {
            case 1:
                return "去付款";
            case 2:
            case 3:
            case 4:
            case 5:
                return "查看物流";
            case 8:
                return "查看详情";
            case 6:
            case 7:
                return "查看退款进度";
            default:
                return "去付款";
        }

    }

    @Override
    public int getItemCount() {
        return mOrderInfoList == null ? 0 : mOrderInfoList.size();
    }

    public class OrderStatusViewHolder extends BaseViewHolder {
        private TextView mTvStatus, mTvGoodsName, mTvGoodsUnitPrice,
                mTvGoodsNum, mTvGoodsNumDesc, mTvGoodsTotalPrice;
        private ImageView mIvGoodsImg;
        private LinearLayout mLlOrderInfo;
        private Button mBtnOrderInfo;

        public OrderStatusViewHolder(View itemView) {
            super(itemView);
            mTvStatus = findViewById(R.id.tv_order_info_status_name);
            mTvGoodsName = findViewById(R.id.tv_order_info_goods_name);
            mTvGoodsUnitPrice = findViewById(R.id.tv_unit_price);
            mTvGoodsNum = findViewById(R.id.tv_course_order_by_num);
            mTvGoodsNumDesc = findViewById(R.id.tv_course_order_by_num_desc);
            mTvGoodsTotalPrice = findViewById(R.id.tv_order_info_goods_total_price);
            mIvGoodsImg = findViewById(R.id.iv_order_info_goods_img);
            mLlOrderInfo = findViewById(R.id.ll_order_info_content);
            mBtnOrderInfo = findViewById(R.id.btn_order_info_click);
            mLlOrderInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), mOrderInfoList.get(getAdapterPosition()), v);
                    }
                }
            });
            mBtnOrderInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), mOrderInfoList.get(getAdapterPosition()), v);
                    }
                }
            });
        }
    }
}
