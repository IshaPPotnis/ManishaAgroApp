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

import com.example.manishaagro.model.DealerModel;
import com.example.manishaagro.model.MeterModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeterActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar meterReadToolbar;
    ConnectionDetector connectionDetector;
    ImageView openImage,closeImage;

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
        openImage.setOnClickListener(this);
        closeImage.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.openImage)
        {
            if(connectionDetector.isConnected(MeterActivity.this))
            {

                Intent visitIntent = new Intent(MeterActivity.this, OpeningActivity.class);
                visitIntent.putExtra("EmployeeOpeningMeterEntry", employeeID);
                startActivity(visitIntent);
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
            }
            else
            {
                Toast.makeText(MeterActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }
        }
    }
}
