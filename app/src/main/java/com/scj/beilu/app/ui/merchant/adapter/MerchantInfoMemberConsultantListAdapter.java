package com.scj.beilu.app.ui.merchant.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoMemberShipListBean;
import com.scj.beilu.app.ui.merchant.MerchantDetailFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/15 19:29
 * 会籍顾问
 */
public class MerchantInfoMemberConsultantListAdapter extends RecyclerView.Adapter<MerchantInfoMemberConsultantListAdapter.MemberConsultViewHolder> {

    private List<MerchantInfoMemberShipListBean> mMemberShipListBeans;
    private GlideRequest<Drawable> mOriginal, mThumbnail;
    private OnItemClickListener mOnItemClickListener;

    public MerchantInfoMemberConsultantListAdapter(MerchantDetailFrag frag) {
        mOriginal = GlideApp.with(frag).asDrawable().optionalCircleCrop();
        mThumbnail = GlideApp.with(frag).asDrawable().optionalCircleCrop();
    }

    public void setMemberShipListBeans(List<MerchantInfoMemberShipListBean> memberShipListBeans) {
        mMemberShipListBeans = memberShipListBeans;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MemberConsultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchant_member_ship, parent, false);
        return new MemberConsultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberConsultViewHolder holder, int position) {
        try {
            MerchantInfoMemberShipListBean shipListBean = mMemberShipListBeans.get(position);
            mOriginal.load(shipListBean.getMemberShipHead())
                    .thumbnail(mThumbnail.load(shipListBean.getMemberShipHeadZip()))
                    .into(holder.mIvMembershipAvatar);
            holder.mTvMembershipName.setText(shipListBean.getMemberShipName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mMemberShipListBeans == null ? 0 : mMemberShipListBeans.size();
    }

    public class MemberConsultViewHolder extends BaseViewHolder {
        private ImageView mIvMembershipAvatar;
        private TextView mTvMembershipName;
        private Button mBtnContact;

        public MemberConsultViewHolder(View itemView) {
            super(itemView);
            mIvMembershipAvatar = findViewById(R.id.iv_merchant_membership_avatar);
            mTvMembershipName = findViewById(R.id.tv_membership_name);
            mBtnContact = findViewById(R.id.btn_membership_contact);
            mBtnContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), mMemberShipListBeans.get(getAdapterPosition()), v);
                    }
                }
            });
        }
    }
}
