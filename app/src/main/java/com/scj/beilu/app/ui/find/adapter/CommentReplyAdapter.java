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
 * date:2019/2/11
 * descriptin:消息回复
 */
public class CommentReplyAdapter extends RecyclerView.Adapter<CommentReplyAdapter.CommentReplyViewHolder> {
    @NonNull
    @Override
    public CommentReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reply, parent, false);
        return new CommentReplyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CommentReplyViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 2;
    }

    class CommentReplyViewHolder extends BaseViewHolder {
        public CommentReplyViewHolder(View itemView) {
            super(itemView);
        }
    }

}
