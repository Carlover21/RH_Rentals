package com.rh.rentals;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;

public class CarDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        // Get data from the intent
        String carName = getIntent().getStringExtra("carName");
        String carPrice = getIntent().getStringExtra("carPrice");
        String carDescription = getIntent().getStringExtra("carDescription");
        int[] imageIds = getIntent().getIntArrayExtra("imageIds");

        // Set the car name, price, and description in TextViews
        TextView txtCarName = findViewById(R.id.txtCarName);
        TextView txtCarPrice = findViewById(R.id.txtCarPrice);
        TextView txtCarDescription = findViewById(R.id.txtCarDescription);
        txtCarName.setText(carName);
        txtCarPrice.setText("Price per day: " + carPrice);
        txtCarDescription.setText(carDescription);

        // Convert int[] to ArrayList<Integer>
        ArrayList<Integer> imageList = new ArrayList<>();
        for (int id : imageIds) {
            imageList.add(id);
        }

        // Set up ViewPager for sliding images
        ViewPager viewPager = findViewById(R.id.viewPager);
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, imageList);
        viewPager.setAdapter(adapter);
    }
}
