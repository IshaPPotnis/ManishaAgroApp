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
        private TextView hoursTimeStart;
        private TextView hoursTimeEnd;
        private TextView differenceDay;
        private TextView differenceHr;
        private TextView differenceMin;
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
            hoursTimeStart = itemView.findViewById(R.id.HoursStart);
            hoursTimeEnd = itemView.findViewById(R.id.HoursEnd);
            differenceDay = itemView.findViewById(R.id.DiffDays);
            differenceHr = itemView.findViewById(R.id.DiffHr);
            differenceMin = itemView.findViewById(R.id.DiffMin);
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
        String monthNames;
        String monthNum;
        String differenceTravelDay;
        //---------------------------------------------//
        holder.customerName.setText(tripModel.getVisitedCustomerName());
        //--------------------------------------------//
        String startDateTempSpace = tripModel.getDateOfTravel();
        String[] spliDateTemp = startDateTempSpace.split(" ");
        String[] splitDateTempDash = spliDateTemp[0].split("-");
        holder.dayOfTravel.setText(splitDateTempDash[2]);
        monthNum = splitDateTempDash[1];
        monthNames = formatMonthName(monthNum);
        Log.v("Month Number", "MonthNumber" + monthNames);
        holder.monthOfTravel.setText(monthNames);

        holder.yearOfTravel.setText(splitDateTempDash[0]);

        String[] spliDateTempTime = spliDateTemp[1].split(":");
        for (String s : spliDateTempTime) {
            System.out.println(s);
            Log.v("SplitTime", "Timearray1" + s);
        }
        holder.hoursTimeStart.setText(spliDateTemp[1]);
//----------------------------------------------------------------



        String endDateTemp = tripModel.getDateOfReturn();
        String[] splitEnd = endDateTemp.split(" ");
        String[] splitEndDate = splitEnd[0].split("-");
        monthNum = splitEndDate[1];
        monthNames = formatMonthName(monthNum);

        holder.dayOfReturn.setText(splitEndDate[2]);
        holder.monthOfReturn.setText(monthNames);
        holder.yearOfReturn.setText(splitEndDate[0]);
        holder.hoursTimeEnd.setText(splitEnd[1]);


        String[] splitEndDateTime = splitEnd[1].split(":");
        String t1 = tripModel.getDateOfTravel();
        String t2 = tripModel.getDateOfReturn();
        differenceTravelDay = DurationCalculate.findDifference(t1, t2);
        String[] splitDuration = differenceTravelDay.split(",");


        holder.differenceDay.setText(splitDuration[0]);
        holder.differenceHr.setText(splitDuration[1]);
        holder.differenceMin.setText(splitDuration[2]);
    }

    public String formatMonthName(String month) {
        String mname = "";
        SimpleDateFormat monthParse = new SimpleDateFormat("MM");
        SimpleDateFormat monthDisplay = new SimpleDateFormat("MMM");
        try {
            mname = monthDisplay.format(monthParse.parse(month));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mname;
    }

    @Override
    public int getItemCount() {
        return employeeReportingModels.size();
    }

    public interface RecyclerViewClickListener {
        void onCardClick(View view, int position);
    }
}