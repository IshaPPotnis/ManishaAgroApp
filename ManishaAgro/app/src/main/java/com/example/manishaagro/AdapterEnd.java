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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_statustab, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TripModel tripModel = employeeTripEndModels.get(position);
        holder.customerName.setText(tripModel.getVisitedCustomerName());
        holder.dateOfTravel.setText(tripModel.getDateOfTravel());
        holder.dateOfReturn.setText(tripModel.getDateOfReturn());

    }

    @Override
    public int getItemCount() {
        return employeeTripEndModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener mListener;
        private TextView customerName;
        private TextView dateOfTravel;
        private TextView dateOfReturn;
        private RelativeLayout mRowContainer;

        public MyViewHolder(@NonNull View itemView,final RecyclerViewClickListener listener) {
            super(itemView);

            mListener = listener;
            customerName = itemView.findViewById(R.id.custname);
            dateOfTravel = itemView.findViewById(R.id.dtTravel);
            dateOfReturn = itemView.findViewById(R.id.dtTravelRtn);
            mRowContainer = itemView.findViewById(R.id.row_container);
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
