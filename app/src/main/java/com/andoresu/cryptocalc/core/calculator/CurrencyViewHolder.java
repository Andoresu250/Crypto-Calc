package com.andoresu.cryptocalc.core.calculator;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.andoresu.cryptocalc.R;
import com.andoresu.cryptocalc.core.calculator.data.Currency;
import com.andoresu.cryptocalc.list.RecyclerViewAdapter;
import com.andoresu.cryptocalc.utils.BaseRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrencyViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder<Currency> {

    @BindView(R.id.radioButton)
    public RadioButton radioButton;

    public CurrencyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}