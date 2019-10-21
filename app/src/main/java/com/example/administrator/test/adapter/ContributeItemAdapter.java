package com.example.administrator.test.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.administrator.test.R;
import com.example.administrator.test.bean.ContributeItemData;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: JINO
 * @Description:
 * @Date: Create in 11:30 2019/9/26
 */
public class ContributeItemAdapter extends BaseAdapter {

    private List<ContributeItemData> dataList;

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList == null ? null : dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ContributeItemData contributeItemData = dataList.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_popup_contribute_item_view, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvIcon = convertView.findViewById(R.id.tv_icon);
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        if(position == 1){
//            convertView.setVisibility(View.GONE);
//        }
        viewHolder.tvIcon.setText(contributeItemData.iconRes);
        viewHolder.tvTitle.setText(contributeItemData.title);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contributeItemData.onClickListener.onClick(v);
            }
        });
        return convertView;
    }

    public void setData(ArrayList<ContributeItemData> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        TextView tvIcon;
        TextView tvTitle;
    }
}
