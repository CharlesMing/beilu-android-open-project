package com.scj.beilu.app.ui.action.adapter;

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
import com.scj.beilu.app.mvp.action.bean.ActionThirdTypeInfoBean;
import com.scj.beilu.app.ui.action.ActionListFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/18 11:34
 */
public class ActionThirdTypeListAdapter extends RecyclerView.Adapter<ActionThirdTypeListAdapter.ThirdViewHolder> {

    private GlideRequest<Drawable> mOriginalRequest;
    private GlideRequest<Drawable> mThumbnailRequest;
    private List<ActionThirdTypeInfoBean> mThirdTypeInfoList;
    private OnItemClickListener mOnItemClickListener;

    public ActionThirdTypeListAdapter(ActionListFrag actionListFrag) {
        mOriginalRequest = GlideApp.with(actionListFrag).asDrawable().optionalFitCenter();
        mThumbnailRequest = GlideApp.with(actionListFrag).asDrawable().optionalFitCenter();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setThirdTypeInfoList(List<ActionThirdTypeInfoBean> thirdTypeInfoList) {
        mThirdTypeInfoList = thirdTypeInfoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThirdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action_third_type, parent, false);
        return new ThirdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThirdViewHolder holder, int position) {
        try {
            ActionThirdTypeInfoBean infoBean = mThirdTypeInfoList.get(position);
            holder.mTvActionTypeVal.setText(infoBean.getActionCateName());
            mOriginalRequest.load(infoBean.getActionCatePic())
                    .thumbnail(mThumbnailRequest.load(infoBean.getActionCatePicZip()))
                    .into(holder.mIvActionTypeBg);

            holder.mIvActionSelect.setVisibility(infoBean.isSelected() ? View.VISIBLE : View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mThirdTypeInfoList == null ? 0 : mThirdTypeInfoList.size();
    }

    class ThirdViewHolder extends BaseViewHolder {
        private ImageView mIvActionTypeBg;
        private TextView mTvActionTypeVal;
        private ImageView mIvActionSelect;

        public ThirdViewHolder(View itemView) {
            super(itemView);
            mIvActionTypeBg = findViewById(R.id.iv_action_type_img);
            mTvActionTypeVal = findViewById(R.id.tv_action_type);
            mIvActionSelect = findViewById(R.id.iv_action_type_select);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActionThirdTypeInfoBean actionThirdTypeInfoBean = mThirdTypeInfoList.get(getAdapterPosition());
                    if (mOnItemClickListener != null && actionThirdTypeInfoBean != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), actionThirdTypeInfoBean, v);
                    }
                }
            });
        }
    }

}
