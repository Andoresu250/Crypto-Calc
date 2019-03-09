package com.andoresu.cryptocalc.core.calculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andoresu.cryptocalc.R;
import com.andoresu.cryptocalc.core.MainActivity;
import com.andoresu.cryptocalc.core.calculator.data.CalculatorResponse;
import com.andoresu.cryptocalc.core.calculator.data.Currency;
import com.andoresu.cryptocalc.list.RecyclerViewAdapter;
import com.andoresu.cryptocalc.utils.BaseFragment;
import com.andoresu.cryptocalc.utils.CurrencyEditText;
import com.andoresu.cryptocalc.utils.ListDialogFragment;
import com.andoresu.cryptocalc.utils.PrefixEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;

import static com.andoresu.cryptocalc.utils.MyUtils.pairTextViewWithView;

public class CalculatorFragment extends BaseFragment implements CalculatorContract.View, ListDialogFragment.ListDialogListener {

    @BindView(R.id.btcEditText)
    public PrefixEditText btcEditText;
    @BindView(R.id.btcLabelTextView)
    public TextView btcLabelTextView;
    @BindView(R.id.reverseImageButton)
    public ImageView reverseImageButton;
    @BindView(R.id.valueEditText)
    public CurrencyEditText valueEditText;
    @BindView(R.id.valueLabelTextView)
    public TextView valueLabelTextView;
    @BindView(R.id.currencyButton)
    public Button currencyButton;
    @BindView(R.id.minusButton)
    public ImageButton minusButton;
    @BindView(R.id.plusButton)
    public ImageButton plusButton;
    @BindView(R.id.percentageTextView)
    public TextView percentageTextView;
    @BindView(R.id.percentageSymbolTextView)
    public TextView percentageSymbolTextView;
    @BindView(R.id.percent01Button)
    public TextView percent01Button;
    @BindView(R.id.percent05Button)
    public TextView percent05Button;
    @BindView(R.id.percent10Button)
    public TextView percent10Button;
    @BindView(R.id.convertButton)
    public Button convertButton;
    @BindViews({R.id.percent01Button, R.id.percent05Button, R.id.percent10Button})
    public List<TextView> percentButtons;
    public TextView selectedPercentButton;
    @BindView(R.id.cleanAllButton)
    public Button cleanAllButton;

    public CalculatorResponse calculatorResponse = new CalculatorResponse();

    private CalculatorContract.ActionsListener actionsListener;

    private int calculatorMode = CalculatorResponse.BTC_MODE;

    private static double percentageStep = 1.0d;

    private Double percentage = 0.0d;

    private Currency selectedCurrency;

    private ListDialogFragment<Currency> listDialogFragment;

    private ArrayList<Currency> currencies;

    private boolean autoOpenDialog = false;

    public static CalculatorFragment newInstance() {

        Bundle args = new Bundle();

        CalculatorFragment fragment = new CalculatorFragment();
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
        pairTextViewWithView(btcLabelTextView, btcEditText, getContext());
        pairTextViewWithView(valueLabelTextView, valueEditText, getContext());
        currencyButton.setOnClickListener(view -> openCurrencyList());
        percent01Button.setOnClickListener(view -> {
            percentageStep = 0.1d;
            selectedPercentButton = percent01Button;
            selectButton();
        });
        percent05Button.setOnClickListener(view -> {
            percentageStep = 0.5d;
            selectedPercentButton = percent05Button;
            selectButton();
        });
        percent10Button.setOnClickListener(view -> {
            percentageStep = 1.0d;
            selectedPercentButton = percent10Button;
            selectButton();
        });
        plusButton.setOnClickListener(view -> applyPercentage(percentageStep));
        minusButton.setOnClickListener(view -> applyPercentage(-percentageStep));
        convertButton.setOnClickListener(view -> calculate());
        valueEditText.setEnabled(false);
        reverseImageButton.setOnClickListener(view -> {
            btcEditText.setEnabled(calculatorMode != CalculatorResponse.BTC_MODE);
            valueEditText.setEnabled(calculatorMode == CalculatorResponse.BTC_MODE);
            if(calculatorMode == CalculatorResponse.BTC_MODE){
                calculatorMode = CalculatorResponse.VALUE_MODE;
                valueEditText.setFocusableInTouchMode(true);
                valueEditText.requestFocus();
            }else{
                calculatorMode = CalculatorResponse.BTC_MODE;
                btcEditText.setFocusableInTouchMode(true);
                btcEditText.requestFocus();
            }
        });
        cleanAllButton.setOnClickListener(view -> cleanAll());
        percent10Button.callOnClick();
        actionsListener.calculate(calculatorResponse);
        valueEditText.setOnEditorActionListener(editorActionListener());
        btcEditText.setOnEditorActionListener(editorActionListener());

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_calculator;
    }

    private void selectButton(){
        for(TextView button : percentButtons){
            if(button.getId() == selectedPercentButton.getId()){
                button.setBackgroundResource(R.drawable.button_accent_border_transparent);
                button.setTextColor(getResources().getColor(R.color.colorAccent));
            }else{
                button.setBackgroundResource(R.drawable.button_border_transparent);
                button.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showCalculatorResponse(CalculatorResponse response) {
        this.calculatorResponse = response;
        percentageTextView.setText(this.percentage.toString());
        if(response.btc != null){
//            response.btc = Math.round(response.btc * 100.0) / 100.0;
            Double btc = response.btc;
            if(calculatorMode == CalculatorResponse.VALUE_MODE){
                btc *= (1 + percentage/100.0d);
            }
            btcEditText.setText(btc.toString());
        }else{
            btcEditText.setText("");
        }
        if(response.value != null){
            Double value = response.value;
            if(calculatorMode == CalculatorResponse.BTC_MODE){
                value *= (1 + percentage/100.0d);
            }
            value = Math.round(value * 100.0) / 100.0;
            valueEditText.setText(value.toString());
        }else{
            valueEditText.setText("");
        }
    }

    @Override
    public void setCurrencies(ArrayList<Currency> currencies) {
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
            listDialogFragment = ListDialogFragment.newInstance(CalculatorFragment.this);
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
        listDialogFragment.show(CalculatorFragment.this);
    }

    private void calculate(){
        if(calculatorMode == CalculatorResponse.BTC_MODE){
            if(btcEditText.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Por favor ingrese los Bitcoins que desea convertir", Toast.LENGTH_SHORT).show();
                return;
            }else{
                calculatorResponse.btc = Double.parseDouble(btcEditText.getText().toString());
                calculatorResponse.value = null;
            }
        }else{
            if(valueEditText.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Por favor ingrese el valor que desea convertir", Toast.LENGTH_SHORT).show();
                return;
            }else{
                calculatorResponse.value = Double.parseDouble(valueEditText.getValue());
                calculatorResponse.btc = null;
            }
        }
        actionsListener.calculate(calculatorResponse);
    }

    @SuppressLint("SetTextI18n")
    private void applyPercentage(double percentage){
        this.percentage += percentage;
        this.percentage = Math.round(this.percentage * 100.0) / 100.0;
        showCalculatorResponse(calculatorResponse);
    }

    private void cleanAll(){
        calculatorResponse.btc = null;
        calculatorResponse.value = null;
        percentage = 0.0d;
        percent10Button.callOnClick();
        showCalculatorResponse(calculatorResponse);
    }

    @Override
    public void onClickPositiveButton() {
        calculatorResponse.currency = selectedCurrency.code;
        currencyButton.setText(selectedCurrency.toString());
        actionsListener.calculate(calculatorResponse);
    }

    public TextView.OnEditorActionListener editorActionListener(){
        return (textView, actionId, keyEvent) -> {
            boolean action = false;
            if (actionId == EditorInfo.IME_ACTION_SEND)
            {
                // hide keyboard
                InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                convertButton.callOnClick();
                action = true;
            }
            return action;
        };
    }
}
