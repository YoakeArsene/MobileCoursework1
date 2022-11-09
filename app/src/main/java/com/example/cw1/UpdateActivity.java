package com.example.cw1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;

public class UpdateActivity extends AppCompatActivity {

    EditText name_input, destination_input, selectDate , description_input;
    Button update_button, see_expense_button;
    RadioGroup radioGroup;
    RadioButton rb;

    String id, name, destination, date, risk, desc;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name_input = findViewById(R.id.name_input_update);
        destination_input = findViewById(R.id.destination_input_update);
        selectDate = findViewById(R.id.SelectedDate_update);
        radioGroup = findViewById(R.id.radioGroup_update);
        description_input = findViewById(R.id.description_input_update);
        update_button = findViewById(R.id.update_button);
        see_expense_button = findViewById(R.id.see_expense_button);
        builder =  new AlertDialog.Builder(this);
        getAndSetIntentData();

        update_button.setOnClickListener(view -> {
            MyDatabaseHelper db = new MyDatabaseHelper(UpdateActivity.this);
            name = name_input.getText().toString().trim();
            destination = destination_input.getText().toString().trim();
            date = selectDate.getText().toString().trim();
            int btnId = radioGroup.getCheckedRadioButtonId();
            rb = findViewById(btnId);
            risk = rb.getText().toString().trim();
            desc = description_input.getText().toString().trim();

            db.updateTrip(id, name, destination, date, risk, desc);
        });

        see_expense_button.setOnClickListener(view -> {
            Intent intent = new Intent(UpdateActivity.this, ExpensesActivity.class);
            intent.putExtra("trip_id", String.valueOf(id));
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_button) {
            deleteTrip();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new UpdateDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date1");
    }
    public void editDate(LocalDate date){
        TextView dateText = (TextView) findViewById(R.id.SelectedDate_update);
        dateText.setText(date.toString());
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id")
                && getIntent().hasExtra("name")
                && getIntent().hasExtra("destination")
                && getIntent().hasExtra("date")
                && getIntent().hasExtra("risk")
                && getIntent().hasExtra("desc")) {

            // Get the data from intent passing from the CustomAdapter
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            destination = getIntent().getStringExtra("destination");
            date = getIntent().getStringExtra("date");
            risk = getIntent().getStringExtra("risk");
            desc = getIntent().getStringExtra("desc");

            RadioButton rYes = findViewById(R.id.radioButtonYes);
            RadioButton rNo = findViewById(R.id.radioButtonNo);
            if (risk.equals("Yes")){
                rYes.setChecked(true);
                rNo.setChecked(false);
            } else {
                rYes.setChecked(false);
                rNo.setChecked(true);
            }
            name_input.setText(name);
            destination_input.setText(destination);
            selectDate.setText(date);
            description_input.setText(desc);
        }
        else {
            Toast.makeText(this,"No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteTrip(){
        builder.setTitle("Delete " + name + "?");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            MyDatabaseHelper databaseHelper = new MyDatabaseHelper(UpdateActivity.this);
            databaseHelper.deleteOneRow(id);
            finish();
        });

        builder.setNegativeButton("No", (dialogInterface, i) -> {
        });

        builder.create().show();
    }

}
