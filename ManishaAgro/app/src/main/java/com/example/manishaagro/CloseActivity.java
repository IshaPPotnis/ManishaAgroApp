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

import com.example.manishaagro.employee.OpenAdapter;
import com.example.manishaagro.model.DailyEmpExpenseModel;
import com.example.manishaagro.model.MeterModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CloseActivity extends AppCompatActivity implements View.OnClickListener {

    public CloaseAdapter cloaseAdapter;
    private List<DailyEmpExpenseModel> meterModels=new ArrayList<>();
    CloaseAdapter.RecyclerViewClickListener listener;
    private RecyclerView recyclerView;

    Toolbar meterReadToolbar;
    ConnectionDetector connectionDetector;
    Button submitReading;
    EditText editTextReadingEnd;
    RelativeLayout endRelative;
    int read=0;
    int startmeter=0;
    String meterReadStart="";

    public String employeeID = "";
    public ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close);
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
        editTextReadingEnd=findViewById(R.id.editTextEndMeter);
        endRelative=findViewById(R.id.textmeterendread);
        submitReading=findViewById(R.id.readingSubmit);
        recyclerView=findViewById(R.id.closeRecyclerview);
        Intent intent = getIntent();
        employeeID = intent.getStringExtra("EmployeeClosingMeterEntry");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CloseActivity.this);
        recyclerView.setLayoutManager(layoutManager);


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DailyEmpExpenseModel> meterModelCall = apiInterface.checkUpdateEndReadEntry("getStart@MeterReadToend",employeeID);
        meterModelCall.enqueue(new Callback<DailyEmpExpenseModel>() {
            @Override
            public void onResponse(Call<DailyEmpExpenseModel> call, Response<DailyEmpExpenseModel> response) {
                String value=response.body().getValue();
                String message=response.body().getMessage();

                if (value.equals("1"))
                {
                  //  Toast.makeText(CloseActivity.this,message,Toast.LENGTH_LONG).show();
                    String startdate=response.body().getStardate();
                     startmeter= response.body().getStartopening_km();
                    Log.v("statRead", "valfromserver" + startmeter);
                    checkOpening();


                }
                else if(value.equals("0"))
                {
                    Toast.makeText(CloseActivity.this,message,Toast.LENGTH_LONG).show();
                    endRelative.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<DailyEmpExpenseModel> call, Throwable t) {

                if(connectionDetector.isConnected(CloseActivity.this))
                { Toast.makeText(CloseActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(CloseActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });

        Log.v("statReadval1", "statrReadval" + startmeter);
   /*     if (startmeter==0)
        {

        }
        else
        {

        }*/



        submitReading.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllCloseRead();
    }
    private void getAllCloseRead()
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("checkTrip", "emp1" + employeeID);
        Call<List<DailyEmpExpenseModel>> listCall = apiInterface.getCloseRead("get@AllEmpCloseRead", employeeID);
        listCall.enqueue(new Callback<List<DailyEmpExpenseModel>>() {
            @Override
            public void onResponse(Call<List<DailyEmpExpenseModel>> call, Response<List<DailyEmpExpenseModel>> response) {

                meterModels = response.body();


                Log.v("checkTripList", "empList" + meterModels);
                cloaseAdapter = new CloaseAdapter(meterModels, CloseActivity.this, listener);
                recyclerView.setAdapter(cloaseAdapter);
                cloaseAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<List<DailyEmpExpenseModel>> call, Throwable t) {
                if(connectionDetector.isConnected(CloseActivity.this))
                {
                    Toast.makeText(CloseActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(CloseActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkOpening()
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DailyEmpExpenseModel> meterModelCall1 = apiInterface.checkUpdateEndReadEntry("CheckEnd@entryMeterRead", employeeID);
        meterModelCall1.enqueue(new Callback<DailyEmpExpenseModel>() {
            @Override
            public void onResponse(Call<DailyEmpExpenseModel> call, Response<DailyEmpExpenseModel> response) {
                String value=response.body().getValue();
                String message=response.body().getMessage();
                if(value.equals("1"))
                {
                    endRelative.setVisibility(View.GONE);
                  //  Toast.makeText(CloseActivity.this,message,Toast.LENGTH_LONG).show();
                }
                else if(value.equals("0"))
                {endRelative.setVisibility(View.VISIBLE);
                    Toast.makeText(CloseActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DailyEmpExpenseModel> call, Throwable t) {
                if(connectionDetector.isConnected(CloseActivity.this))
                { Toast.makeText(CloseActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(CloseActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.readingSubmit)
        {

            if (endRelative.getVisibility() == View.VISIBLE)
            {
                if(connectionDetector.isConnected(CloseActivity.this))
                {

                    submitReadingEnd();


                }
                else
                {
                    Toast.makeText(CloseActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    private void submitReadingEnd()
    {   meterReadStart=editTextReadingEnd.getText().toString().trim();
        read= Integer.parseInt(meterReadStart);


        try {
           // int num = Integer.parseInt(read);
            read= Integer.parseInt(meterReadStart);
            Log.v("T1", "check1" + employeeID);
            Log.v("T2", "check2" + read);
            Log.v("T3", "check3" + startmeter);
            if(startmeter<read)
            {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                Call<DailyEmpExpenseModel> meterModelCall = apiInterface.UpdateEndReadEntry("End@entryMeterRead",employeeID,read);
                meterModelCall.enqueue(new Callback<DailyEmpExpenseModel>() {
                    @Override
                    public void onResponse(Call<DailyEmpExpenseModel> call, Response<DailyEmpExpenseModel> response) {
                        String value=response.body().getValue();
                        String message=response.body().getMessage();
                        if (value.equals("1"))
                        {
                            // Toast.makeText(CloseActivity.this,message,Toast.LENGTH_LONG).show();
                            editTextReadingEnd.setText("");
                            endRelative.setVisibility(View.GONE);
                            finish();

                        }
                        else if(value.equals("0"))
                        {
                            Toast.makeText(CloseActivity.this,message,Toast.LENGTH_LONG).show();
                        }
                        else if(value.equals("2"))
                        {
                            editTextReadingEnd.setText("");
                            endRelative.setVisibility(View.GONE);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<DailyEmpExpenseModel> call, Throwable t) {

                        if(connectionDetector.isConnected(CloseActivity.this))
                        { Toast.makeText(CloseActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

                        }
                        else
                        {
                            Toast.makeText(CloseActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                Log.i("",read+" is a number");
            }
            else
            {
                Toast.makeText(CloseActivity.this,"Enter Proper Reading",Toast.LENGTH_LONG).show();
            }

        } catch (NumberFormatException e) {
            Log.i("",read+" is not a number");
        }





    }



}
