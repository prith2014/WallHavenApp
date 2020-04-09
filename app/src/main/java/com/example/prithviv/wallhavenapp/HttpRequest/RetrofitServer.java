package com.example.prithviv.wallhavenapp.HttpRequest;

import android.util.Log;

import com.example.prithviv.wallhavenapp.models.WallpaperList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServer {
    private static final String WALLHAVEN_API_URL = "https://wallhaven.cc/api/v1/";
    private Retrofit retrofit;
    private WallhavenAPI wallhavenAPI;
    private boolean isWallpaperLoading;
    private int pageNumber;

    public RetrofitServer() {
        retrofit = new Retrofit.Builder()
                .baseUrl(WALLHAVEN_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        wallhavenAPI = retrofit.create(WallhavenAPI.class);

        isWallpaperLoading = false;
        pageNumber = 0;
    }

    public Retrofit getRetrofitInstance() {
        return retrofit;
    }

    public void setIsWallpaperLoading(boolean isLoading) {
        isWallpaperLoading = isLoading;
    }

    public boolean isWallpaperLoading() {
        return isWallpaperLoading;
    }

    public WallpaperList getWallpapers(WallpaperList wallpaperList) {
        setIsWallpaperLoading(true);
        Call<WallpaperList> retroCall = wallhavenAPI.listLatestWallpapers(getNextPageNumber());

        retroCall.enqueue(new Callback<WallpaperList>() {
            @Override
            public void onResponse(Call<WallpaperList> call, Response<WallpaperList> response) {
                if (response.isSuccessful()) {
                    //wallpaperList = response.body();
                }
            }

            @Override
            public void onFailure(Call<WallpaperList> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

        return wallpaperList;
    }

    private int getNextPageNumber() {
        pageNumber++;
        //Log.d("Page", Integer.toString(pageNumber));
        return pageNumber;
    }
}
