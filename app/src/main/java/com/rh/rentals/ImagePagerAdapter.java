package com.rh.rentals;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder> {
    private Context context;
    private List<String> imageUris;

    public ImagePagerAdapter(Context context, List<String> imageUris) {
        this.context = context;
        this.imageUris = imageUris;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_slider_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Uri imageUri = Uri.parse(imageUris.get(position));
        Glide.with(context).load(imageUri).into(holder.imageView);  // ✅ Load image using Glide
    }

    @Override
    public int getItemCount() {
        return imageUris.size();
    }

    // ✅ Fix: Define ImageViewHolder properly
    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView); // ✅ Ensure correct ID
        }
    }
}
