package com.example.manishaagro.employee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.manishaagro.R;
import com.example.manishaagro.model.VisitProductMapModel;

import java.util.List;

public class ProductListViewAdapter extends ArrayAdapter<VisitProductMapModel> {
    private Context context;
   private List<VisitProductMapModel> productListvisit;


    public ProductListViewAdapter(Context context,List<VisitProductMapModel> productListvisit)
    {
        super(context, R.layout.column_row_list,productListvisit);
        this.context=context;
        this.productListvisit=productListvisit;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        convertView=layoutInflater.inflate(R.layout.column_row_list,parent,false);

        TextView productName = convertView.findViewById(R.id.ProductName);
        TextView packing = convertView.findViewById(R.id.Packing);
        TextView quantity = convertView.findViewById(R.id.Quantity);

        VisitProductMapModel visitProductMapModel=productListvisit.get(position);
        productName.setText(visitProductMapModel.getProductname());
        packing.setText(visitProductMapModel.getPacking());
        quantity.setText(visitProductMapModel.getProductquantity());
        return convertView;
    }
}

