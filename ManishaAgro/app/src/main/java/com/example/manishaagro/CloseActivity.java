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
import com.example.manishaagro.model.MeterModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CloseActivity extends AppCompatActivity implements View.OnClickListener {

    public CloaseAdapter cloaseAdapter;
    private List<MeterModel> meterModels=new ArrayList<>();
    CloaseAdapter.RecyclerViewClickListener listener;
    private RecyclerView recyclerView;

    Toolbar meterReadToolbar;
    ConnectionDetector connectionDetector;
    Button submitReading;
    EditText editTextReadingEnd;
    RelativeLayout endRelative;
    int read=0;
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

        checkOpening();
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
        Call<List<MeterModel>> listCall = apiInterface.getCloseRead("get@AllEmpCloseRead", employeeID);
        listCall.enqueue(new Callback<List<MeterModel>>() {
            @Override
            public void onResponse(Call<List<MeterModel>> call, Response<List<MeterModel>> response) {

                meterModels = response.body();


                Log.v("checkTripList", "empList" + meterModels);
                cloaseAdapter = new CloaseAdapter(meterModels, CloseActivity.this, listener);
                recyclerView.setAdapter(cloaseAdapter);
                cloaseAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<List<MeterModel>> call, Throwable t) {
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
        Call<MeterModel> meterModelCall1 = apiInterface.checkUpdateEndReadEntry("CheckEnd@entryMeterRead", employeeID);
        meterModelCall1.enqueue(new Callback<MeterModel>() {
            @Override
            public void onResponse(Call<MeterModel> call, Response<MeterModel> response) {
                String value=response.body().getValue();
                String message=response.body().getMessage();
                if(value.equals("1"))
                {
                    endRelative.setVisibility(View.GONE);
                    Toast.makeText(CloseActivity.this,message,Toast.LENGTH_LONG).show();
                }
                else if(value.equals("0"))
                {endRelative.setVisibility(View.VISIBLE);
                    Toast.makeText(CloseActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MeterModel> call, Throwable t) {
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
                {   submitReadingEnd();
                    getAllCloseRead();

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
        Log.v("T3", "Follow" + employeeID);
        Log.v("T3", "Follow" + read);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MeterModel> meterModelCall = apiInterface.UpdateEndReadEntry("End@entryMeterRead",employeeID,read);
        meterModelCall.enqueue(new Callback<MeterModel>() {
            @Override
            public void onResponse(Call<MeterModel> call, Response<MeterModel> response) {
                String value=response.body().getValue();
                String message=response.body().getMessage();
                if (value.equals("1"))
                {
                    Toast.makeText(CloseActivity.this,message,Toast.LENGTH_LONG).show();
                    editTextReadingEnd.setText("");
                    endRelative.setVisibility(View.GONE);

                }
                else if(value.equals("0"))
                {
                    Toast.makeText(CloseActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MeterModel> call, Throwable t) {

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
}
