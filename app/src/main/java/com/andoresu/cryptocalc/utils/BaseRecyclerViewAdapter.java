package com.andoresu.cryptocalc.utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannedString;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static com.andoresu.cryptocalc.utils.MyUtils.removeTrailingLineFeed;


public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder<T>> {

    public final Context context;
    public ArrayList<T> items;
    public final OnItemClickListener<T> listener;

    public BaseRecyclerViewAdapter(Context context, @NonNull ArrayList<T> items, OnItemClickListener<T> listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    public BaseRecyclerViewAdapter(Context context, OnItemClickListener<T> listener) {
        this.context = context;
        this.items = new ArrayList<>();
        this.listener = listener;
    }

    public BaseRecyclerViewAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
        this.listener = null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<T> holder, int position) {
        T item = items.get(position);
        holder.bind(item, listener);
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(T item) {
        if(!items.contains(item)){
            items.add(item);
            notifyItemInserted(items.size() - 1);
        }
    }

    public void addAll(List<T> items) {
        for (T item : items) {
            add(item);
        }
        notifyDataSetChanged();
    }

    public void set(List<T> items){
        clear();
        addAll(items);
    }

    public void remove(T item) {
        int position = items.indexOf(item);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public T getItem(int position){
        return items.get(position);
    }

    public T get(int position){
        return getItem(position);
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
            notifyItemRemoved(0);
        }
        notifyDataSetChanged();

    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public CharSequence getText(int id, Object... args) {
        for(int i = 0; i < args.length; ++i)
            args[i] = args[i] instanceof String ? TextUtils.htmlEncode((String)args[i]) : args[i];
        return removeTrailingLineFeed(Html.fromHtml(String.format(Html.toHtml(new SpannedString(context.getText(id))), args)));
    }

    public abstract static class BaseViewHolder<T> extends RecyclerView.ViewHolder {

        public View itemView;

        public BaseViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void bind(final T item, final OnItemClickListener<T> listener) {
            if(listener != null){
                itemView.setOnClickListener(v -> listener.onItemClick(item));
            }
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T item);
    }

}
