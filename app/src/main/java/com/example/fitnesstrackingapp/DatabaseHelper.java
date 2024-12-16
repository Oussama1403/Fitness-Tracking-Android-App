package com.example.fitnesstrackingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_DATA = "calories";
    public static final String DATA_WEIGHT = "weight";
    public static final String DATA_STEPS = "steps";
    public static final String DATA_CALORIES = "calories_burned";
    public static final String DATA_DATE = "date";
    private static final String DATABASE_NAME = "fitnessTracker.db";
    private static final int DATABASE_VERSION = 4;

    public static final String TABLE_USERS = "users";
    public static final String TABLE_WEIGHT = "weight";
    public static final String TABLE_STEPS = "steps";

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String WEIGHT_ID = "id";
    public static final String WEIGHT_VALUE = "weight";
    public static final String WEIGHT_DATE = "date";

    public static final String STEPS_ID = "id";
    public static final String STEPS_COUNT = "steps";
    public static final String STEPS_DATE = "date";

    public static final String CALORIES_ID = "id";
    public static final String CALORIES_COUNT = "calories";

    public static final String DATA_EXERCISE_TYPE = "exercise_type";
    public static final String DATA_EXERCISE_DURATION = "exercise_duration";
    public static final String DATA_DISTANCE_COVERED = "distance_covered";
    public static final String DATA_CONSUMED = "calories_consumed";
    public static final String DATA_EXERCISE_NOTES = "exercise_notes";
    public static final String DATA_GOAL = "goal";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                USERNAME + " TEXT PRIMARY KEY, " +
                PASSWORD + " TEXT)";
        db.execSQL(createUsersTable);

        String createWeightTable = "CREATE TABLE " + TABLE_WEIGHT + " (" +
                WEIGHT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USERNAME + " TEXT, " +
                WEIGHT_VALUE + " REAL, " +
                WEIGHT_DATE + " TEXT)";
        db.execSQL(createWeightTable);

        String createStepsTable = "CREATE TABLE " + TABLE_STEPS + " (" +
                STEPS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USERNAME + " TEXT, " +
                STEPS_COUNT + " INTEGER, " +
                STEPS_DATE + " TEXT)";
        db.execSQL(createStepsTable);

        String createCaloriesTable = "CREATE TABLE " + TABLE_DATA + " (" +
                CALORIES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USERNAME + " TEXT, " +
                CALORIES_COUNT + " INTEGER, " +
                DATA_CALORIES + " TEXT, " +
                DATA_DATE + " TEXT, " +
                DATA_WEIGHT + " REAL, " +
                DATA_STEPS + " INTEGER DEFAULT 0, " +
                DATA_EXERCISE_TYPE + " TEXT, " +
                DATA_EXERCISE_DURATION + " INTEGER, " +
                DATA_DISTANCE_COVERED + " REAL, " +
                DATA_CONSUMED + " REAL, " +
                DATA_EXERCISE_NOTES + " TEXT, " +
                DATA_GOAL + " TEXT" +
                ")";
        db.execSQL(createCaloriesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
