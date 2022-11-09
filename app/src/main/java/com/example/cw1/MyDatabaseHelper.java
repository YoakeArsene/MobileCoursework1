package com.example.cw1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "TripExpenseManager.db";
    private static final int DATABASE_VERSION =  1;

    private static final String TABLE_NAME = "trips";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESTINATION = "destination";
    private static final String COLUMN_DATE_OF_THE_TRIP = "date_of_the_trip";
    private static final String COLUMN_RISK_ASSESSMENT = "risk_assessment";
    private static final String COLUMN_DESCRIPTION = "description";

    private static final String EXPENSE_TABLE_NAME = "expenses";
    private static final String COLUMN_EXPENSE_ID = "expense_id";
    private static final String COLUMN_TRIP_ID = "trip_id";
    private static final String COLUMN_EXPENSE_TYPE = "expense_type";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_EXPENSE_TIME = "expense_time";
    private static final String COLUMN_ADDITIONAL_COMMENT = "additional_comment";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                               COLUMN_NAME + " TEXT, " +
                               COLUMN_DESTINATION + " TEXT, " +
                               COLUMN_DATE_OF_THE_TRIP + " TEXT, " +
                               COLUMN_RISK_ASSESSMENT + " TEXT, " +
                               COLUMN_DESCRIPTION + " TEXT);";

        String query1 = "CREATE TABLE " + EXPENSE_TABLE_NAME +
                        " (" + COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                               COLUMN_TRIP_ID + " TEXT REFERENCES trips(_id), " +
                               COLUMN_EXPENSE_TYPE + " TEXT, " +
                               COLUMN_AMOUNT + " TEXT, " +
                               COLUMN_ADDITIONAL_COMMENT + " TEXT, " +
                               COLUMN_EXPENSE_TIME + " TEXT);";
        db.execSQL(query);
        db.execSQL(query1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EXPENSE_TABLE_NAME);
        onCreate(db);
    }

    public void addTrip(String name, String destination, String date, String risk, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DESTINATION, destination);
        contentValues.put(COLUMN_DATE_OF_THE_TRIP, date);
        contentValues.put(COLUMN_RISK_ASSESSMENT, risk);
        contentValues.put(COLUMN_DESCRIPTION, description);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully added!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateTrip(String id, String name, String destination, String date, String risk, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DESTINATION, destination);
        contentValues.put(COLUMN_DATE_OF_THE_TRIP, date);
        contentValues.put(COLUMN_RISK_ASSESSMENT, risk);
        contentValues.put(COLUMN_DESCRIPTION, description);

        long result = db.update(
                TABLE_NAME,
                contentValues,
                "_id = ?",
                new String[]{id});

        if (result == -1) {
            Toast.makeText(context, "UPDATE: FAILED ...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "UPDATE: DONE ...", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRow(String rowId){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id = ?", new String[]{rowId});
        if (result == -1){
            Toast.makeText(context, "Delete failed.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public void addExpense(String trip_id, String expense_type, String amount, String expense_time, String additional_comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TRIP_ID, trip_id);
        contentValues.put(COLUMN_EXPENSE_TYPE, expense_type);
        contentValues.put(COLUMN_AMOUNT, amount);
        contentValues.put(COLUMN_EXPENSE_TIME, expense_time);
        contentValues.put(COLUMN_ADDITIONAL_COMMENT, additional_comment);

        long result = db.insert(EXPENSE_TABLE_NAME, null, contentValues);

        if (result == -1) {
            Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully added!", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllExpenses(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + EXPENSE_TABLE_NAME + " WHERE " + COLUMN_TRIP_ID + " = '" + id + "'";
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor searchTrip(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " LIKE '%" + name + "%'";
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }



}
