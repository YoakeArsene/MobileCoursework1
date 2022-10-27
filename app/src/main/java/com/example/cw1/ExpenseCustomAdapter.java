package com.example.cw1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseCustomAdapter extends RecyclerView.Adapter<ExpenseCustomAdapter.MyViewHolder> {
    private Context context;
    Activity activity;
    private ArrayList expenseId, tripId, expenseType, expenseAmount, expenseTime, expenseComment;
    int position;
    String expenseTripId;

    public ExpenseCustomAdapter(Context context,
                         Activity activity,
                         ArrayList expenseId,
                         ArrayList tripId,
                         ArrayList expenseType,
                         ArrayList expenseAmount,
                         ArrayList expenseTime,
                         ArrayList expenseComment,
                         String expenseTripId){
        this.context = context;
        this.activity = activity;
        this.expenseId = expenseId;
        this.tripId = tripId;
        this.expenseType = expenseType;
        this.expenseAmount = expenseAmount;
        this.expenseTime = expenseTime;
        this.expenseComment = expenseComment;
        this.expenseTripId = expenseTripId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expense_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;
        holder.expense_type_text.setText(String.valueOf(expenseType.get(position)));
        holder.expense_time_text.setText(String.valueOf(expenseTime.get(position)));
        holder.expense_amount_text.setText(String.valueOf(expenseAmount.get(position)));
    }

    @Override
    public int getItemCount() {
        return expenseId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView expense_type_text, expense_time_text, expense_amount_text;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            expense_type_text = itemView.findViewById(R.id.trip_name_text);
            expense_time_text = itemView.findViewById(R.id.trip_destination_text);
            expense_amount_text = itemView.findViewById(R.id.trip_date_text);
        }
    }
}
