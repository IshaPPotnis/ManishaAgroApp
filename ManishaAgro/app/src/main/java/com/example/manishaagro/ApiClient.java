package com.example.manishaagro;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://activexsolutions.com/php/";
 // private static final String BASE_URL = "http://192.168.43.204/AgroApi/Includes/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient(){

        Gson gson=new  GsonBuilder().setLenient().create();
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
