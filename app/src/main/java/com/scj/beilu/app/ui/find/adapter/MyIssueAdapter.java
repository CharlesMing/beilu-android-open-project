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
 * date:2019/2/9
 * descriptin:我的组织
 */
public class MyIssueAdapter extends RecyclerView.Adapter<MyIssueAdapter.MyIssueHolder> {
    @NonNull
    @Override
    public MyIssueHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_issuename, parent, false);
        return new MyIssueHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyIssueHolder holder, int position) {
    }


    @Override
    public int getItemCount() {
        return 3;
    }

    class MyIssueHolder extends BaseViewHolder {
        private RecyclerView mRecyclerView;
        public MyIssueHolder(View itemView) {
            super(itemView);
            mRecyclerView=findViewById(R.id.rv_myIssue);
        }
    }
}
