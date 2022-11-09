package com.example.cw1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    MyDatabaseHelper db;
    ArrayList<String> trip_id, trip_name, trip_destination, trip_date, trip_risk, trip_desc;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivityForResult(intent, 1);
        });

        db = new MyDatabaseHelper(MainActivity.this);
        trip_id = new ArrayList<>();
        trip_name = new ArrayList<>();
        trip_destination = new ArrayList<>();
        trip_date = new ArrayList<>();
        trip_risk = new ArrayList<>();
        trip_desc = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this,
                                          this,
                                                 trip_id,
                                                 trip_name,
                                                 trip_destination,
                                                 trip_date,
                                                 trip_risk,
                                                 trip_desc);

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.delete_all_button){
            deleteAll();
        }
        if(item.getItemId() == R.id.search_button){
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = db.readAllData();
        if (cursor.getCount() != 0){
            while(cursor.moveToNext()){
                trip_id.add(cursor.getString(0));
                trip_name.add(cursor.getString(1));
                trip_destination.add(cursor.getString(2));
                trip_date.add(cursor.getString(3));
                trip_risk.add(cursor.getString(4));
                trip_desc.add(cursor.getString(5));
            }
        }
        else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAll(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all?");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            MyDatabaseHelper db = new MyDatabaseHelper(MainActivity.this);
            db.deleteAllData();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        builder.setNegativeButton("No", (dialogInterface, i) -> {
        });

        builder.create().show();
    }
}