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
import android.widget.TextView;
import android.widget.Toast;

import com.example.manishaagro.model.TripModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.END_TRIP_ENTRY;
import static com.example.manishaagro.utils.Constants.GET_VISITED_DETAILS_EYLPME;
import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_RETURN;
import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_TRAVEL;
import static com.example.manishaagro.utils.Constants.STATUS_EMPLOYEE_VISITED_CUSTOMER;
import static com.example.manishaagro.utils.Constants.STATUS_VISITED_CUSTOMER_NAME;

public class EmployeeVisitDetailsToMgrActivity extends AppCompatActivity {
    Toolbar empDetailTool;
    AdapterEmployeeDetails.RecyclerViewClickListener listener;
    public ApiInterface apiInterface;
    private RecyclerView recyclerEmpDtl;
    private List<TripModel> EmpVisitList;
    public AdapterEmployeeDetails adapterEmployeeDetails;
    String EmpRefId="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_visit_details_to_mgr);

        empDetailTool=findViewById(R.id.toolbarEmpDetail);


        setSupportActionBar(empDetailTool);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }



        Intent intent = getIntent();
        String keyForCompare = intent.getStringExtra("EmpIDNAME");
        Log.v("yek", "keyyy" + keyForCompare);
        if (keyForCompare != null && keyForCompare.equals("EmployeeIDNamePassed")) {
            String name = intent.getStringExtra("EmployeeId");
             EmpRefId=name;
            getVisiteDetailOfEmployee();

        }

        recyclerEmpDtl=findViewById(R.id.EmployeeDetails);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EmployeeVisitDetailsToMgrActivity.this);
        recyclerEmpDtl.setLayoutManager(layoutManager);




    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void  getVisiteDetailOfEmployee()
    {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("checkTrip", "emp1" + EmpRefId);
        Call<List<TripModel>> listCall = apiInterface.getAllVisit(GET_VISITED_DETAILS_EYLPME,EmpRefId);
        listCall.enqueue(new Callback<List<TripModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TripModel>> call, @NonNull Response<List<TripModel>> response) {
                EmpVisitList = response.body();
                Log.v("checkTripList", "empList" + EmpVisitList);
                adapterEmployeeDetails = new AdapterEmployeeDetails(EmpVisitList, EmployeeVisitDetailsToMgrActivity.this, listener);
                recyclerEmpDtl.setAdapter(adapterEmployeeDetails);
                adapterEmployeeDetails.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<TripModel>> call, @NonNull Throwable t) {
            }
        });


    }
}
