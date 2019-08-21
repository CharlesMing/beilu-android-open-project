package com.scj.beilu.app.ui.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * author:SunGuiLan
 * date:2019/1/30
 * descriptin: 发现
 */
public class HomeDynamicAdapter extends RecyclerView.Adapter {
    //图片+内容+文字
    final int VIEW_TYPE_CONTENT1 = 0x001;
    //视频+文字
    final int VIEW_TYPE_CONTENT3 = 0x003;

    private OnItemClickListen onItemClickListen;

    public interface OnItemClickListen {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListen(OnItemClickListen onItemClickListen) {
        this.onItemClickListen = onItemClickListen;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view;
        switch (viewType) {
            case VIEW_TYPE_CONTENT1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_hot_txt_img, parent, false);
                return new Content1ViewHolder(view);

            case VIEW_TYPE_CONTENT3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_hot_video, parent, false);
                return new Content3ViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_hot_txt_img, parent, false);
                return new Content1ViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        //instanceof 判断其左边对象是否为其右边类的实例
        if (holder instanceof Content1ViewHolder) {
//            Content1ViewHolder content1ViewHolder = (Content1ViewHolder) holder;
//            content1ViewHolder.mRvImgList.setAdapter(new HomeImageAdapter());
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (onItemClickListen != null)
//                        onItemClickListen.onItemClick(holder.itemView, position);
//                }
//            });
        }


    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class Content1ViewHolder extends BaseViewHolder {
        RecyclerView mRvImgList;

        public Content1ViewHolder(View itemView) {
            super(itemView);
            mRvImgList = findViewById(R.id.rv_news_img);
//            mRvImgList.addItemDecoration(new RecyclerView.ItemDecoration() {
//                @Override
//                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                    super.getItemOffsets(outRect, view, parent, state);
//                    if (parent.getAdapter().getItemCount() == 10) {
//                        outRect.set(5, 0, 5, 0);
//                    } else {
//                        outRect.set(5, 0, 5, 0);
//                    }
//                }
//            });
        }
    }


    class Content3ViewHolder extends BaseViewHolder {
        public Content3ViewHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public int getItemViewType(int position) {
         if (position % 2 == 0) {
            return VIEW_TYPE_CONTENT1;
        } else return VIEW_TYPE_CONTENT3;
    }
}