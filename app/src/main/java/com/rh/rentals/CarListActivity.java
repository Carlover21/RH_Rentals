package com.rh.rentals;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CarListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        // Create a list of cars (hardcoded for now)
        ArrayList<String> carList = new ArrayList<>();
        carList.add("Tesla Model S");
        carList.add("BMW X5");
        carList.add("Audi A8");
        carList.add("Mercedes-Benz C-Class");

        // Set up ListView and Adapter
        ListView listView = findViewById(R.id.listViewCars);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, carList);
        listView.setAdapter(adapter);
    }
}
