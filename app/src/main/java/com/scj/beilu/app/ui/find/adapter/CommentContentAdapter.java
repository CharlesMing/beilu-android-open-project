package com.scj.beilu.app.ui.find.adapter;

import android.graphics.Rect;
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
 * descriptin:  消息
 */
public class CommentContentAdapter extends RecyclerView.Adapter<CommentContentAdapter.CommentContentViewHolder> {

    @NonNull
    @Override
    public CommentContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_comment_three, parent, false);
        return new CommentContentViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentContentViewHolder holder, int position) {
        holder.mRecyclerView.setAdapter(new CommentReplyAdapter());
        holder.mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 10, 0, 30);
            }
        });
    }


    @Override
    public int getItemCount() {
        return 5;
    }

    class CommentContentViewHolder extends BaseViewHolder {
        private RecyclerView mRecyclerView;

        public CommentContentViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = findViewById(R.id.rv_reply);
        }
    }
}
