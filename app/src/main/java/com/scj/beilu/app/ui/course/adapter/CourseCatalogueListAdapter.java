package com.scj.beilu.app.ui.course.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.pro.lib.fragmentation.support.SupportFragment;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.course.bean.CourseVideoInfoBean;

import java.util.List;
import java.util.Locale;

/**
 * @author Mingxun
 * @time on 2019/3/21 20:52
 */
public class CourseCatalogueListAdapter extends RecyclerView.Adapter<CourseCatalogueListAdapter.CourseCatalogueViewHolder> {

    private List<CourseVideoInfoBean> mVideoInfoBeanList;
    private GlideRequest<Drawable> mOriginalRequest;
    private GlideRequest<Drawable> mThumbnailRequest;
    private OnItemClickListener<CourseVideoInfoBean> mOnItemClickListener;

    public CourseCatalogueListAdapter(SupportFragment frag) {
        mOriginalRequest = GlideApp.with(frag).asDrawable().centerCrop();
        mThumbnailRequest = GlideApp.with(frag).asDrawable().centerCrop();
    }

    public void setVideoInfoBeanList(List<CourseVideoInfoBean> videoInfoBeanList) {
        mVideoInfoBeanList = videoInfoBeanList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<CourseVideoInfoBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CourseCatalogueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_catalogue_content, parent, false);
        return new CourseCatalogueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseCatalogueViewHolder holder, int position) {
        CourseVideoInfoBean videoInfoBean = mVideoInfoBeanList.get(position);
        holder.mTvOrderByNum.setText(String.valueOf(position + 1));
        mOriginalRequest.load(videoInfoBean.getCourseVideoPic())
                .thumbnail(mThumbnailRequest.load(videoInfoBean.getCourseVideoPicZip()))
                .into(holder.mIvCourseImg);
        holder.mTvTitle.setText(videoInfoBean.getCourseVideoName());
        holder.mTvTime.setText(String.valueOf(time(videoInfoBean.getCourseVideoTime())));
        holder.mTvPlayCount.setText(String.valueOf(videoInfoBean.getCourseVideoBrowseCount()));
        if (videoInfoBean.getCourseVideoPrice() == null || videoInfoBean.getCourseVideoIsPurchase() == 1) {
            holder.mTvUnitPrice.setVisibility(View.GONE);
            holder.mIvPlayer.setVisibility(View.VISIBLE);
            holder.mTvIsFree.setText("播放");
        } else {
            holder.mIvPlayer.setVisibility(View.GONE);
            holder.mTvUnitPrice.setVisibility(View.VISIBLE);
            holder.mTvUnitPrice.setText("¥" + videoInfoBean.getCourseVideoPrice());
            holder.mTvIsFree.setText("单节购买");
        }

    }

    private String time(long seconds) {
        String standardTime;
        if (seconds <= 0) {
            standardTime = "00:00";
        } else if (seconds < 60) {
            standardTime = String.format(Locale.getDefault(), "00:%02d", seconds % 60);
        } else if (seconds < 3600) {
            standardTime = String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60);
        } else {
            standardTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", seconds / 3600, seconds % 3600 / 60, seconds % 60);
        }
        return standardTime;
    }

    @Override
    public int getItemCount() {
        return mVideoInfoBeanList == null ? 0 : mVideoInfoBeanList.size();
    }

    public class CourseCatalogueViewHolder extends BaseViewHolder {

        private ImageView mIvCourseImg;
        private TextView mTvOrderByNum;
        private TextView mTvTitle;
        private ImageView mIvPlayer;
        private TextView mTvUnitPrice;
        private TextView mTvIsFree;
        private TextView mTvTime, mTvPlayCount;

        public CourseCatalogueViewHolder(View itemView) {
            super(itemView);
            mIvCourseImg = findViewById(R.id.iv_course_img);
            mTvOrderByNum = findViewById(R.id.tv_course_order_by_num);
            mIvPlayer = findViewById(R.id.iv_course_player);
            mTvUnitPrice = findViewById(R.id.tv_course_unit_price);
            mTvIsFree = findViewById(R.id.tv_course_is_free);
            mTvTitle = findViewById(R.id.tv_course_title);
            mTvTime = findViewById(R.id.tv_course_time);
            mTvPlayCount = findViewById(R.id.tv_course_play_count);
            mIvPlayer.setOnClickListener(mOnClickListener);
            mTvUnitPrice.setOnClickListener(mOnClickListener);
            mIvCourseImg.setOnClickListener(mOnClickListener);
        }

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    CourseVideoInfoBean videoInfoBean = mVideoInfoBeanList.get(getAdapterPosition());
                    mOnItemClickListener.onItemClick(getAdapterPosition(), videoInfoBean, v);
                }
            }
        };
    }
}
