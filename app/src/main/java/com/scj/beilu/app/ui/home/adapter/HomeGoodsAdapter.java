package com.scj.beilu.app.ui.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * author:SunGuiLan
 * date:2019/2/13
 * descriptin:
 */
public class HomeGoodsAdapter extends RecyclerView.Adapter<HomeGoodsAdapter.HomeGoodsViewHolder> {
    @NonNull
    @Override
    public HomeGoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_goods, parent, false);
        return new HomeGoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeGoodsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class HomeGoodsViewHolder extends BaseViewHolder {

        public HomeGoodsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
