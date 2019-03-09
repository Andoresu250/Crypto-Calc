package com.andoresu.cryptocalc.core.calculator.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Currency implements Serializable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("symbol")
    @Expose
    public String symbol;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("symbolNative")
    @Expose
    public String symbolNative;
    @SerializedName("decimalDigits")
    @Expose
    public Integer decimalDigits;
    @SerializedName("rounding")
    @Expose
    public Integer rounding;
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("namePlural")
    @Expose
    public String namePlural;
    public boolean selected = false;

    @Override
    public String toString() {
        return code + "(" + symbolNative + ")";
    }
}