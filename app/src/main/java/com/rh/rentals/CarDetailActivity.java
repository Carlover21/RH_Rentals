package com.rh.rentals;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarDetailActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TextView txtCarName, txtCarPrice, txtCarDescription;
    private Button btnBookNow, btnEditCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        // ✅ Initialize UI Elements
        viewPager = findViewById(R.id.viewPager);
        txtCarName = findViewById(R.id.txtCarName);
        txtCarPrice = findViewById(R.id.txtCarPrice);
        txtCarDescription = findViewById(R.id.txtCarDescription);
        btnBookNow = findViewById(R.id.btnBookNow);
        btnEditCar = findViewById(R.id.btnEditCar);

        // ✅ Get Data from Intent
        String carName = getIntent().getStringExtra("carName");
        double carPrice = getIntent().getDoubleExtra("carPrice", -1); // ✅ Fix price retrieval
        String carDescription = getIntent().getStringExtra("carDescription");
        String imageUrisString = getIntent().getStringExtra("carImages");

        // ✅ Fix Price Display (Avoid "null")
        if (carPrice == -1) {
            txtCarPrice.setText("Price: Not Available");
        } else {
            txtCarPrice.setText("Price: $" + carPrice);
        }

        txtCarName.setText(carName);
        txtCarDescription.setText(carDescription);

        // ✅ Convert String image URIs to ArrayList<String>
        List<String> imageList = new ArrayList<>();
        if (imageUrisString != null && !imageUrisString.isEmpty()) {
            imageList = Arrays.asList(imageUrisString.split(","));
        }

        // ✅ Set up ViewPager2 with Adapter
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, new ArrayList<>(imageList));
        viewPager.setAdapter(adapter);

        // ✅ Handle Button Clicks
        btnBookNow.setOnClickListener(v -> {
            Toast.makeText(this, "Booking feature coming soon!", Toast.LENGTH_SHORT).show();
        });

        btnEditCar.setOnClickListener(v -> {
            int carId = getIntent().getIntExtra("carId", -1);

            if (carId == -1) {
                Toast.makeText(this, "Error: Car ID not found!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent editIntent = new Intent(CarDetailActivity.this, EditCarActivity.class);
            editIntent.putExtra("carId", carId);
            editIntent.putExtra("carName", carName);
            editIntent.putExtra("carPrice", carPrice);
            editIntent.putExtra("carImages", imageUrisString);
            startActivity(editIntent);
        });

    }
}
