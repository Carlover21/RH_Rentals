package com.rh.rentals;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarListActivity extends AppCompatActivity {
    private RecyclerView recyclerViewCars;
    private CarAdapter carAdapter;
    private DatabaseHelper databaseHelper;
    private TextView txtNoCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        recyclerViewCars = findViewById(R.id.recyclerViewCars);
        txtNoCars = findViewById(R.id.txtNoCars);
        databaseHelper = new DatabaseHelper(this);

        recyclerViewCars.setLayoutManager(new LinearLayoutManager(this));

        // Fix: Ensure correct constructor is used
        List<Car> carList = databaseHelper.getAllCars();
        carAdapter = new CarAdapter(this, carList, databaseHelper);
        recyclerViewCars.setAdapter(carAdapter);

        checkIfCarsExist();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCarList();
    }

    private void loadCarList() {
        List<Car> carList = databaseHelper.getAllCars();
        carAdapter.updateList(carList); // Now this method exists
        checkIfCarsExist();
    }

    private void checkIfCarsExist() {
        if (carAdapter.getItemCount() == 0) {
            txtNoCars.setVisibility(View.VISIBLE);
            recyclerViewCars.setVisibility(View.GONE);
        } else {
            txtNoCars.setVisibility(View.GONE);
            recyclerViewCars.setVisibility(View.VISIBLE);
        }
    }
}
