package com.example.administrator.test.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.test.R;
import com.example.administrator.test.bean.ContributeItemData;

import java.util.List;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 11:16 2020/4/17
 */
public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.ViewHolder> {

    private List<ContributeItemData> mDatas;

    public StaggeredAdapter(List<ContributeItemData> itemData) {
        this.mDatas = itemData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_popup_contribute_item_view,parent,false);
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
