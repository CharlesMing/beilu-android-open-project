package com.scj.beilu.app.ui.merchant.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scj.beilu.app.R;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoDetailsListBean;
import com.scj.beilu.app.ui.merchant.MerchantDetailFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/15 17:15
 */
public class MerchantInfoDetailListAdapter extends RecyclerView.Adapter {

    private List<MerchantInfoDetailsListBean> mDetailsList;

    public static final int TYPE_COACH = 0x001;
    public static final int TYPE_SHIP = 0x002;
    private final int TYPE_BASE = 0x003;
    public static final int TYPE_FACILITY = 0x004;
    private MerchantDetailFrag mDetailFrag;
    private OnItemClickListener mOnItemClickListener;

    public MerchantInfoDetailListAdapter(MerchantDetailFrag frag) {
        mDetailFrag = frag;
    }

    public void setDetailsList(List<MerchantInfoDetailsListBean> detailsList) {
        mDetailsList = detailsList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mDetailsList == null ? 0 : mDetailsList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_BASE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchant_info_base_content, parent, false);
            return new MerchantInfoBaseInfoViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchant_info_content, parent, false);
            return new MerchantInfoContentViewHolder(mDetailFrag, view, viewType, mOnItemClickListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MerchantInfoDetailsListBean details = mDetailsList.get(position);
        if (holder instanceof MerchantInfoBaseInfoViewHolder) {
            MerchantInfoBaseInfoViewHolder baseInfoViewHolder = (MerchantInfoBaseInfoViewHolder) holder;
            try {
                baseInfoViewHolder.mTvName1.setText("开店：" + details.getStartTime());
                baseInfoViewHolder.mTvName2.setText("关店：" + details.getEndTime());
                baseInfoViewHolder.mTvName3.setText("开业：" + details.getStartBusinessTime());
                baseInfoViewHolder.mTvName4.setText("装修：" + details.getFitmentTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (holder instanceof MerchantInfoContentViewHolder) {
            MerchantInfoContentViewHolder contentInfoViewHolder = (MerchantInfoContentViewHolder) holder;

            try {
                contentInfoViewHolder.onBindData(details, getItemViewType(position));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDetailsList.get(position).getCoachList() != null) {
            return TYPE_COACH;
        } else if (mDetailsList.get(position).getMemberShipList() != null) {
            return TYPE_SHIP;
        } else if (mDetailsList.get(position).getInstallList() != null) {
            return TYPE_FACILITY;
        } else {
            return TYPE_BASE;
        }
    }


}
