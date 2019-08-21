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
 * date:2019/2/10
 * descriptin:
 */
public class IssueMemberAdapter extends RecyclerView.Adapter<IssueMemberAdapter.IssueMemberHolder> {
    @NonNull
    @Override
    public IssueMemberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_like_avatar, parent, false);
        return new IssueMemberHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull IssueMemberHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class IssueMemberHolder extends BaseViewHolder {

        public IssueMemberHolder(View itemView) {
            super(itemView);
        }
    }
}
