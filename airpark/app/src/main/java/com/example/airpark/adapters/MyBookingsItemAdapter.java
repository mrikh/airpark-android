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

import com.example.airpark.R;
import com.example.airpark.activities.ChosenCarparkActivity;
import com.example.airpark.models.BookingTicket;

import java.util.ArrayList;
import java.util.List;

public class MyBookingsItemAdapter extends RecyclerView.Adapter<MyBookingsItemAdapter.MyViewHolder> {
    private List<BookingTicket> bookingsList;
    private Context context;
    private ArrayList<String> test;

    public MyBookingsItemAdapter(List<BookingTicket> bookingsList){
        this.bookingsList = bookingsList;
    }
    public MyBookingsItemAdapter(ArrayList<String> test){
        this.test = test;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_bookings_list_item,parent,false);
        return new MyBookingsItemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookingsItemAdapter.MyViewHolder holder, int position) {
//        final BookingTicket ticket = bookingsList.get(position);
//        holder.carparkName.setText(ticket.getCarparkName());
//        holder.carparkType.setText(carpark.getCarparkTypeString());
        //holder.bookingDate.setText();

        /**Hardcoded**/
        holder.airportName.setText(test.get(0));
        holder.bookingDate.setText(test.get(2));
        holder.carparkName.setText(test.get(1));

        holder.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChosenCarparkActivity.class);
//                intent.putExtra("ticket", ticket);
                context.startActivity(intent);
            }
        });

//        Glide.with(holder.carparkImage.getContext()).load(carpark.getCarparkImage()).into(holder.carparkImage);
    }

    @Override
    public int getItemCount() {
//        return bookingsList.size();
        return test.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView airportName, bookingDate, carparkName;
        private ImageView nextBtn, deleteBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            // Bind Ui with id
            context = itemView.getContext();
            airportName = (TextView) itemView.findViewById(R.id.myBookings_airportName);
            bookingDate = (TextView) itemView.findViewById(R.id.myBooking_dates);
            carparkName = (TextView) itemView.findViewById(R.id.myBooking_carparkName);
            nextBtn = (ImageView) itemView.findViewById(R.id.myBookings_nextButton);
//            deleteBtn = (ImageView) itemView.findViewById(R.id.myBookings_delete);
        }
    }
}