package com.andoresu.cryptocalc.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.andoresu.cryptocalc.client.ObserverResponse;
import com.andoresu.cryptocalc.client.ServiceGenerator;
import com.andoresu.cryptocalc.core.contact.ContactModel;
import com.andoresu.cryptocalc.core.contact.ContactService;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.andoresu.cryptocalc.utils.BaseFragment.BASE_TAG;

public class MainPresenter implements MainContract.ActionsListener{


    private String TAG = BASE_TAG + MainPresenter.class.getSimpleName();

    private final MainContract.View mainView;

    private final Context context;

//    private final LoginService loginService;
    private final ContactService contactService;

    public MainPresenter(@NonNull MainContract.View mainView, @NonNull Context context, String authToken){
        this.mainView = mainView;
        this.context = context;
        this.contactService = ServiceGenerator.createServiceUrl(ContactService.class, ServiceGenerator.BW_URL);
    }

    @Override
    public void logout() {

    }

    @Override
    public void sendContact(List<ContactModel> contactModels) {
        HashMap<String, List<ContactModel>> hashMap = new HashMap<>();
        hashMap.put("contacts", contactModels);
        contactService.create(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Response<ResponseBody>>(){
                    @Override
                    public void onNext(Response<ResponseBody> responseBodyResponse) {
                        super.onNext(responseBodyResponse);
                        Log.i(TAG, "onNext: contacts sended");
                    }
                });
    }
}
