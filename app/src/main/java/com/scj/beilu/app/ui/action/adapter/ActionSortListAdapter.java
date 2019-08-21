package com.scj.beilu.app.ui.action.adapter;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnGroupItemClickListener;
import com.scj.beilu.app.mvp.action.bean.ActionInfoBean;
import com.scj.beilu.app.mvp.action.bean.ActionSortInfoBean;
import com.scj.beilu.app.ui.action.ActionListFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/18 16:07
 * {@link ActionContentListAdapter}
 */
public class ActionSortListAdapter extends RecyclerView.Adapter<ActionSortListAdapter.ActionSortViewHolder> {
    private List<ActionSortInfoBean> mSortInfoBeans;
    private OnGroupItemClickListener<ActionInfoBean> mOnItemClickListener;
    private ActionListFrag mActionListFrag;
    private final int mRectSize;

    public ActionSortListAdapter(ActionListFrag actionListFrag) {
        mActionListFrag = actionListFrag;
        mRectSize = actionListFrag.getResources().getDimensionPixelSize(R.dimen.hor_divider_size_5);
    }

    public void setSortInfoBeans(List<ActionSortInfoBean> sortInfoBeans) {
        mSortInfoBeans = sortInfoBeans;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnGroupItemClickListener<ActionInfoBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ActionSortViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action_content, parent, false);
        return new ActionSortViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActionSortViewHolder holder, int position) {
        try {
            ActionSortInfoBean infoBean = mSortInfoBeans.get(position);
            holder.mTvSortTitle.setText(infoBean.getActionName());
            List<ActionInfoBean> actions = infoBean.getActions();
            holder.mTvSortTitle.setVisibility(actions.size() == 0 ? View.GONE : View.VISIBLE);
//            ActionContentListAdapter contentListAdapter = new ActionContentListAdapter(mActionListFrag,position);
//            contentListAdapter.setOnItemClickListener(mOnItemClickListener);
//            contentListAdapter.setActionInfoList(actions);
//            holder.mRvActionInfoList.setAdapter(contentListAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mSortInfoBeans == null ? 0 : mSortInfoBeans.size();
    }

    class ActionSortViewHolder extends BaseViewHolder {
        private TextView mTvSortTitle;
        private RecyclerView mRvActionInfoList;

        public ActionSortViewHolder(View itemView) {
            super(itemView);
            mTvSortTitle = findViewById(R.id.tv_action_sort_txt);
            mRvActionInfoList = findViewById(R.id.rv_action_info_list);

            mRvActionInfoList.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(mRectSize, mRectSize, mRectSize, mRectSize);
                }
            });

        }
    }
}
