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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private final Context context;
    Activity activity;
    ArrayList<String> tripId, tripName, tripDestination, tripDate, tripRisk, tripDesc;
    int position;


    public SearchAdapter(Context context,
                         SearchActivity activity,
                         ArrayList<String> tripId,
                         ArrayList<String> tripName,
                         ArrayList<String> tripDestination,
                         ArrayList<String> tripDate,
                         ArrayList<String> tripRisk,
                         ArrayList<String> tripDesc) {
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
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;
        holder.trip_id_text.setText(String.valueOf(tripId.get(position)));
        holder.trip_name_text.setText(String.valueOf(tripName.get(position)));
        holder.trip_destination_text.setText(String.valueOf(tripDestination.get(position)));
        holder.trip_date_text.setText(String.valueOf(tripDate.get(position)));

        String a;
        if (String.valueOf(tripRisk.get(position)).equals("1")) {
            a = "yes";
        } else {
            a = "no";
        }
        a = "Risk Assessment: " + a;
        holder.trip_risk_text.setText(a);
    }

    @Override
    public int getItemCount() {
        return tripId.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView trip_id_text, trip_name_text, trip_destination_text, trip_date_text, trip_risk_text;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            trip_id_text = itemView.findViewById(R.id.trip_id_text);
            trip_name_text = itemView.findViewById(R.id.trip_name_text);
            trip_destination_text = itemView.findViewById(R.id.trip_destination_text);
            trip_date_text = itemView.findViewById(R.id.trip_date_text);
            trip_risk_text = itemView.findViewById(R.id.trip_risk_text);
        }
    }
}
