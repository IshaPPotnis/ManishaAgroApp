package com.example.manishaagro;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.manishaagro.model.ProfileModel;
import com.example.manishaagro.model.TripModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.EMPLOYEE_PROFILE;
import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_RETURN;
import static com.example.manishaagro.utils.Constants.STATUS_DATE_OF_TRAVEL;
import static com.example.manishaagro.utils.Constants.STATUS_EMPLOYEE_VISITED_CUSTOMER;
import static com.example.manishaagro.utils.Constants.STATUS_VISITED_CUSTOMER_NAME;

public class EmployeeStatusActivity extends AppCompatActivity {
    public ApiInterface apiInterface;
    Toolbar visitDemoDetailsToolbar;
    String employeeID="",name="",dateOfTravel="",dateOfReturn="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_status);

        visitDemoDetailsToolbar = findViewById(R.id.toolbarVisitedDetailDemo);
        setSupportActionBar(visitDemoDetailsToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }



        Intent intent = getIntent();
        String keyForCompare = intent.getStringExtra("EmpStsVal");
        Log.v("yek", "keyyy" + keyForCompare);
        if (keyForCompare != null && keyForCompare.equals(STATUS_EMPLOYEE_VISITED_CUSTOMER)) {
             name = intent.getStringExtra(STATUS_VISITED_CUSTOMER_NAME);
             dateOfTravel = intent.getStringExtra(STATUS_DATE_OF_TRAVEL);
             dateOfReturn = intent.getStringExtra(STATUS_DATE_OF_RETURN);
            employeeID=intent.getStringExtra("EMPLOYEE_ID_STATUS");

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getVisitedCustomerDetailsofStatusFrg();
    }
    private void getVisitedCustomerDetailsofStatusFrg()
    {
        Log.v("yek", "empid" + employeeID);
        Log.v("yek", "name" + name);
        Log.v("yek", "dateoftravel" + dateOfTravel);
        Log.v("yek", "dateofreturn" + dateOfReturn);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TripModel> visitedDetailsCalls = apiInterface.getCustomerVisitedDetailsOfEmp("Get@allDetailsOfVisitedCust", employeeID,name,dateOfTravel,dateOfReturn);
        visitedDetailsCalls.enqueue(new Callback<TripModel>() {
            @Override
            public void onResponse(Call<TripModel> call, Response<TripModel> response) {

            }

            @Override
            public void onFailure(Call<TripModel> call, Throwable t) {

            }
        });
    }
}
