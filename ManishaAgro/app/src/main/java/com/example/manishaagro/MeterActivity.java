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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    RelativeLayout placeRel;
    EditText editPlace;
    Button haltbutton;
    public int placeTD = 0;
    int haltval=0;
    CheckBox isHaltCheck;
    RadioGroup radioGroupPlace;
    RadioButton radioDistrict, radioTaluka;

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

        placeRel=findViewById(R.id.ImagRel5);
        openImage=findViewById(R.id.openImage);
        closeImage=findViewById(R.id.closeImage);
        reportImg=findViewById(R.id.ReportImage);
        haltbutton=findViewById(R.id.halt);
        isHaltCheck=findViewById(R.id.ischeckHalt);
        editPlace=findViewById(R.id.editTextPlaceName);
        radioGroupPlace=findViewById(R.id.radioDistrictTaluka);
        radioDistrict=findViewById(R.id.radioButtonDistrict);
        radioTaluka=findViewById(R.id.radioButtonTaluka);

        openImage.setOnClickListener(this);
        closeImage.setOnClickListener(this);
        reportImg.setOnClickListener(this);
        haltbutton.setOnClickListener(this);

        isHaltCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    placeRel.setVisibility(View.VISIBLE);
                }
                else
                {
                    placeRel.setVisibility(View.GONE);
                }
            }
        });

        radioGroupPlace.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if (checkedId == R.id.radioButtonDistrict)
                {
                    placeTD=1;
                }
                else if(checkedId == R.id.radioButtonTaluka)
                {
                    placeTD=2;
                }
            }
        });
    }




    @Override
    public void onClick(final View v) {
        if(v.getId()==R.id.halt)
        {

            if(connectionDetector.isConnected(MeterActivity.this))
            {
                if (isHaltCheck.isChecked())
                {
                    placeRel.setVisibility(View.VISIBLE);
                    haltval=1;
                    if (radioDistrict.isChecked() || radioTaluka.isChecked())
                    {
                        final String placenm=editPlace.getText().toString().trim();
                        if (placenm.equals(""))
                        {
                            Toast.makeText(MeterActivity.this,"Enter place name",Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            final int placeintval=placeTD;
                            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                            Call<DailyEmpExpenseModel> meterModelCall = apiInterface.UpdateHaltEntry("Update@DExpHalt",employeeID,haltval,placeintval,placenm);
                            meterModelCall.enqueue(new Callback<DailyEmpExpenseModel>() {
                                @Override
                                public void onResponse(Call<DailyEmpExpenseModel> call, Response<DailyEmpExpenseModel> response) {
                                    String value=response.body().getValue();
                                    String message=response.body().getMessage();
                                    if (value.equals("1"))
                                    {
                                        editPlace.setText("");
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
                    }
                    else
                    {
                        Toast.makeText(MeterActivity.this,"Select District or Taluka option",Toast.LENGTH_SHORT).show();
                    }


                }
                else
                {
                  haltval=0;
                    placeRel.setVisibility(View.GONE);
                }




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
                finish();
            }
            else
            {
                Toast.makeText(MeterActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }
        }


    }
}
