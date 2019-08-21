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

/**
 * @author Mingxun
 * @time on 2019/1/11 19:26
 */
public class MeNavListAdapter extends RecyclerView.Adapter<MeNavListAdapter.NavViewHolder> {
    private String[] mNavTitles;
    private int[] mResIcon;

    private OnItemClickListener<Integer> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<Integer> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public MeNavListAdapter(String[] navTitle, int[] resIcon) {
        mNavTitles = navTitle;
        mResIcon = resIcon;
    }

    @NonNull
    @Override
    public NavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_me_nav_item_content,
                parent, false);
        return new NavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NavViewHolder holder, int position) {
        holder.mTvNav.setCompoundDrawablesWithIntrinsicBounds(0, mResIcon[position], 0, 0);
        holder.mTvNav.setText(mNavTitles[position]);
    }

    @Override
    public int getItemCount() {
        return mNavTitles == null || mResIcon == null ? 0 : mNavTitles.length;
    }

    public class NavViewHolder extends BaseViewHolder {
        private TextView mTvNav;

        public NavViewHolder(View itemView) {
            super(itemView);
            mTvNav = findViewById(R.id.tv_me_nav);
            mTvNav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), getAdapterPosition(), v);
                    }
                }
            });
        }
    }

}
