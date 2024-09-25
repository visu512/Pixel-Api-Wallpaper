package com.example.mywallpaper.Api;

import com.example.mywallpaper.Models.WallpaperResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

// this interface is implements for API
public interface Api {

    @GET("curated")
    Call<WallpaperResponse> getWallpaper(
            @Header("Authorization") String credentials,
            @Query("page") int pageCount,
            @Query("per_page") int perPage
    );

}
