package com.rh.rentals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        // Create a list of car prices
        ArrayList<String> carPrices = new ArrayList<>();
        carPrices.add("$120/day");
        carPrices.add("$150/day");
        carPrices.add("$180/day");
        carPrices.add("$200/day");

        // Create a list of car descriptions
        ArrayList<String> carDescriptions = new ArrayList<>();
        carDescriptions.add("Tesla Model S is a luxury electric car with advanced technology and superior performance.");
        carDescriptions.add("BMW X5 is a high-end SUV known for its comfort, power, and off-road capabilities.");
        carDescriptions.add("Audi A8 offers a premium driving experience with top-notch interiors and smooth handling.");
        carDescriptions.add("Mercedes-Benz C-Class combines elegance and performance in a compact luxury sedan.");

        // Set up ListView and Adapter
        ListView listView = findViewById(R.id.listViewCars);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, carList);
        listView.setAdapter(adapter);

        // Handle item clicks to navigate to CarDetailActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CarListActivity.this, CarDetailActivity.class);
                intent.putExtra("carName", carList.get(position));
                intent.putExtra("carPrice", carPrices.get(position));
                intent.putExtra("carDescription", carDescriptions.get(position));

                // Pass image resource IDs for the selected car
                int[] imageIds;
                switch (position) {
                    case 0:
                        imageIds = new int[]{R.drawable.tesla_1, R.drawable.tesla_2, R.drawable.tesla_3,};
                        break;
                    case 1:
                        imageIds = new int[]{R.drawable.bmw_1, R.drawable.bmw_2, R.drawable.bmw_3};
                        break;
                    case 2:
                        imageIds = new int[]{R.drawable.audi_1, R.drawable.audi_2, R.drawable.audi_3};
                        break;
                    default:
                        imageIds = new int[]{R.drawable.mercedes_1, R.drawable.mercedes_2, R.drawable.mercedes_3};
                        break;
                }
                intent.putExtra("imageIds", imageIds);
                startActivity(intent);
            }
        });
    }
}

