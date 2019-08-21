package com.scj.beilu.app.ui.mine.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * @author Mingxun
 * @time on 2019/1/14 21:44
 */
public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.LikeViewHolder> {
    @NonNull
    @Override
    public LikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_like, parent, false);
        return new LikeViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull LikeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class LikeViewHolder extends BaseViewHolder {


        public LikeViewHolder(View itemView) {
            super(itemView);
        }
    }
}
