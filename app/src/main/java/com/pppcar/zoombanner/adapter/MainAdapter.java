package com.pppcar.zoombanner.adapter;



import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.pppcar.zoombanner.R;
import com.pppcar.zoombanner.network.beans.MainResult;


public class MainAdapter extends BaseHeaderAdapter<MainResult.NewsDTO>{

    public MainAdapter(Context context, View headerView) {
        super(context, headerView);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_main;
    }

    @Override
    protected void convert(ViewHolder viewHolder, MainResult.NewsDTO item) {
       TextView textView= viewHolder.itemView.findViewById(R.id.text);
       textView.setText(item.getTitle());
    }
}