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
 * date:2019/2/18
 * descriptin:
 */
public class CoachAlbumAdapter extends RecyclerView.Adapter<CoachAlbumAdapter.CoachAlbumViewHolder> {
    @NonNull
    @Override
    public CoachAlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coach_album, parent, false);
        return new CoachAlbumViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CoachAlbumViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class CoachAlbumViewHolder extends BaseViewHolder {
        public CoachAlbumViewHolder(View itemView) {
            super(itemView);
        }
    }
}
