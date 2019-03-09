package com.andoresu.cryptocalc.list;

import android.content.Context;
import android.support.annotation.NonNull;

import com.andoresu.cryptocalc.utils.BaseRecyclerViewAdapter;

import java.util.ArrayList;

public abstract class RecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T> {

    public RecyclerViewAdapter(Context context, @NonNull ArrayList<T> items, OnItemClickListener<T> listener) {
        super(context, items, listener);
    }

    public RecyclerViewAdapter(Context context, OnItemClickListener<T> listener) {
        super(context, listener);
    }

    public RecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<T> holder, int position) {
        T item = items.get(position);
        holder.bind(item, listener);
        setData(holder, position);
    }

    public abstract void setData(BaseViewHolder<T> holder, int position);

}
