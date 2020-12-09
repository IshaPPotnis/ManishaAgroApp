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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.manishaagro.employee.AdapterEnd;
import com.example.manishaagro.employee.CustomerVisitEndActivity;
import com.example.manishaagro.employee.OpenAdapter;
import com.example.manishaagro.model.DailyEmpExpenseModel;
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
    MessageDialog messageDialog;
    Button submitReading;
    ProgressBar progressBar;
    EditText editTextReadingStart;
    public OpenAdapter openAdapter;
    private List<DailyEmpExpenseModel> meterModels=new ArrayList<>();
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
        messageDialog=new MessageDialog();
        meterReadToolbar = findViewById(R.id.toolbarMeter);
        progressBar=findViewById(R.id.progress);
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
        Call<List<DailyEmpExpenseModel>> listCall = apiInterface.getStartRead("get@AllEmpStartRead", employeeID);
        listCall.enqueue(new Callback<List<DailyEmpExpenseModel>>() {
            @Override
            public void onResponse(Call<List<DailyEmpExpenseModel>> call, Response<List<DailyEmpExpenseModel>> response) {

                meterModels = response.body();


                    Log.v("checkTripList", "empList" + meterModels);
                    openAdapter = new OpenAdapter(meterModels, OpeningActivity.this, listener);
                    recyclerView.setAdapter(openAdapter);
                    openAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<List<DailyEmpExpenseModel>> call, Throwable t) {
                if(connectionDetector.isConnected(OpeningActivity.this))
                {
                    messageDialog.msgDialog(OpeningActivity.this);
                  //  Toast.makeText(OpeningActivity.this,"Cannot Communicate to Server",Toast.LENGTH_LONG).show();
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
        Call<DailyEmpExpenseModel> meterModelCall1 = apiInterface.checkInsertStartReadEntry("CheckStart@entryMeterRead", employeeID);
        meterModelCall1.enqueue(new Callback<DailyEmpExpenseModel>() {
            @Override
            public void onResponse(Call<DailyEmpExpenseModel> call, Response<DailyEmpExpenseModel> response) {
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
            public void onFailure(Call<DailyEmpExpenseModel> call, Throwable t) {
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


    int ParseInteger(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Integer.parseInt(strNumber);
            } catch(Exception e) {
                return -1;
            }
        }
        else return 0;
    }
    private void submitReadingStart()
    {   meterReadStart=editTextReadingStart.getText().toString().trim();
        read= ParseInteger(meterReadStart);
        if(read==0)
        {
            Toast.makeText(OpeningActivity.this,"Enter Start Kilometer Reading",Toast.LENGTH_SHORT).show();
        }
        else
        {
            progressBar.setVisibility(View.VISIBLE);
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<DailyEmpExpenseModel> meterModelCall = apiInterface.InsertStartReadEntry("Star@entryMeterRead", employeeID,read);
            meterModelCall.enqueue(new Callback<DailyEmpExpenseModel>() {
                @Override
                public void onResponse(Call<DailyEmpExpenseModel> call, Response<DailyEmpExpenseModel> response) {

                    String value=response.body().getValue();
                    String message=response.body().getMessage();
                    if (value.equals("1"))
                    {  // progressBar.setVisibility(View.VISIBLE);
                        //   Toast.makeText(OpeningActivity.this,message,Toast.LENGTH_LONG).show();
                        editTextReadingStart.setText("");
                        startRelative.setVisibility(View.GONE);
                        //getStartRead();
                        finish();


                    }
                    else if(value.equals("0"))
                    {

                        //Toast.makeText(OpeningActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<DailyEmpExpenseModel> call, Throwable t) {

                    if(connectionDetector.isConnected(OpeningActivity.this))
                    {
                        messageDialog.msgDialog(OpeningActivity.this);
                        //Toast.makeText(OpeningActivity.this,"Cannot Communicate to Server",Toast.LENGTH_LONG).show();


                    }
                    else
                    {
                        Toast.makeText(OpeningActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }




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
