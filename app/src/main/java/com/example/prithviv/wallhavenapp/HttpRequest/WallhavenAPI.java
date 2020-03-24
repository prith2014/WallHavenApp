package com.example.prithviv.wallhavenapp.HttpRequest;

import com.example.prithviv.wallhavenapp.models.Data;
import com.example.prithviv.wallhavenapp.models.Wallpaper;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WallhavenAPI {
    @GET("search")
    Call<Wallpaper> listLatestWallpapers(@Query("page") int page);

    @GET("w/{wallpaperID}")
    Call<Data> getWallpaper(@Path("wallpaperID") String wallpaperID);
}
