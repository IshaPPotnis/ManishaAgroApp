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

public class AdapterStatus extends RecyclerView.Adapter<AdapterStatus.MyViewHolder> {
    private static List<TripModel> employeeReportingModels;
    public ApiInterface apiInterface;
    public Context context;
    private AdapterStatus.RecyclerViewClickListener mListener;


    public AdapterStatus(List<TripModel> empReport, Context context, RecyclerViewClickListener listener) {
        this.employeeReportingModels = empReport;
        this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_statustab, parent, false);
        return new MyViewHolder(view, mListener);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener mListener;
        private TextView CustName;
        private TextView dtTravel;
        private TextView dtReturn;

        private RelativeLayout mRowContainer;

        public MyViewHolder(@NonNull View itemView,final RecyclerViewClickListener listener) {
            super(itemView);

            mListener = listener;
            CustName = itemView.findViewById(R.id.custname);
            dtTravel = itemView.findViewById(R.id.dtTravel);
            dtReturn=itemView.findViewById(R.id.dtTravelRtn);

            mRowContainer = itemView.findViewById(R.id.row_container);

            mRowContainer.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.row_container) {
                mListener.onCardClick(mRowContainer, getAdapterPosition());
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TripModel tripModel = employeeReportingModels.get(position);
        holder.CustName.setText(tripModel.getVisitedCustomerName());
        holder.dtTravel.setText(tripModel.getDateOfTravel());
        holder.dtReturn.setText(tripModel.getDateOfReturn());



    }

    @Override
    public int getItemCount() {
        return employeeReportingModels.size();
    }


    public interface RecyclerViewClickListener {
        void onCardClick(View view, int position);
    }

}
