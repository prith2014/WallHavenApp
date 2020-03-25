package com.example.prithviv.wallhavenapp.HttpRequest;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServer {
    private static final String WALLHAVEN_API_URL = "https://wallhaven.cc/api/v1/";
    private Retrofit retrofit;

    public RetrofitServer() {
        retrofit = new Retrofit.Builder()
                .baseUrl(WALLHAVEN_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public Retrofit getRetrofitInstance() {
        return retrofit;
    }
}
