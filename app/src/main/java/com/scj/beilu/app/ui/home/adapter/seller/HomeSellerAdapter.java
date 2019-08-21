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
 * date:2019/2/14
 * descriptin:
 */
public class HomeSellerAdapter extends RecyclerView.Adapter<HomeSellerAdapter.HomeSellerViewHolder> {
    private OnItemClickListen onItemClickListen;

    public void setOnItemClickListener(OnItemClickListen onItemClickListener) {
        this.onItemClickListen = onItemClickListener;
    }

    public interface OnItemClickListen {
        void onItemClick(View view, int position);
    }

    @NonNull
    @Override
    public HomeSellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_selller, parent, false);
        return new HomeSellerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeSellerViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListen != null)
                    onItemClickListen.onItemClick(holder.itemView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class HomeSellerViewHolder extends BaseViewHolder {

        public HomeSellerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
