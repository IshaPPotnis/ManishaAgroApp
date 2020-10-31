package com.example.manishaagro;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    public List<String> purchasedProductList;
    Activity activity;

    public ListViewAdapter(Activity activity,List<String> list){
        super();
        this.activity=activity;
        this.purchasedProductList =list;
    }

    @Override
    public int getCount() {
        return purchasedProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return purchasedProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView productName;
        TextView packing;
        TextView quantity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater=activity.getLayoutInflater();
        if(convertView == null){
            convertView=inflater.inflate(R.layout.column_row_list, null);
            holder=new ViewHolder();
            holder.productName = convertView.findViewById(R.id.ProductName);
            holder.packing = convertView.findViewById(R.id.Packing);
            holder.quantity = convertView.findViewById(R.id.Quantity);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }

        String productDetails = purchasedProductList.get(position);
        String[] split = productDetails.split("-");
        holder.productName.setText(split[0]);
        holder.packing.setText(split[1]);
        holder.quantity.setText(split[2]);
        return convertView;
    }
}
