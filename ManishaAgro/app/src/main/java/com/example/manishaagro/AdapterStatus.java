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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdapterStatus extends RecyclerView.Adapter<AdapterStatus.MyViewHolder> {
    private static List<TripModel> employeeReportingModels;
    public Context context;
    private AdapterStatus.RecyclerViewClickListener mListener;


    public AdapterStatus(List<TripModel> empReport, Context context, RecyclerViewClickListener listener) {
        employeeReportingModels = empReport;
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
        private TextView customerName;
        private TextView dayOfTravel;
        private TextView monthOfTravel;
        private TextView yearOfTravel;
        private TextView dayOfReturn;
        private TextView monthOfReturn;
        private TextView yearOfReturn;
        private RelativeLayout mRowContainer;

        public MyViewHolder(@NonNull View itemView, final RecyclerViewClickListener listener) {
            super(itemView);
            mListener = listener;
            customerName = itemView.findViewById(R.id.custname);
            dayOfTravel = itemView.findViewById(R.id.dayStart);
            monthOfTravel = itemView.findViewById(R.id.monthStart);
            yearOfTravel = itemView.findViewById(R.id.yearStart);
            dayOfReturn = itemView.findViewById(R.id.dayEnd);
            monthOfReturn = itemView.findViewById(R.id.monthEnd);
            yearOfReturn = itemView.findViewById(R.id.yearEnd);
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

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        String month_name="";

        //---------------------------------------------//
        holder.customerName.setText(tripModel.getVisitedCustomerName());
        //--------------------------------------------//
        String StartdateTemp=tripModel.getDateOfTravel();
        String spliDateTemp[]=StartdateTemp.split("-");
        for(int i=spliDateTemp.length-1; i >= 0;  i--){
            System.out.println(spliDateTemp[i]);
            Log.v("Splitarrar", "array1" + spliDateTemp[i]);
        }

        holder.dayOfTravel.setText(spliDateTemp[2]);

        cal.set(Calendar.MONTH, Integer.parseInt(spliDateTemp[1]));
        month_name = month_date.format(cal.getTime());
        holder.monthOfTravel.setText(month_name);

        holder.yearOfTravel.setText(spliDateTemp[0]);

        //---------------------------------//
        String EnddateTemp=tripModel.getDateOfReturn();
        String splitEndDate[]=EnddateTemp.split("-");
        for(int i=splitEndDate.length-1; i >= 0 ; i--){
            System.out.println(splitEndDate[i]);
            Log.v("Splitarrar", "array1" + spliDateTemp[i]);
        }
        holder.dayOfReturn.setText(splitEndDate[2]);

        cal.set(Calendar.MONTH, Integer.parseInt(splitEndDate[1]));
        month_name = month_date.format(cal.getTime());
        holder.monthOfReturn.setText(month_name);

        holder.yearOfReturn.setText(splitEndDate[0]);




    }

    @Override
    public int getItemCount() {
        return employeeReportingModels.size();
    }

    public interface RecyclerViewClickListener {
        void onCardClick(View view, int position);
    }
}