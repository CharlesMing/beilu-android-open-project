package com.scj.beilu.app.ui.find.adapter;

import android.content.Context;
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
 * descriptin:还未加入组织
 */
public class NoIssueAdapter extends RecyclerView.Adapter {
    //组织
    final int VIEW_TYPE_CONTENT1 = 0x001;
    //推荐的组织
    final int VIEW_TYPE_CONTENT2 = 0x002;
    final int VIEW_TYPE_CONTENT3 = 0x003;
    private Context context;

    public NoIssueAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view;
        switch (viewType) {
            case VIEW_TYPE_CONTENT1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noissue_one, parent, false);
                return new Content1ViewHolder(view);
            case VIEW_TYPE_CONTENT2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_state_rv, parent, false);
                return new Content2ViewHolder(view);
            case VIEW_TYPE_CONTENT3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noissue_three, parent, false);
                return new Content3ViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noissue_one, parent, false);
                return new Content1ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //instanceof 判断其左边对象是否为其右边类的实例
        if (holder instanceof Content1ViewHolder) {
        }
        if (holder instanceof Content2ViewHolder) {
            Content2ViewHolder content2ViewHolder = (Content2ViewHolder) holder;
            content2ViewHolder.mRecyclerView.setAdapter(new MoreIssueAdapter());
            content2ViewHolder.mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(0, 15, 0, 15);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class Content1ViewHolder extends BaseViewHolder {

        public Content1ViewHolder(View itemView) {
            super(itemView);

        }
    }

    class Content2ViewHolder extends BaseViewHolder {
        private RecyclerView mRecyclerView;

        public Content2ViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = findViewById(R.id.mRecyclerView);
        }
    }
    class Content3ViewHolder extends BaseViewHolder {

        public Content3ViewHolder(View itemView) {
            super(itemView);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_CONTENT1;
        }
        if (position == 2)
            return VIEW_TYPE_CONTENT3;
        else
            return VIEW_TYPE_CONTENT2;

    }
}
