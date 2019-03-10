package com.andoresu.cryptocalc.core.percentage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.andoresu.cryptocalc.R;
import com.andoresu.cryptocalc.core.calculator.CalculatorContract;
import com.andoresu.cryptocalc.core.calculator.CalculatorPresenter;
import com.andoresu.cryptocalc.core.calculator.CurrencyViewHolder;
import com.andoresu.cryptocalc.core.calculator.data.CalculatorResponse;
import com.andoresu.cryptocalc.core.calculator.data.Currency;
import com.andoresu.cryptocalc.list.RecyclerViewAdapter;
import com.andoresu.cryptocalc.utils.BaseFragment;
import com.andoresu.cryptocalc.utils.CurrencyEditText;
import com.andoresu.cryptocalc.utils.ListDialogFragment;
import com.andoresu.cryptocalc.utils.PrefixEditText;

import java.util.ArrayList;

import butterknife.BindView;

import static com.andoresu.cryptocalc.utils.MyUtils.pairTextViewWithView;

public class PercentageFragment extends BaseFragment implements CalculatorContract.View, ListDialogFragment.ListDialogListener {

    @BindView(R.id.valueEditText)
    public CurrencyEditText valueEditText;
    @BindView(R.id.valueLabelTextView)
    public TextView valueLabelTextView;
    @BindView(R.id.currencyButton)
    public Button currencyButton;
    @BindView(R.id.sellPriceEditText)
    public PrefixEditText sellPriceEditText;
    @BindView(R.id.sellPriceLabelTextView)
    public TextView sellPriceLabelTextView;
    @BindView(R.id.percentageTextView)
    public TextView percentageTextView;
    @BindView(R.id.percentageSymbolTextView)
    public TextView percentageSymbolTextView;
    @BindView(R.id.calculateButton)
    public Button calculateButton;
    @BindView(R.id.cleanAllButton)
    public Button cleanAllButton;

    private int calculatorMode = CalculatorResponse.BTC_MODE;

    public CalculatorResponse calculatorResponse = new CalculatorResponse();

    private Double percentage = 0.0d;

    private Currency selectedCurrency;

    private ListDialogFragment<Currency> listDialogFragment;

    private ArrayList<Currency> currencies;

    private boolean autoOpenDialog = false;

    private CalculatorContract.ActionsListener actionsListener;

    public static PercentageFragment newInstance() {
        
        Bundle args = new Bundle();
        
        PercentageFragment fragment = new PercentageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        actionsListener = new CalculatorPresenter(this, getContext());
    }

    @Override
    public void handleView() {
        actionsListener.getCurrencies();
        pairTextViewWithView(valueLabelTextView, valueEditText, getContext());
        currencyButton.setOnClickListener(view -> openCurrencyList());
        cleanAllButton.setOnClickListener(view -> cleanAll());
        actionsListener.calculate(calculatorResponse);
        valueEditText.setEnabled(false);
        calculateButton.setOnClickListener(view -> calculate());
        sellPriceEditText.setOnEditorActionListener(editorActionListener());
        sellPriceEditText.setFocusableInTouchMode(true);
        sellPriceEditText.requestFocus();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_percentage;
    }

    @Override
    public void showCalculatorResponse(CalculatorResponse response) {
        this.calculatorResponse = response;
        if(baseFragment == null){
            return;
        }
        if(response.value != null){
            Double value = response.value;
            value = Math.round(value * 100.0) / 100.0;
            valueEditText.setText(value.toString());
        }
    }

    @Override
    public void setCurrencies(ArrayList<Currency> currencies) {
        if(baseFragment == null){
            return;
        }
        this.currencies = currencies;
        if(autoOpenDialog){
            openCurrencyList();
        }
        autoOpenDialog = false;
    }

    private void openCurrencyList(){
        if(currencies == null){
            autoOpenDialog = true;
            actionsListener.getCurrencies();
            return;
        }
        if(listDialogFragment == null){
            listDialogFragment = ListDialogFragment.newInstance(this);
            listDialogFragment.setRecyclerViewAdapter(new RecyclerViewAdapter<Currency>(getContext(), currencies, null) {
                @Override
                public void setData(BaseViewHolder<Currency> holder, int position) {
                    CurrencyViewHolder viewHolder = (CurrencyViewHolder) holder;
                    Currency currency = get(position);
                    viewHolder.radioButton.setText(currency.name);
                    viewHolder.radioButton.setChecked(currency.selected);
                    viewHolder.radioButton.setOnClickListener(view1 -> {
                        boolean checked = viewHolder.radioButton.isChecked();
                        if(checked){
                            currency.selected = true;
                            for(Currency c : items){
                                if(!c.id.equals(currency.id)){
                                    c.selected = false;
                                }
                            }
                            selectedCurrency = currency;
                            notifyDataSetChanged();
                        }
                    });
                }

                @Override
                protected int getLayoutResId() {
                    return R.layout.item_currency;
                }

                @NonNull
                @Override
                public BaseViewHolder<Currency> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View view = inflater.inflate(getLayoutResId(), parent, false);
                    return new CurrencyViewHolder(view);
                }
            });
        }
        listDialogFragment.show(this);
    }

    @SuppressLint("SetTextI18n")
    private void calculate(){
        Double btcValue = Double.parseDouble(valueEditText.getValue());
        Double sellValue = Double.parseDouble(sellPriceEditText.getText().toString());
        percentage = ((sellValue - btcValue)/btcValue) * 100;
        percentage = Math.round(percentage * 100.0) / 100.0;
        percentageTextView.setText(percentage.toString());
    }

    @Override
    public void onClickPositiveButton() {
        calculatorResponse.currency = selectedCurrency.code;
        currencyButton.setText(selectedCurrency.toString());
        sellPriceEditText.setTag(selectedCurrency.toString());
        actionsListener.calculate(calculatorResponse);
    }

    private void cleanAll(){
        calculatorResponse.btc = 1.0;
        calculatorResponse.value = null;
        percentage = 0.0d;
        showCalculatorResponse(calculatorResponse);
    }

    public TextView.OnEditorActionListener editorActionListener(){
        return (textView, actionId, keyEvent) -> {
            boolean action = false;
            if (actionId == EditorInfo.IME_ACTION_SEND)
            {
                // hide keyboard
                InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                calculateButton.callOnClick();
                action = true;
            }
            return action;
        };
    }
}
