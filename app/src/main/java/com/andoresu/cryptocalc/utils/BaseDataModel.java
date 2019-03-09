package com.andoresu.cryptocalc.utils;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseDataModel implements Serializable {

    private static final String DATE_FORMAT_SIMPLE = "dd-MM-yyyy";
    private static final String DATE_FORMAT_FULL = "dd-MM-yyyy hh:mm";

    public String id;
    public Date createdAt;
    public Date updatedAt;

    public BaseDataModel(){}

    @Override
    public String toString() {
        String s = "";
        s += "id: " + id + "\n";
        s += "createdAt: " + createdAt + "\n";
        s += "updatedAt: " + updatedAt + "\n";
        return s;
    }

    @SuppressLint("SimpleDateFormat")
    public String getSimpleCreatedAt(){
        return simpleDate(createdAt);
    }

    @SuppressLint("SimpleDateFormat")
    public String getCreatedAt(){
        return fullDate(updatedAt);
    }

    @SuppressLint("SimpleDateFormat")
    public String simpleDate(Date date){
        if(date == null){
            return "";
        }
        return new SimpleDateFormat(DATE_FORMAT_SIMPLE).format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public String fullDate(Date date){
        if(date == null){
            return "";
        }
        return new SimpleDateFormat(DATE_FORMAT_FULL).format(date);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BaseDataModel){
            return id.equals(((BaseDataModel) obj).id);
        }
        return false;
    }
}
