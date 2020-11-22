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


import com.example.manishaagro.model.DailyEmpExpenseModel;
import com.example.manishaagro.model.MeterModel;

import java.util.List;

public class CloaseAdapter extends RecyclerView.Adapter<CloaseAdapter.MyViewHolder>  {
    private static List<DailyEmpExpenseModel> meterModelList;
    public Context context;
    private CloaseAdapter.RecyclerViewClickListener mListener;


    public CloaseAdapter(List<DailyEmpExpenseModel> closeReport, Context context, RecyclerViewClickListener listener) {
        meterModelList = closeReport;
        this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.close_list, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DailyEmpExpenseModel tripModel = meterModelList.get(position);

        int startread=tripModel.getStartopening_km();
        holder.startkm1.setText(String.valueOf(startread)+" KM ");

        int closeread=tripModel.getEndclosing_km();


        int totalread=closeread - startread;
        holder.startkm.setText(String.valueOf(closeread)+" KM ");


        String EnddateTempSpace=tripModel.getEnddate();
        Log.v("End", "Enddate" +EnddateTempSpace);
        if (EnddateTempSpace.equals("0000-00-00 00:00:00"))
        {
            holder.startdate.setText(" ");
        }
        else
        {
            String str[]=EnddateTempSpace.split(" ");
            String str1=str[0];
            holder.startdate.setText(str1);
        }
        String StartdateTempSpace=tripModel.getStardate();
        Log.v("start", "Startdate" +StartdateTempSpace);

        if (StartdateTempSpace.equals("0000-00-00 00:00:00"))
        {
            holder.startdate1.setText(" ");
        }
        else
        {
            String strStart[]=StartdateTempSpace.split(" ");
            String strStart1=strStart[0];
            holder.startdate1.setText(strStart1);
        }







        if(totalread<0)
        {
            holder.totaltxt.setText("0"+" KM ");
        }
        else
        {
            holder.totaltxt.setText(String.valueOf(totalread)+" KM ");
        }


    }

    @Override
    public int getItemCount() {
        return meterModelList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener mListener;
        private TextView startdate;
        private TextView startdate1;
        private TextView startkm;
        private TextView totaltxt;
        private TextView startkm1;

        private RelativeLayout mRowContainer;

        public MyViewHolder(@NonNull View itemView,final RecyclerViewClickListener listener) {
            super(itemView);

            mListener = listener;
            startdate = itemView.findViewById(R.id.startdate);
            startdate1 = itemView.findViewById(R.id.startdate1);
            startkm = itemView.findViewById(R.id.startkm);
            startkm1 = itemView.findViewById(R.id.startkm1);
            mRowContainer = itemView.findViewById(R.id.row_container);
            totaltxt=itemView.findViewById(R.id.TextTotal);

            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }


    public interface RecyclerViewClickListener {
        void onEndTripCardClick(View view, int position);
    }
}
