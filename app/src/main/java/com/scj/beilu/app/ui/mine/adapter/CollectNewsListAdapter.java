package com.scj.beilu.app.ui.mine.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;
import com.scj.beilu.app.ui.mine.MineCollectFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/9 17:20
 */
public class CollectNewsListAdapter extends RecyclerView.Adapter<CollectNewsListAdapter.NewsListViewHolder> {

    private List<NewsInfoBean> mNewsInfoList;
    private GlideRequest<Drawable> mOriginal;
    private GlideRequest<Drawable> mThumbnail;
    private OnItemClickListener mOnItemClickListener;

    public CollectNewsListAdapter(MineCollectFrag frag) {
        mOriginal = GlideApp.with(frag).asDrawable().centerCrop();
        mThumbnail = GlideApp.with(frag).asDrawable().centerCrop();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setNewsInfoList(List<NewsInfoBean> newsInfoList) {
        mNewsInfoList = newsInfoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect_news_content, parent, false);
        return new NewsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListViewHolder holder, int position) {
        try {
            NewsInfoBean infoBean = mNewsInfoList.get(position);
            holder.mTvTitle.setText(infoBean.getNewsTitle());
            holder.mTvSource.setText(infoBean.getNewsSource());
            mOriginal.load(infoBean.getNewsPic())
                    .thumbnail(0.2f)
                    .into(holder.mIvImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mNewsInfoList == null ? 0 : mNewsInfoList.size();
    }

    public class NewsListViewHolder extends BaseViewHolder {
        private TextView mTvTitle, mTvSource;
        private ImageView mIvImg;
        private ConstraintLayout mConstraintLayout;

        public NewsListViewHolder(View itemView) {
            super(itemView);
            mTvTitle = findViewById(R.id.tv_news_author_name);
            mTvSource = findViewById(R.id.tv_news_source);
            mIvImg = findViewById(R.id.iv_news_img);
            mConstraintLayout = findViewById(R.id.cl_parent_content);
            mConstraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), mNewsInfoList.get(getAdapterPosition()), v);
                    }
                }
            });
        }
    }

}
