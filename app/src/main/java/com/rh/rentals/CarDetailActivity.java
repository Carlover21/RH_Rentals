package com.rh.rentals;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarDetailActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TextView txtCarName, txtCarPrice, txtCarDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        // ✅ Corrected: Use ViewPager2 instead of ViewPager
        viewPager = findViewById(R.id.viewPager);
        txtCarName = findViewById(R.id.txtCarName);
        txtCarPrice = findViewById(R.id.txtCarPrice);
        txtCarDescription = findViewById(R.id.txtCarDescription);

        // Get data from Intent
        String carName = getIntent().getStringExtra("carName");
        String carPrice = getIntent().getStringExtra("carPrice");
        String carDescription = getIntent().getStringExtra("carDescription");
        String imageUrisString = getIntent().getStringExtra("carImages"); // Expecting comma-separated image URIs

        // Set text values
        txtCarName.setText(carName);
        txtCarPrice.setText("Price: $" + carPrice);
        txtCarDescription.setText(carDescription);

        // Convert String image URIs to ArrayList<String>
        List<String> imageList = new ArrayList<>();
        if (imageUrisString != null) {
            imageList = Arrays.asList(imageUrisString.split(","));
        }

        // ✅ Corrected: Set up ViewPager2 with RecyclerView.Adapter
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, new ArrayList<>(imageList));
        viewPager.setAdapter(adapter);
    }
}
