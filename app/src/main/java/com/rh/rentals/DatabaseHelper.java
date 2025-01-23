package com.rh.rentals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CarRentalDB";
    private static final int DATABASE_VERSION = 5;
    private static final String TABLE_CARS = "cars";

    // Column Names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DESCRIPTION = "description"; // Added description column
    private static final String COLUMN_IMAGE_URI = "image_uris"; // Store multiple images as comma-separated URIs

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_CARS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PRICE + " REAL, " +
                COLUMN_DESCRIPTION + " TEXT, " + // Added description column
                COLUMN_IMAGE_URI + " TEXT)"; // Added column for image URIs
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 5) {
            db.execSQL("ALTER TABLE " + TABLE_CARS + " ADD COLUMN " + COLUMN_DESCRIPTION + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_CARS + " ADD COLUMN " + COLUMN_IMAGE_URI + " TEXT");
        }
    }

    // Method to add a car to the database
    public boolean addCar(String name, double price, String description, String imageUris) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_IMAGE_URI, imageUris);

        long result = db.insert(TABLE_CARS, null, values);
        db.close();
        return result != -1;
    }

    // Method to update a car in the database
    public boolean updateCar(int id, String name, double price, String description, String imageUris) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_IMAGE_URI, imageUris);

        int rowsUpdated = db.update(TABLE_CARS, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsUpdated > 0;
    }

    // Method to get all cars from the database
    public List<Car> getAllCars() {
        List<Car> carList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARS, null);

        if (cursor.moveToFirst()) {
            do {
                carList.add(new Car(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3), cursor.getString(4)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return carList;
    }

    // Method to delete a car from the database
    public void deleteCar(int carId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CARS, COLUMN_ID + "=?", new String[]{String.valueOf(carId)});
        db.close();
    }
}
