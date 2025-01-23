package com.rh.rentals;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarDetailActivity extends AppCompatActivity {
    private ViewPager2 viewPagerCarImages;
    private ImagePagerAdapter imagePagerAdapter;
    private TextView textViewCarName, textViewCarPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        // ✅ Initialize Views
        viewPagerCarImages = findViewById(R.id.viewPagerCarImages);
        textViewCarName = findViewById(R.id.textViewCarName);
        textViewCarPrice = findViewById(R.id.textViewCarPrice);

        if (viewPagerCarImages == null) {
            throw new NullPointerException("ViewPager2 is NULL! Check XML ID in activity_car_detail.xml");
        }

        // ✅ Get Data from Intent
        String carName = getIntent().getStringExtra("carName");
        double carPrice = getIntent().getDoubleExtra("carPrice", 0.0);
        String imageUrisString = getIntent().getStringExtra("carImages");

        // ✅ Handle Null Values Gracefully
        List<String> imageUris = new ArrayList<>();
        if (imageUrisString != null && !imageUrisString.isEmpty()) {
            imageUris = Arrays.asList(imageUrisString.split(","));
        }

        // ✅ Display Car Name & Price
        textViewCarName.setText(carName != null ? carName : "Unknown Car");
        textViewCarPrice.setText("Price: $" + carPrice);

        // ✅ Set up ViewPager2 with ImagePagerAdapter
        imagePagerAdapter = new ImagePagerAdapter(this, imageUris);
        viewPagerCarImages.setAdapter(imagePagerAdapter);
    }
}
