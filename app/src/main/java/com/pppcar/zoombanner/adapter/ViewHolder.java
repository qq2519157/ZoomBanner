package com.pppcar.zoombanner.adapter;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by TANG on 2018-03-16.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mItemView;

    private ViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        mViews = new SparseArray<>();
    }


    public static ViewHolder create(@NonNull LayoutInflater inflater, @LayoutRes int layoutId, ViewGroup parent) {
        View view = inflater.inflate(layoutId, parent,false);
        return create(view);
    }

    public static ViewHolder create(@NonNull View itemView) {
        return new ViewHolder(itemView);
    }

    public View getItemView() {
        return mItemView;
    }

    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mItemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    public void setText(@IdRes int viewId, String text) {
        ((TextView) getView(viewId)).setText(TextUtils.isEmpty(text) ? "" : text);
    }

    public void setText(@IdRes int viewId, SpannableStringBuilder builder){
        if (builder==null)return;
        ((TextView) getView(viewId)).setText(builder);

    }
}
