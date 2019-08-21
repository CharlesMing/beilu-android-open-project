package com.scj.beilu.app.ui.store.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scj.beilu.app.R;
import com.scj.beilu.app.listener.OnGroupItemClickListener;
import com.scj.beilu.app.mvp.store.bean.GoodsSpecificationInfoBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/28 22:01
 */
public class GoodsSpecificationChildListAdapter extends RecyclerView.Adapter<SpecificationChildViewHolder> {

    private int mGroupPosition;
    private List<GoodsSpecificationInfoBean> mSpecificationInfoBeans;
    private OnGroupItemClickListener<GoodsSpecificationInfoBean> mGroupItemClickListener;

    public GoodsSpecificationChildListAdapter(OnGroupItemClickListener<GoodsSpecificationInfoBean> itemClickListener) {
        mGroupItemClickListener = itemClickListener;
    }

    public void setSpecificationInfoBeans(List<GoodsSpecificationInfoBean> specificationInfoBeans, int groupPosition) {
        mSpecificationInfoBeans = specificationInfoBeans;
        mGroupPosition = groupPosition;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SpecificationChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_specification_child, parent, false);
        return new SpecificationChildViewHolder(view, mGroupPosition,mGroupItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecificationChildViewHolder holder, final int position) {
        final GoodsSpecificationInfoBean infoBean = mSpecificationInfoBeans.get(position);
        holder.onBind(infoBean);
    }

    @Override
    public int getItemCount() {
        return mSpecificationInfoBeans == null ? 0 : mSpecificationInfoBeans.size();
    }


}
