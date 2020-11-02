package com.example.manishaagro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.manishaagro.model.DealerProductMap;
import com.example.manishaagro.model.VisitProductMapModel;

import java.util.List;

public class ProductListViewAdapterDealer extends ArrayAdapter<DealerProductMap> {
    private Context context;
    private List<DealerProductMap> productListvisit;

    public ProductListViewAdapterDealer(Context context,List<DealerProductMap> productListvisit)
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

        DealerProductMap dealerProductMap=productListvisit.get(position);
        productName.setText(dealerProductMap.getProductname());
        packing.setText(dealerProductMap.getPacking());
        quantity.setText(dealerProductMap.getProductquantity());
        return convertView;
    }
}
