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

        holder.btnDeleteCar.setOnClickListener(v -> {
            databaseHelper.deleteCar(car.getId());
            carList.remove(position);
            notifyItemRemoved(position);
        });

        holder.btnEditCar.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditCarActivity.class);
            intent.putExtra("carId", car.getId());
            intent.putExtra("carName", car.getName());
            intent.putExtra("carPrice", car.getPrice());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public void updateList(List<Car> updatedList) {
        this.carList.clear();
        this.carList.addAll(updatedList);
        notifyDataSetChanged();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCarName, textViewCarPrice;
        Button btnDeleteCar, btnEditCar;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCarName = itemView.findViewById(R.id.textViewCarName);
            textViewCarPrice = itemView.findViewById(R.id.textViewCarPrice);
            btnDeleteCar = itemView.findViewById(R.id.btnDeleteCar);
            btnEditCar = itemView.findViewById(R.id.btnEditCar);
        }
    }
}
