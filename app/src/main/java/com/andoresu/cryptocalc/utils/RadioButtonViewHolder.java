package com.andoresu.cryptocalc.utils;

import android.view.View;
import android.widget.RadioButton;

import com.andoresu.cryptocalc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RadioButtonViewHolder<T> extends BaseRecyclerViewAdapter.BaseViewHolder<T>{

    @BindView(R.id.radioButton)
    public RadioButton radioButton;

    public RadioButtonViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
