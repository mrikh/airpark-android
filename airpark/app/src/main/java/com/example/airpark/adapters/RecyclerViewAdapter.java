package com.example.airpark.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.airpark.R;
import com.example.airpark.models.CarPark;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<CarPark> carparkList;

    public RecyclerViewAdapter(List<CarPark> carparkList){
        this.carparkList = carparkList;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carpark_recyclerview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, int position) {
        final CarPark carpark = carparkList.get(position);
        holder.carparkName.setText(carpark.getCarparkName());
        holder.carparkType.setText(carpark.getCarparkType());
    }

    @Override
    public int getItemCount() {
        return carparkList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView carparkName, carparkType, carparkPrice;
        private ImageView carparkImage, nextButton;
        private CardView cardview;

        public MyViewHolder(View itemView){
            super(itemView);
            // Bind Ui with id
            carparkName = itemView.findViewById(R.id.card_carparkName);
            carparkType = itemView.findViewById(R.id.card_carparkType);
            carparkPrice = itemView.findViewById(R.id.card_carparkPrice);
            carparkImage = itemView.findViewById(R.id.card_carparkImage);
            nextButton = itemView.findViewById(R.id.card_nextButton);
            cardview = itemView.findViewById(R.id.carpark_cardView);
        }
    }
}
