package com.rh.rentals;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2; // ✅ Ensure ViewPager2 is used
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private Context context;
    private List<Car> carList;
    private DatabaseHelper databaseHelper;

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
        holder.textViewCarPrice.setText("Price: $" + car.getPrice());

        // Convert stored image URI string to a list
        String[] imageUris = car.getImageUris().split(",");
        List<String> imageList = new ArrayList<>(Arrays.asList(imageUris));

        // ✅ Set up ViewPager2 with ImagePagerAdapter
        ImagePagerAdapter adapter = new ImagePagerAdapter(context, imageList);
        holder.viewPagerCarImages.setAdapter(adapter); // ✅ Fix type error (now uses ViewPager2)

        // Handle Delete Car Button
        holder.btnDeleteCar.setOnClickListener(v -> {
            databaseHelper.deleteCar(car.getId()); // Remove car from DB
            carList.remove(position);
            notifyItemRemoved(position);
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

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCarName, textViewCarPrice;
        ViewPager2 viewPagerCarImages; // ✅ Updated to ViewPager2
        Button btnDeleteCar;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCarName = itemView.findViewById(R.id.textViewCarName);
            textViewCarPrice = itemView.findViewById(R.id.textViewCarPrice);
            viewPagerCarImages = itemView.findViewById(R.id.viewPagerCarImages); // ✅ Updated reference
            btnDeleteCar = itemView.findViewById(R.id.btnDeleteCar);
        }
    }
}
