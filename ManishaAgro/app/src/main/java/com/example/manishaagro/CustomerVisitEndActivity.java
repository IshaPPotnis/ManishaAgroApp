package com.example.manishaagro;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manishaagro.model.TripModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.END_TRIP_ENTRY;

public class CustomerVisitEndActivity extends AppCompatActivity {
    Toolbar visitEndToolbar;
    public ApiInterface apiInterface;
    public AdapterEnd adapterEnd;
    private RecyclerView recyclerViewEndTrip;
    private List<TripModel> checkTripEndList;
    String employeeID = "";
    AdapterEnd.RecyclerViewClickListener listener;
    String tripCustomerName = "";
    String tripCustomerAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_visit_end);
        visitEndToolbar = findViewById(R.id.toolbarvisitEnd);
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
        recyclerViewEndTrip = findViewById(R.id.EndTripCheckTRecyview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CustomerVisitEndActivity.this);
        recyclerViewEndTrip.setLayoutManager(layoutManager);
        listener = new AdapterEnd.RecyclerViewClickListener() {
            @Override
            public void onEndTripCardClick(View view, int position) {
                tripCustomerName = checkTripEndList.get(position).getVisitedCustomerName();
                tripCustomerAddress = checkTripEndList.get(position).getAddress();
                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerVisitEndActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("End Trip?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                entryEndTrip();
                                onRestart();
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

    public void refresh(View view) {          //refresh is onClick name given to the button
        onRestart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getCheckEndTrip();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCheckEndTrip();
    }

    private void entryEndTrip() {
        final String STEmp_ID1 = employeeID;
        final String customerName = tripCustomerName;
        final String customerAddress = tripCustomerAddress;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TripModel> empIdDesignationModelCall = apiInterface.updateEndtripDateEntry(END_TRIP_ENTRY, STEmp_ID1, customerName, customerAddress);
        empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
            @Override
            public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                assert response.body() != null;
                String value = response.body().getValue();
                String message = response.body().getMassage();
                switch (value) {
                    case "1":
                    case "0":
                        Toast.makeText(CustomerVisitEndActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<TripModel> call, Throwable t) {
                Toast.makeText(CustomerVisitEndActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCheckEndTrip() {
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
                Toast.makeText(CustomerVisitEndActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
