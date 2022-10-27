package com.example.cw1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cw1.MyDatabaseHelper;
import com.example.cw1.R;
import com.example.cw1.SearchAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button searchButton;
    MyDatabaseHelper db = new MyDatabaseHelper(SearchActivity.this);
    SearchAdapter searchTripAdapter;
    RecyclerView searchRecycleView;

    EditText search_input;
    ArrayList<String> trip_id, trip_name, trip_destination, trip_date, trip_risk, trip_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchRecycleView = findViewById(R.id.searchRecyclerView);

        searchButton = findViewById(R.id.search_trip_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db = new MyDatabaseHelper(SearchActivity.this);
                trip_id = new ArrayList<>();
                trip_name = new ArrayList<>();
                trip_destination = new ArrayList<>();
                trip_date = new ArrayList<>();
                trip_risk = new ArrayList<>();
                trip_desc = new ArrayList<>();

                search_input = findViewById(R.id.search_input);
                StoreSearchTrip(search_input.getText().toString());

            }
        });
    }

    void StoreSearchTrip(String tripName) {


        Cursor cursor = db.searchTrip(tripName);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {

                trip_id.add(cursor.getString(0));
                trip_name.add(cursor.getString(1));
                trip_destination.add(cursor.getString(2));
                trip_date.add(cursor.getString(3));
                trip_risk.add(cursor.getString(4));
                trip_desc.add(cursor.getString(5));


            }


            searchTripAdapter = new SearchAdapter(
                    SearchActivity.this,
                    this,
                    trip_id,
                    trip_name,
                    trip_destination,
                    trip_date,
                    trip_risk,
                    trip_desc);

            searchRecycleView.setAdapter(searchTripAdapter);
            searchRecycleView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
            recreate();
        }

    }

}