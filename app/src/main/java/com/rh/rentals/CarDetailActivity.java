package com.rh.rentals;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Arrays;
import java.util.List;

public class CarDetailActivity extends AppCompatActivity {

    private ViewPager2 viewPagerCarImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        // Initialize ViewPager2
        viewPagerCarImages = findViewById(R.id.viewPagerCarImages);

        // Get the car images from the intent (assuming images are passed as a comma-separated string)
        String carImages = getIntent().getStringExtra("carImages");
        if (carImages != null) {
            String[] imageUris = carImages.split(",");
            List<String> imageList = Arrays.asList(imageUris); // Ensure Arrays is imported

            // Set up the ImagePagerAdapter for ViewPager2
            ImagePagerAdapter adapter = new ImagePagerAdapter(this, imageList);
            viewPagerCarImages.setAdapter(adapter);
        }
    }
}
