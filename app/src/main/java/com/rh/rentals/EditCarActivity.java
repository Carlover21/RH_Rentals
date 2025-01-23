package com.rh.rentals;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;

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

        btnSelectImages.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            imagePickerLauncher.launch(intent);
        });

        btnSaveChanges.setOnClickListener(v -> {
            String name = editTextCarName.getText().toString();
            String priceStr = editTextCarPrice.getText().toString();
            String description = editTextCarDescription.getText().toString();

            if (name.isEmpty() || priceStr.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceStr);
            String newImageUris = String.join(",", imageUris);

            if (carId != -1) {
                boolean isUpdated = databaseHelper.updateCar(carId, name, price, description, newImageUris);
                if (isUpdated) {
                    Toast.makeText(this, "Car updated successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Update failed!", Toast.LENGTH_SHORT).show();
                }
            }

            finish();
        });
    }

    private void updateImagePagerAdapter() {
        if (imagePagerAdapter == null) {
            imagePagerAdapter = new ImagePagerAdapter(this, imageUris);
            viewPagerCarImages.setAdapter(imagePagerAdapter);
        } else {
            imagePagerAdapter.notifyDataSetChanged();
        }
    }
}
