package com.example.airpark.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airpark.R;
import com.example.airpark.models.BookingTicket;
import com.example.airpark.models.CarparkListSection;

import java.util.List;

/**
 * Airpark Application - Group 14
 *
 * CS4125 -> System Analysis & Design
 * CS5721 -> Software Design
 *
 * Creates sectioned car park screen and holds appropriate items within section
 */
public class CarparkSectionAdapter extends RecyclerView.Adapter<CarparkSectionAdapter.MyViewHolder> {
    private List<CarparkListSection> sectionList;
    private Context context;
    private CarparkItemAdapter itemAdapter;

    /**
     * Constructs carpark sectioned object
     * @param context
     * @param sectionList
     */
    public CarparkSectionAdapter(Context context, List<CarparkListSection> sectionList){
        this.context = context;
        this.sectionList = sectionList;
    }

    @NonNull
    @Override
    public CarparkSectionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_carpark_list_section,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarparkSectionAdapter.MyViewHolder holder, int position) {
        CarparkListSection section = sectionList.get(position);
        holder.bind(section);
    }

    @Override
    public int getItemCount() { return sectionList.size(); }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView sectionName;
        private RecyclerView itemRecyclerView;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            sectionName = itemView.findViewById(R.id.section_item_title);
            itemRecyclerView = itemView.findViewById(R.id.item_recycler_view);
        }
        public void bind(CarparkListSection section){
            sectionName.setText(section.getSectionTitle());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
            itemRecyclerView.setLayoutManager(linearLayoutManager);
            itemAdapter = new CarparkItemAdapter(section.getItemsInSection());
            itemRecyclerView.setAdapter(itemAdapter);
        }
    }
}
