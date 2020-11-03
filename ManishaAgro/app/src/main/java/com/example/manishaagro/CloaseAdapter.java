package com.example.manishaagro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manishaagro.employee.OpenAdapter;
import com.example.manishaagro.model.MeterModel;

import java.util.List;

public class CloaseAdapter extends RecyclerView.Adapter<CloaseAdapter.MyViewHolder>  {
    private static List<MeterModel> meterModelList;
    public Context context;
    private CloaseAdapter.RecyclerViewClickListener mListener;


    public CloaseAdapter(List<MeterModel> closeReport, Context context, RecyclerViewClickListener listener) {
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
        final MeterModel tripModel = meterModelList.get(position);

        int startread=tripModel.getStartmeterreading();

        int closeread=tripModel.getEndmeterreading();

        int totalread=closeread - startread;
        holder.startkm.setText(String.valueOf(closeread)+" KM ");

        String StartdateTempSpace=tripModel.getDateend();
        String spliDateTemp[]=StartdateTempSpace.split(" ");

        String dtTravels="";
        dtTravels=spliDateTemp[0];

        holder.startdate.setText(dtTravels);
        holder.totaltxt.setText(String.valueOf(totalread)+" KM ");
    }

    @Override
    public int getItemCount() {
        return meterModelList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener mListener;
        private TextView startdate;
        private TextView startkm;
        private TextView totaltxt;

        private RelativeLayout mRowContainer;

        public MyViewHolder(@NonNull View itemView,final RecyclerViewClickListener listener) {
            super(itemView);

            mListener = listener;
            startdate = itemView.findViewById(R.id.startdate);
            startkm = itemView.findViewById(R.id.startkm);
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
