package com.scj.beilu.app.ui.home.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.GlideRequests;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;

/**
 * author:SunGuiLan
 * date:2019/2/13
 * descriptin: 首页--4个按钮
 */
public class HomeButtonAdapter extends RecyclerView.Adapter<HomeButtonAdapter.HomeButtonViewHolder> {

    private String[] name = {"附近商家", "动作库", "商城", "健身记录"};
    private int[] image = {R.drawable.ic_shop, R.drawable.ic_action, R.drawable.ic_buy, R.drawable.ic_record};

    private OnItemClickListener mItemClickListener;

    private GlideRequest<Drawable> mGlideRequest;

    public HomeButtonAdapter(GlideRequests with) {
        mGlideRequest = with.asDrawable().fitCenter();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public HomeButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_button, parent, false);
        return new HomeButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeButtonViewHolder holder, final int position) {
        mGlideRequest.load(image[position]).into(holder.iv_photo);
        holder.tv_name.setText(name[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position, null, view);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class HomeButtonViewHolder extends BaseViewHolder {
        private ImageView iv_photo;
        private TextView tv_name;

        public HomeButtonViewHolder(View itemView) {
            super(itemView);
            itemView.setId(R.id.home_nav_button);
            iv_photo = findViewById(R.id.iv_photo);
            tv_name = findViewById(R.id.tv_name);
        }
    }
}
