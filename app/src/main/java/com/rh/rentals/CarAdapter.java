package com.rh.rentals;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private final Context context;
    private final List<Car> carList;
    private final DatabaseHelper databaseHelper;

    public CarAdapter(Context context, List<Car> carList, DatabaseHelper databaseHelper) {
        this.context = context;
        this.carList = carList;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);

        holder.textViewCarName.setText(car.getName());
        holder.textViewCarPrice.setText(context.getString(R.string.car_price, car.getPrice()));

        // Ensure car.getImageUris() is not null before calling split()
        String imageUris = car.getImageUris();
        if (imageUris != null && !imageUris.isEmpty()) {
            String[] imageUrisArray = imageUris.split(",");
            List<String> imageList = new ArrayList<>(Arrays.asList(imageUrisArray));

            // Set up ViewPager2 with ImagePagerAdapter
            ImagePagerAdapter adapter = new ImagePagerAdapter(context, imageList);
            holder.viewPagerCarImages.setAdapter(adapter);
        } else {
            holder.viewPagerCarImages.setAdapter(null); // Clear the adapter if no images
        }

        // Handle Delete Car Button
        holder.btnDeleteCar.setOnClickListener(v -> {
            databaseHelper.deleteCar(car.getId()); // Remove car from DB
            carList.remove(position);
            notifyItemRemoved(position);
        });

        // Handle Edit Car Button
        holder.btnEditCar.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditCarActivity.class);
            intent.putExtra("carId", car.getId());
            intent.putExtra("carName", car.getName());
            intent.putExtra("carPrice", car.getPrice());
            intent.putExtra("carImages", car.getImageUris()); // Send images as string
            context.startActivity(intent);
        });

        // Handle Click to View Car Details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CarDetailActivity.class);
            intent.putExtra("carName", car.getName());
            intent.putExtra("carPrice", car.getPrice());
            intent.putExtra("carImages", car.getImageUris()); // Send images as string
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    // ✅ Added updateList() method to refresh RecyclerView dynamically
    public void updateList(List<Car> updatedList) {
        this.carList.clear();
        this.carList.addAll(updatedList);
        notifyItemRangeChanged(0, updatedList.size());
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCarName, textViewCarPrice;
        ViewPager2 viewPagerCarImages;
        Button btnDeleteCar, btnEditCar;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCarName = itemView.findViewById(R.id.textViewCarName);
            textViewCarPrice = itemView.findViewById(R.id.textViewCarPrice);
            viewPagerCarImages = itemView.findViewById(R.id.viewPagerCarImages);
            btnDeleteCar = itemView.findViewById(R.id.btnDeleteCar);
            btnEditCar = itemView.findViewById(R.id.btnEditCar);
        }
    }
}
