package com.example.prithviv.wallhavenapp.HttpRequest;

import com.example.prithviv.wallhavenapp.models.Data;
import com.example.prithviv.wallhavenapp.models.Wallpaper;
import com.example.prithviv.wallhavenapp.models.WallpaperList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WallhavenAPI {
    @GET("search")
    Call<WallpaperList> listLatestWallpapers(@Query("page") int page);

    @GET("search")
    Call<WallpaperList> listLatestWallpapers(@Query("categories") String category,
                                             @Query("purity") String purity,
                                             @Query("sorting") String sorting,
                                             @Query("order") String order,
                                             @Query("page") int page);

    @GET("search")
    Call<WallpaperList> listTopListWallpapers(@Query("categories") String category,
                                            @Query("purity") String purity,
                                            @Query("topRange") String topRange,
                                            @Query("sorting") String sorting,
                                            @Query("order") String order,
                                            @Query("page") int page);

    @GET("search")
    Call<WallpaperList> listSearchWallpapers(@Query("q") String query, @Query("page") int page);

    @GET("search")
    Call<WallpaperList> listSearchWallpapers(@Query("q") String query,
                                             @Query("categories") String category,
                                             @Query("purity") String purity,
                                             @Query("sorting") String sorting,
                                             @Query("order") String order,
                                             @Query("page") int page);

    @GET("w/{wallpaperID}")
    Call<Wallpaper> getWallpaper(@Path("wallpaperID") String wallpaperID);
}
