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
 * date:2019/2/19
 * descriptin:
 */
public class ShopPhotoAdapter extends RecyclerView.Adapter<ShopPhotoAdapter.ShopPhotoViewHolder> {
    private OnItemClickListen onItemClickListen;

    public interface OnItemClickListen {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListen(OnItemClickListen onItemClickListen) {
        this.onItemClickListen = onItemClickListen;
    }

    @NonNull
    @Override
    public ShopPhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopphoto, parent, false);
        return new ShopPhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopPhotoViewHolder holder, final int position) {
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
        return 4;
    }

    public class ShopPhotoViewHolder extends BaseViewHolder {

        public ShopPhotoViewHolder(View itemView) {
            super(itemView);
        }
    }
}
