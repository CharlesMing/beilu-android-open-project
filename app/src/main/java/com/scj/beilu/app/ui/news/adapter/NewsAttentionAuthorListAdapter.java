package com.scj.beilu.app.ui.news.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
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
import com.scj.beilu.app.mvp.news.bean.NewsAuthorInfoBean;
import com.scj.beilu.app.ui.news.NewsAttentionListFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/5/11 17:46
 * 關注的作者列表
 */
public class NewsAttentionAuthorListAdapter extends RecyclerView.Adapter<NewsAttentionAuthorListAdapter.AuthorViewHolder> {
    private List<NewsAuthorInfoBean> authorInfoList;
    private GlideRequest<Drawable> mOriginalRequest;
    private GlideRequest<Drawable> mThumbnailRequest;
    private OnItemClickListener mOnItemClickListener;

    public NewsAttentionAuthorListAdapter(NewsAttentionListFrag frag) {
        mOriginalRequest = GlideApp.with(frag).asDrawable().optionalCircleCrop();
        mThumbnailRequest = GlideApp.with(frag).asDrawable().optionalCircleCrop();
    }

    public void setAuthorInfoList(List<NewsAuthorInfoBean> authorInfoList) {
        this.authorInfoList = authorInfoList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_my_attention_content, parent, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
        try {
            if (position == 3) {
                mOriginalRequest.load(R.drawable.ic_my_attiontion_more)
                        .into(holder.iv_news_my_attention_author);
                holder.tv_news_author_name.setText("我的关注");
            } else {
                NewsAuthorInfoBean authorInfoBean = authorInfoList.get(position);
                mOriginalRequest.load(authorInfoBean.getUserOriginalHead())
                        .thumbnail(mThumbnailRequest.load(authorInfoBean.getUserCompressionHead()))
                        .into(holder.iv_news_my_attention_author);
                holder.tv_news_author_name.setText(authorInfoBean.getUserNickName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (authorInfoList == null) {
            return 0;
        } else if (authorInfoList.size() > 3) {
            return 4;
        } else {
            return authorInfoList.size();
        }
    }

    class AuthorViewHolder extends BaseViewHolder {
        ImageView iv_news_my_attention_author;
        TextView tv_news_author_name;

        public AuthorViewHolder(View itemView) {
            super(itemView);
            iv_news_my_attention_author = findViewById(R.id.iv_news_my_attention_author);
            tv_news_author_name = findViewById(R.id.tv_news_author_name);
            iv_news_my_attention_author.setOnClickListener(view -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(getAdapterPosition(), authorInfoList.get(getAdapterPosition()), view);
                }
            });
        }
    }


}
