package com.example.manishaagro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manishaagro.model.TripModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterEnd extends RecyclerView.Adapter<AdapterEnd.MyViewHolder> {
    private static List<TripModel> employeeTripEndModels;
    public Context context;
    private AdapterEnd.RecyclerViewClickListener mListener;

    public AdapterEnd(List<TripModel> tripEndReport, Context context, RecyclerViewClickListener listener) {
        employeeTripEndModels = tripEndReport;
        this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_of_end_trip_check, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TripModel tripModel = employeeTripEndModels.get(position);
        holder.customerName.setText(tripModel.getVisitedCustomerName());
        holder.addresstxt.setText(tripModel.getAddress());

        String StartdateTempSpace=tripModel.getDateOfTravel();
        String spliDateTemp[]=StartdateTempSpace.split(" ");

        String dtTravels="";
        dtTravels=spliDateTemp[0];

        holder.travelDate.setText(dtTravels);


    }

    @Override
    public int getItemCount() {
        return employeeTripEndModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener mListener;
        private TextView customerName;
        private TextView addresstxt;
        private TextView travelDate;
        private RelativeLayout mRowContainer;

        public MyViewHolder(@NonNull View itemView,final RecyclerViewClickListener listener) {
            super(itemView);

            mListener = listener;
            customerName = itemView.findViewById(R.id.custname);
            addresstxt = itemView.findViewById(R.id.CustAddress);
            mRowContainer = itemView.findViewById(R.id.row_container);
            travelDate=itemView.findViewById(R.id.travelDate);
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.row_container) {
                mListener.onEndTripCardClick(mRowContainer, getAdapterPosition());
            }

        }
    }

    public interface RecyclerViewClickListener {
        void onEndTripCardClick(View view, int position);
    }

}
