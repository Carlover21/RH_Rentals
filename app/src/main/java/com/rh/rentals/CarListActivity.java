package com.rh.rentals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CarListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private DatabaseHelper databaseHelper;
    private List<Car> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewCars);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Database Helper
        databaseHelper = new DatabaseHelper(this);

        // Load cars from database
        loadCars();
    }

    private void loadCars() {
        carList = databaseHelper.getAllCars(); // Fetch all saved cars

        if (carList.isEmpty()) {
            Toast.makeText(this, "No cars available", Toast.LENGTH_SHORT).show();
        } else {
            carAdapter = new CarAdapter(this, carList, databaseHelper);
            recyclerView.setAdapter(carAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCars(); // Reload car list when returning to this screen
    }
}
