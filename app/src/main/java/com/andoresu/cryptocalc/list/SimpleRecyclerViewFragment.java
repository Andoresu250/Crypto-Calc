package com.andoresu.cryptocalc.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andoresu.cryptocalc.R;
import com.andoresu.cryptocalc.utils.BaseFragment;

import java.util.List;

import butterknife.BindView;

public class SimpleRecyclerViewFragment<T> extends BaseFragment {

    @BindView(R.id.listRecyclerView)
    public RecyclerView listRecyclerView;

    @BindView(R.id.emptyTextView)
    public TextView emptyTextView;

    private LinearLayoutManager linearLayoutManager;

    public RecyclerViewAdapter<T> viewAdapter;

    private boolean hasFixedSized = false;

    public SimpleRecyclerViewFragment(){}

    public static <T>SimpleRecyclerViewFragment<T> newInstance() {

        Bundle args = new Bundle();

        SimpleRecyclerViewFragment<T> fragment = new SimpleRecyclerViewFragment<>();
        fragment.setArguments(args);
        Log.i(TAG, "newInstance: ");
        return fragment;
    }

    public void setHasFixedSized(boolean hasFixedSized) {
        this.hasFixedSized = hasFixedSized;
    }

    @Override
    public void handleView() {
        linearLayoutManager = new LinearLayoutManager(this.getContext(), 1, false);
        listRecyclerView.setHasFixedSize(hasFixedSized);
        listRecyclerView.setLayoutManager(linearLayoutManager);
        listRecyclerView.setAdapter(viewAdapter);
        isEmpty();
    }

    public void setViewAdapter(RecyclerViewAdapter<T> viewAdapter){
        Log.i(TAG, "setViewAdapter: ");
        this.viewAdapter = viewAdapter;
    }

    public void isEmpty(){
        if(emptyTextView == null){
            return;
        }
        if(viewAdapter == null || viewAdapter.items == null || viewAdapter.items.isEmpty()){
            emptyTextView.setVisibility(View.VISIBLE);
        }else{
            emptyTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getLayoutId(){
        return R.layout.fragment_simple_recycler_view;
    }

    public int getItemCount() {
        return viewAdapter.getItemCount();
    }

    public void add(T item) {
        viewAdapter.add(item);
        isEmpty();
    }

    public void add(List<T> items) {
        addAll(items);
    }

    public void addAll(List<T> items) {
        viewAdapter.addAll(items);
        isEmpty();
    }

    public void set(List<T> items){
        viewAdapter.set(items);
        isEmpty();
    }

    public void remove(T item) {
        viewAdapter.remove(item);
        isEmpty();
    }

    public T getItem(int position){
        return viewAdapter.get(position);
    }

    public T get(int position){
        return getItem(position);
    }

    public void clear() {
        viewAdapter.clear();
        isEmpty();
    }

}