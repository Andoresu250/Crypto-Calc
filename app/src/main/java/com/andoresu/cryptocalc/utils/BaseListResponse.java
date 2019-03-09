package com.andoresu.cryptocalc.utils;

import java.io.Serializable;

public class BaseListResponse implements Serializable {

    public static final int PER_PAGE = 15;

    public Integer totalCount = 0;

    public int getTotalPage(){

        double res = (double)totalCount /(double) PER_PAGE;
        return (int) Math.ceil(res);
    }

}