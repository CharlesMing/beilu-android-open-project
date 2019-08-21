package com.scj.beilu.app.ui.news.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.common.entity.SearchHistoryEntity;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/18 17:31
 */
public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ContentViewHolder> {

    private List<SearchHistoryEntity> mHistoryList;
    private OnItemClickListener mItemClickListener;

    public void setHistoryList(List<SearchHistoryEntity> historyList) {
        mHistoryList = historyList;
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_history_tag, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        holder.mTvContent.setText(mHistoryList.get(position).getTagName());
    }

    @Override
    public int getItemCount() {
        return mHistoryList == null ? 0 : mHistoryList.size();
    }

    class ContentViewHolder extends BaseViewHolder {
        private TextView mTvContent;

        public ContentViewHolder(View itemView) {
            super(itemView);
            mTvContent = findViewById(R.id.tv_history_tag);
            mTvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SearchHistoryEntity searchHistoryEntity = mHistoryList.get(getAdapterPosition());
                    if (mItemClickListener != null && searchHistoryEntity != null) {
                        mItemClickListener.onItemClick(getAdapterPosition(), searchHistoryEntity, v);
                    }
                }
            });
        }
    }
}
