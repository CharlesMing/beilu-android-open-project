package com.scj.beilu.app.ui.find.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.scj.beilu.app.GlideRequests;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;

import java.util.List;

/**
 * author:SunGuiLan
 * date:2019/1/30
 * descriptin: 发现
 */
public class FindMListAdapter extends RecyclerView.Adapter {
    //图片+内容+文字
    final int VIEW_TYPE_TXT_IMG = 0x001;
    //视频+文字
    final int VIEW_TYPE_VIDEO = 0x003;

    private List<FindInfoBean> mFindInfoList;

    private OnItemClickListener<FindInfoBean> mOnItemClickListener;
    private GlideRequests mGlideRequests;

    public FindMListAdapter(GlideRequests glideRequests) {
        this.mGlideRequests = glideRequests;
    }

    public void setOnItemClickListener(OnItemClickListener<FindInfoBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setFindInfoList(List<FindInfoBean> findInfoList) {
        mFindInfoList = findInfoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_TXT_IMG) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_hot_txt_img, parent, false);
            return new ContentTxtImgViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_hot_video, parent, false);
            return new ContentVideoViewHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        FindInfoBean infoFind = mFindInfoList.get(position);
        if (holder instanceof ContentTxtImgViewHolder) {
            ContentTxtImgViewHolder txtImgViewHolder = (ContentTxtImgViewHolder) holder;

            mGlideRequests
                    .load(infoFind.getUserHead())
                    .transform(new CircleCrop())
                    .into(txtImgViewHolder.mIvAvatar);

            txtImgViewHolder.mTvUserName.setText(infoFind.getUserNickName());
            txtImgViewHolder.mTvFindContent.setText(infoFind.getDynamicDec());
        } else if (holder instanceof ContentVideoViewHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return mFindInfoList == null ? 0 : mFindInfoList.size();
    }

    class ContentTxtImgViewHolder extends BaseViewHolder {

        private ImageView mIvAvatar;
        private TextView mTvUserName, mTvFindContent;

        RecyclerView mRvImgList;

        public ContentTxtImgViewHolder(View itemView) {
            super(itemView);
            mIvAvatar = findViewById(R.id.iv_item_find_avatar);
            mTvUserName = findViewById(R.id.tv_item_find_user_name);
            mTvFindContent = findViewById(R.id.tv_item_find_txt);
            mRvImgList = findViewById(R.id.rv_item_find_pic);

        }
    }


    class ContentVideoViewHolder extends BaseViewHolder {
        public ContentVideoViewHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public int getItemViewType(int position) {
        FindInfoBean findInfo = mFindInfoList.get(position);
        if (findInfo.getDynamicVideoAddr() != null) {
            return VIEW_TYPE_VIDEO;
        } else {
            return VIEW_TYPE_TXT_IMG;
        }

    }
}