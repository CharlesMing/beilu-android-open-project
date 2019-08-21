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
import com.scj.beilu.app.mvp.action.bean.ActionInfoBean;
import com.scj.beilu.app.ui.action.ActionListFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/18 15:56
 */
public class ActionContentListAdapter extends RecyclerView.Adapter<ActionContentListAdapter.ActionInfoViewHolder> {

    private OnItemClickListener<ActionInfoBean> mOnItemClickListener;
    private List<ActionInfoBean> mActionInfoList;
    private GlideRequest<Drawable> mOriginalRequest;
    private GlideRequest<Drawable> mThumbnailRequest;

    public ActionContentListAdapter(ActionListFrag actionListFrag) {
        mOriginalRequest = GlideApp.with(actionListFrag).asDrawable().centerCrop();
        mThumbnailRequest = GlideApp.with(actionListFrag).asDrawable();
    }

    public void setActionInfoList(List<ActionInfoBean> actionInfoList) {
        mActionInfoList = actionInfoList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<ActionInfoBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ActionInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action_info_content, parent, false);
        return new ActionInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActionInfoViewHolder holder, int position) {
        try {
            ActionInfoBean actionInfo = mActionInfoList.get(position);
            holder.mTvActionInfo.setText(actionInfo.getActionName());
            mOriginalRequest.load(actionInfo.getActionPic())
                    .into(holder.mIvActionImg);
            holder.iv_action_lock_transparent.setVisibility(actionInfo.getIsLock() == 1 ? View.GONE : View.VISIBLE);
            holder.iv_action_lock.setVisibility(actionInfo.getIsLock() == 1 ? View.GONE : View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mActionInfoList == null ? 0 : mActionInfoList.size();
    }

    class ActionInfoViewHolder extends BaseViewHolder {
        private ImageView mIvActionImg;
        private TextView mTvActionInfo;
        private ImageView iv_action_lock, iv_action_lock_transparent;

        public ActionInfoViewHolder(View itemView) {
            super(itemView);
            mIvActionImg = findViewById(R.id.iv_action_info_img);
            mTvActionInfo = findViewById(R.id.tv_action_info_txt);
            iv_action_lock = findViewById(R.id.iv_action_lock);
            iv_action_lock_transparent = findViewById(R.id.iv_action_lock_transparent);
            itemView.setOnClickListener(v -> {
                ActionInfoBean actionInfoBean = mActionInfoList.get(getAdapterPosition());
                if (mOnItemClickListener != null && actionInfoBean != null) {
                    mOnItemClickListener.onItemClick(getAdapterPosition(), actionInfoBean, v);
                }
            });
            iv_action_lock_transparent.setOnClickListener(view -> {
                ActionInfoBean actionInfoBean = mActionInfoList.get(getAdapterPosition());
                if (mOnItemClickListener != null && actionInfoBean != null) {
                    mOnItemClickListener.onItemClick(getAdapterPosition(), actionInfoBean, view);
                }
            });
        }
    }

}
