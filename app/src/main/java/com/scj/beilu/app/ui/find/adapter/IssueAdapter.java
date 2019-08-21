package com.scj.beilu.app.ui.find.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * author:SunGuiLan
 * date:2019/2/7
 * descriptin:已经加入了组织
 * */
public class IssueAdapter extends RecyclerView.Adapter {
    //我的组织
    final int VIEW_TYPE_CONTENT1 = 0x001;
    //组织动态
    final int VIEW_TYPE_CONTENT2 = 0x002;
    private Context context;

    public IssueAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view;
        switch (viewType) {
            case VIEW_TYPE_CONTENT1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myissue_one, parent, false);
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        Logger.e(view.getWidth() + "====");
                    }
                });
                return new Content1ViewHolder(view);
            case VIEW_TYPE_CONTENT2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myissue_two, parent, false);
                return new Content2ViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myissue_one, parent, false);
                return new Content1ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //instanceof 判断其左边对象是否为其右边类的实例
        if (holder instanceof Content1ViewHolder) {
            Content1ViewHolder content1ViewHolder = (Content1ViewHolder) holder;
            content1ViewHolder.mRecyclerView.setAdapter(new MyIssueAdapter());
            content1ViewHolder.mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(0, 5, 5, 5);
                }
            });
        }
        if (holder instanceof Content2ViewHolder) {
            Content2ViewHolder content2ViewHolder = (Content2ViewHolder) holder;
//            content2ViewHolder.mRecyclerView.setAdapter(new FindMListAdapter());
            content2ViewHolder.mRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class Content1ViewHolder extends BaseViewHolder {
        RecyclerView mRecyclerView;

        public Content1ViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = findViewById(R.id.rv_myIssue);

        }
    }


    class Content2ViewHolder extends BaseViewHolder {
        private RecyclerView mRecyclerView;

        public Content2ViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = findViewById(R.id.mRecyclerView);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_CONTENT1;
        } else return VIEW_TYPE_CONTENT2;
    }

}
