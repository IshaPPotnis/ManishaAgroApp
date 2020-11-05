package com.example.manishaagro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import com.example.manishaagro.model.TripModel;

import java.util.List;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.MyViewHolder> {
    private static List<TripModel> tripPenModelList;
    public Context context;
    private PendingAdapter.RecyclerViewClickListener mListener;


    public PendingAdapter(List<TripModel> penReport, Context context, RecyclerViewClickListener listener) {
        tripPenModelList = penReport;
        this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pendings_list, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final TripModel modelTrip = tripPenModelList.get(position);
        String strname=modelTrip.getVisitedCustomerName();
        holder.txtName.setText(strname);
        String strdateStart=modelTrip.getDateOfTravel();
        String[] str =strdateStart.split(" ");
        holder.txtDate.setText(str[0]);

    }

    @Override
    public int getItemCount() {
        return tripPenModelList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener mListener;
        private TextView txtName;
        private TextView txtDate;


        private RelativeLayout mRowContainer;

        public MyViewHolder(@NonNull View itemView,final RecyclerViewClickListener listener) {
            super(itemView);
            mListener = listener;
            txtName = itemView.findViewById(R.id.visitername);
            txtDate = itemView.findViewById(R.id.visitDate);

            mRowContainer = itemView.findViewById(R.id.row_container);
            mRowContainer.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

        }
    }
    public interface RecyclerViewClickListener {
        void onDealerProductDetailClick(View view, int position);
    }

}
