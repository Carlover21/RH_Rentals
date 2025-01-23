package com.rh.rentals;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class EditCarActivity extends AppCompatActivity {
    private EditText editTextCarName, editTextCarPrice, editTextCarDescription;
    private ViewPager2 viewPagerCarImages;
    private ImagePagerAdapter imagePagerAdapter;
    private List<String> imageUris = new ArrayList<>();
    private int carId;
    private DatabaseHelper databaseHelper;

    private final ActivityResultLauncher<Intent> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        imageUris.add(selectedImageUri.toString());
                        imagePagerAdapter.notifyDataSetChanged();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);

        editTextCarName = findViewById(R.id.editTextCarName);
        editTextCarPrice = findViewById(R.id.editTextCarPrice);
        editTextCarDescription = findViewById(R.id.editTextCarDescription);
        viewPagerCarImages = findViewById(R.id.viewPagerCarImages);
        Button btnSelectImages = findViewById(R.id.btnSelectImages);
        Button btnSaveChanges = findViewById(R.id.btnSaveChanges);
        databaseHelper = new DatabaseHelper(this);

        carId = getIntent().getIntExtra("carId", -1);
        editTextCarName.setText(getIntent().getStringExtra("carName"));
        editTextCarPrice.setText(String.valueOf(getIntent().getDoubleExtra("carPrice", 0.0)));
        editTextCarDescription.setText(getIntent().getStringExtra("carDescription"));

        String imageUrisString = getIntent().getStringExtra("carImages");
        if (imageUrisString != null && !imageUrisString.isEmpty()) {
            String[] imageUriArray = imageUrisString.split(",");
            for (String uri : imageUriArray) {
                imageUris.add(uri);
            }
        }

        imagePagerAdapter = new ImagePagerAdapter(this, imageUris);
        viewPagerCarImages.setAdapter(imagePagerAdapter);

        // Image Picker
        btnSelectImages.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        // Save Changes
        btnSaveChanges.setOnClickListener(v -> {
            String name = editTextCarName.getText().toString();
            double price = Double.parseDouble(editTextCarPrice.getText().toString());
            String description = editTextCarDescription.getText().toString();
            String newImageUris = String.join(",", imageUris);

            if (carId != -1) {
                databaseHelper.updateCar(carId, name, price, description, newImageUris);
            }

            finish();
        });
    }
}
