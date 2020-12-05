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

import com.example.manishaagro.employee.CustomerVisitEndActivity;
import com.example.manishaagro.employee.CustomerVisitStartActivity;
import com.example.manishaagro.employee.DemoEntryActivity;
import com.example.manishaagro.model.DailyEmpExpenseModel;
import com.example.manishaagro.model.TripModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

import static com.example.manishaagro.utils.Constants.END_TRIP_ENTRY;
import static com.example.manishaagro.utils.Constants.VISITED_CUSTOMER_ENTRY;

public class CloseActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressBar progressBar;
    public CloaseAdapter cloaseAdapter;
    private List<DailyEmpExpenseModel> meterModels = new ArrayList<>();
    CloaseAdapter.RecyclerViewClickListener listener;
    private RecyclerView recyclerView;
    Toolbar meterReadToolbar;
    ConnectionDetector connectionDetector;
    Button submitReading;
    public int intval=-1;
    public int intvaldemo=-1;
    public int intvalEnd=-1;
    EditText editTextReadingEnd, editRoute;
    RelativeLayout endRelative;
    Button sendButton;
    int read = 0;
 //   boolean visitdataResponse=false;
   // boolean demodataResponse=false;
    //boolean enddataResponse=false;
    String tmpOfflineVisitData="";
    String tmpOfflineDemoData="";
    String tmpOfflineEndData="";
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
        sendButton=findViewById(R.id.sendofflinedata);
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
                    File myObjvisit = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/VisitDataDir.txt");
                    File myObjdemo = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/VisitDemoEntryDir.txt");
                    File myObjend = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/StoreForEndDir.txt");

                    if (myObjvisit.length()==0 && myObjdemo.length()==0 && myObjend.length()==0 )
                    {
                        submitReadingEnd();

                    }
                    else
                    {
                        Toast.makeText(CloseActivity.this, "Send Offline Data", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(CloseActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }

            }
        }
        if(v.getId()==R.id.sendofflinedata)
        {
            //////////////visit entry offline
            try {
                File myObj = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/VisitDataDir.txt");
                Scanner myReader = new Scanner(myObj);
                tmpOfflineVisitData="";

                // String data = myReader.nextLine();
                while (myReader.hasNextLine()) {

                    String data = myReader.nextLine();

                    final String[] columnData = data.split(",");
                    for(String s:columnData)
                    {
                        System.out.println(" "+ s +" ");
                    }
                    System.out.println();
                    String str1 = columnData[0];
                    String str2 = columnData[1];//.replace("\"","");;
                    String str3 = columnData[2];
                    String str4 = columnData[3];
                    String str5 = columnData[4];
                    String str6 = columnData[5];
                    String str7 = columnData[6];
                    String str8 = columnData[7];
                    String str9 = columnData[8];
                    String str10 = columnData[9];
                    String str11 = columnData[10];

                    String strid=str2.replace("\"","");

                    System.out.println("1  :" +str1);
                    System.out.println("2  :" +str2);
                    System.out.println("3  :" +str3);
                    System.out.println("4  :" +str4);
                    System.out.println("5  :" +str5);
                    System.out.println("6  :" +str6);
                    System.out.println("7  :" +str7);
                    System.out.println("8  :" +str8);
                    System.out.println("9  :" +str9);
                    System.out.println("1-  :" +str10);
                    System.out.println("11  :" +str11);

                    int intresvisit = visitEntry(str1, strid, str3, str4, str5, str6, str7, str8, str9, str10,str11);
                    if (intresvisit==1)
                    {
                        Toast.makeText(CloseActivity.this,"Ok",Toast.LENGTH_SHORT).show();
                    }
                    else if(intresvisit==0)
                    {
                        tmpOfflineVisitData=tmpOfflineVisitData+""+str1+","+strid+","+str3+","+str4+","+str5+","+str6+","+str7+","+str8+","+str9+","+str10+","+str11+"#";

                    }


                }
                PrintWriter writer=new PrintWriter(myObj);
                writer.print("");
                writer.close();
                myReader.close();
                Log.v("data1", "visit data" + tmpOfflineVisitData);


                if(tmpOfflineVisitData.equals(""))
                {

                }
                else
                {

                    String[] tmpOfflineVisitDataSplit=tmpOfflineVisitData.split("#");
                    int tmpVisitsdata=tmpOfflineVisitDataSplit.length;

                    Log.v("data1 vist", "visit data length" + tmpVisitsdata);

                    for (int i=0; i<tmpVisitsdata;i++)
                    {
                        String[] strTmpVisits = tmpOfflineVisitDataSplit[i].split(",");

                        for(String sv:strTmpVisits)
                        {
                            System.out.println(" "+ sv +" ");
                        }


                        String str1 = strTmpVisits[0];
                        String str2 = strTmpVisits[1];//.replace("\"","");;
                        String str3 = strTmpVisits[2];
                        String str4 = strTmpVisits[3];
                        String str5 = strTmpVisits[4];
                        String str6 = strTmpVisits[5];
                        String str7 = strTmpVisits[6];
                        String str8 = strTmpVisits[7];
                        String str9 = strTmpVisits[8];
                        String str10 = strTmpVisits[9];
                        String str11 = strTmpVisits[10];



                        System.out.println("s1  :" +str1);
                        System.out.println("s2  :" +str2);
                        System.out.println("s3  :" +str3);
                        System.out.println("s4  :" +str4);
                        System.out.println("s5  :" +str5);
                        System.out.println("s6  :" +str6);
                        System.out.println("s7  :" +str7);
                        System.out.println("s8  :" +str8);
                        System.out.println("s9  :" +str9);
                        System.out.println("s1-  :" +str10);
                        System.out.println("s11  :" +str11);








                        String StrVisitData=str1+","+str2+","+str3+","+str4+","+str5+","+str6+","+str7+","+str8+","+str9+","+str10+","+str11;

                        String VisitDataDir="VisitDataDir.txt";
                        generateNoteOnSD(CloseActivity.this,VisitDataDir,StrVisitData);

                    }
                    ///
                }




            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }


///////////////////////////////////////////////////////////////demoentry offline///////////

            try {
                File myObj = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/VisitDemoEntryDir.txt");
                Scanner myReader = new Scanner(myObj);

                // String data = myReader.nextLine();
                while (myReader.hasNextLine()) {

                    String  data = myReader.nextLine();
                    final String[] columnDataOne = data.split(",");
                    for(String s:columnDataOne)
                    {
                        System.out.println(" "+ s +" ");
                    }
                    System.out.println();
                    String strs1 = columnDataOne[0];
                    String strs2 = columnDataOne[1];
                    String strs3 = columnDataOne[2];
                    String strs4 = columnDataOne[3];
                    String strs5 = columnDataOne[4];
                    String strs6 = columnDataOne[5];
                    String strs7 = columnDataOne[6];
                    String strs8 = columnDataOne[7];
                    String strs9 = columnDataOne[8];
                    String strs10 = columnDataOne[9];
                    String strs11 = columnDataOne[10];
                    String strs12 = columnDataOne[11];
                    String strs13 = columnDataOne[12];
                    String strs14 = columnDataOne[13];

                    String strsid=strs2.replace("\"","");
                    System.out.println("1  :" +strs1);
                    System.out.println("2  :" +strs2);
                    System.out.println("3  :" +strs3);
                    System.out.println("4  :" +strs4);
                    System.out.println("5  :" +strs5);
                    System.out.println("6  :" +strs6);
                    System.out.println("7  :" +strs7);
                    System.out.println("8  :" +strs8);
                    System.out.println("9  :" +strs9);
                    System.out.println("1-  :" +strs10);

                    System.out.println("11  :" +strs11);
                    System.out.println("12  :" +strs12);
                    System.out.println("13  :" +strs13);
                    System.out.println("14  :" +strs14);
                //    demodataResponse=false;
                    int intcheckDemo =demoEntry(strs1,strsid,strs3,strs4,strs5,strs6,strs7,strs8,strs9,strs10,strs11,strs12,strs13,strs14);
                    if (intcheckDemo==1)
                    {
                        Toast.makeText(CloseActivity.this,"Ok",Toast.LENGTH_SHORT).show();
                    }
                    else if(intcheckDemo==0)
                    {
                        tmpOfflineDemoData=tmpOfflineDemoData+""+strs1+","+strsid+","+strs3+","+strs4+","+strs5+","+strs6+","+strs7+","+strs8+","+strs9+","+strs10+","+strs11+","+strs12+","+strs13+","+strs14+"#";
                    }


                }

                PrintWriter writer=new PrintWriter(myObj);
                writer.print("");
                writer.close();
                myReader.close();
                Log.v("data1", "visit data" + tmpOfflineDemoData);

                if(tmpOfflineDemoData.equals(""))
                {

                }
                else
                {
                    String[] tmpOfflineDemoDataSplit=tmpOfflineDemoData.split("#");
                    int tmpDemodata=tmpOfflineDemoDataSplit.length;
                    for (int i=0; i<tmpDemodata;i++)
                    {
                        String[] strTmpDemo=tmpOfflineDemoDataSplit[i].split(",");


                        String strs1 = strTmpDemo[0];
                        String strs2 = strTmpDemo[1];
                        String strs3 = strTmpDemo[2];
                        String strs4 = strTmpDemo[3];
                        String strs5 = strTmpDemo[4];
                        String strs6 = strTmpDemo[5];
                        String strs7 = strTmpDemo[6];
                        String strs8 = strTmpDemo[7];
                        String strs9 = strTmpDemo[8];
                        String strs10 = strTmpDemo[9];
                        String strs11 = strTmpDemo[10];
                        String strs12 = strTmpDemo[11];
                        String strs13 = strTmpDemo[12];
                        String strs14 = strTmpDemo[13];

                        String StrVisitData=strs1+","+strs2+","+strs3+","+strs4+","+strs5+","+strs6+","+strs7+","+strs8+","+strs9+","+strs10+","+strs11+","+strs12+","+strs13+","+strs14;


                        String VisitDataDir="VisitDemoEntryDir.txt";
                        generateNoteOnSD(CloseActivity.this,VisitDataDir,StrVisitData);


                    }

                }


            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }


///////////////////////////////////////////end visit

            try {
                File myObj = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/StoreForEndDir.txt");
                Scanner myReader = new Scanner(myObj);

                // String data = myReader.nextLine();
                while (myReader.hasNextLine()) {

                    String  data = myReader.nextLine();
                    final String[] columnDataTwo = data.split(",");
                    for(String s:columnDataTwo)
                    {
                        System.out.println(" "+ s +" ");
                    }
                    System.out.println();
                    String strsend1 = columnDataTwo[0];
                    String strsend2 = columnDataTwo[1];
                    String strsend3 = columnDataTwo[2];
                    String strsend4 = columnDataTwo[3];
                    String strsend5 = columnDataTwo[4];
                    String strsend6 = columnDataTwo[5];


                    String strsid=strsend2.replace("\"","");
                    System.out.println("1  :" +strsend1);
                    System.out.println("2  :" +strsend2);
                    System.out.println("3  :" +strsend3);
                    System.out.println("4  :" +strsend4);
                    System.out.println("5  :" +strsend5);
                    System.out.println("6  :" +strsend6);

                 //   enddataResponse=false;
                    int intcheckEnd=endEntry(strsend1,strsid,strsend4,strsend5,strsend6);
                    if (intcheckEnd==1)
                    {
                        Toast.makeText(CloseActivity.this,"Ok",Toast.LENGTH_SHORT).show();
                    }
                    else if(intcheckEnd==0)
                    {
                        tmpOfflineEndData=tmpOfflineEndData+""+strsend1+","+strsid+","+strsend3+","+strsend4+","+strsend5+","+strsend6+"#";
                    }

                }

                PrintWriter writer=new PrintWriter(myObj);
                writer.print("");
                writer.close();
                myReader.close();
                Log.v("data1", "visit data" + tmpOfflineEndData);



                if(tmpOfflineEndData.equals(""))
                {

                }
                else
                {

                    String[] tmpOfflineEndDataSplit=tmpOfflineEndData.split("#");
                    int tmpEndsdata=tmpOfflineEndDataSplit.length;

                    for (int i=0; i<tmpEndsdata;i++)
                    {
                        String[] strTmpEnds=tmpOfflineEndDataSplit[i].split(",");

                        String strsend1 = strTmpEnds[0];
                        String strsend2 = strTmpEnds[1];
                        String strsend3 = strTmpEnds[2];
                        String strsend4 = strTmpEnds[3];
                        String strsend5 = strTmpEnds[4];
                        String strsend6 = strTmpEnds[5];



                        String StoreForEnd=strsend1+","+strsend2+","+strsend3+","+strsend4+","+strsend5+","+strsend6;



                        String StoreForEndDir="StoreForEndDir.txt";
                        generateNoteOnSD(CloseActivity.this,StoreForEndDir,StoreForEnd);


                    }


                }


            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }
    }

private int endEntry(String strsend1,String strsid,String strsend4,String strsend5,String strsend6)
{
    final String finalstrsend1=strsend1;
    final String finalstrsid=strsid;
    final String finalstrsend4=strsend4;
    final String finalstrsend5=strsend5;
    final String finalstrsend6=strsend6;

    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    Call<TripModel> empIdDesignationModelCall = apiInterface.updateEndtripDateEntry(finalstrsend1, finalstrsid, finalstrsend4, finalstrsend5,finalstrsend6);
    empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
        @Override
        public void onResponse(Call<TripModel> call, Response<TripModel> response) {
            assert response.body() != null;
            String value = response.body().getValue();
            String message = response.body().getMassage();

            intvalEnd= Integer.parseInt(value);
            switch (value) {
                case "1":
                 //   Toast.makeText(CloseActivity.this, message, Toast.LENGTH_SHORT).show();
                   // enddataResponse=true;
                    intvalEnd=1;
                    break;
                case "0":
                    intvalEnd=0;
                    //enddataResponse=false;
                    //Toast.makeText(CustomerVisitEndActivity.this, message, Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onFailure(Call<TripModel> call, Throwable t) {
            if (connectionDetector.isConnected(CloseActivity.this)) {
                Toast.makeText(CloseActivity.this, "Server Not Found", Toast.LENGTH_SHORT).show();

                intvalEnd=0;
            }
            else
            {
                Toast.makeText(CloseActivity.this, "Offline Data Saved ", Toast.LENGTH_SHORT).show();
                intvalEnd=0;


            }
        }
    });



return intvalEnd;
}

    private int demoEntry(String strs1,String strs2,String strs3,String strs4,String strs5,String strs6,String strs7,String strs8,String strs9,String strs10,String strs11,String strs12,String strs13,String strs14)
    {

        final String keystrdemo=strs1;
        final String keyiddemo=strs2;
        final String farmerNameText=strs3;
        final String farmerDemoType=strs4;
        final String farmerCrops=strs5;
        final String farmerCropHealth=strs6;
        final String farmerDemoName=strs7;
        final String farmerUsageType=strs8;

        final String farmerWaterQty=strs9;
        final String farmerWaterAdditions=strs10;
        final String farmerAdditions=strs11;
        final int farmerFallowUp= Integer.parseInt(strs12);
        final String farmerFollowUpDate=strs13;
        final int demoVisit= Integer.parseInt(strs14);




        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TripModel> empIdDesignationModelCall = apiInterface.insertDemoEntry(keystrdemo,
                keyiddemo, farmerNameText, farmerDemoType, farmerCrops, farmerCropHealth, farmerDemoName, farmerUsageType,
                farmerWaterQty, farmerWaterAdditions, farmerAdditions, farmerFallowUp, farmerFollowUpDate, demoVisit);
        empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
            @Override
            public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                assert response.body() != null;
                String value = response.body().getValue();
                String message = response.body().getMassage();
                intvaldemo= Integer.parseInt(value);

                if (value.equals("1")) {
                    intvaldemo=1;
                    //demodataResponse=true;

                } else if (value.equals("0")) {
                    Toast.makeText(CloseActivity.this, message, Toast.LENGTH_SHORT).show();
                    intvaldemo=0;
                   // demodataResponse=false;
                }
            }

            @Override
            public void onFailure(Call<TripModel> call, Throwable t) {
                if (connectionDetector.isConnected(CloseActivity.this)) {
                    intvaldemo=0;
                    Toast.makeText(CloseActivity.this, "Server Not Found", Toast.LENGTH_LONG).show();
                  //  demodataResponse=false;

                } else {
                    intvaldemo=0;
                    Toast.makeText(CloseActivity.this, "No Internet Connection offline saved data", Toast.LENGTH_LONG).show();
                               //   demodataResponse=false;
                                    }
            }
        });

        return intvaldemo;
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
    private void submitReadingEnd() {

        meterReadStart = editTextReadingEnd.getText().toString().trim();
        final String strRoute = editRoute.getText().toString().trim();
        read = ParseInteger(meterReadStart);


      //  try {
            // int num = Integer.parseInt(read);
         //   read = Integer.parseInt(meterReadStart);
            Log.v("T1", "check1" + employeeID);
            Log.v("T2", "check2" + read);
            Log.v("T3", "check3" + startMeter);

        if (strRoute.equals("")||read==0)
        {

            Toast.makeText(CloseActivity.this, "Enter Route and Reading", Toast.LENGTH_SHORT).show();

        }
        else
        {
            if (startMeter <=read)
            {  progressBar.setVisibility(View.VISIBLE);
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
                            endRelative.setVisibility(View.GONE);
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
            else
            {

                Toast.makeText(CloseActivity.this, "Enter Proper Reading", Toast.LENGTH_SHORT).show();
            }

        }
        Log.i("", read + " is a number");

      //  } catch (NumberFormatException e) {
       ///     Log.i("", read + " is not a number");
       // }


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


    private int visitEntry(String str1, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10,String str11) {
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
        final String farmerVisitdate = str11;


        Log.v("Check id emp", "emp id" + employeeID);


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TripModel> empIdDesignationModelCall = apiInterface.insertVisitedStartEntry(keystr, keyempid, farmerFullName, farmerAddressText, farmerVillage, farmerTaluka, farmerDistrict, farmerContact, acreValue, farmerVisitPurpose,farmerVisitdate);
        empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
            @Override
            public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                assert response.body() != null;
                String  value = response.body().getValue();
                intval= Integer.parseInt(value);
                String message = response.body().getMassage();
                if (value.equals("1")) {
                   // visitdataResponse=true;
                    intval=1;
                  //  Toast.makeText(CloseActivity.this, message, Toast.LENGTH_SHORT).show();
                } else if (value.equals("0")) {
                    intval=0;
                   // visitdataResponse=false;
                    //Toast.makeText(CustomerVisitStartActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TripModel> call, Throwable t) {
                if (connectionDetector.isConnected(CloseActivity.this)) {
                    intval=0;
                    if (t instanceof SocketTimeoutException) {

                    } else if (t instanceof IOException) {

                    } else {

                        if (call.isCanceled()) {
                           // visitdataResponse=false;
                            System.out.println("Call was cancelled forcefully");
                        } else {
                            //visitdataResponse=false;
                            System.out.println("Network Error :: " + t.getLocalizedMessage());
                        }
                    }
                } else {
                    intval=0;
                    //visitdataResponse=false;
                    Toast.makeText(CloseActivity.this, "No Internet Connection offline saved data", Toast.LENGTH_LONG).show();

                }

            }
        });

        return  intval;//visitdataResponse;
    }



    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "ManishaAgroData");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile,true);
            writer.append(sBody);
            writer.append("\n");
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
