package com.pppcar.zoombanner.adapter;



import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.pppcar.zoombanner.R;


public class mainAdapter extends BaseHeaderAdapter<String>{

    public mainAdapter(Context context, View headerView) {
        super(context, headerView);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_main;
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item) {
       TextView textView= viewHolder.itemView.findViewById(R.id.text);
       textView.setText(item);
    }
}