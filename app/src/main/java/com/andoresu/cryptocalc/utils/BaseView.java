package com.andoresu.cryptocalc.utils;


import com.andoresu.cryptocalc.client.ErrorResponse;

public interface BaseView {

    void showProgressIndicator(boolean active);

    void showGlobalError(ErrorResponse errorResponse);

    void onLogoutFinish();

    void showMessage(String msg);

}
