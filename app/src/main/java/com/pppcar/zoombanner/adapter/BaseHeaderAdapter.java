package com.pppcar.zoombanner.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TANG on 2018-03-16.
 */

public abstract class BaseHeaderAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_HEADER = 0X001;
    protected Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<T> mDatas;

    private OnItemClickListener<T> mOnItemClickListener;

    private List<View> mViews = new ArrayList<>();


    protected abstract int getItemLayoutId();

    protected abstract void convert(ViewHolder viewHolder, T item);


    public BaseHeaderAdapter(Context context, View headerView) {
        this(context, headerView,null);
    }


    public BaseHeaderAdapter(Context context, View headerView, List<T> datas) {
        mContext = context;
        mViews.add(headerView);
        mLayoutInflater = LayoutInflater.from(context);
        mDatas = datas == null ? new ArrayList<T>() : datas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return ViewHolder.create(mViews.get(0));
        }
        return ViewHolder.create(mLayoutInflater, getItemLayoutId(),parent);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        ViewHolder viewHolder = (ViewHolder) holder;
        bindItemView(viewHolder, position);
    }

    void bindItemView(final ViewHolder viewHolder, final int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        convert(viewHolder, getItem(position));
        if (mOnItemClickListener == null) return;
        viewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(viewHolder, getItem(position), position-mViews.size());
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mViews.size()) {
            return TYPE_HEADER;
        }
        return super.getItemViewType(position);
    }

    public T getItem(int position) {
        return mDatas.get(position - mViews.size());
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + mViews.size();
    }

    public void refresh(List<T> datas) {

        if (datas == null) {
            datas = new ArrayList<>();
        }
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void loadMore(List<T> datas) {
        if (isEmpty(datas)) return;
        int positionStart = getItemCount();
        mDatas.addAll(datas);
        notifyItemInserted(positionStart);
    }


    public boolean isEmpty(List<T> datas) {
        if (datas == null || datas.size() == 0) {
            return true;
        }
        return false;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(ViewHolder viewHolder, T item, int position);
    }


    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
