package com.andoresu.cryptocalc.core;

import android.content.Context;
import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.andoresu.cryptocalc.utils.BaseFragment.BASE_TAG;

public class MainPresenter implements MainContract.ActionsListener{


    private String TAG = BASE_TAG + MainPresenter.class.getSimpleName();

    private final MainContract.View mainView;

    private final Context context;

//    private final LoginService loginService;

    public MainPresenter(@NonNull MainContract.View mainView, @NonNull Context context, String authToken){
        this.mainView = mainView;
        this.context = context;
//        this.loginService = ServiceGenerator.createService(LoginService.class, authToken);

    }

    @Override
    public void logout() {

    }

}
