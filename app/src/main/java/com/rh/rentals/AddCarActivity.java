package com.rh.rentals;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddCarActivity extends AppCompatActivity {
    private EditText etCarName, etCarPrice;
    private DatabaseHelper databaseHelper; // Database Helper to manage SQLite operations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        // Link UI elements
        etCarName = findViewById(R.id.etCarName);
        etCarPrice = findViewById(R.id.etCarPrice);
        Button btnSaveCar = findViewById(R.id.btnSaveCar);

        // Initialize Database Helper
        databaseHelper = new DatabaseHelper(this);

        // Set button click listener to save car to the database
        btnSaveCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etCarName.getText().toString();
                String priceText = etCarPrice.getText().toString();

                // Validate input fields
                if (!name.isEmpty() && !priceText.isEmpty()) {
                    double price = Double.parseDouble(priceText);

                    // Insert car into the database
                    if (databaseHelper.addCar(name, price)) {
                        Toast.makeText(AddCarActivity.this, "Car Added Successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close this activity
                    } else {
                        Toast.makeText(AddCarActivity.this, "Failed to Add Car", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddCarActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
