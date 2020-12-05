package com.example.manishaagro;

import android.content.Context;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    public int intval = -1;
    public int intvaldemo = -1;
    public int intvalEnd = -1;
    EditText editTextReadingEnd, editRoute;
    RelativeLayout endRelative;
    Button sendButton;
    int read = 0;
    String tmpOfflineVisitData;
    String tmpOfflineDemoData = "";
    String tmpOfflineEndData = "";
    int startMeter = 0;
    String meterReadStart = "";
    public String employeeID = "";
    public ApiInterface apiInterface;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close);
        connectionDetector = new ConnectionDetector();
        meterReadToolbar = findViewById(R.id.toolbarMeter);
        progressBar = findViewById(R.id.progress);
        setSupportActionBar(meterReadToolbar);
        if (getSupportActionBar() != null) {
            final ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            final ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        sendButton = findViewById(R.id.sendofflinedata);
        editRoute = findViewById(R.id.editTextRoutes);
        editTextReadingEnd = findViewById(R.id.editTextEndMeter);
        endRelative = findViewById(R.id.textmeterendread);
        submitReading = findViewById(R.id.readingSubmit);
        recyclerView = findViewById(R.id.closeRecyclerview);

        final Intent intent = getIntent();
        employeeID = intent.getStringExtra("EmployeeClosingMeterEntry");
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CloseActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        final Call<DailyEmpExpenseModel> meterModelCall = apiInterface.checkUpdateEndReadEntry("getStart@MeterReadToend", employeeID);
        meterModelCall.enqueue(new Callback<DailyEmpExpenseModel>() {
            @Override
            public void onResponse(final Call<DailyEmpExpenseModel> call, final Response<DailyEmpExpenseModel> response) {
                final String value = response.body().getValue();
                final String message = response.body().getMessage();

                if (value.equals("1")) {
                    //  Toast.makeText(CloseActivity.this,message,Toast.LENGTH_LONG).show();
                    final String startdate = response.body().getStardate();
                    startMeter = response.body().getStartopening_km();
                    Log.v("statRead", "valfromserver" + startMeter);
                    checkOpening();
                } else if (value.equals("0")) {
                    Toast.makeText(CloseActivity.this, message, Toast.LENGTH_LONG).show();
                    endRelative.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(final Call<DailyEmpExpenseModel> call, final Throwable t) {
                if (connectionDetector.isConnected(CloseActivity.this)) {
                    Toast.makeText(CloseActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(CloseActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        Log.v("statReadval1", "statrReadval" + startMeter);
        sendButton.setOnClickListener(this);
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
        final Call<List<DailyEmpExpenseModel>> listCall = apiInterface.getCloseRead("get@AllEmpCloseRead", employeeID);
        listCall.enqueue(new Callback<List<DailyEmpExpenseModel>>() {
            @Override
            public void onResponse(final Call<List<DailyEmpExpenseModel>> call, final Response<List<DailyEmpExpenseModel>> response) {
                meterModels = response.body();
                Log.v("checkTripList", "empList" + meterModels);
                cloaseAdapter = new CloaseAdapter(meterModels, CloseActivity.this, listener);
                recyclerView.setAdapter(cloaseAdapter);
                cloaseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(final Call<List<DailyEmpExpenseModel>> call, final Throwable t) {
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
        final Call<DailyEmpExpenseModel> meterModelCall1 = apiInterface.checkUpdateEndReadEntry("CheckEnd@entryMeterRead", employeeID);
        meterModelCall1.enqueue(new Callback<DailyEmpExpenseModel>() {
            @Override
            public void onResponse(final Call<DailyEmpExpenseModel> call, final Response<DailyEmpExpenseModel> response) {
                final String value = response.body().getValue();
                final String message = response.body().getMessage();
                if (value.equals("1")) {
                    endRelative.setVisibility(View.GONE);
                    //  Toast.makeText(CloseActivity.this,message,Toast.LENGTH_LONG).show();
                } else if (value.equals("0")) {
                    endRelative.setVisibility(View.VISIBLE);
                    Toast.makeText(CloseActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(final Call<DailyEmpExpenseModel> call, final Throwable t) {
                if (connectionDetector.isConnected(CloseActivity.this)) {
                    Toast.makeText(CloseActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(CloseActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.readingSubmit) {

            if (endRelative.getVisibility() == View.VISIBLE) {
                if (connectionDetector.isConnected(CloseActivity.this)) {
                    final File myObjVisit = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/VisitDataDir.txt");
                    final File myObjDemo = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/VisitDemoEntryDir.txt");
                    final File myObjEnd = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/StoreForEndDir.txt");

                    if (myObjVisit.length() == 0 && myObjDemo.length() == 0 && myObjEnd.length() == 0) {
                        submitReadingEnd();
                    } else {
                        Toast.makeText(CloseActivity.this, "Send Offline Data", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(CloseActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }

            }
        }
        if (v.getId() == R.id.sendofflinedata) {
            //////////////visit entry offline
            try {
                final File myObj = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/VisitDataDir.txt");
                final Scanner myReader = new Scanner(myObj);
                tmpOfflineVisitData = "";
                StringBuilder stringBuilder = new StringBuilder(); // Added this to make it the string with all failure strings
                while (myReader.hasNextLine()) {
                    final String data = myReader.nextLine();
                    final String[] columnData = data.split(",");
                    for (final String s : columnData) {
                        System.out.println(" " + s + " ");
                    }
                    System.out.println();
                    final String str1 = columnData[0];
                    final String str2 = columnData[1];
                    final String str3 = columnData[2];
                    final String str4 = columnData[3];
                    final String str5 = columnData[4];
                    final String str6 = columnData[5];
                    final String str7 = columnData[6];
                    final String str8 = columnData[7];
                    final String str9 = columnData[8];
                    final String str10 = columnData[9];
                    final String str11 = columnData[10];
                    final String strId = str2.replace("\"", "");

                    System.out.println("1  :" + str1);
                    System.out.println("2  :" + str2);
                    System.out.println("3  :" + str3);
                    System.out.println("4  :" + str4);
                    System.out.println("5  :" + str5);
                    System.out.println("6  :" + str6);
                    System.out.println("7  :" + str7);
                    System.out.println("8  :" + str8);
                    System.out.println("9  :" + str9);
                    System.out.println("1-  :" + str10);
                    System.out.println("11  :" + str11);

                    final int insertVisit = visitEntry(str1, strId, str3, str4, str5, str6, str7, str8, str9, str10, str11);
                    if (insertVisit == 1) {
                        Toast.makeText(CloseActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                    } else if (insertVisit == 0) {
                        tmpOfflineVisitData = tmpOfflineVisitData + "" + str1 + "," + strId + "," + str3 + "," + str4 + "," + str5 + "," + str6 + "," + str7 + "," + str8 + "," + str9 + "," + str10 + "," + str11 + "#";
                        stringBuilder.append("\n").append(data);
                    }
                }
                final PrintWriter writer = new PrintWriter(myObj);
                writer.print(stringBuilder);
                writer.close();
                myReader.close();
              /*  Log.v("data1", "visit data" + tmpOfflineVisitData);
                if (tmpOfflineVisitData.equals("")) {
                } else {
                    final String[] tmpOfflineVisitDataSplit = tmpOfflineVisitData.split("#");
                    final int tmpVisitData = tmpOfflineVisitDataSplit.length;

                    Log.v("data1 visit", "visit data length" + tmpVisitData);

                    for (final String s : tmpOfflineVisitDataSplit) {
                        final String[] strTmpVisits = s.split(",");
                        for (final String sv : strTmpVisits) {
                            System.out.println(" " + sv + " ");
                        }
                        final String str1 = strTmpVisits[0];
                        final String str2 = strTmpVisits[1];
                        final String str3 = strTmpVisits[2];
                        final String str4 = strTmpVisits[3];
                        final String str5 = strTmpVisits[4];
                        final String str6 = strTmpVisits[5];
                        final String str7 = strTmpVisits[6];
                        final String str8 = strTmpVisits[7];
                        final String str9 = strTmpVisits[8];
                        final String str10 = strTmpVisits[9];
                        final String str11 = strTmpVisits[10];
                        System.out.println("s1  :" + str1);
                        System.out.println("s2  :" + str2);
                        System.out.println("s3  :" + str3);
                        System.out.println("s4  :" + str4);
                        System.out.println("s5  :" + str5);
                        System.out.println("s6  :" + str6);
                        System.out.println("s7  :" + str7);
                        System.out.println("s8  :" + str8);
                        System.out.println("s9  :" + str9);
                        System.out.println("s1-  :" + str10);
                        System.out.println("s11  :" + str11);

                        final String StrVisitData = str1 + "," + str2 + "," + str3 + "," + str4 + "," + str5 + "," + str6 + "," + str7 + "," + str8 + "," + str9 + "," + str10 + "," + str11;
                        final String VisitDataDir = "VisitDataDir.txt";
                        generateNoteOnSD(CloseActivity.this, VisitDataDir, StrVisitData);
                    }
                }*/
            } catch (final FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
///////////////////////////////////////////////////////////////demoentry offline///////////

            try {
                final File myObj = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/VisitDemoEntryDir.txt");
                final Scanner myReader = new Scanner(myObj);

                // String data = myReader.nextLine();
                while (myReader.hasNextLine()) {

                    final String data = myReader.nextLine();
                    final String[] columnDataOne = data.split(",");
                    for (final String s : columnDataOne) {
                        System.out.println(" " + s + " ");
                    }
                    System.out.println();
                    final String strs1 = columnDataOne[0];
                    final String strs2 = columnDataOne[1];
                    final String strs3 = columnDataOne[2];
                    final String strs4 = columnDataOne[3];
                    final String strs5 = columnDataOne[4];
                    final String strs6 = columnDataOne[5];
                    final String strs7 = columnDataOne[6];
                    final String strs8 = columnDataOne[7];
                    final String strs9 = columnDataOne[8];
                    final String strs10 = columnDataOne[9];
                    final String strs11 = columnDataOne[10];
                    final String strs12 = columnDataOne[11];
                    final String strs13 = columnDataOne[12];
                    final String strs14 = columnDataOne[13];

                    final String strsid = strs2.replace("\"", "");
                    System.out.println("1  :" + strs1);
                    System.out.println("2  :" + strs2);
                    System.out.println("3  :" + strs3);
                    System.out.println("4  :" + strs4);
                    System.out.println("5  :" + strs5);
                    System.out.println("6  :" + strs6);
                    System.out.println("7  :" + strs7);
                    System.out.println("8  :" + strs8);
                    System.out.println("9  :" + strs9);
                    System.out.println("1-  :" + strs10);
                    System.out.println("11  :" + strs11);
                    System.out.println("12  :" + strs12);
                    System.out.println("13  :" + strs13);
                    System.out.println("14  :" + strs14);
                    final int intcheckDemo = demoEntry(strs1, strsid, strs3, strs4, strs5, strs6, strs7, strs8, strs9, strs10, strs11, strs12, strs13, strs14);
                    if (intcheckDemo == 1) {
                        Toast.makeText(CloseActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                    } else if (intcheckDemo == 0) {
                        tmpOfflineDemoData = tmpOfflineDemoData + "" + strs1 + "," + strsid + "," + strs3 + "," + strs4 + "," + strs5 + "," + strs6 + "," + strs7 + "," + strs8 + "," + strs9 + "," + strs10 + "," + strs11 + "," + strs12 + "," + strs13 + "," + strs14 + "#";
                    }
                }
                final PrintWriter writer = new PrintWriter(myObj);
                writer.print("");
                writer.close();
                myReader.close();
                Log.v("data1", "visit data" + tmpOfflineDemoData);

                if (tmpOfflineDemoData.equals("")) {
                } else {
                    final String[] tmpOfflineDemoDataSplit = tmpOfflineDemoData.split("#");
                    for (final String s : tmpOfflineDemoDataSplit) {
                        final String[] strTmpDemo = s.split(",");
                        final String str1 = strTmpDemo[0];
                        final String str2 = strTmpDemo[1];
                        final String str3 = strTmpDemo[2];
                        final String str4 = strTmpDemo[3];
                        final String str5 = strTmpDemo[4];
                        final String str6 = strTmpDemo[5];
                        final String str7 = strTmpDemo[6];
                        final String str8 = strTmpDemo[7];
                        final String str9 = strTmpDemo[8];
                        final String str10 = strTmpDemo[9];
                        final String str11 = strTmpDemo[10];
                        final String str12 = strTmpDemo[11];
                        final String str13 = strTmpDemo[12];
                        final String str14 = strTmpDemo[13];

                        final String StrVisitData = str1 + "," + str2 + "," + str3 + "," + str4 + "," + str5 + "," + str6 + "," + str7 + "," + str8 + "," + str9 + "," + str10 + "," + str11 + "," + str12 + "," + str13 + "," + str14;
                        final String VisitDataDir = "VisitDemoEntryDir.txt";
                        generateNoteOnSD(CloseActivity.this, VisitDataDir, StrVisitData);
                    }
                }
            } catch (final FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            try {
                final File myObj = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/StoreForEndDir.txt");
                final Scanner myReader = new Scanner(myObj);

                while (myReader.hasNextLine()) {
                    final String data = myReader.nextLine();
                    final String[] columnDataTwo = data.split(",");
                    for (final String s : columnDataTwo) {
                        System.out.println(" " + s + " ");
                    }
                    System.out.println();
                    final String strEnd1 = columnDataTwo[0];
                    final String strEnd2 = columnDataTwo[1];
                    final String strEnd3 = columnDataTwo[2];
                    final String strEnd4 = columnDataTwo[3];
                    final String strEnd5 = columnDataTwo[4];
                    final String strEnd6 = columnDataTwo[5];

                    final String strId = strEnd2.replace("\"", "");
                    System.out.println("1  :" + strEnd1);
                    System.out.println("2  :" + strEnd2);
                    System.out.println("3  :" + strEnd3);
                    System.out.println("4  :" + strEnd4);
                    System.out.println("5  :" + strEnd5);
                    System.out.println("6  :" + strEnd6);

                    final int intCheckEnd = endEntry(strEnd1, strId, strEnd4, strEnd5, strEnd6);
                    if (intCheckEnd == 1) {
                        Toast.makeText(CloseActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                    } else if (intCheckEnd == 0) {
                        tmpOfflineEndData = tmpOfflineEndData + "" + strEnd1 + "," + strId + "," + strEnd3 + "," + strEnd4 + "," + strEnd5 + "," + strEnd6 + "#";
                    }

                }
                final PrintWriter writer = new PrintWriter(myObj);
                writer.print("");
                writer.close();
                myReader.close();
                Log.v("data1", "visit data" + tmpOfflineEndData);
                if (tmpOfflineEndData.equals("")) {
                } else {
                    final String[] tmpOfflineEndDataSplit = tmpOfflineEndData.split("#");
                    for (final String s : tmpOfflineEndDataSplit) {
                        final String[] strTmpEnds = s.split(",");
                        final String strEnd1 = strTmpEnds[0];
                        final String strEnd2 = strTmpEnds[1];
                        final String strEnd3 = strTmpEnds[2];
                        final String strEnd4 = strTmpEnds[3];
                        final String strEnd5 = strTmpEnds[4];
                        final String strEnd6 = strTmpEnds[5];
                        final String StoreForEnd = strEnd1 + "," + strEnd2 + "," + strEnd3 + "," + strEnd4 + "," + strEnd5 + "," + strEnd6;
                        final String StoreForEndDir = "StoreForEndDir.txt";
                        generateNoteOnSD(CloseActivity.this, StoreForEndDir, StoreForEnd);
                    }
                }
            } catch (final FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    private int endEntry(final String strsend1, final String strsid, final String strsend4, final String strsend5, final String strsend6) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        final Call<TripModel> empIdDesignationModelCall = apiInterface.updateEndtripDateEntry(strsend1, strsid, strsend4, strsend5, strsend6);
        empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
            @Override
            public void onResponse(final Call<TripModel> call, final Response<TripModel> response) {
                assert response.body() != null;
                final String value = response.body().getValue();
                final String message = response.body().getMassage();
                intvalEnd = Integer.parseInt(value);
                switch (value) {
                    case "1":
                        intvalEnd = 1;
                        break;
                    case "0":
                        intvalEnd = 0;
                        break;
                }
            }

            @Override
            public void onFailure(final Call<TripModel> call, final Throwable t) {
                if (connectionDetector.isConnected(CloseActivity.this)) {
                    Toast.makeText(CloseActivity.this, "Server Not Found", Toast.LENGTH_SHORT).show();

                    intvalEnd = 0;
                } else {
                    Toast.makeText(CloseActivity.this, "Offline Data Saved ", Toast.LENGTH_SHORT).show();
                    intvalEnd = 0;


                }
            }
        });


        return intvalEnd;
    }

    private int demoEntry(final String strs1, final String strs2, final String strs3, final String strs4, final String strs5, final String strs6, final String strs7, final String strs8, final String strs9, final String strs10, final String strs11, final String strs12, final String strs13, final String strs14) {
        final int farmerFallowUp = Integer.parseInt(strs12);
        final int demoVisit = Integer.parseInt(strs14);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        final Call<TripModel> empIdDesignationModelCall = apiInterface.insertDemoEntry(strs1,
                strs2, strs3, strs4, strs5, strs6, strs7, strs8,
                strs9, strs10, strs11, farmerFallowUp, strs13, demoVisit);
        empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
            @Override
            public void onResponse(final Call<TripModel> call, final Response<TripModel> response) {
                assert response.body() != null;
                final String value = response.body().getValue();
                final String message = response.body().getMassage();
                intvaldemo = Integer.parseInt(value);

                if (value.equals("1")) {
                    intvaldemo = 1;
                    //demodataResponse=true;

                } else if (value.equals("0")) {
                    Toast.makeText(CloseActivity.this, message, Toast.LENGTH_SHORT).show();
                    intvaldemo = 0;
                    // demodataResponse=false;
                }
            }

            @Override
            public void onFailure(final Call<TripModel> call, final Throwable t) {
                if (connectionDetector.isConnected(CloseActivity.this)) {
                    intvaldemo = 0;
                    Toast.makeText(CloseActivity.this, "Server Not Found", Toast.LENGTH_LONG).show();
                    //  demodataResponse=false;

                } else {
                    intvaldemo = 0;
                    Toast.makeText(CloseActivity.this, "No Internet Connection offline saved data", Toast.LENGTH_LONG).show();
                    //   demodataResponse=false;
                }
            }
        });

        return intvaldemo;
    }


    int ParseInteger(final String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Integer.parseInt(strNumber);
            } catch (final Exception e) {
                return -1;
            }
        } else return 0;
    }

    private void submitReadingEnd() {
        meterReadStart = editTextReadingEnd.getText().toString().trim();
        final String strRoute = editRoute.getText().toString().trim();
        read = ParseInteger(meterReadStart);
        Log.v("T1", "check1" + employeeID);
        Log.v("T2", "check2" + read);
        Log.v("T3", "check3" + startMeter);
        if (strRoute.equals("") || read == 0) {
            Toast.makeText(CloseActivity.this, "Enter Route and Reading", Toast.LENGTH_SHORT).show();
        } else {
            if (startMeter <= read) {
                progressBar.setVisibility(View.VISIBLE);
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                final Call<DailyEmpExpenseModel> meterModelCall = apiInterface.UpdateEndReadEntry("End@entryMeterRead", employeeID, read, strRoute);
                meterModelCall.enqueue(new Callback<DailyEmpExpenseModel>() {
                    @Override
                    public void onResponse(final Call<DailyEmpExpenseModel> call, final Response<DailyEmpExpenseModel> response) {
                        final String value = response.body().getValue();
                        final String message = response.body().getMessage();
                        if (value.equals("1")) {
                            editTextReadingEnd.setText("");
                            editRoute.setText("");
                            endRelative.setVisibility(View.GONE);
                            finish();
                        } else if (value.equals("0")) {
                            endRelative.setVisibility(View.GONE);
                            Toast.makeText(CloseActivity.this, message, Toast.LENGTH_LONG).show();
                        } else if (value.equals("2")) {
                            editTextReadingEnd.setText("");
                            endRelative.setVisibility(View.GONE);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(final Call<DailyEmpExpenseModel> call, final Throwable t) {

                        if (connectionDetector.isConnected(CloseActivity.this)) {
                            Toast.makeText(CloseActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(CloseActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                Toast.makeText(CloseActivity.this, "Enter Proper Reading", Toast.LENGTH_SHORT).show();
            }

        }
        Log.i("", read + " is a number");
    }

    double ParseDouble(final String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch (final Exception e) {
                return -1;
            }
        } else return 0;
    }


    private int visitEntry(final String str1, final String str2, final String str3, final String str4, final String str5, final String str6, final String str7, final String str8, final String str9, final String str10, final String str11) {
        final double acreValue = ParseDouble(str9);
        Log.v("Check id emp", "emp id" + employeeID);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        final Call<TripModel> empIdDesignationModelCall = apiInterface.insertVisitedStartEntry(str1, str2, str3, str4, str5, str6, str7, str8, acreValue, str10, str11);
        empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
            @Override
            public void onResponse(final Call<TripModel> call, final Response<TripModel> response) {
                assert response.body() != null;
                final String value = response.body().getValue();
                intval = Integer.parseInt(value);
                if (value.equals("1")) {
                    intval = 1;
                } else if (value.equals("0")) {
                    intval = 0;
                }
            }

            @Override
            public void onFailure(final Call<TripModel> call, final Throwable t) {
                if (connectionDetector.isConnected(CloseActivity.this)) {
                    intval = 0;
                    if (t instanceof SocketTimeoutException) {
                    } else if (t instanceof IOException) {
                    } else {
                        if (call.isCanceled()) {
                            System.out.println("Call was cancelled forcefully");
                        } else {
                            System.out.println("Network Error :: " + t.getLocalizedMessage());
                        }
                    }
                } else {
                    intval = 0;
                    Toast.makeText(CloseActivity.this, "No Internet Connection offline saved data", Toast.LENGTH_LONG).show();
                }
            }
        });
        return intval;
    }


    public void generateNoteOnSD(final Context context, final String sFileName, final String sBody) {
        try {
            final File root = new File(Environment.getExternalStorageDirectory(), "ManishaAgroData");
            if (!root.exists()) {
                root.mkdirs();
            }
            final File GPXFile = new File(root, sFileName);
            final FileWriter writer = new FileWriter(GPXFile, true);
            writer.append(sBody);
            writer.append("\n");
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
