package com.scj.beilu.app.ui.find.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * author:SunGuiLan
 * date:2019/2/12
 * descriptin:
 */
public class CommentRecommendAdapter extends RecyclerView.Adapter<CommentRecommendAdapter.CommentRecommendHolder> {
    @NonNull
    @Override
    public CommentRecommendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_item, parent, false);
        return new CommentRecommendHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentRecommendHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class CommentRecommendHolder extends BaseViewHolder {

        public CommentRecommendHolder(View itemView) {
            super(itemView);
        }
    }
}
