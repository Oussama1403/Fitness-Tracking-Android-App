package com.example.fitnesstrackingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button addDataButton;
    TextView Msg;
    DatabaseHelper dbHelper;

    ListView recentDataListView;  // Declare ListView here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Msg = findViewById(R.id.msg);  // Add this line

        addDataButton = findViewById(R.id.addDataButton);
        dbHelper = new DatabaseHelper(this);

        // In your MainActivity, inside onCreate() method before loading the data
        SharedPreferences sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String currentUser = sharedPref.getString("current_user", null); // Default to null if no user is logged in

        //recentDataListView = findViewById(R.id.recentDataListView);  // Use the class-level variable here

        // Retrieve the latest data from the database and display it on the dashboard
        loadData();

        // Button to navigate to the data entry page
        addDataButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DataEntryActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // Reload the data when the user comes back to the MainActivity
    }

    private void loadData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Retrieve the current logged-in user from SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String currentUser = sharedPref.getString("current_user", null); // Default to null if no user is logged in

        if (currentUser != null) {
            // Query to get data for the current user, filtered by the username
            Cursor cursor = db.query(DatabaseHelper.TABLE_DATA,
                    new String[] { DatabaseHelper.DATA_WEIGHT,
                            DatabaseHelper.DATA_STEPS,
                            DatabaseHelper.DATA_CALORIES,
                            DatabaseHelper.DATA_DATE,
                            DatabaseHelper.DATA_EXERCISE_TYPE,
                            DatabaseHelper.DATA_EXERCISE_DURATION,
                            DatabaseHelper.DATA_DISTANCE_COVERED,
                            DatabaseHelper.DATA_CONSUMED,
                            DatabaseHelper.DATA_GOAL,
                            DatabaseHelper.DATA_EXERCISE_NOTES
                    },  // Add new columns
                    DatabaseHelper.USERNAME + " = ?", // Filter by current user
                    new String[] { currentUser },     // Pass current user as the parameter
                    null, null, DatabaseHelper.DATA_DATE + " DESC"); // Order by date descending

            List<String> recentData = new ArrayList<>();  // Initialize here to fetch data

            if (cursor != null && cursor.moveToFirst()) {
                Msg.setVisibility(View.GONE);

                do {
                    @SuppressLint("Range") String weight = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATA_WEIGHT));
                    @SuppressLint("Range") String steps = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATA_STEPS));
                    @SuppressLint("Range") String calories = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATA_CALORIES));
                    @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATA_DATE)); // Get date
                    @SuppressLint("Range") String exerciseType = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATA_EXERCISE_TYPE)); // Get exercise type
                    @SuppressLint("Range") String exerciseDuration = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATA_EXERCISE_DURATION)); // Get exercise duration
                    @SuppressLint("Range") String distance = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATA_DISTANCE_COVERED)); // Get distance covered
                    @SuppressLint("Range") String caloriesConsumed = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATA_CONSUMED)); // Get calories consumed
                    @SuppressLint("Range") String exerciseNotes = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATA_EXERCISE_NOTES)); // Get exercise notes
                    @SuppressLint("Range") String goal = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATA_GOAL)); // Get exercise goal


                    // Create entry with exercise type and duration
                    String entry = "Date: " + date + "\nWeight: " + weight + " kg\nSteps: " + steps + "\nCalories: " + calories +
                            "\nExercise Type: " + exerciseType + "\nDuration: " + exerciseDuration + " minutes"+ "\nDistance: " + distance + " km\nCalories Consumed: " + caloriesConsumed + " kcal" +
                            "\nNotes: " + exerciseNotes + "\nGoal "+ goal;
                    recentData.add(entry);
                } while (cursor.moveToNext());  // Continue until the end of the cursor

                cursor.close();
            } else {
                Msg.setText("You have not entered any data yet");
                Msg.setVisibility(View.VISIBLE);
            }

            // Set up RecyclerView
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set layout manager
            CustomAdapter adapter = new CustomAdapter(this, recentData); // Set adapter with data
            recyclerView.setAdapter(adapter); // Attach the adapter to RecyclerView
        } else {
            Msg.setText("User not logged in");
            Msg.setVisibility(View.VISIBLE);
        }
    }

}
