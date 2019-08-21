package com.scj.beilu.app.ui.home.adapter;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.scj.beilu.app.R;

import com.scj.beilu.app.base.BaseViewHolder;




/**
 * author: SunGuiLan
 * date:2019/2/13
 * descriptin:
 */
public class HomeNewsAdapter extends RecyclerView.Adapter {
    final int VIEW_TYPE_CONTENT1 = 0x002;
    final int VIEW_TYPE_CONTENT2 = 0x003;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view;
        switch (viewType) {
            case VIEW_TYPE_CONTENT1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_news_content1, parent, false);
                return new Content1ViewHolder(view);
            case VIEW_TYPE_CONTENT2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_news_content2, parent, false);
                return new Content2ViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_news_content2, parent, false);
                return new Content2ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof Content1ViewHolder) {
            Content1ViewHolder content1ViewHolder = (Content1ViewHolder) holder;
            content1ViewHolder.mRvImgList.setAdapter(new HomeImageAdapter());
        }
    }


    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return VIEW_TYPE_CONTENT2;
        } else {
            return VIEW_TYPE_CONTENT1;
        }
    }


    class Content1ViewHolder extends BaseViewHolder {
        RecyclerView mRvImgList;

        public Content1ViewHolder(View itemView) {
            super(itemView);
            mRvImgList = findViewById(R.id.rv_news_img);
            mRvImgList.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    if (parent.getAdapter().getItemCount() == 10) {
                        outRect.set(5, 0, 0, 0);
                    } else {
                        outRect.set(5, 0, 5, 0);
                    }
                }
            });
        }
    }


    class Content2ViewHolder extends BaseViewHolder {
        public Content2ViewHolder(View itemView) {
            super(itemView);

        }
    }


}

