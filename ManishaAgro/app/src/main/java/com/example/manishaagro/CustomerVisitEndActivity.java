package com.example.manishaagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import com.example.manishaagro.model.TripModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.EMPLOYEE_VISITED_CUSTOMER;

public class CustomerVisitEndActivity extends AppCompatActivity {
    Toolbar visitEndToolbar;
    public ApiInterface apiInterface;
    public AdapterEnd adapterEnd;
    private RecyclerView recyclerViewEndTrip;
    private List<TripModel> checkTripEndList;
    String employeeID="";
    AdapterEnd.RecyclerViewClickListener listener;

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


    }

    @Override
    protected void onResume() {
        super.onResume();
        getCheckEndTrip();

    }
    private void getCheckEndTrip()
    {
        final String STEmp_ID1 = employeeID;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("CodeIncome", "user1" + employeeID);
        Call<List<TripModel>> listCall = apiInterface.checkAndGetEndTrip("Check@ReturnEndTrip", STEmp_ID1);
        listCall.enqueue(new Callback<List<TripModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TripModel>> call, @NonNull Response<List<TripModel>> response) {
                checkTripEndList = response.body();
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
