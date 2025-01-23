package com.rh.rentals;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CarListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;  // ✅ Declare RecyclerView
    private CarAdapter carAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        recyclerView = findViewById(R.id.recyclerView);  // ✅ Initialize RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);
        List<Car> carList = databaseHelper.getAllCars();

        // ✅ Debugging: Check if cars exist in the database
        if (carList.isEmpty()) {
            Log.e("CarListActivity", "No cars found in database");
        } else {
            Log.d("CarListActivity", "Loaded " + carList.size() + " cars from database");
        }

        carAdapter = new CarAdapter(this, carList, databaseHelper);
        recyclerView.setAdapter(carAdapter);  // ✅ Set Adapter
    }
}
