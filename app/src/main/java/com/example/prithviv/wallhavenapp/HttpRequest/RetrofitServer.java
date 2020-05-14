package com.example.prithviv.wallhavenapp.HttpRequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.example.prithviv.wallhavenapp.ContextProvider;
import com.example.prithviv.wallhavenapp.models.Wallpaper;
import com.example.prithviv.wallhavenapp.models.WallpaperList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/*
I think what you want is to write a facade
that's an interface with the methods you want

your program is concerned with, for example, there existing a, I dunno, getProducts method, which returns a list of products
you might then implement that facade, which contains that method, via the http requests
and then you might implement it again to just return a static value for testing or developing

I would have a WallpaperFacade, which is an interface, that has a method getWallpapers
then a WallpaperService, which is a class, which implements that Facade, and which handles the http calls

 */
public class RetrofitServer {
    private static final String WALLHAVEN_API_URL = "https://wallhaven.cc/api/v1/";
    private static final String GENERAL_CATEGORY = "com.example.wallhavenapp.generalcategory";
    private static final String ANIME_CATEGORY = "com.example.wallhavenapp.animecategory";
    private static final String PEOPLE_CATEGORY = "com.example.wallhavenapp.peoplecategory";
    private static final String SFW_PURITY = "com.example.wallhavenapp.sfwpurity";
    private static final String SKETCHY_PURITY = "com.example.wallhavenapp.sketchypurity";
    private static final String NSFW_PURITY = "com.example.wallhavenapp.nsfwpurity";

    private Retrofit retrofit;
    private WallhavenAPI wallhavenAPI;
    private boolean isWallpaperLoading;
    private int pageNumber;
    private ContextProvider contextProvider;

    public RetrofitServer(ContextProvider contextProvider) {
        retrofit = new Retrofit.Builder()
                .baseUrl(WALLHAVEN_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        wallhavenAPI = retrofit.create(WallhavenAPI.class);
        this.contextProvider = contextProvider;

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

    public Call<WallpaperList> getLatestWallpapersCall() {
        setIsWallpaperLoading(true);
        Call<WallpaperList> retroCall = wallhavenAPI.listLatestWallpapers(getUserSetCategories(),
                getUserSetPurity(), "date_added", "desc", getNextPageNumber());
        return retroCall;
    }

    public Call<WallpaperList> getToplistWallpapersCall() {
        setIsWallpaperLoading(true);
        Call<WallpaperList> retroCall = wallhavenAPI.listTopListWallpapers(getUserSetCategories(),
                getUserSetPurity(), "1M", "toplist", "desc", getNextPageNumber());
        return retroCall;
    }

    public Call<WallpaperList> getSearchWallpapersCall(String searchQuery) {
        setIsWallpaperLoading(true);
        Call<WallpaperList> retroCall = wallhavenAPI.listSearchWallpapers(searchQuery, getUserSetCategories(),
                getUserSetPurity(), "relevance", "desc", getNextPageNumber());
        return retroCall;
    }

    public Call<Wallpaper> getSelectedWallpaper(String selectedWallpaperID) {
        setIsWallpaperLoading(true);
        Call<Wallpaper> retroCall = wallhavenAPI.getWallpaper(selectedWallpaperID);
        return retroCall;
    }

    public void refreshPageNumber() {
        pageNumber = 0;
    }

    private int getNextPageNumber() {
        pageNumber++;
        //Log.d("Page", Integer.toString(pageNumber));
        return pageNumber;
    }

    private String getUserSetCategories() {
        //SharedPreferences sharedPreferences = contextProvider.getContext().getSharedPreferences("com.example.wallhavenapp", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(contextProvider.getContext());
        boolean general = sharedPreferences.getBoolean(GENERAL_CATEGORY, true);
        boolean anime = sharedPreferences.getBoolean(ANIME_CATEGORY, true);
        boolean people = sharedPreferences.getBoolean(PEOPLE_CATEGORY, true);

        StringBuilder sb = new StringBuilder();
        sb.append(general ? "1" : "0");
        sb.append(anime ? "1" : "0");
        sb.append(people ? "1" : "0");
        //Log.d("Categories", sb.toString());

        return sb.toString();
    }

    private String getUserSetPurity() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(contextProvider.getContext());
        boolean sfw = sharedPreferences.getBoolean(SFW_PURITY, true);
        boolean sketchy = sharedPreferences.getBoolean(SKETCHY_PURITY, false);
        boolean nsfw = sharedPreferences.getBoolean(NSFW_PURITY, false);

        StringBuilder sb = new StringBuilder();
        sb.append(sfw ? "1" : "0");
        sb.append(sketchy ? "1" : "0");
        sb.append(nsfw ? "1" : "0");

        return sb.toString();
    }
}
