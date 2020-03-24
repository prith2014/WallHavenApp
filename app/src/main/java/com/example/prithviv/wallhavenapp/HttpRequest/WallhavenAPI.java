package com.example.prithviv.wallhavenapp.HttpRequest;

import com.example.prithviv.wallhavenapp.models.Wallpaper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface WallhavenAPI {
    @GET("search")
    Call<Wallpaper> listLatestWallpapers();
}
