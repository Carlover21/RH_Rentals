package com.rh.rentals;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;

public class AddCarActivity extends AppCompatActivity {
    private ViewPager2 viewPagerCarImages;
    private EditText etCarName, etCarPrice;
    private Button btnSelectImages, btnSaveCar;
    private List<String> selectedImageUris = new ArrayList<>();
    private ImagePagerAdapter imagePagerAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        // ✅ Initialize UI elements
        viewPagerCarImages = findViewById(R.id.viewPagerCarImages);
        etCarName = findViewById(R.id.etCarName);
        etCarPrice = findViewById(R.id.etCarPrice);
        btnSelectImages = findViewById(R.id.btnSelectImages);
        btnSaveCar = findViewById(R.id.btnSaveCar);
        databaseHelper = new DatabaseHelper(this);

        // ✅ Initialize adapter for ViewPager2
        imagePagerAdapter = new ImagePagerAdapter(this, selectedImageUris);
        viewPagerCarImages.setAdapter(imagePagerAdapter);

        // ✅ Handle Image Selection
        btnSelectImages.setOnClickListener(v -> selectImages());

        // ✅ Handle Save Car Button Click
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

                            // ✅ Persist permission for image URI
                            getContentResolver().takePersistableUriPermission(
                                    imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            selectedImageUris.add(imageUri.toString());
                        }
                    } else if (result.getData().getData() != null) {
                        Uri imageUri = result.getData().getData();

                        // ✅ Persist permission for single image
                        getContentResolver().takePersistableUriPermission(
                                imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

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

        // ✅ Validate user input
        if (carName.isEmpty()) {
            etCarName.setError("Car name is required!");
            return;
        }
        if (carPrice.isEmpty()) {
            etCarPrice.setError("Price is required!");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(carPrice);
        } catch (NumberFormatException e) {
            etCarPrice.setError("Enter a valid price!");
            return;
        }

        if (selectedImageUris.isEmpty()) {
            Toast.makeText(this, "Please select at least one image.", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ Convert list of image URIs to a single string
        String imageUrisString = String.join(",", selectedImageUris);

        // ✅ Save to database
        boolean success = databaseHelper.addCar(carName, price, imageUrisString);
        if (success) {
            Toast.makeText(this, "Car added successfully!", Toast.LENGTH_SHORT).show();
            finish(); // Close activity after saving
        } else {
            Toast.makeText(this, "Error adding car!", Toast.LENGTH_SHORT).show();
        }
    }
}
