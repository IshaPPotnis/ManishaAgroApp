package com.example.manishaagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.manishaagro.model.TripModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.EMPLOYEE_VISITED_CUSTOMER;
import static com.example.manishaagro.utils.Constants.END_TRIP_ENTRY;
import static com.example.manishaagro.utils.Constants.VISITED_CUSTOMER_ENTRY;

public class CustomerVisitEndActivity extends AppCompatActivity {
    Toolbar visitEndToolbar;
    public ApiInterface apiInterface;
    public AdapterEnd adapterEnd;
    private RecyclerView recyclerViewEndTrip;
    private List<TripModel> checkTripEndList;
    String employeeID="";
    AdapterEnd.RecyclerViewClickListener listener;
    String TripCustName="";
    String TripCustAdd="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_visit_end);
        visitEndToolbar=findViewById(R.id.toolbarvisitEnd);
        setSupportActionBar(visitEndToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }


        Intent intent = getIntent();
        employeeID = intent.getStringExtra("visitedEmployee");

        recyclerViewEndTrip=findViewById(R.id.EndTripCheckTRecyview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CustomerVisitEndActivity.this);
        recyclerViewEndTrip.setLayoutManager(layoutManager);


        listener=new AdapterEnd.RecyclerViewClickListener() {
            @Override
            public void onEndTripCardClick(View view, int position) {
                TripCustName=checkTripEndList.get(position).getVisitedCustomerName();
                TripCustAdd=checkTripEndList.get(position).getAddress();
                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerVisitEndActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("End Trip?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                entryEndTrip();
                                onResume();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        };


    }

    @Override
    protected void onResume() {
        super.onResume();
        getCheckEndTrip();

    }
    private void entryEndTrip()
    {
        final String STEmp_ID1 = employeeID;
        final String CutomerNm =TripCustName;
        final String CustomerAdd =TripCustAdd;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TripModel> empIdDesignationModelCall = apiInterface.updateEndtripDateEntry(END_TRIP_ENTRY, STEmp_ID1,CutomerNm,CustomerAdd);
        empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
            @Override
            public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                String value = response.body().getValue();
                String message = response.body().getMassage();


                if(value.equals("1"))
                {

                    Toast.makeText(CustomerVisitEndActivity.this,message,Toast.LENGTH_SHORT);

                }
                else if(value.equals("0"))
                {
                    Toast.makeText(CustomerVisitEndActivity.this,message,Toast.LENGTH_SHORT);
                }


            }

            @Override
            public void onFailure(Call<TripModel> call, Throwable t) {
                Toast.makeText(CustomerVisitEndActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void getCheckEndTrip()
    {
        final String STEmp_ID1 = employeeID;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("checkTrip", "emp1" + employeeID);
        Call<List<TripModel>> listCall = apiInterface.checkAndGetEndTrip("Check@ReturnEndTrip", STEmp_ID1);
        listCall.enqueue(new Callback<List<TripModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TripModel>> call, @NonNull Response<List<TripModel>> response) {
                checkTripEndList = response.body();
                Log.v("checkTripList", "empList" + checkTripEndList);
                adapterEnd = new AdapterEnd(checkTripEndList, CustomerVisitEndActivity.this, listener);
                recyclerViewEndTrip.setAdapter(adapterEnd);
                adapterEnd.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<TripModel>> call, @NonNull Throwable t) {
            }
        });

    }
}
