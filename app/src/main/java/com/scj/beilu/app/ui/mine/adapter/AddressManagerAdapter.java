package com.scj.beilu.app.ui.mine.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.mine.address.bean.AddressInfoBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/1/18 16:19
 */
public class AddressManagerAdapter extends RecyclerView.Adapter<AddressManagerAdapter.AddressManagerViewHolder> {
    private List<AddressInfoBean> mAllAddressList;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setAddressList(List<AddressInfoBean> addressList) {
        mAllAddressList = addressList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddressManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new AddressManagerViewHolder(view);
    }

    /**
     * 进行item数据绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull AddressManagerViewHolder holder, int position) {
        AddressInfoBean meAllAddressBean = mAllAddressList.get(position);
        holder.mTvName.setText(meAllAddressBean.getUserAddrName());
        holder.mTvTelePhone.setText(meAllAddressBean.getUserAddrTel());
        holder.mTvPlace.setText(meAllAddressBean.getUserAddrProvince() + meAllAddressBean.getUserAddrCity()
                + meAllAddressBean.getUserAddrDetail());
        holder.mTvEdit.setText("编辑");
        if (meAllAddressBean.getUserAddrDefault() == 1) {
            holder.mTvDefault.setVisibility(View.VISIBLE);
        } else {
            holder.mTvDefault.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mAllAddressList == null ? 0 : mAllAddressList.size();
    }


    public class AddressManagerViewHolder extends BaseViewHolder {
        private TextView mTvName;
        private TextView mTvTelePhone;
        private TextView mTvPlace;
        private TextView mTvEdit;
        private TextView mTvDefault;

        public AddressManagerViewHolder(View itemView) {
            super(itemView);
            mTvName = findViewById(R.id.tv_name);
            mTvTelePhone = findViewById(R.id.tv_telephone);
            mTvPlace = findViewById(R.id.tv_place);
            mTvEdit = findViewById(R.id.tv_edit);
            mTvDefault = findViewById(R.id.tv_default);
            itemView.setId(R.id.address_item_content);
            mTvEdit.setOnClickListener(view -> {
                if (mOnItemClickListener != null && mAllAddressList != null) {
                    mOnItemClickListener.onItemClick(getAdapterPosition(), mAllAddressList, view);
                }
            });
            itemView.setOnClickListener(v -> {
                if (mOnItemClickListener != null && mAllAddressList != null) {
                    mOnItemClickListener.onItemClick(getAdapterPosition(), mAllAddressList, v);
                }
            });
        }

    }


}
