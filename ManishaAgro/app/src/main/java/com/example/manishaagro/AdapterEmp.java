package com.example.manishaagro;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashSet;
import java.util.List;

public class AdapterEmp extends RecyclerView.Adapter<AdapterEmp.MyViewHolder> {
  private static List<ProfileModel> employeeReportingModels;
    public ApiInterface apiInterface;
    public Context context;
    private HashSet<MapView> mapViews = new HashSet<>();
    private static GoogleMap map;
    private RecyclerViewClickListener mListener;
    public static double Lattitudevalue=0,LongitudeValue=0;
    public static ProfileModel profileModeldata;

    public static LatLng position;


    public AdapterEmp(List<ProfileModel> empReport, Context context, RecyclerViewClickListener listener) {
        this.employeeReportingModels = empReport;
        this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
       MyViewHolder myViewHolder =new MyViewHolder(view,mListener);
        mapViews.add(myViewHolder.mapView);
        return myViewHolder;
        //return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ProfileModel profileModel = employeeReportingModels.get(position);
        holder.mid.setText(profileModel.getEmpid());
        holder.mName.setText(profileModel.getName());
        holder.txtlat1.setText(profileModel.getLatitude());
        holder.txtlong1.setText(profileModel.getLongitude());
    }

    @Override
    public int getItemCount() {
        return employeeReportingModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, OnMapReadyCallback {
        private RecyclerViewClickListener mListener;
        private TextView mName;
        private TextView mid;
        private TextView txtlat1;
        private TextView txtlong1;
        private RelativeLayout mRowContainer;
        private MapView mapView;

        public MyViewHolder(@NonNull View itemView, final RecyclerViewClickListener listener) {
            super(itemView);

            mListener = listener;
            mName = itemView.findViewById(R.id.rptname);
            mid = itemView.findViewById(R.id.rptid);
            mapView=itemView.findViewById(R.id.map_listitem);
            txtlat1=itemView.findViewById(R.id.textlat2);
            txtlong1=itemView.findViewById(R.id.textlong2);
            mRowContainer = itemView.findViewById(R.id.row_container);
            mapView.onCreate(null);
            mapView.onResume();

            mRowContainer.setOnClickListener(this);

            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    map=googleMap;

                    googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                        @Override
                        public void onMapLoaded() {
                            double val1= Double.parseDouble(txtlat1.getText().toString().trim());
                            double val2= Double.parseDouble(txtlong1.getText().toString().trim());
                            position=new LatLng(val1,val2);
                            MarkerOptions markerOptions =new MarkerOptions();
                            markerOptions.position(position);
                            markerOptions.title("Loation");
                            googleMap.clear();
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,10));
                            googleMap.addMarker(markerOptions);
                        }
                    });
                }
            });

        }



        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.row_container) {
                mListener.onRowClick(mRowContainer, getAdapterPosition());
            }
        }


        @Override
        public void onMapReady(GoogleMap googleMap) {

        }
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }
}
