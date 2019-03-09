package com.andoresu.cryptocalc.core.calculator;

import android.content.Context;

import com.andoresu.cryptocalc.client.ObserverResponse;
import com.andoresu.cryptocalc.client.ServiceGenerator;
import com.andoresu.cryptocalc.core.calculator.data.CalculatorResponse;
import com.andoresu.cryptocalc.core.calculator.data.Currency;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class CalculatorPresenter implements CalculatorContract.ActionsListener{

    private final CalculatorContract.View view;
    private final Context context;
    private final CalculatorService calculatorService;

    public CalculatorPresenter(CalculatorContract.View view, Context context){
        this.view = view;
        this.context = context;
        this.calculatorService = ServiceGenerator.createAPIService(CalculatorService.class);
    }

    @Override
    public void calculate(CalculatorResponse calculatorResponse) {
        calculatorService.calculate(calculatorResponse)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Response<CalculatorResponse>>(view){
                    @Override
                    public void onNext(Response<CalculatorResponse> response) {
                        super.onNext(response);
                        if(response.isSuccessful()){
                            if(response.body() != null){
                                view.showCalculatorResponse(response.body());
                            }
                        }else{
                            view.showCalculatorResponse(calculatorResponse);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.showCalculatorResponse(calculatorResponse);
                    }
                });
    }

    @Override
    public void getCurrencies() {
        calculatorService.getCurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Response<ArrayList<Currency>>>(view){
                    @Override
                    public void onNext(Response<ArrayList<Currency>> response) {
                        super.onNext(response);
                        if(response.isSuccessful()){
                            if(response.body() != null){
                                view.setCurrencies(response.body());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
