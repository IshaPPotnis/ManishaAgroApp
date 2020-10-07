package com.example.manishaagro;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.manishaagro.model.ProfileModel;
import com.example.manishaagro.model.TripModel;
import com.example.manishaagro.utils.EmployeeType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.R.id.VisitStartSubmit;
import static com.example.manishaagro.R.id.cirLoginButton;
import static com.example.manishaagro.utils.Constants.EMPI_USER;
import static com.example.manishaagro.utils.Constants.LOGIN_EMPLOYEE;
import static com.example.manishaagro.utils.Constants.LOGIN_MANAGER;
import static com.example.manishaagro.utils.Constants.PASSING_DATA;
import static com.example.manishaagro.utils.Constants.VISITED_CUSTOMER_ENTRY;

public class CustomeVisitStartActivity extends AppCompatActivity implements View.OnClickListener {
    ApiInterface apiInterface;
    Toolbar visitStartToolbar;
    EditText editTextFarmerName,editTextFarmerAddress;
    Button visitEntrySubmit;
    String employeeID="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custome_visit_start);
        visitStartToolbar=findViewById(R.id.toolbarvisit);
        setSupportActionBar(visitStartToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }

        editTextFarmerName=findViewById(R.id.editTextFarmerName);
        editTextFarmerAddress=findViewById(R.id.editTextFarmerAddress);
        visitEntrySubmit=findViewById(R.id.VisitStartSubmit);

        Intent intent = getIntent();
        employeeID = intent.getStringExtra("visitedEmployee");

        visitEntrySubmit.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if (v.getId() == VisitStartSubmit) {
            visitEntry();
        }

    }

    private void visitEntry()
    {
        final String farmerNameText = editTextFarmerName.getText().toString().trim();
        final String farmerAddressText = editTextFarmerAddress.getText().toString().trim();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TripModel> empIdDesignationModelCall = apiInterface.insertVisitedStartEntry(VISITED_CUSTOMER_ENTRY, employeeID,farmerNameText,farmerAddressText);
        empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
            @Override
            public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                String value = response.body().getValue();
                String message = response.body().getMassage();


                if(value.equals("1"))
                {
                    editTextFarmerName.setText("");
                    editTextFarmerAddress.setText("");
                    Toast.makeText(CustomeVisitStartActivity.this,message,Toast.LENGTH_SHORT);

                }
                else if(value.equals("0"))
                {
                    Toast.makeText(CustomeVisitStartActivity.this,message,Toast.LENGTH_SHORT);
                }


            }

            @Override
            public void onFailure(Call<TripModel> call, Throwable t) {
                Toast.makeText(CustomeVisitStartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
