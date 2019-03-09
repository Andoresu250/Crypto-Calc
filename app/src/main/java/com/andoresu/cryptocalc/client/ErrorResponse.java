package com.andoresu.cryptocalc.client;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;

import retrofit2.Response;

@SuppressLint("LogNotTimber")
public class ErrorResponse implements Serializable{

    private final static String TAG = "GORDYSKY_" + ErrorResponse.class.getSimpleName();

    public String error;
    public String[] errors;
    public String raw;


    public ErrorResponse(){}

    public static ErrorResponse getFailErrorResponse(){
        return new ErrorResponse("Error Desconocido ", 0);
    }

    public static ErrorResponse getBadJsonResponse(){
        return new ErrorResponse("Error al deserializar información ", 0);
    }

    public static ErrorResponse failConnection(){
        return new ErrorResponse("No hay una red disponible ", 0);
    }

    public static ErrorResponse timeOut(){
        return new ErrorResponse("Tiempo de espera agotado intente otra vez ", 0);
    }

    public static ErrorResponse runTimeException(){
        return new ErrorResponse("Error de ejecución ", 0);
    }

    public ErrorResponse(String error, int code){
        this.error = error + " Codigo error: " + code;
    }

    public ErrorResponse(String error, String[] errors) {
        this.error = error;
        this.errors = errors;
    }

    public static ErrorResponse response(String response){
        Gson gson = new Gson();
        ErrorResponse errorResponse;
        Log.i(TAG, "response: " + response);
        try{
            errorResponse = gson.fromJson(response, ErrorResponse.class);
        }catch (Exception e){
//            e.printStackTrace();
            errorResponse = new ErrorResponse("Ha ocurrido un error en el servidor.", 0);
        }
        errorResponse.raw = response;
        return errorResponse;
    }


    public static ErrorResponse response(Response response){
        Log.i(TAG, "response: constructor init");
        if(response.code() >= 500){
            Log.i(TAG, "response: server error");
            return new ErrorResponse("Ha ocurrido un error en el servidor.", response.code());
        }

        try {
            Log.i(TAG, "response: try to get string from error body");
            String s = response.errorBody().string();
            Log.i(TAG, "response: successful get string from error body");
            return ErrorResponse.response(s);
        } catch (IOException e) {
            Log.i(TAG, "response: failed to get string from error body");
            e.printStackTrace();
            return new ErrorResponse("Ha ocurrido un error en el servidor.", response.code());
        }

    }

    private String getError(){
        String s = "";
        if(this.error != null){
            s += this.error + "\n";
        }
        if(this.errors != null){
            for(String error : this.errors){
                s += error + "\n";
            }
        }
        return s.equals("") ? null : s;
    }

    @Override
    public String toString() {
        return getError();
    }
}