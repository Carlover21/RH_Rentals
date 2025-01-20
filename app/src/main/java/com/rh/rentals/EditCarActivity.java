package com.rh.rentals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditCarActivity extends AppCompatActivity {
    private EditText etCarName, etCarPrice;
    private Button btnSaveChanges;
    private DatabaseHelper databaseHelper;
    private int carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);

        etCarName = findViewById(R.id.etCarName);
        etCarPrice = findViewById(R.id.etCarPrice);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        databaseHelper = new DatabaseHelper(this);

        // ✅ Get Data from Intent
        carId = getIntent().getIntExtra("carId", -1);
        String carName = getIntent().getStringExtra("carName");
        double carPrice = getIntent().getDoubleExtra("carPrice", -1);

        // ✅ Fill UI with Existing Data
        etCarName.setText(carName);
        etCarPrice.setText(carPrice != -1 ? String.valueOf(carPrice) : "");

        // ✅ Handle Save Button
        btnSaveChanges.setOnClickListener(v -> {
            String newName = etCarName.getText().toString().trim();
            String newPriceStr = etCarPrice.getText().toString().trim();

            if (newName.isEmpty() || newPriceStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double newPrice = Double.parseDouble(newPriceStr);

            // ✅ Update Car in Database
            boolean success = databaseHelper.updateCar(carId, newName, newPrice, "");
            if (success) {
                Toast.makeText(this, "Car updated successfully!", Toast.LENGTH_SHORT).show();
                finish(); // Close Activity
            } else {
                Toast.makeText(this, "Error updating car!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
