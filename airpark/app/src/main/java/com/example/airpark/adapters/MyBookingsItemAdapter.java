package com.example.airpark.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.airpark.R;
import com.example.airpark.activities.Bookings.SelectedBookingActivity;
import com.example.airpark.models.BookingModel;
import com.example.airpark.models.BookingTicket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyBookingsItemAdapter extends RecyclerView.Adapter<MyBookingsItemAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<BookingModel> result;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy - hh:mm a");

    //Remove once database is added
    public MyBookingsItemAdapter(ArrayList<BookingModel> result){
        this.result = result;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_carpark_list_item,parent,false);
        return new MyBookingsItemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookingsItemAdapter.MyViewHolder holder, int position) {

        BookingModel model = result.get(position);

        holder.airportName.setText(model.getAirportName());
        holder.carparkName.setText(model.getCarparkName());

        String entry = sdf.format(new Date(model.getStartDateTime()));
        String[] entryDateAndTime = entry.split(" - ");

        String exit = sdf.format(new Date(model.getStartDateTime()));
        String[] exitDateAndTime = exit.split(" - ");

        holder.bookingDate.setText(entryDateAndTime[0] + "-" + exitDateAndTime[0]);

        holder.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectedBookingActivity.class);
                intent.putExtra("ticket", result.get(position));
                context.startActivity(intent);
            }
        });

        Glide.with(holder.myBookingsImage.getContext()).load(model.getCarparkImage()).into(holder.myBookingsImage);
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView airportName, bookingDate, carparkName;
        private ImageView nextBtn, myBookingsImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            // Bind Ui with id
            context = itemView.getContext();
            bookingDate = (TextView) itemView.findViewById(R.id.card_carparkType);
            carparkName = (TextView) itemView.findViewById(R.id.card_carparkName2);

            airportName = (TextView) itemView.findViewById(R.id.card_carparkName);
            nextBtn = (ImageView) itemView.findViewById(R.id.card_nextButton);
            myBookingsImage = itemView.findViewById(R.id.card_carparkImage);

            carparkName.setVisibility(View.VISIBLE);
            itemView.findViewById(R.id.card_carparkPrice).setVisibility(View.GONE);
        }
    }
}