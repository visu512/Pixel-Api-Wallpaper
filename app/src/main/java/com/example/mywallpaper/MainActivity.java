package com.example.mywallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywallpaper.Api.RetrofitClient;
import com.example.mywallpaper.Models.Wallpaper;
import com.example.mywallpaper.Models.WallpaperResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView imageRecyclerView;
    private final String API_KEY ="H6Q8zrM4s9UKparRCYl64ugHo372LUv2wWd1oujnsuf0yOqVRML2vgeg";
    private int pageCount = 1;
    private static int perPage = 80;
    private List<Wallpaper> wallpaperList = new ArrayList<>();
    private NestedScrollView nestedScrollview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Set the status bar color
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary, null));


        initRecyclerview();


        nestedScrollview = findViewById(R.id.nestedScrollView);
        setUpPagination(true);

    }

    // Add the paginations
    private void setUpPagination(boolean isPaginationAllowed) {
        if (isPaginationAllowed) {
            try {
                nestedScrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        // Check if the user has scrolled to the bottom of the NestedScrollView
                        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                            // Fetch data for the next page
                            fetchData(++pageCount);
                            Toast.makeText(MainActivity.this, "Page: " + pageCount, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (Exception e) {
                // Handle any exceptions that occur
                e.printStackTrace(); // Log the exception for debugging
                Toast.makeText(MainActivity.this, "Error setting up pagination: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void initRecyclerview() {
        imageRecyclerView =  findViewById(R.id.recycler);
        imageRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        imageRecyclerView.setLayoutManager(gridLayoutManager);

        fetchData(pageCount);
    }

    private void fetchData(int pageCount) {

        Call<WallpaperResponse> call = RetrofitClient
                .getInstance()
                .getApi().getWallpaper(API_KEY,pageCount,perPage);


        call.enqueue(new Callback<WallpaperResponse>() {
            @Override
            public void onResponse(Call<WallpaperResponse> call, Response<WallpaperResponse> response) {

            WallpaperResponse wallpaperResponse = response.body();
                if(response.isSuccessful() && null != wallpaperResponse){
                 wallpaperList.addAll(wallpaperResponse.getPhotosList());
                 WallpaperAdapter wallpaperAdapter = new WallpaperAdapter(getApplicationContext(),wallpaperList);
                 imageRecyclerView.setAdapter(wallpaperAdapter);
                 wallpaperAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<WallpaperResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}