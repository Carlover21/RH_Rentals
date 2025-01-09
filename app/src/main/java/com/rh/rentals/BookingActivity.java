package com.rh.rentals;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Get references to input fields and button
        EditText edtName = findViewById(R.id.edtName);
        EditText edtContact = findViewById(R.id.edtContact);
        EditText edtCar = findViewById(R.id.edtCar);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        // Set click listener for the submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String contact = edtContact.getText().toString();
                String car = edtCar.getText().toString();

                if (name.isEmpty() || contact.isEmpty() || car.isEmpty()) {
                    Toast.makeText(BookingActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BookingActivity.this, "Booking submitted!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
