package com.example.manishaagro;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manishaagro.model.TripModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdapterEmployeeDetails extends RecyclerView.Adapter<AdapterEmployeeDetails.MyViewHolder> {
    private static List<TripModel> EmpVisitDetailsRpt;
    public Context context;
    public DurationCalculate durationCalculate;
    private AdapterEmployeeDetails.RecyclerViewClickListener mListener;
    public String time1,time2;


    public AdapterEmployeeDetails(List<TripModel> tripdtlReport, Context context, RecyclerViewClickListener listener) {
        EmpVisitDetailsRpt = tripdtlReport;
        this.context = context;
        this.mListener = listener;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_for_manager, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEmployeeDetails.MyViewHolder holder, int position) {

        String DiffrenceTravelDay="";
        final TripModel tripModel = EmpVisitDetailsRpt.get(position);
        holder.customerName.setText(tripModel.getVisitedCustomerName());
        holder.addresstxt.setText(tripModel.getAddress());

         time1=tripModel.getDateOfTravel();
         time2=tripModel.getDateOfReturn();
        Log.v("Timecheck", "time" + time1);
        Log.v("Timecheck2", "time2" + time2);

        DiffrenceTravelDay=durationCalculate.findDifference(time1,time2);

        holder.durationtxt.setText(DiffrenceTravelDay);
    }

    @Override
    public int getItemCount() {
        return EmpVisitDetailsRpt.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener mListener;
        private TextView customerName;
        private TextView addresstxt;
        private TextView durationtxt;
        private RelativeLayout mRowContainer;

        public MyViewHolder(@NonNull View itemView,final RecyclerViewClickListener listener) {
            super(itemView);

            mListener = listener;
            customerName = itemView.findViewById(R.id.CustomerName);
            addresstxt = itemView.findViewById(R.id.custAddMgr);
            durationtxt=itemView.findViewById(R.id.Empduration);
            mRowContainer = itemView.findViewById(R.id.row_container);
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.row_container) {
//                mListener.onEmpVisitdtlClick(mRowContainer, getAdapterPosition());
            }
        }
    }

    public interface RecyclerViewClickListener {
        void onEmpVisitdtlClick(View view, int position);
    }
}
