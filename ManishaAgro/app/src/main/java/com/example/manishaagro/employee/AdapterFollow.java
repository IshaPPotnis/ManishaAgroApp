package com.example.manishaagro.employee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manishaagro.R;
import com.example.manishaagro.model.TripModel;

import java.util.List;

public class AdapterFollow extends RecyclerView.Adapter<AdapterFollow.MyViewHolder> {


    private static List<TripModel> employeeTripEndModels;
    public Context context;
    private RecyclerViewClickListener mListener;


    public AdapterFollow(List<TripModel> tripEndReport, Context context, RecyclerViewClickListener listener) {
        employeeTripEndModels = tripEndReport;
        this.context = context;
        this.mListener = listener;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_followup, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            final TripModel tripModel=employeeTripEndModels.get(position);


        holder.customerName.setText(tripModel.getVisitedCustomerName());


        String StartdateTempSpace=tripModel.getFollowupdate();
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
        private TextView travelDate;
        private RelativeLayout mRowContainer;

        public MyViewHolder(@NonNull View itemView,final RecyclerViewClickListener listener) {
            super(itemView);

            mListener = listener;
            customerName = itemView.findViewById(R.id.Followcustname);

            mRowContainer = itemView.findViewById(R.id.row_container_followup);
            travelDate=itemView.findViewById(R.id.FollowDate);
            mRowContainer.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.row_container_followup)
            {
                mListener.onFollowupClick(mRowContainer, getAdapterPosition());

            }
        }
    }
    public interface RecyclerViewClickListener {
       void onFollowupClick(View view, int position);
    }
}
