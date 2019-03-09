package com.andoresu.cryptocalc.authorization;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.andoresu.cryptocalc.R;
import com.andoresu.cryptocalc.authorization.data.Country;
import com.andoresu.cryptocalc.core.calculator.CalculatorFragment;
import com.andoresu.cryptocalc.core.calculator.CurrencyViewHolder;
import com.andoresu.cryptocalc.core.calculator.data.Currency;
import com.andoresu.cryptocalc.list.RecyclerViewAdapter;
import com.andoresu.cryptocalc.utils.BaseDialogFragment;
import com.andoresu.cryptocalc.utils.ListDialogFragment;
import com.andoresu.cryptocalc.utils.PrefixEditText;
import com.andoresu.cryptocalc.utils.RadioButtonViewHolder;

import java.util.ArrayList;

import butterknife.BindView;

import static com.andoresu.cryptocalc.utils.MyUtils.pairTextViewWithView;

public class PhoneDialogFragment extends BaseDialogFragment implements ListDialogFragment.ListDialogListener {

    @BindView(R.id.countryButton)
    public Button countryButton;
    @BindView(R.id.phoneEditText)
    public PrefixEditText phoneEditText;
    @BindView(R.id.phoneLabelTextView)
    public TextView phoneLabelTextView;

    public PhoneDialogListener phoneDialogListener;

    private ListDialogFragment<Country> listDialogFragment;

    private Country selectedCountry;

    public static PhoneDialogFragment newInstance(@NonNull PhoneDialogListener phoneDialogListener) {

        Bundle args = new Bundle();

        PhoneDialogFragment fragment = new PhoneDialogFragment();
        fragment.setArguments(args);
        fragment.setTitle("Ingresa tu telefono");
        fragment.setCancelable(false);
        fragment.phoneDialogListener = phoneDialogListener;
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.df_phone;
    }

    @Override
    public void handleView() {
        pairTextViewWithView(phoneLabelTextView, phoneEditText, getContext());
        builder.setNegativeButton(R.string.close, (dialogInterface, i) -> {
            phoneDialogListener.onClickNegativeButton();
            dialogInterface.dismiss();
        });
        builder.setPositiveButton(R.string.ok, null);

        ArrayList<Country> countries = new ArrayList<>();
        countries.add(new Country("Colombia", "+57"));
        countries.add(new Country("España", "+34"));
        countries.add(new Country("Chile", "+56"));
        countries.add(new Country("Peru", "+51"));
        countries.add(new Country("Argentina", "+54"));
        countries.add(new Country("Venezuela", "+58"));

        countryButton.setOnClickListener(view -> openCountriesList(countries));

        dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button button = ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> {
                String number = phoneEditText.getText().toString();
                if(number.isEmpty() || number.length() <= 7){
                    phoneEditText.setError("Ingrese un numero valido");
                }else{
                    phoneDialogListener.onClickPositiveButton(number);
                    dialog.dismiss();
                }
            });
        });
    }

    private void openCountriesList(ArrayList<Country> countries){
        if(listDialogFragment == null){
            listDialogFragment = ListDialogFragment.newInstance(PhoneDialogFragment.this);
            listDialogFragment.setTitle("Paises");
            listDialogFragment.setRecyclerViewAdapter(new RecyclerViewAdapter<Country>(getContext(), countries, null) {
                @Override
                public void setData(BaseViewHolder<Country> holder, int position) {
                    RadioButtonViewHolder<Country> viewHolder = (RadioButtonViewHolder<Country>) holder;
                    Country country = get(position);
                    viewHolder.radioButton.setText(country.name);
                    viewHolder.radioButton.setChecked(country.selected);
                    viewHolder.radioButton.setOnClickListener(view1 -> {
                        boolean checked = viewHolder.radioButton.isChecked();
                        if(checked){
                            Log.e(TAG, "setData: checked");
                            country.selected = true;
                            for(Country c : items){
                                if(!c.equals(country)){
                                    c.selected = false;
                                }
                            }
                            selectedCountry = country;
                            notifyDataSetChanged();
                        }else{
                            Log.e(TAG, "setData: not checked");
                        }
                    });
                }

                @Override
                protected int getLayoutResId() {
                    return R.layout.item_currency;
                }

                @NonNull
                @Override
                public BaseViewHolder<Country> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View view = inflater.inflate(getLayoutResId(), parent, false);
                    return new RadioButtonViewHolder<>(view);
                }
            });
        }
        listDialogFragment.show(PhoneDialogFragment.this);
    }

    @Override
    public void onClickPositiveButton() {
        phoneEditText.setTag(selectedCountry.code);
    }

    public interface PhoneDialogListener {
        void onClickPositiveButton(String phone);

        void onClickNegativeButton();
    }
}
