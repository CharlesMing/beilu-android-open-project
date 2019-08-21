package com.scj.beilu.app.ui.find.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * author:SunGuiLan
 * date:2019/2/7
 * descriptin:
 */
public class FindAttentionAdapter extends RecyclerView.Adapter {
    //图片+内容+文字
    final int VIEW_TYPE_CONTENT1 = 0x001;
    //视频+文字
    final int VIEW_TYPE_CONTENT2 = 0x002;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view;
        switch (viewType) {
            case VIEW_TYPE_CONTENT1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_hot_txt_img, parent, false);

                return new FindAttentionAdapter.Content1ViewHolder(view);
            case VIEW_TYPE_CONTENT2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_hot_video, parent, false);
                return new FindAttentionAdapter.Content2ViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_hot_txt_img, parent, false);
                return new FindAttentionAdapter.Content1ViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        //instanceof 判断其左边对象是否为其右边类的实例
        if (holder instanceof FindAttentionAdapter.Content1ViewHolder) {
//            FindAttentionAdapter.Content1ViewHolder content1ViewHolder = (FindAttentionAdapter.Content1ViewHolder) holder;
//            content1ViewHolder.tv_item_attention.setVisibility(View.GONE);
//            content1ViewHolder.mRvImgList.setAdapter(new FindImageAdapter());
        }

        if (holder instanceof FindAttentionAdapter.Content2ViewHolder) {
            FindAttentionAdapter.Content2ViewHolder content1ViewHolder = (FindAttentionAdapter.Content2ViewHolder) holder;
//            content1ViewHolder.tv_item_attention.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class Content1ViewHolder extends BaseViewHolder {
        RecyclerView mRvImgList;
        TextView tv_item_attention;

        public Content1ViewHolder(View itemView) {
            super(itemView);
            mRvImgList = findViewById(R.id.rv_news_img);
            tv_item_attention = findViewById(R.id.tv_item_attention);
//            mRvImgList.addItemDecoration(new RecyclerView.ItemDecoration() {
//                @Override
//                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                    super.getItemOffsets(outRect, view, parent, state);
//                    if (parent.getAdapter().getItemCount() == 10) {
//                        outRect.set(5, 10, 5, 0);
//                    } else {
//                        outRect.set(5, 10, 5, 0);
//                    }
//                }
//            });
        }
    }


    class Content2ViewHolder extends BaseViewHolder {
        TextView tv_item_attention;

        public Content2ViewHolder(View itemView) {
            super(itemView);
            tv_item_attention = findViewById(R.id.tv_item_attention);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return VIEW_TYPE_CONTENT1;
        } else return VIEW_TYPE_CONTENT2;
    }
}
