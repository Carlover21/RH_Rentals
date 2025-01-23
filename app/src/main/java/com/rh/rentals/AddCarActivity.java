package com.rh.rentals;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

    private EditText editTextCarName, editTextCarPrice, editTextCarDescription;
    private Button btnSaveCar, btnSelectImages;
    private ViewPager2 viewPagerCarImages;
    private ImagePagerAdapter imagePagerAdapter;
    private DatabaseHelper databaseHelper;
    private List<String> imageUris = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        // Initialize UI components
        editTextCarName = findViewById(R.id.etCarName);
        editTextCarPrice = findViewById(R.id.etCarPrice);
        editTextCarDescription = findViewById(R.id.editTextCarDescription);
        btnSaveCar = findViewById(R.id.btnSaveCar);
        btnSelectImages = findViewById(R.id.btnSelectImages);
        viewPagerCarImages = findViewById(R.id.viewPagerCarImages);

        databaseHelper = new DatabaseHelper(this);
        imagePagerAdapter = new ImagePagerAdapter(this, imageUris);
        viewPagerCarImages.setAdapter(imagePagerAdapter);

        // Image Picker
        btnSelectImages.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            imagePickerLauncher.launch(intent);
        });

        // Save Car Button
        btnSaveCar.setOnClickListener(v -> saveCar());
    }

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    if (result.getData().getClipData() != null) {
                        int count = result.getData().getClipData().getItemCount();
                        for (int i = 0; i < count; i++) {
                            Uri imageUri = result.getData().getClipData().getItemAt(i).getUri();
                            imageUris.add(imageUri.toString());
                        }
                    } else if (result.getData().getData() != null) {
                        Uri imageUri = result.getData().getData();
                        imageUris.add(imageUri.toString());
                    }
                    updateImagePagerAdapter();
                    Log.d("ImagePicker", "Selected Images: " + imageUris);
                } else {
                    Log.d("ImagePicker", "No Image Selected");
                }
            });

    private void updateImagePagerAdapter() {
        imagePagerAdapter.notifyDataSetChanged();
    }

    private void saveCar() {
        String carName = editTextCarName.getText().toString();
        String priceStr = editTextCarPrice.getText().toString();
        String description = editTextCarDescription.getText().toString();

        if (carName.isEmpty() || priceStr.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        String carImages = String.join(",", imageUris);

        boolean result = databaseHelper.addCar(carName, price, description, carImages);

        if (result) {
            Toast.makeText(this, "Car added successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error adding car", Toast.LENGTH_SHORT).show();
        }
    }
}
