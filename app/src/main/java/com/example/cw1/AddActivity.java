package com.example.cw1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;


public class AddActivity extends AppCompatActivity {
    EditText name_input, destination_input, selectDate , description_input;
    Button add_button;
    RadioGroup radioGroup;
    RadioButton rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name_input = findViewById(R.id.name_input);
        destination_input = findViewById(R.id.destination_input);
        selectDate = findViewById(R.id.SelectedDate);
        radioGroup = findViewById(R.id.radioGroup);
        description_input = findViewById(R.id.description_input);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(view -> {

            int radioId = radioGroup.getCheckedRadioButtonId();
            rb = findViewById(radioId);
            if (name_input.getText().toString().trim().isEmpty() ||
                    destination_input.getText().toString().trim().isEmpty() ||
                    selectDate.getText().toString().trim().isEmpty() ||
                    rb.getText().toString().trim().isEmpty() ||
                    description_input.getText().toString().trim().isEmpty()){
                Toast.makeText(AddActivity.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(AddActivity.this, name_input.getText().toString() +
                                destination_input.getText().toString() +
                                selectDate.getText().toString() +
                                rb.getText().toString() +
                                description_input.getText().toString(),
                        Toast.LENGTH_SHORT).show();
                MyDatabaseHelper DB = new MyDatabaseHelper(AddActivity.this);
                DB.addTrip(name_input.getText().toString().trim(),
                        destination_input.getText().toString().trim(),
                        selectDate.getText().toString().trim(),
                        rb.getText().toString().trim(),
                        description_input.getText().toString().trim());
            }
        });
    }
    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date1");
    }
    public void updateDate(LocalDate date){
        TextView dateText = (TextView) findViewById(R.id.SelectedDate);
        dateText.setText(date.toString());
    }
}