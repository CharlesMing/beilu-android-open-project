package com.scj.beilu.app.ui.store.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scj.beilu.app.R;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.store.entity.GoodsShoppingCarInfoBean;
import com.scj.beilu.app.ui.store.ShoppingCarFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/1 17:24
 */
public class ShoppingCartListAdapter extends RecyclerView.Adapter<ShoppingCartListViewHolder> {
    private OnItemClickListener<GoodsShoppingCarInfoBean> mOnItemClickListener;
    private ShoppingCarFrag mShoppingCarFrag;
    private List<GoodsShoppingCarInfoBean> mCarInfoBeanList;

    public ShoppingCartListAdapter(ShoppingCarFrag shoppingCarFrag) {
        mShoppingCarFrag = shoppingCarFrag;
    }

    public void setOnItemClickListener(OnItemClickListener<GoodsShoppingCarInfoBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setCarInfoBeanList(List<GoodsShoppingCarInfoBean> carInfoBeanList) {
        mCarInfoBeanList = carInfoBeanList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShoppingCartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_cart_content, parent, false);
        return new ShoppingCartListViewHolder(view, mShoppingCarFrag, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartListViewHolder holder, int position) {
        GoodsShoppingCarInfoBean carInfoBean = mCarInfoBeanList.get(position);
        holder.bindData(carInfoBean);
    }

    @Override
    public int getItemCount() {
        return mCarInfoBeanList == null ? 0 : mCarInfoBeanList.size();
    }
}
