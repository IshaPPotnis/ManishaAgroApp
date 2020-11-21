package com.example.manishaagro;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.manishaagro.employee.AdapterEnd;
import com.example.manishaagro.employee.CustomerVisitEndActivity;
import com.example.manishaagro.employee.OpenAdapter;
import com.example.manishaagro.model.MeterModel;
import com.example.manishaagro.model.TripModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpeningActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar meterReadToolbar;
    ConnectionDetector connectionDetector;
    Button submitReading;
    EditText editTextReadingStart;
    public OpenAdapter openAdapter;
    private List<MeterModel> meterModels=new ArrayList<>();
  OpenAdapter.RecyclerViewClickListener listener;
    RelativeLayout startRelative;
    int read=0;
    String meterReadStart="";
    private RecyclerView recyclerView;

    public String employeeID = "";
    public ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        connectionDetector=new ConnectionDetector();
        meterReadToolbar = findViewById(R.id.toolbarMeter);
        setSupportActionBar(meterReadToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        editTextReadingStart=findViewById(R.id.editTextStartMeter);
        startRelative=findViewById(R.id.textmeterstartread);
        submitReading=findViewById(R.id.meterRedingSubmit);
        recyclerView=findViewById(R.id.startReadRecyclerview);

        Intent intent = getIntent();
        employeeID = intent.getStringExtra("EmployeeOpeningMeterEntry");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OpeningActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        checkOpening();
        submitReading.setOnClickListener(this);
        getStartRead();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStartRead();
    }


    private void getStartRead()
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("checkTrip", "emp1" + employeeID);
        Call<List<MeterModel>> listCall = apiInterface.getStartRead("get@AllEmpStartRead", employeeID);
        listCall.enqueue(new Callback<List<MeterModel>>() {
            @Override
            public void onResponse(Call<List<MeterModel>> call, Response<List<MeterModel>> response) {

                meterModels = response.body();


                    Log.v("checkTripList", "empList" + meterModels);
                    openAdapter = new OpenAdapter(meterModels, OpeningActivity.this, listener);
                    recyclerView.setAdapter(openAdapter);
                    openAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<List<MeterModel>> call, Throwable t) {
                if(connectionDetector.isConnected(OpeningActivity.this))
                {
                    //Toast.makeText(OpeningActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(OpeningActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkOpening()
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MeterModel> meterModelCall1 = apiInterface.checkInsertStartReadEntry("CheckStart@entryMeterRead", employeeID);
        meterModelCall1.enqueue(new Callback<MeterModel>() {
            @Override
            public void onResponse(Call<MeterModel> call, Response<MeterModel> response) {
                String value=response.body().getValue();
                String message=response.body().getMessage();
                if(value.equals("1"))
                {
                    startRelative.setVisibility(View.GONE);
                 //   Toast.makeText(OpeningActivity.this,message,Toast.LENGTH_LONG).show();
                }
                else if(value.equals("0"))
                {startRelative.setVisibility(View.VISIBLE);
                    //Toast.makeText(OpeningActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MeterModel> call, Throwable t) {
                if(connectionDetector.isConnected(OpeningActivity.this))
                {
                    //Toast.makeText(OpeningActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(OpeningActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private void submitReadingStart()
    {   meterReadStart=editTextReadingStart.getText().toString().trim();
        read= Integer.parseInt(meterReadStart);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MeterModel> meterModelCall = apiInterface.InsertStartReadEntry("Star@entryMeterRead", employeeID,read);
        meterModelCall.enqueue(new Callback<MeterModel>() {
            @Override
            public void onResponse(Call<MeterModel> call, Response<MeterModel> response) {
                String value=response.body().getValue();
                String message=response.body().getMessage();
                if (value.equals("1"))
                {
                 //   Toast.makeText(OpeningActivity.this,message,Toast.LENGTH_LONG).show();
                    editTextReadingStart.setText("");
                    startRelative.setVisibility(View.GONE);
                    getStartRead();

                }
                else if(value.equals("0"))
                {
                    //Toast.makeText(OpeningActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MeterModel> call, Throwable t) {

                if(connectionDetector.isConnected(OpeningActivity.this))
                { //Toast.makeText(OpeningActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(OpeningActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.meterRedingSubmit)
        {
            if (startRelative.getVisibility() == View.VISIBLE)
            {
                if(connectionDetector.isConnected(OpeningActivity.this))
                {
                    submitReadingStart();

                }
                else
                {
                    Toast.makeText(OpeningActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }

            }


        }

    }
}
