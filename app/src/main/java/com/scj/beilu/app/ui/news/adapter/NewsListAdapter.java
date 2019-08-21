package com.scj.beilu.app.ui.news.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scj.beilu.app.GlideRequests;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/1/24 17:42
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.Content2ViewHolder> {
    private List<NewsInfoBean> mNewsListEntityList;
    private final LayoutInflater mInflater;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;
    private GlideRequests mRequests;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public NewsListAdapter(Context context, GlideRequests requests) {
        this.mRequests = requests;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setNewsListEntityList(List<NewsInfoBean> newsListEntityList) {
        mNewsListEntityList = newsListEntityList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public Content2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_news_content, parent, false);
        return new Content2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Content2ViewHolder holder, int position) {
        NewsInfoBean newsListBean = mNewsListEntityList.get(position);
        holder.mTvTitle.setText(newsListBean.getNewsTitle());
        String mSource = mContext.getResources().getString(R.string.txt_news_item_source);
        mSource = String.format(mSource, newsListBean.getNewsSource(), String.valueOf(newsListBean.getNewsBrowseCount()));
        holder.mTvSource.setText(mSource);
        mRequests.load(newsListBean.getNewsPic())
                .thumbnail(0.2f)
                .into(holder.mIvImg);

    }


    @Override
    public int getItemCount() {
        return mNewsListEntityList == null ? 0 : mNewsListEntityList.size();
    }


    class Content2ViewHolder extends BaseViewHolder {

        private TextView mTvTitle;
        private TextView mTvSource;
        private ImageView mIvImg;

        public Content2ViewHolder(View itemView) {
            super(itemView);
            mTvTitle = findViewById(R.id.tv_news_author_name);
            mTvSource = findViewById(R.id.tv_news_source);
            mIvImg = findViewById(R.id.iv_news_img);
            itemView.setId(R.id.item_news_view_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewsInfoBean newsListEntity = mNewsListEntityList.get(getAdapterPosition());
                    if (mOnItemClickListener != null && newsListEntity != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), newsListEntity, v);
                    }
                }
            });
        }
    }


}

