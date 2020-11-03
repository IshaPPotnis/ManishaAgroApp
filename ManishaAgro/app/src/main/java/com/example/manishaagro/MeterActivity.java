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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.manishaagro.model.DealerModel;
import com.example.manishaagro.model.MeterModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeterActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar meterReadToolbar;
    ConnectionDetector connectionDetector;
    Button submitReading;
    EditText editTextReadingStart,editTextReadingEnd;
    RelativeLayout endRelative,startRelative;
    int read=0;
    String meterReadStart="";
    String meterReadEnd="";
    public String employeeID = "";
    public ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter);
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
        editTextReadingEnd=findViewById(R.id.editTextEndMeter);
        endRelative=findViewById(R.id.textmeterendread);
        startRelative=findViewById(R.id.textmeterstartread);
        submitReading=findViewById(R.id.meterRedingSubmit);

        Intent intent = getIntent();
        employeeID = intent.getStringExtra("visitedEmployeeMeterEntry");

        submitReading.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.meterRedingSubmit)
        {
            if (startRelative.getVisibility() == View.VISIBLE && endRelative.getVisibility()==View.GONE)
            {
                if(connectionDetector.isConnected(MeterActivity.this))
                {submitReadingStart();

                }
                else
                {
                    Toast.makeText(MeterActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }

            }
            if (endRelative.getVisibility()==View.VISIBLE && startRelative.getVisibility()==View.GONE)
            {
                if(connectionDetector.isConnected(MeterActivity.this))
                {
                    submitReadingEnd();
                }
                else
                {
                    Toast.makeText(MeterActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }

            }

        }

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
                    Toast.makeText(MeterActivity.this,message,Toast.LENGTH_LONG).show();
                    editTextReadingStart.setText("");
                    startRelative.setVisibility(View.GONE);
                    endRelative.setVisibility(View.VISIBLE);
                }
                else if(value.equals("0"))
                {
                    Toast.makeText(MeterActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MeterModel> call, Throwable t) {

                if(connectionDetector.isConnected(MeterActivity.this))
                {
                    Toast.makeText(MeterActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MeterActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    private void submitReadingEnd()
    {

        meterReadEnd=editTextReadingEnd.getText().toString().trim();
        read= Integer.parseInt(meterReadEnd);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MeterModel> meterModelCall = apiInterface.UpdateEndReadEntry("Ends@entryMeterRead", employeeID,read);
        meterModelCall.enqueue(new Callback<MeterModel>() {
            @Override
            public void onResponse(Call<MeterModel> call, Response<MeterModel> response) {
                String value=response.body().getValue();
                String message=response.body().getMessage();
                if (value.equals("1"))
                {
                    editTextReadingEnd.setText("");
                    endRelative.setVisibility(View.GONE);
                    startRelative.setVisibility(View.VISIBLE);
                    Toast.makeText(MeterActivity.this,message,Toast.LENGTH_LONG).show();
                }
                else if(value.equals("0"))
                {
                    Toast.makeText(MeterActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MeterModel> call, Throwable t) {

                if(connectionDetector.isConnected(MeterActivity.this))
                {
                    Toast.makeText(MeterActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MeterActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
