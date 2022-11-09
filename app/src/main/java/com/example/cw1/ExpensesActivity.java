package com.example.cw1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ExpensesActivity extends AppCompatActivity {

    RecyclerView expenseRecyclerView;
    FloatingActionButton add_expense_button;
    String tripId;
    MyDatabaseHelper db;
    ArrayList<String> expense_id, trip_id, expense_type, expense_amount, expense_time, expense_comment;
    ExpenseCustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        getId();
        expenseRecyclerView = findViewById(R.id.expenseRecyclerView);
        add_expense_button = findViewById(R.id.add_expense_button);

        add_expense_button.setOnClickListener(view -> {
            Intent intent = new Intent(ExpensesActivity.this, AddExpenseActivity.class);
            intent.putExtra("trip_id", tripId);
            startActivityForResult(intent, 1);
        });

        db = new MyDatabaseHelper(ExpensesActivity.this);
        expense_id = new ArrayList<>();
        trip_id = new ArrayList<>();
        expense_type = new ArrayList<>();
        expense_amount = new ArrayList<>();
        expense_time = new ArrayList<>();
        expense_comment = new ArrayList<>();

        storeDataInArrays(tripId);

        customAdapter = new ExpenseCustomAdapter(ExpensesActivity.this,
                this,
                expense_id,
                expense_type,
                expense_amount,
                expense_time,
                String.valueOf(tripId));

        expenseRecyclerView.setAdapter(customAdapter);
        expenseRecyclerView.setLayoutManager(new LinearLayoutManager(ExpensesActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }

    void getId(){
        if (getIntent().hasExtra("trip_id")){
            tripId = getIntent().getStringExtra("trip_id");
        }
    }

    void storeDataInArrays(String expense_trip_id){
        Cursor cursor = db.readAllExpenses(expense_trip_id);
        if (cursor.getCount() != 0){
            while(cursor.moveToNext()){
                expense_id.add(cursor.getString(0));
                trip_id.add(cursor.getString(1));
                expense_type.add(cursor.getString(2));
                expense_amount.add(cursor.getString(3));
                expense_comment.add(cursor.getString(4));
                expense_time.add(cursor.getString(5));
            }
        }
        else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}