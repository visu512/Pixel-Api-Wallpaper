package com.example.mywallpaper;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mywallpaper.Models.Wallpaper;

import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.ViewHolder> {
    private Context context;
    private List<Wallpaper> imageList;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public WallpaperAdapter(Context context, List<Wallpaper> imageList) {
        this.context = context;
        this.imageList = imageList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Show progress bar while loading
        holder.progressbar.setVisibility(View.VISIBLE);

        Glide.with(context)
                .load(imageList.get(position).getSrc().getMedium())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressbar.setVisibility(View.GONE);
                        return false; // Return false to allow Glide's error handling
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressbar.setVisibility(View.GONE);
                        return false; // Return false to allow Glide's default behavior
                    }
                })
                .into(holder.imageView);

        holder.imageView.setOnClickListener(v -> recyclerViewClickInterface.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ProgressBar progressbar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.wallpaper);
            progressbar = itemView.findViewById(R.id.progressBar);
        }
    }
}
