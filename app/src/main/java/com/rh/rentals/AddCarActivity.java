package com.rh.rentals;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class AddCarActivity extends AppCompatActivity {

    private EditText editTextCarName, editTextCarPrice, editTextCarDescription;
    private Button btnSaveCar, btnSelectImages;
    private DatabaseHelper databaseHelper;
    private List<String> imageUris = new ArrayList<>(); // Initialize to prevent null errors

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    if (result.getData().getClipData() != null) {
                        // Multiple images selected
                        int count = result.getData().getClipData().getItemCount();
                        for (int i = 0; i < count; i++) {
                            Uri imageUri = result.getData().getClipData().getItemAt(i).getUri();
                            imageUris.add(imageUri.toString());
                        }
                    } else if (result.getData().getData() != null) {
                        // Single image selected
                        Uri imageUri = result.getData().getData();
                        imageUris.add(imageUri.toString());
                    }
                    Log.d("ImagePicker", "Selected Images: " + imageUris.toString()); // Debugging
                } else {
                    Log.d("ImagePicker", "No Image Selected");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        // Initialize the views
        editTextCarName = findViewById(R.id.etCarName);
        editTextCarPrice = findViewById(R.id.etCarPrice);
        editTextCarDescription = findViewById(R.id.editTextCarDescription);
        btnSaveCar = findViewById(R.id.btnSaveCar);
        btnSelectImages = findViewById(R.id.btnSelectImages);

        databaseHelper = new DatabaseHelper(this);

        // Set Click Listener for Image Selection
        btnSelectImages.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            imagePickerLauncher.launch(intent);
        });

        // Save Car to Database
        btnSaveCar.setOnClickListener(v -> {
            String carName = editTextCarName.getText().toString();
            String carPriceStr = editTextCarPrice.getText().toString();
            String carDescription = editTextCarDescription.getText().toString();

            if (carName.isEmpty() || carPriceStr.isEmpty() || carDescription.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double carPrice = Double.parseDouble(carPriceStr);
            String carImages = String.join(",", imageUris); // Convert list to string

            // Add the car to the database
            boolean result = databaseHelper.addCar(carName, carPrice, carDescription, carImages);

            if (result) {
                Toast.makeText(this, "Car added successfully!", Toast.LENGTH_SHORT).show();
                finish(); // Close activity
            } else {
                Toast.makeText(this, "Error adding car", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
