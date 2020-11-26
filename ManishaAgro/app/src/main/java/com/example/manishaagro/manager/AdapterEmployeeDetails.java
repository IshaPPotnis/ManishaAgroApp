package com.example.manishaagro.manager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manishaagro.R;
import com.example.manishaagro.model.TripModel;

import java.util.List;

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
        holder.txtEmpTextviewContact.setText(tripModel.getContactdetail());

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
        private TextView txtEmpTextviewContact;
        private RelativeLayout mRowContainer;

        public MyViewHolder(@NonNull View itemView,final RecyclerViewClickListener listener) {
            super(itemView);

            mListener = listener;
            customerName = itemView.findViewById(R.id.CustomerName);
            addresstxt = itemView.findViewById(R.id.custAddMgr);
            durationtxt=itemView.findViewById(R.id.Empduration);
            mRowContainer = itemView.findViewById(R.id.row_container);
            txtEmpTextviewContact=itemView.findViewById(R.id.EmpTextviewContact);

            txtEmpTextviewContact.setOnClickListener(this);

            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.EmpTextviewContact) {
//                mListener.onEmpVisitdtlClick(mRowContainer, getAdapterPosition());
                mListener.onEmpVisitdtlClick(null,v,getAdapterPosition(),v.getId());
            }
          //  else if(v.getId()==R.id.row_container)
            //{
               // mListener.onEmpVisitdtlClick(null,v, getAdapterPosition(),v.getId());
            //}
        }
    }

    public interface RecyclerViewClickListener {
        void onEmpVisitdtlClick(AdapterView<?> parent, View view, int position,long id);
       // void onEmpVisitdtlClick(AdapterView<?> parent, View view, int position,long id);
    }


}
