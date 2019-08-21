package com.scj.beilu.app.ui.merchant.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
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
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoCoachBean;
import com.scj.beilu.app.ui.merchant.MerchantDetailFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/15 19:29
 * 教练
 */
public class MerchantInfoCoachListAdapter extends RecyclerView.Adapter<MerchantInfoCoachListAdapter.CoachViewHolder> {

    private List<MerchantInfoCoachBean> mCoachBeans;
    private GlideRequest<Drawable> mOriginal, mThumbnail;
    private OnItemClickListener mOnItemClickListener;

    public MerchantInfoCoachListAdapter(MerchantDetailFrag frag) {
        mOriginal = GlideApp.with(frag).asDrawable().optionalCircleCrop();
        mThumbnail = GlideApp.with(frag).asDrawable().optionalCircleCrop();
    }

    public void setCoachBeans(List<MerchantInfoCoachBean> coachBeans) {
        mCoachBeans = coachBeans;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CoachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchant_coach, parent, false);
        return new CoachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachViewHolder holder, int position) {
        try {
            MerchantInfoCoachBean coachBean = mCoachBeans.get(position);
            mOriginal.load(coachBean.getCoachHead())
                    .thumbnail(mThumbnail.load(coachBean.getCoachHeadZip()))
                    .into(holder.mIvAvatar);

            holder.mTvCoachName.setText(coachBean.getCoachName());
            holder.mTvCoachAdvantage.setText(coachBean.getCoachExpert());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mCoachBeans == null ? 0 : mCoachBeans.size();
    }

    public class CoachViewHolder extends BaseViewHolder {
        private ImageView mIvAvatar;
        private TextView mTvCoachName, mTvCoachAdvantage;

        public CoachViewHolder(View itemView) {
            super(itemView);
            mIvAvatar = findViewById(R.id.iv_merchant_coach_avatar);
            mTvCoachName = findViewById(R.id.tv_coach_name);
            mTvCoachAdvantage = findViewById(R.id.tv_coach_advantage);
            mIvAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), mCoachBeans.get(getAdapterPosition()), v);
                    }
                }
            });
        }
    }

}
