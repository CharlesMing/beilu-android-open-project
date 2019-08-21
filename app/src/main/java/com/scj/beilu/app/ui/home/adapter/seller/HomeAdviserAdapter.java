package com.scj.beilu.app.ui.home.adapter.seller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * author:SunGuiLan
 * date:2019/2/16
 * descriptin:
 */
public class HomeAdviserAdapter extends RecyclerView.Adapter<HomeAdviserAdapter.HomeAdviserViewHolder> {

    @NonNull
    @Override
    public HomeAdviserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seller_adviser, parent, false);
        return new HomeAdviserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdviserViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class HomeAdviserViewHolder extends BaseViewHolder {

        public HomeAdviserViewHolder(View itemView) {
            super(itemView);
        }
    }
}
