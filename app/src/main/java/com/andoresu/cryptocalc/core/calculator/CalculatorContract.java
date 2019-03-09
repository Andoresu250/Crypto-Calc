package com.andoresu.cryptocalc.core.calculator;

import com.andoresu.cryptocalc.core.calculator.data.CalculatorResponse;
import com.andoresu.cryptocalc.core.calculator.data.Currency;
import com.andoresu.cryptocalc.utils.BaseView;

import java.util.ArrayList;
import java.util.List;

public interface CalculatorContract {

    interface View extends BaseView {

        void showCalculatorResponse(CalculatorResponse response);

        void setCurrencies(ArrayList<Currency> currencies);

    }

    interface ActionsListener {
        void calculate(CalculatorResponse response);

        void getCurrencies();

    }

}
