package com.example.manishaagro;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manishaagro.model.DailyEmpExpenseModel;
import com.example.manishaagro.model.TripModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CloseActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressBar progressBar;
    public CloaseAdapter cloaseAdapter;
    private List<DailyEmpExpenseModel> meterModels = new ArrayList<>();
    CloaseAdapter.RecyclerViewClickListener listener;
    private RecyclerView recyclerView;
    Toolbar meterReadToolbar;
    ConnectionDetector connectionDetector;
    Button submitReading;
    EditText editTextReadingEnd, editRoute;
    RelativeLayout endRelative;
    int read = 0;
    int startMeter = 0;
    String meterReadStart = "";
    public String employeeID = "";
    public ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close);
        connectionDetector = new ConnectionDetector();
        meterReadToolbar = findViewById(R.id.toolbarMeter);
        progressBar = findViewById(R.id.progress);
        setSupportActionBar(meterReadToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        editRoute = findViewById(R.id.editTextRoutes);
        editTextReadingEnd = findViewById(R.id.editTextEndMeter);
        endRelative = findViewById(R.id.textmeterendread);
        submitReading = findViewById(R.id.readingSubmit);
        recyclerView = findViewById(R.id.closeRecyclerview);
        Intent intent = getIntent();
        employeeID = intent.getStringExtra("EmployeeClosingMeterEntry");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CloseActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DailyEmpExpenseModel> meterModelCall = apiInterface.checkUpdateEndReadEntry("getStart@MeterReadToend", employeeID);
        meterModelCall.enqueue(new Callback<DailyEmpExpenseModel>() {
            @Override
            public void onResponse(Call<DailyEmpExpenseModel> call, Response<DailyEmpExpenseModel> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    //  Toast.makeText(CloseActivity.this,message,Toast.LENGTH_LONG).show();
                    String startdate = response.body().getStardate();
                    startMeter = response.body().getStartopening_km();
                    Log.v("statRead", "valfromserver" + startMeter);
                    checkOpening();
                } else if (value.equals("0")) {
                    Toast.makeText(CloseActivity.this, message, Toast.LENGTH_LONG).show();
                    endRelative.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<DailyEmpExpenseModel> call, Throwable t) {
                if (connectionDetector.isConnected(CloseActivity.this)) {
                    Toast.makeText(CloseActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(CloseActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        Log.v("statReadval1", "statrReadval" + startMeter);
        submitReading.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllCloseRead();
    }

    private void getAllCloseRead() {
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
                if (connectionDetector.isConnected(CloseActivity.this)) {
                    Toast.makeText(CloseActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CloseActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkOpening() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DailyEmpExpenseModel> meterModelCall1 = apiInterface.checkUpdateEndReadEntry("CheckEnd@entryMeterRead", employeeID);
        meterModelCall1.enqueue(new Callback<DailyEmpExpenseModel>() {
            @Override
            public void onResponse(Call<DailyEmpExpenseModel> call, Response<DailyEmpExpenseModel> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                if (value.equals("1")) {
                    endRelative.setVisibility(View.GONE);
                    //  Toast.makeText(CloseActivity.this,message,Toast.LENGTH_LONG).show();
                } else if (value.equals("0")) {
                    endRelative.setVisibility(View.VISIBLE);
                    Toast.makeText(CloseActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DailyEmpExpenseModel> call, Throwable t) {
                if (connectionDetector.isConnected(CloseActivity.this)) {
                    Toast.makeText(CloseActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(CloseActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.readingSubmit) {

            if (endRelative.getVisibility() == View.VISIBLE) {
                if (connectionDetector.isConnected(CloseActivity.this)) {
                    try {
                        File myObj = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/VisitDataDir.txt");
                        Scanner myReader = new Scanner(myObj);
                        final String data = myReader.nextLine();
                        while (myReader.hasNextLine()) {
                            final String data = myReader.nextLine();
                            final String[] columnData = data.split(",");
                            String str1 = columnData[0];
                            String str2 = columnData[1];
                            String str3 = columnData[2];
                            String str4 = columnData[3];
                            String str5 = columnData[4];
                            String str6 = columnData[5];
                            String str7 = columnData[6];
                            String str8 = columnData[7];
                            String str9 = columnData[8];
                            String str10 = columnData[9];
                            String str11 = columnData[10];
                            visitEntry(str1, str2, str3, str4, str5, str6, str7, str8, str9, str10);
                        }
                        myReader.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                    submitReadingEnd();
                } else {
                    Toast.makeText(CloseActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }

            }
        }
    }


    private void submitReadingEnd() {
        progressBar.setVisibility(View.VISIBLE);
        meterReadStart = editTextReadingEnd.getText().toString().trim();
        final String strRoute = editRoute.getText().toString().trim();
        read = Integer.parseInt(meterReadStart);


        try {
            // int num = Integer.parseInt(read);
            read = Integer.parseInt(meterReadStart);
            Log.v("T1", "check1" + employeeID);
            Log.v("T2", "check2" + read);
            Log.v("T3", "check3" + startMeter);
            if (startMeter < read) {
                if (strRoute.equals("")) {

                    Toast.makeText(CloseActivity.this, "Enter Route", Toast.LENGTH_SHORT).show();

                } else {
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                    Call<DailyEmpExpenseModel> meterModelCall = apiInterface.UpdateEndReadEntry("End@entryMeterRead", employeeID, read, strRoute);
                    meterModelCall.enqueue(new Callback<DailyEmpExpenseModel>() {
                        @Override
                        public void onResponse(Call<DailyEmpExpenseModel> call, Response<DailyEmpExpenseModel> response) {
                            String value = response.body().getValue();
                            String message = response.body().getMessage();
                            if (value.equals("1")) {
                                // Toast.makeText(CloseActivity.this,message,Toast.LENGTH_LONG).show();
                                editTextReadingEnd.setText("");
                                editRoute.setText("");
                                endRelative.setVisibility(View.GONE);
                                finish();

                            } else if (value.equals("0")) {
                                Toast.makeText(CloseActivity.this, message, Toast.LENGTH_LONG).show();
                            } else if (value.equals("2")) {
                                editTextReadingEnd.setText("");
                                endRelative.setVisibility(View.GONE);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<DailyEmpExpenseModel> call, Throwable t) {

                            if (connectionDetector.isConnected(CloseActivity.this)) {
                                Toast.makeText(CloseActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(CloseActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                Log.i("", read + " is a number");
            } else {
                Toast.makeText(CloseActivity.this, "Enter Proper Reading", Toast.LENGTH_LONG).show();
            }

        } catch (NumberFormatException e) {
            Log.i("", read + " is not a number");
        }


    }

    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch (Exception e) {
                return -1;
            }
        } else return 0;
    }


    private void visitEntry(String str1, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        final String keystr = str1;
        final String keyempid = str2;
        final String farmerFullName = str3;
        final String farmerAddressText = str4;
        final String farmerContact = str8;
        final String farmerVillage = str5;
        final String farmerTaluka = str6;
        final String farmerDistrict = str7;
        double acreValue = ParseDouble(str9);
        final String farmerVisitPurpose = str10;


        Log.v("Check id emp", "emp id" + employeeID);


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TripModel> empIdDesignationModelCall = apiInterface.insertVisitedStartEntry(keystr, keyempid, farmerFullName, farmerAddressText, farmerVillage, farmerTaluka, farmerDistrict, farmerContact, acreValue, farmerVisitPurpose);
        empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
            @Override
            public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                assert response.body() != null;
                String value = response.body().getValue();
                String message = response.body().getMassage();
                if (value.equals("1")) {

                    Toast.makeText(CloseActivity.this, message, Toast.LENGTH_SHORT).show();
                } else if (value.equals("0")) {
                    //Toast.makeText(CustomerVisitStartActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TripModel> call, Throwable t) {
                if (connectionDetector.isConnected(CloseActivity.this)) {
                    if (t instanceof SocketTimeoutException) {
                        //Toast.makeText(CustomerVisitStartActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    } else if (t instanceof IOException) {
                        //Toast.makeText(CustomerVisitStartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        //Call was cancelled by user
                        if (call.isCanceled()) {
                            System.out.println("Call was cancelled forcefully");
                        } else {
                            System.out.println("Network Error :: " + t.getLocalizedMessage());
                        }
                    }
                } else {

                    Toast.makeText(CloseActivity.this, "No Internet Connection offline saved data", Toast.LENGTH_LONG).show();

                }
            }
        });


    }

}
