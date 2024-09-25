package com.example.mywallpaper.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

// model class get photos
public class WallpaperResponse {

    @SerializedName("photos")
    private List<Wallpaper> photosList;

    // constructor
    public WallpaperResponse(List<Wallpaper> photosList) {
        this.photosList = photosList;
    }

    public List<Wallpaper> getPhotosList() {
        return photosList;
    }

    public void setPhotosList(List<Wallpaper> photosList) {
        this.photosList = photosList;
    }
}
