package com.rh.rentals;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2; // ✅ Ensure ViewPager2 is imported
import java.util.ArrayList;
import java.util.List;

public class AddCarActivity extends AppCompatActivity {
    private ViewPager2 viewPagerCarImages; // ✅ Updated to ViewPager2
    private EditText etCarName, etCarPrice;
    private Button btnSelectImages, btnSaveCar;
    private List<String> selectedImageUris = new ArrayList<>();
    private ImagePagerAdapter imagePagerAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        // Initialize UI elements
        viewPagerCarImages = findViewById(R.id.viewPagerCarImages);
        etCarName = findViewById(R.id.etCarName);
        etCarPrice = findViewById(R.id.etCarPrice);
        btnSelectImages = findViewById(R.id.btnSelectImages);
        btnSaveCar = findViewById(R.id.btnSaveCar);
        databaseHelper = new DatabaseHelper(this);

        // Initialize adapter for ViewPager2
        imagePagerAdapter = new ImagePagerAdapter(this, selectedImageUris);
        viewPagerCarImages.setAdapter(imagePagerAdapter); // ✅ No more type conversion error

        // Handle image selection
        btnSelectImages.setOnClickListener(v -> selectImages());

        // Handle saving the car data
        btnSaveCar.setOnClickListener(v -> saveCarToDatabase());
    }

    // ✅ Allow user to select multiple images
    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    if (result.getData().getClipData() != null) {
                        int count = result.getData().getClipData().getItemCount();
                        for (int i = 0; i < count; i++) {
                            Uri imageUri = result.getData().getClipData().getItemAt(i).getUri();
                            selectedImageUris.add(imageUri.toString());
                        }
                    } else if (result.getData().getData() != null) {
                        Uri imageUri = result.getData().getData();
                        selectedImageUris.add(imageUri.toString());
                    }
                    imagePagerAdapter.notifyDataSetChanged();
                }
            });

    private void selectImages() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imagePickerLauncher.launch(intent);
    }

    // ✅ Save car with images to SQLite database
    private void saveCarToDatabase() {
        String carName = etCarName.getText().toString().trim();
        String carPrice = etCarPrice.getText().toString().trim();

        if (carName.isEmpty() || carPrice.isEmpty() || selectedImageUris.isEmpty()) {
            return;
        }

        String imageUrisString = String.join(",", selectedImageUris); // Store URIs as comma-separated string
        databaseHelper.addCar(carName, Double.parseDouble(carPrice), imageUrisString);
        finish(); // Close activity after saving
    }
}
