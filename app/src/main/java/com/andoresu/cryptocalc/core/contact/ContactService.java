package com.andoresu.cryptocalc.core.contact;

import com.andoresu.cryptocalc.core.calculator.data.CalculatorResponse;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ContactService {

    @POST("contacts/")
    Observable<Response<ResponseBody>> create(@Body HashMap<String, List<ContactModel>> hashMap);
}
