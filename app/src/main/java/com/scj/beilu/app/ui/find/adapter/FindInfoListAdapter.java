package com.scj.beilu.app.ui.find.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.GlideRequests;
import com.scj.beilu.app.R;
import com.scj.beilu.app.listener.OnGroupItemClickListener;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;
import com.scj.beilu.app.mvp.find.bean.FindImageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/21 15:43
 */
public class FindInfoListAdapter extends RecyclerView.Adapter {
    //图片+内容+文字
    private final int VIEW_TYPE_TXT_IMG = 0x100;
    //视频+文字
    private final int VIEW_TYPE_VIDEO = 0x101;
    private List<FindInfoBean> mFindInfoList;
    private OnItemClickListener<FindInfoBean> mOnItemClickListener;
    private OnGroupItemClickListener<ArrayList<FindImageBean>> mOnGroupItemClickListener;

    private GlideRequest<Drawable> mThumbnailRequest;
    private GlideRequest<Drawable> mOriginalRequest;
    private GlideRequests mGlideRequests;

    private Context mContext;
    private final int mRestSize;
    public static final String TAG = "FindInfoListAdapter";

    public FindInfoListAdapter(GlideRequests glideRequests, Context context) {
        mGlideRequests = glideRequests;
        this.mThumbnailRequest = glideRequests.asDrawable().centerCrop();
        this.mOriginalRequest = glideRequests.asDrawable().centerCrop();
        this.mContext = context;
        mRestSize = mContext.getResources().getDimensionPixelOffset(R.dimen.rect_5);
    }

    public void setOnItemClickListener(OnItemClickListener<FindInfoBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnGroupItemClickListener(OnGroupItemClickListener<ArrayList<FindImageBean>> onGroupItemClickListener) {
        mOnGroupItemClickListener = onGroupItemClickListener;
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
            return new ContentTxtImgViewHolder(mContext, view, mRestSize);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_hot_video, parent, false);
            return new ContentVideoViewHolder(mContext, view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final FindInfoBean infoFind = mFindInfoList.get(position);

        if (holder instanceof ContentTxtImgViewHolder) {
            try {
                ContentTxtImgViewHolder txtImgViewHolder = (ContentTxtImgViewHolder) holder;
                final ArrayList<FindImageBean> picList = infoFind.getDynamicPic();
                if (picList == null) return;
                int size = picList.size();
                if (size == 1) {
                    txtImgViewHolder.mRvImgList.setVisibility(View.GONE);
                    txtImgViewHolder.mIvPic.setVisibility(View.VISIBLE);
                    mOriginalRequest.load(picList.get(0).getDynamicPicAddr())
                            .thumbnail(mThumbnailRequest.load(picList.get(0).getDynamicPicZip()))
                            .into(txtImgViewHolder.mIvPic);

                    txtImgViewHolder.mIvPic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onItemClick(0, infoFind, v);
                            }
                        }
                    });
                } else {
                    txtImgViewHolder.mIvPic.setVisibility(View.GONE);
                    txtImgViewHolder.mRvImgList.setVisibility(View.VISIBLE);
                    FindListContentImageAdapter imageAdapter = new FindListContentImageAdapter(mGlideRequests, position);
                    imageAdapter.setOnGroupItemClickListener(mOnGroupItemClickListener);
                    imageAdapter.setHasStableIds(true);
                    imageAdapter.setFindImageList(picList);
                    txtImgViewHolder.mRvImgList.setAdapter(imageAdapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (holder instanceof ContentVideoViewHolder) {
            ContentVideoViewHolder videoViewHolder = (ContentVideoViewHolder) holder;
            videoViewHolder.onBind(position, infoFind);
        }
        BaseContentViewHolder contentViewHolder = (BaseContentViewHolder) holder;
        contentViewHolder.onBind(mGlideRequests, infoFind);
        contentViewHolder.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mFindInfoList == null ? 0 : mFindInfoList.size();
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
