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
 * date:2019/2/7
 * descriptin:
 */
public class MoreIssueAdapter extends RecyclerView.Adapter<MoreIssueAdapter.FindIssueHolder> {
    @NonNull
    @Override
    public FindIssueHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_issue, parent, false);
        return new FindIssueHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FindIssueHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class FindIssueHolder extends BaseViewHolder {
        public FindIssueHolder(View itemView) {
            super(itemView);
        }
    }
}
