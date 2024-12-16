package com.example.fitnesstrackingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataEntryActivity extends AppCompatActivity {

    EditText weightEditText, stepsEditText, caloriesEditText, exerciseDurationEditText, distanceCoveredEditText, caloriesConsumedEditText, exerciseNotesEditText, goalEditText;
    Spinner exerciseTypeSpinner;
    Button saveButton;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        weightEditText = findViewById(R.id.weightEditText);
        stepsEditText = findViewById(R.id.stepsEditText);
        caloriesEditText = findViewById(R.id.caloriesEditText);
        exerciseTypeSpinner = findViewById(R.id.exerciseTypeSpinner);
        exerciseDurationEditText = findViewById(R.id.exerciseDurationEditText);
        distanceCoveredEditText = findViewById(R.id.distanceCoveredEditText);
        caloriesConsumedEditText = findViewById(R.id.caloriesConsumedEditText);
        exerciseNotesEditText = findViewById(R.id.exerciseNotesEditText);
        goalEditText = findViewById(R.id.goalEditText);
        saveButton = findViewById(R.id.saveButton);
        dbHelper = new DatabaseHelper(this);

        String[] exerciseTypes = {"Walking", "Running", "Cycling", "Swimming", "Yoga"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, exerciseTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseTypeSpinner.setAdapter(adapter);

        saveButton.setOnClickListener(v -> {
            String weight = weightEditText.getText().toString();
            String steps = stepsEditText.getText().toString();
            String calories = caloriesEditText.getText().toString();
            String exerciseType = exerciseTypeSpinner.getSelectedItem().toString();
            String exerciseDuration = exerciseDurationEditText.getText().toString();
            String distanceCovered = distanceCoveredEditText.getText().toString();
            String caloriesConsumed = caloriesConsumedEditText.getText().toString();
            String exerciseNotes = exerciseNotesEditText.getText().toString();
            String goal = goalEditText.getText().toString();

            if (!weight.isEmpty() && !steps.isEmpty() && !calories.isEmpty() && !exerciseType.isEmpty() && !exerciseDuration.isEmpty()) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                SharedPreferences sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE);
                String currentUser = sharedPref.getString("current_user", null);

                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.DATA_WEIGHT, weight);
                values.put(DatabaseHelper.DATA_STEPS, steps);
                values.put(DatabaseHelper.DATA_CALORIES, calories);
                values.put(DatabaseHelper.DATA_DATE, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                values.put(DatabaseHelper.USERNAME, currentUser);
                values.put(DatabaseHelper.DATA_EXERCISE_TYPE, exerciseType);
                values.put(DatabaseHelper.DATA_EXERCISE_DURATION, exerciseDuration);
                values.put(DatabaseHelper.DATA_DISTANCE_COVERED, distanceCovered);
                values.put(DatabaseHelper.DATA_CONSUMED, caloriesConsumed);
                values.put(DatabaseHelper.DATA_EXERCISE_NOTES, exerciseNotes);
                values.put(DatabaseHelper.DATA_GOAL, goal);

                long newRowId = db.insert(DatabaseHelper.TABLE_DATA, null, values);

                if (newRowId != -1) {
                    Toast.makeText(DataEntryActivity.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DataEntryActivity.this, "Error saving data", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(DataEntryActivity.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
