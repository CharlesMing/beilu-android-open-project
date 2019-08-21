package com.scj.beilu.app.ui.store.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scj.beilu.app.R;
import com.scj.beilu.app.listener.OnGroupItemClickListener;
import com.scj.beilu.app.mvp.store.bean.GoodsSpecificationInfoBean;
import com.scj.beilu.app.mvp.store.bean.GoodsSpecificationListBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/28 22:01
 */
public class GoodsSpecificationGroupListAdapter extends RecyclerView.Adapter<SpecificationGroupViewHolder> {

    private List<GoodsSpecificationListBean> mGoodsSpecificationListList;
    private OnGroupItemClickListener<GoodsSpecificationInfoBean> mGroupItemClickListener;

    public void setGoodsSpecificationListList(List<GoodsSpecificationListBean> goodsSpecificationListList) {
        mGoodsSpecificationListList = goodsSpecificationListList;
        notifyDataSetChanged();
    }

    public void setGroupItemClickListener(OnGroupItemClickListener<GoodsSpecificationInfoBean> groupItemClickListener) {
        mGroupItemClickListener = groupItemClickListener;
    }

    @NonNull
    @Override
    public SpecificationGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_specification_group, parent,
                false);
        return new SpecificationGroupViewHolder(view, mGroupItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecificationGroupViewHolder holder, int position) {
        GoodsSpecificationListBean goodsSpecificationListBean = mGoodsSpecificationListList.get(position);

        holder.onBind(goodsSpecificationListBean, position);
    }

    @Override
    public int getItemCount() {
        return mGoodsSpecificationListList == null ? 0 : mGoodsSpecificationListList.size();
    }


}
