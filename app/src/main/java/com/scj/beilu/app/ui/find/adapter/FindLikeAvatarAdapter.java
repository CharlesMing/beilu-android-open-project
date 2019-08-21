package com.scj.beilu.app.ui.find.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.GlideRequests;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.mvp.find.bean.FindInfoLikeMemberBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/25 15:56
 */
public class FindLikeAvatarAdapter extends RecyclerView.Adapter<FindLikeAvatarAdapter.AvatarViewHolder> {
    private List<FindInfoLikeMemberBean> mMemberList;
    private GlideRequest<Drawable> mOriginalGlideRequest;

    public FindLikeAvatarAdapter(List<FindInfoLikeMemberBean> likeNumbers, GlideRequests requests) {
        mMemberList = likeNumbers;
        mOriginalGlideRequest = requests.asDrawable().circleCrop();
    }

    @NonNull
    @Override
    public AvatarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_like_avatar, parent, false);
        return new AvatarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AvatarViewHolder holder, int position) {
        FindInfoLikeMemberBean memberBean = mMemberList.get(position);
        mOriginalGlideRequest
                .load(memberBean.getUserHead())
                .error(R.drawable.ic_default_avatar)
                .circleCrop()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mMemberList == null ? 0 : mMemberList.size();
    }

    class AvatarViewHolder extends BaseViewHolder {
        private ImageView mImageView;

        public AvatarViewHolder(View itemView) {
            super(itemView);
            mImageView = findViewById(R.id.iv_avatar);
        }
    }
}
