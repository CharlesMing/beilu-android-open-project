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
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.news.bean.NewsAuthorInfoBean;
import com.scj.beilu.app.ui.news.NewsMyAttentionAuthorListFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/5/11 17:46
 * 作者列表
 */
public class NewsAuthorListAdapter extends RecyclerView.Adapter<NewsAuthorListAdapter.RecommendAuthorViewHolder> {

    private List<NewsAuthorInfoBean> authorInfoList;
    private GlideRequest<Drawable> mOriginalRequest;
    private GlideRequest<Drawable> mThumbnailRequest;
    private OnItemClickListener mOnItemClickListener;
    private int mResLayout = R.layout.item_news_recommend_attention_author;
    private boolean isRecommend = true;

    public NewsAuthorListAdapter(BaseMvpFragment frag) {
        if (frag instanceof NewsMyAttentionAuthorListFrag) {
            mResLayout = R.layout.item_news_my_attention_author_content;
            isRecommend = false;
        }
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
    public RecommendAuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mResLayout, parent, false);
        return new RecommendAuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendAuthorViewHolder holder, int position) {
        try {
            NewsAuthorInfoBean authorInfoBean = authorInfoList.get(position);
            mOriginalRequest.load(authorInfoBean.getUserOriginalHead())
                    .thumbnail(mThumbnailRequest.load(authorInfoBean.getUserCompressionHead()))
                    .into(holder.iv_news_author_avatar);
            holder.tv_news_author_name.setText(authorInfoBean.getUserNickName());
            String desc = "无";
            if (authorInfoBean.getUserBrief() != null && authorInfoBean.getUserBrief().length() > 0) {
                desc = authorInfoBean.getUserBrief();
            }
            holder.tv_news_content.setText(desc);
            int count = authorInfoBean.getFansCount();
            int fansOut = count / 10000;
            String countStr;
            if (fansOut > 0) {
                countStr = fansOut + "万";
            } else {
                countStr = count + "";
            }
            holder.tv_news_fans.setText(countStr + "粉丝");
            holder.tv_news_recommend_attention_status.setText(authorInfoBean.getIsFocus() == 1 ? "已关注" : "+ 关注");
            holder.tv_news_recommend_attention_status.setSelected(authorInfoBean.getIsFocus() == 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (authorInfoList == null) {
            return 0;
        } else if (isRecommend && authorInfoList.size() >= 3) {
            return 3;
        } else {
            return authorInfoList.size();
        }
    }

    class RecommendAuthorViewHolder extends BaseViewHolder {
        private ImageView iv_news_author_avatar;
        private TextView tv_news_author_name, tv_news_content, tv_news_fans, tv_news_recommend_attention_status;

        public RecommendAuthorViewHolder(View itemView) {
            super(itemView);
            iv_news_author_avatar = findViewById(R.id.iv_news_author_avatar);
            tv_news_author_name = findViewById(R.id.tv_news_author_name);
            tv_news_content = findViewById(R.id.tv_news_content);
            tv_news_fans = findViewById(R.id.tv_news_fans);
            tv_news_recommend_attention_status = findViewById(R.id.tv_news_recommend_attention_status);
            tv_news_recommend_attention_status.setOnClickListener(onItemClick);
            iv_news_author_avatar.setOnClickListener(onItemClick);
        }

        final View.OnClickListener onItemClick = view -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition(), authorInfoList.get(getAdapterPosition()), view);
            }
        };
    }
}
