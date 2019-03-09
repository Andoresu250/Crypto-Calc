package com.andoresu.cryptocalc.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.andoresu.cryptocalc.R;
import com.andoresu.cryptocalc.list.RecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;

public class ListDialogFragment<T> extends BaseDialogFragment{

    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;
    public RecyclerViewAdapter<T> recyclerViewAdapter;

    public ListDialogListener listDialogListener;

    public static <T>ListDialogFragment<T> newInstance(@NonNull ListDialogListener listDialogListener) {
        Bundle args = new Bundle();
        ListDialogFragment<T> fragment = new ListDialogFragment<>();
        fragment.setArguments(args);
        fragment.setTitle("Monedas");
        fragment.setListDialogListener(listDialogListener);
        return fragment;
    }

    private void setListDialogListener(ListDialogListener listDialogListener) {
        this.listDialogListener = listDialogListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.df_list;
    }

    @Override
    public void handleView() {
        builder.setNegativeButton(R.string.close, (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setPositiveButton(R.string.ok, (dialogInterface, i) -> {
            listDialogListener.onClickPositiveButton();
            dialogInterface.dismiss();
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), 1, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(this.recyclerViewAdapter);
    }

    public void setRecyclerViewAdapter(RecyclerViewAdapter<T> recyclerViewAdapter){
        this.recyclerViewAdapter = recyclerViewAdapter;
    }

    @Override
    public void onResume() {
        super.onResume();
        setDialogSize(null, 0.5);
    }

    public interface ListDialogListener {

        void onClickPositiveButton();

    }
}
