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
import com.example.manishaagro.model.DailyEmpExpenseModel;
import com.example.manishaagro.model.MeterModel;
import com.example.manishaagro.model.TripModel;

import java.util.List;

public class OpenAdapter extends RecyclerView.Adapter<OpenAdapter.MyViewHolder> {
    private static List<DailyEmpExpenseModel> meterModelList;
    public Context context;
    private OpenAdapter.RecyclerViewClickListener mListener;


    public OpenAdapter(List<DailyEmpExpenseModel> startReport, Context context, RecyclerViewClickListener listener) {
        meterModelList = startReport;
        this.context = context;
        this.mListener = listener;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.start_list, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OpenAdapter.MyViewHolder holder, int position) {
        final DailyEmpExpenseModel tripModel = meterModelList.get(position);
        int startread=tripModel.getStartopening_km();
        holder.startkm.setText(String.valueOf(startread)+" KM ");

        String StartdateTempSpace=tripModel.getStardate();
        String spliDateTemp[]=StartdateTempSpace.split(" ");

        String dtTravels="";
        dtTravels=spliDateTemp[0];

        holder.startdate.setText(dtTravels);
    }

    @Override
    public int getItemCount() {
        return meterModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private RecyclerViewClickListener mListener;
        private TextView startdate;
        private TextView startkm;

        private RelativeLayout mRowContainer;

        public MyViewHolder(@NonNull View itemView,final RecyclerViewClickListener listener) {
            super(itemView);

            mListener = listener;
            startdate = itemView.findViewById(R.id.startdate);
            startkm = itemView.findViewById(R.id.startkm);
            mRowContainer = itemView.findViewById(R.id.row_container);

            mRowContainer.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.row_container)
            {

            }

        }
    }


    public interface RecyclerViewClickListener {
        void onEndTripCardClick(View view, int position);
    }
}
