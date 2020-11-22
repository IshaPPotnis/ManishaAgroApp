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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.manishaagro.model.DailyEmpExpenseModel;
import com.example.manishaagro.model.DealerModel;
import com.example.manishaagro.model.MeterModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeterActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar meterReadToolbar;
    ConnectionDetector connectionDetector;
    ImageView openImage,closeImage,reportImg;
    Button haltbutton;

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
        Intent intent = getIntent();
        employeeID = intent.getStringExtra("visitedEmployeeMeterEntry");
        openImage=findViewById(R.id.openImage);
        closeImage=findViewById(R.id.closeImage);
        reportImg=findViewById(R.id.ReportImage);
        haltbutton=findViewById(R.id.halt);
        openImage.setOnClickListener(this);
        closeImage.setOnClickListener(this);
        reportImg.setOnClickListener(this);
        haltbutton.setOnClickListener(this);
    }


    @Override
    public void onClick(final View v) {
        if(v.getId()==R.id.halt)
        {
            if(connectionDetector.isConnected(MeterActivity.this))
            {
                int haltval=1;
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                Call<DailyEmpExpenseModel> meterModelCall = apiInterface.UpdateHaltEntry("Update@DExpHalt",employeeID,haltval);
                meterModelCall.enqueue(new Callback<DailyEmpExpenseModel>() {
                    @Override
                    public void onResponse(Call<DailyEmpExpenseModel> call, Response<DailyEmpExpenseModel> response) {
                        String value=response.body().getValue();
                        String message=response.body().getMessage();
                        if (value.equals("1"))
                        {
                            Toast.makeText(MeterActivity.this,message,Toast.LENGTH_SHORT).show();
                        }
                        else if(value.equals("0"))
                        {
                            Toast.makeText(MeterActivity.this,message,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DailyEmpExpenseModel> call, Throwable t) {

                    }
                });


            }
            else
            {
                Toast.makeText(MeterActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }

        }
        if (v.getId()==R.id.openImage)
        {
            if(connectionDetector.isConnected(MeterActivity.this))
            {

                Intent visitIntent = new Intent(MeterActivity.this, OpeningActivity.class);
                visitIntent.putExtra("EmployeeOpeningMeterEntry", employeeID);
                startActivity(visitIntent);
                finish();
            }
            else
            {
                Toast.makeText(MeterActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }

        }
        if(v.getId()==R.id.closeImage)
        {
            if(connectionDetector.isConnected(MeterActivity.this))
            {

                Intent visitIntent = new Intent(MeterActivity.this, CloseActivity.class);
                visitIntent.putExtra("EmployeeClosingMeterEntry", employeeID);
                startActivity(visitIntent);
                finish();
            }
            else
            {
                Toast.makeText(MeterActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }
        }


        if(v.getId()==R.id.ReportImage)
        {
            if(connectionDetector.isConnected(MeterActivity.this))
            {

                Intent visitIntent = new Intent(MeterActivity.this, ExpenseReportActivity.class);
                visitIntent.putExtra("EmployeeExpenseRptMeterEntry", employeeID);
                startActivity(visitIntent);
            }
            else
            {
                Toast.makeText(MeterActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }
        }


    }
}
