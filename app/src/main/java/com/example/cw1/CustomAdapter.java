package com.example.cw1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    Activity activity;
    private ArrayList tripId, tripName, tripDestination, tripDate, tripRisk, tripDesc;
    int position;

    public CustomAdapter(Context context,
                         Activity activity,
                         ArrayList tripId,
                         ArrayList tripName,
                         ArrayList tripDestination,
                         ArrayList tripDate,
                         ArrayList tripRisk,
                         ArrayList tripDesc){
        this.context = context;
        this.activity = activity;
        this.tripId = tripId;
        this.tripName = tripName;
        this.tripDestination = tripDestination;
        this.tripDate = tripDate;
        this.tripRisk = tripRisk;
        this.tripDesc = tripDesc;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;
        holder.trip_id_text.setText(String.valueOf(tripId.get(position)));
        holder.trip_name_text.setText(String.valueOf(tripName.get(position)));
        holder.trip_destination_text.setText(String.valueOf(tripDestination.get(position)));
        holder.trip_date_text.setText(String.valueOf(tripDate.get(position)));
        holder.trip_risk_text.setText(String.valueOf(tripRisk.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);

                intent.putExtra("id", String.valueOf(tripId.get(position)));
                intent.putExtra("name", String.valueOf(tripName.get(position)));
                intent.putExtra("destination", String.valueOf(tripDestination.get(position)));
                intent.putExtra("date", String.valueOf(tripDate.get(position)));
                intent.putExtra("risk", String.valueOf(tripRisk.get(position)));
                intent.putExtra("desc", String.valueOf(tripDesc.get(position)));
                  activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView trip_id_text, trip_name_text, trip_destination_text, trip_date_text, trip_risk_text;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            trip_id_text = itemView.findViewById(R.id.trip_id_text);
            trip_name_text = itemView.findViewById(R.id.trip_name_text);
            trip_destination_text = itemView.findViewById(R.id.trip_destination_text);
            trip_date_text = itemView.findViewById(R.id.trip_date_text);
            trip_risk_text = itemView.findViewById(R.id.trip_risk_text);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
