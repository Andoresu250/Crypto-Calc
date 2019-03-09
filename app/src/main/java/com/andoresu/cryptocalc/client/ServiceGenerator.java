package com.andoresu.cryptocalc.client;


import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.andoresu.cryptocalc.client.GsonBuilderUtils.getGenericGson;
import static com.andoresu.cryptocalc.client.GsonBuilderUtils.getUserGson;
import static com.andoresu.cryptocalc.utils.BaseFragment.BASE_TAG;


public class ServiceGenerator {

    public static final String TAG = BASE_TAG + ServiceGenerator.class.getSimpleName();

    private static final String API_URL = "https://crypto-calculator-api.herokuapp.com/";
//    private static final String API_URL = "https://gordisky-api.herokuapp.com/";


    public static <S> S createAPIService(Class<S> serviceClass){
        return createServiceUrl(serviceClass, API_URL);
    }

    private static <S> S createServiceUrl(Class<S> serviceClass, String url){
        AuthenticationInterceptor interceptor =
                new AuthenticationInterceptor(null);

        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .addInterceptor(interceptor)
                ;

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(url)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create(getUserGson()))
                ;

        Retrofit retrofit = builder.build();

        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }

    public static <S> S createService(Class<S> serviceClass, Gson gson) {
        return createService(serviceClass, null, gson);
    }
//
    public static <S> S createService(Class<S> serviceClass, String authToken) {
        return createService(serviceClass, authToken, null);
    }
//
    public static <S> S createService(Class<S> serviceClass, final String authToken, Gson gson) {


        AuthenticationInterceptor interceptor =
                new AuthenticationInterceptor(authToken);

        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                ;

        if (!httpClient.interceptors().contains(interceptor)) {
            httpClient.addInterceptor(interceptor);
        }else{
            httpClient.interceptors().remove(interceptor);
            httpClient.addInterceptor(interceptor);
        }

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        builder.client(httpClient.build());

        if(gson != null){
            builder.addConverterFactory(GsonConverterFactory.create(gson));
        }else{
            builder.addConverterFactory(GsonConverterFactory.create(getGenericGson()));
        }

        Retrofit retrofit = builder.build();

        return retrofit.create(serviceClass);
    }

    private static class AuthenticationInterceptor implements Interceptor {

        final String authToken;

        private AuthenticationInterceptor(String authToken) {
            this.authToken = authToken;
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .method(original.method(), original.body());

            if(authToken != null){
                builder.header("Authorization", authToken);
            }

            Request request = builder.build();
            return chain.proceed(request);
        }
    }



}
