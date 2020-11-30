package com.example.airpark.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airpark.R;
import com.example.airpark.activities.ChosenCarparkActivity;
import com.example.airpark.activities.SearchActivity;
import com.example.airpark.activities.SelectCarparkActivity;
import com.example.airpark.models.BookingTicket;
import com.example.airpark.models.CarPark;
import com.example.airpark.models.Price;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Creates each car park item that's displayed on SelectCarparkActivity.java
 */
public class CarparkItemAdapter extends RecyclerView.Adapter<CarparkItemAdapter.MyViewHolder> {
    private List<CarPark> carparkList;
    private Price price;
    private BookingTicket ticket;


    /**
     * Constructs car park item object
     * @param carparkList
     * @param ticket
     */
    public CarparkItemAdapter(List<CarPark> carparkList, BookingTicket ticket){
        this.carparkList = carparkList;
        this.ticket = ticket;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_carpark_list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final CarPark carpark = carparkList.get(position);
        price = new Price(carpark.getPrice());
        holder.carparkName.setText(carpark.getCarparkName());
        holder.carparkType.setText(carpark.getCarparkType());
        try {
            holder.carparkPrice.setText("€" + getFullPrice(carpark));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.context, ChosenCarparkActivity.class);
                intent.putExtra("ticket", ticket);
                intent.putExtra("Car Park", carpark);
                try {
                    intent.putExtra("Full Price", getFullPrice(carpark));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                holder.context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return carparkList.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView carparkName, carparkType, carparkPrice;
        private ImageView carparkImage, nextButton;
        private CardView cardview;
        private final Context context;

        public MyViewHolder(View itemView){
            super(itemView);
            // Bind Ui with id
            context = itemView.getContext();
            carparkName = itemView.findViewById(R.id.card_carparkName);
            carparkType = itemView.findViewById(R.id.card_carparkType);
            carparkPrice = itemView.findViewById(R.id.card_carparkPrice);
            carparkImage = itemView.findViewById(R.id.card_carparkImage);
            nextButton = itemView.findViewById(R.id.card_nextButton);
            cardview = itemView.findViewById(R.id.carpark_cardView);
        }
    }

    private String getFullPrice(CarPark carpark) throws ParseException {
        DecimalFormat dFormat = new DecimalFormat("#.00");
        double fullPrice = price.calculatePrice(ticket.getArrivalTime(), ticket.getExitTime(), ticket.getArrivalDate(), ticket.getExitDate(), carpark.getCarparkType());

        return dFormat.format(fullPrice);
    }
}
