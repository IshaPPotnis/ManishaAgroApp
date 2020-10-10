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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdapterStatus extends RecyclerView.Adapter<AdapterStatus.MyViewHolder> {
    private static List<TripModel> employeeReportingModels;
    public Context context;
    private AdapterStatus.RecyclerViewClickListener mListener;
    public DurationCalculate durationCalculate;


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

            hoursTimeStart=itemView.findViewById(R.id.HoursStart);

            hoursTimeEnd=itemView.findViewById(R.id.HoursEnd);
            differenceDay=itemView.findViewById(R.id.DiffDays);
            differenceHr=itemView.findViewById(R.id.DiffHr);
            differenceMin=itemView.findViewById(R.id.DiffMin);





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


        String month_names="";
        String monthNum="";
        String DiffrenceTravelDay="";

        //---------------------------------------------//
        holder.customerName.setText(tripModel.getVisitedCustomerName());
        //--------------------------------------------//
        String StartdateTempSpace=tripModel.getDateOfTravel();
        String spliDateTemp[]=StartdateTempSpace.split(" ");

      //  for(int i=0; i < spliDateTemp.length; i++){
        //    System.out.println(spliDateTemp[i]);
          //  Log.v("Splitarrar", "array1" + spliDateTemp[i]);
        //}


        String spliDateTempDash[]=spliDateTemp[0].split("-");
    //    for(int i=spliDateTempDash.length-1; i >= 0;  i--){
      //      System.out.println(spliDateTempDash[i]);
        //    Log.v("Splitarrar", "array1" + spliDateTempDash[i]);
        //}

        holder.dayOfTravel.setText(spliDateTempDash[2]);
        monthNum=spliDateTempDash[1];
        month_names=formatMonthName(monthNum);
        Log.v("Month Number", "MonthNumber" + month_names);
      //  cal.set(Calendar.MONTH, Integer.parseInt(monthNum));
       // month_name = month_date.format(cal.getTime());
        holder.monthOfTravel.setText(month_names);

        holder.yearOfTravel.setText(spliDateTempDash[0]);

        String spliDateTempTime[]=spliDateTemp[1].split(":");
          for(int i=0; i < spliDateTempTime.length; i++){
              System.out.println(spliDateTempTime[i]);
            Log.v("SplitTime", "Timearray1" + spliDateTempTime[i]);
        }

          holder.hoursTimeStart.setText(spliDateTemp[1]);


        //---------------------------------//
        String EnddateTemp=tripModel.getDateOfReturn();


        String splitEnd[]=EnddateTemp.split(" ");

       // for(int i=0; i < splitEnd.length; i++){
         //   System.out.println(splitEnd[i]);
           // Log.v("Splitarrar", "array1" + spliDateTemp[i]);
        //}




        String splitEndDate[]=splitEnd[0].split("-");
        //for(int i=splitEndDate.length-1; i >= 0 ; i--){
          ///  System.out.println(splitEndDate[i]);
            //Log.v("Splitarrar", "Endarray1" + splitEndDate[i]);
        //}
        holder.dayOfReturn.setText(splitEndDate[2]);

        monthNum=splitEndDate[1];
        month_names=formatMonthName(monthNum);

        holder.monthOfReturn.setText(month_names);

        holder.yearOfReturn.setText(splitEndDate[0]);

        String splitEndDateTime[]=splitEnd[1].split(":");
        holder.hoursTimeEnd.setText(splitEnd[1]);

        String t1=tripModel.getDateOfTravel();
        String t2=tripModel.getDateOfReturn();

        DiffrenceTravelDay=durationCalculate.findDifference(t1,t2);

        String SplitDuration[]=DiffrenceTravelDay.split(",");


        holder.differenceDay.setText(SplitDuration[0]);
        holder.differenceHr.setText(SplitDuration[1]);
        holder.differenceMin.setText(SplitDuration[2]);

    }

    public String formatMonthName(String month) {
         String mname="";
        SimpleDateFormat monthParse = new SimpleDateFormat("MM");
        SimpleDateFormat monthDisplay = new SimpleDateFormat("MMM");
        try {
            mname= monthDisplay.format(monthParse.parse(month));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  mname;
    }





/*    public static String findDifference(String start_date,
                               String end_date)
    {

        SimpleDateFormat sdf
                = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss");

        String RetrunDiff="";

        // Try Class
        try {


            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);


            long difference_In_Time
                    = d2.getTime() - d1.getTime();


            long difference_In_Seconds
                    = TimeUnit.MILLISECONDS
                    .toSeconds(difference_In_Time)
                    % 60;

            long difference_In_Minutes
                    = TimeUnit
                    .MILLISECONDS
                    .toMinutes(difference_In_Time)
                    % 60;

            long difference_In_Hours
                    = TimeUnit
                    .MILLISECONDS
                    .toHours(difference_In_Time)
                    % 24;

            long difference_In_Days
                    = TimeUnit
                    .MILLISECONDS
                    .toDays(difference_In_Time)
                    % 365;

            long difference_In_Years
                    = TimeUnit
                    .MILLISECONDS
                    .toDays(difference_In_Time)
                    / 365l;


            System.out.print(
                    "Difference"
                            + " between two dates is: ");

            // Print result
            System.out.println(difference_In_Days
                            + " Days,"
                            + difference_In_Hours
                            + " Hrs,"
                            + difference_In_Minutes
                            + " Min,"
                            + difference_In_Seconds
                            + " Sec");
            RetrunDiff=difference_In_Days
                    + " Days,"
                    + difference_In_Hours
                    + " Hrs,"
                    + difference_In_Minutes
                    + " Min,"
                    + difference_In_Seconds
                    + " Sec";
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return  RetrunDiff;
    }*/




    @Override
    public int getItemCount() {
        return employeeReportingModels.size();
    }

    public interface RecyclerViewClickListener {
        void onCardClick(View view, int position);
    }
}