package com.andoresu.cryptocalc.core.calculator;

import com.andoresu.cryptocalc.core.calculator.data.CalculatorResponse;
import com.andoresu.cryptocalc.core.calculator.data.Currency;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CalculatorService {

    @POST("calculators/calculate")
    Observable<Response<CalculatorResponse>> calculate(@Body CalculatorResponse calculatorResponse);

    @GET("calculators/currencies")
    Observable<Response<ArrayList<Currency>>> getCurrencies();

}
