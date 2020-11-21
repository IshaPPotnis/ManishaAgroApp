package com.example.manishaagro.employee;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.manishaagro.ApiClient;
import com.example.manishaagro.ApiInterface;
import com.example.manishaagro.R;
import com.example.manishaagro.model.DealerModel;
import com.example.manishaagro.model.ProductModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerEntryActivity extends AppCompatActivity {
    Toolbar dealerToolbar;
    Button goToProductActButton;
    EditText editTextDealerName;
    EditText editTextDealerPurpose;

    public String employeeID = "";
    public ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_entry);
        dealerToolbar = findViewById(R.id.toolbarDealer);
        setSupportActionBar(dealerToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }


        editTextDealerName = findViewById(R.id.editTextDealerName);
        editTextDealerPurpose=findViewById(R.id.editTextDealerNamePurpose);
        goToProductActButton=findViewById(R.id.gotoProdctAct);



        Intent intent = getIntent();
        employeeID = intent.getStringExtra("visitedEmployeeDealerEntry");





goToProductActButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        String dealersName=editTextDealerName.getText().toString().trim();
        String dealerpurpose=editTextDealerPurpose.getText().toString().trim();
        if(dealersName.equals("")||dealerpurpose.equals(""))
        {
            Toast.makeText(DealerEntryActivity.this,"Enter Dealer Name",Toast.LENGTH_LONG).show();
        }
        else
        {


            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<DealerModel> empIdDesignationModelCall = apiInterface.insertDealerEntry("dealerEntry@Emp_id", employeeID,dealersName,dealerpurpose);
            empIdDesignationModelCall.enqueue(new Callback<DealerModel>() {
                @Override
                public void onResponse(Call<DealerModel> call, Response<DealerModel> response) {
                    assert response.body() != null;
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    String dealerid= String.valueOf(response.body().getDealer_id());

                    if (value.equals("1"))
                    {

                        Intent intentadpEmp = new Intent(DealerEntryActivity.this, ProductActivity.class);
                        intentadpEmp.putExtra("EmployeeIdInDealer",employeeID);
                        intentadpEmp.putExtra("DealerNameDealerAct",dealerid);
                        intentadpEmp.putExtra("EmpID&DealerNAME", "EmpID&Dealer");
                        startActivity(intentadpEmp);
                        finish();
                      //  Toast.makeText(DealerEntryActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                    else if(value.equals("0"))
                    {
                        Toast.makeText(DealerEntryActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<DealerModel> call, Throwable t) {
                    Toast.makeText(DealerEntryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
});

    }





}
