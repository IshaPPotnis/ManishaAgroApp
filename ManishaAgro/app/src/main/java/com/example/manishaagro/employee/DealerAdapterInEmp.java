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
import com.example.manishaagro.model.DealerModel;

import java.util.List;

public class DealerAdapterInEmp extends RecyclerView.Adapter<DealerAdapterInEmp.MyViewHolder> {

    private static List<DealerModel> dealerModelList;
    public Context context;
    private DealerAdapterInEmp.RecyclerViewClickListener mListener;


    public DealerAdapterInEmp(List<DealerModel> dealerReport, Context context,RecyclerViewClickListener listener) {
        dealerModelList = dealerReport;
        this.context = context;
        this.mListener = listener;
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_dealer, parent, false);
        return new MyViewHolder(view, mListener);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DealerModel modelDealer = dealerModelList.get(position);
        holder.dealerName.setText(modelDealer.getDealername());
        String timedateval=modelDealer.getDate_of_purchase();
        String[] timedateval1=timedateval.split(" ");
        holder.purDate.setText(timedateval1[0]);
        holder.proName.setText(modelDealer.getProduct_name());
        String str=modelDealer.getPacking();
        holder.proPack.setText("( "+str+" )");
        holder.proQty.setText(" - "+String.valueOf(modelDealer.getQuantity()));

    }

    @Override
    public int getItemCount() {
        return dealerModelList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener mListener;
        private TextView dealerName;
        private TextView purDate;
        private TextView proName;
        private TextView proPack;
        private TextView proQty;

        private RelativeLayout mRowContainer;


        public MyViewHolder(@NonNull View itemView,final RecyclerViewClickListener listener) {
            super(itemView);

            mListener = listener;
            dealerName = itemView.findViewById(R.id.dealername);
            purDate = itemView.findViewById(R.id.purchaseDate);
            proName = itemView.findViewById(R.id.productname);
            proQty = itemView.findViewById(R.id.productqty);
            proPack = itemView.findViewById(R.id.productpacking);

            mRowContainer = itemView.findViewById(R.id.row_container);
            mRowContainer.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface RecyclerViewClickListener {
        //void onCardClick(View view, int position);
    }

}
