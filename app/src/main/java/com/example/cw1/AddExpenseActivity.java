package com.example.cw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddExpenseActivity extends AppCompatActivity{

    String tripId;
    EditText amount_input, time_input, comment_input, trip_id_input;
    Spinner type_input;
    Button add_expense_to_db_button;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        list = new ArrayList<>();
        list.add("Food");
        list.add("Travel");
        list.add("Other");
        type_input = (Spinner) findViewById(R.id.type_input);
        amount_input = findViewById(R.id.amount_input);
        time_input = findViewById(R.id.time_input);
        comment_input = findViewById(R.id.comment_input);
        add_expense_to_db_button = findViewById(R.id.add_expense_to_db_button);

        add_expense_to_db_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (type_input.getSelectedItem().toString().isEmpty() ||
                        amount_input.getText().toString().isEmpty() ||
                        time_input.getText().toString().isEmpty() ||
                        comment_input.getText().toString().isEmpty()){
                Toast.makeText(AddExpenseActivity.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            }
                else{
                    Toast.makeText(AddExpenseActivity.this, type_input.getSelectedItem().toString() +
                                    amount_input.getText().toString() +
                                    time_input.getText().toString() +
                                    comment_input.getText().toString(),
                            Toast.LENGTH_SHORT).show();

                    MyDatabaseHelper DB = new MyDatabaseHelper(AddExpenseActivity.this);
                    DB.addExpense(tripId = getIntent().getStringExtra("trip_id"),
                            type_input.getSelectedItem().toString().trim(),
                            amount_input.getText().toString().trim(),
                            time_input.getText().toString().trim(),
                            comment_input.getText().toString().trim());
             }
            }
        });

        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
        type_input.setAdapter(spinnerAdapter);
    }

    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new ExpenseDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date1");
    }
    public void updateExpenseTime(LocalDate date){
        TextView dateText = (TextView) findViewById(R.id.time_input);
        dateText.setText(date.toString());
    }
}