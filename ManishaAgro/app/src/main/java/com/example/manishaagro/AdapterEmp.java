package com.example.manishaagro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterEmp extends RecyclerView.Adapter<AdapterEmp.MyViewHolder> {
    List<ProfileModel> employeeReportingModels;
    public ApiInterface apiInterface;
    public Context context;
    private RecyclerViewClickListener mListener;


    public AdapterEmp(List<ProfileModel> empReport, Context context, RecyclerViewClickListener listener) {
        this.employeeReportingModels = empReport;
        this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ProfileModel profileModel = employeeReportingModels.get(position);
        holder.mid.setText(profileModel.getEmpid());
        holder.mName.setText(profileModel.getName());
    }

    @Override
    public int getItemCount() {
        return employeeReportingModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener mListener;
        private TextView mName, mid;
        private RelativeLayout mRowContainer;

        public MyViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            mListener = listener;
            mName = itemView.findViewById(R.id.rptname);
            mid = itemView.findViewById(R.id.rptid);
            mRowContainer = itemView.findViewById(R.id.row_container);
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.row_container) {
                mListener.onRowClick(mRowContainer, getAdapterPosition());
            }
        }
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }
}
