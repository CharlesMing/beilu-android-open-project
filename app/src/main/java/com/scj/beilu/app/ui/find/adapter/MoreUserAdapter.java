package com.scj.beilu.app.ui.find.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * author: SunGuiLan
 * date:2019/2/1
 * descriptin:查找更多用户
 */
public class MoreUserAdapter extends RecyclerView.Adapter<MoreUserAdapter.FindMoreUserViewHolder> {

    @NonNull
    @Override
    public FindMoreUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more_user, parent, false);
        return new MoreUserAdapter.FindMoreUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindMoreUserViewHolder holder, int position) {
        holder.mTvAttention.setSelected(position % 2 == 0);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class FindMoreUserViewHolder extends BaseViewHolder {
        private TextView mTvAttention;

        public FindMoreUserViewHolder(View itemView) {
            super(itemView);
            mTvAttention = findViewById(R.id.tv_item_attention);
        }
    }
}
