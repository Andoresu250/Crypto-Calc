package com.andoresu.cryptocalc.core.calculator.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CalculatorResponse implements Serializable{

    public static final int BTC_MODE = 1;
    public static final int VALUE_MODE = 2;

    @SerializedName("btc")
    @Expose
    public Double btc = 1d;
    @SerializedName("currency")
    @Expose
    public String currency = "USD";
    @SerializedName("symbol")
    @Expose
    public String symbol = "$";
    @SerializedName("value")
    @Expose
    public Double value;
}
