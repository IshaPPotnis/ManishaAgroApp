package com.example.manishaagro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.manishaagro.model.TripModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfflinrDataActivity extends AppCompatActivity {
    Toolbar offlinetoolbar;
    ProgressDialog prgDialog;
    MessageDialog messageDialog;
    ConnectionDetector connectionDetector;
    ApiInterface apiInterface;
    DBHelper controller;
    Button visitText;
    Button demoText;
    Button synVisitBut;
    String allDataStr = "";
    TextView showText;
    public ArrayList<TripModel> localEmpTripList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offlinr_data);
        controller = new DBHelper(this);
        offlinetoolbar = findViewById(R.id.toolbaroffline);
        connectionDetector = new ConnectionDetector();
        messageDialog = new MessageDialog();
        setSupportActionBar(offlinetoolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        visitText = findViewById(R.id.visitEntry);
        demoText = findViewById(R.id.DemoEntry);
        showText = findViewById(R.id.showEntry);
        synVisitBut = findViewById(R.id.SyncVisit);
        visitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File myObj = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/VisitDataDir.txt");
                    Scanner myReader = new Scanner(myObj);
                    showText.setText("");
                    allDataStr = "";
                    int count = 0;
                    // String data = myReader.nextLine();
                    while (myReader.hasNextLine()) {
                        count = count + 1;
                        String data = myReader.nextLine();
                        final String[] columnData = data.split(",");
                        for (String s : columnData) {
                            System.out.println(" " + s + " ");
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
                        String strid = str2.replace("\"", "");
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

                        allDataStr = allDataStr + "" + count + ", Name : " + str3 + ", Address : " + str4 + ", Village : " + str5 + ", Taluka: " + str6 + ", District: " + str7 + ", Contact : " + str8 + ", Acre(in area)" + str9 + ", Purpose : " + str10 + ", Date : " + str11 + "\n\n";
                    }
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                showText.setText(allDataStr);
            }
        });

        demoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText.setText("");
                allDataStr = "";
                int count = 0;
                try {
                    File myObj = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/VisitDemoEntryDir.txt");
                    Scanner myReader = new Scanner(myObj);

                    // String data = myReader.nextLine();
                    while (myReader.hasNextLine()) {
                        count = count + 1;
                        String data = myReader.nextLine();
                        final String[] columnDataOne = data.split(",");
                        for (String s : columnDataOne) {
                            System.out.println(" " + s + " ");
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
                        String strs15 = columnDataOne[14];
                        String strs16 = columnDataOne[15];

                        String strsid = strs2.replace("\"", "");
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
                        System.out.println("14  :" + strs15);
                        System.out.println("14  :" + strs16);
                        allDataStr = allDataStr + "" + count + ", Name : " + strs3 + ", Demo Type : " + strs4 + ", Demo Name : " + strs7 + ", Crop Health : " + strs6 + ", Usage Type : " + strs8 + ", Crop Bad Reason : " + strs9 + ", Water Quantity : " + strs10 + ", Additions : " + strs11 + ", Crops : " + strs5 + ", Crops Additions : " + strs12 + ",Crop Stage : " + strs13 + ", Follow Up : " + strs14 + ", Follow Up Date : " + strs15 + ",Demo Visit : " + strs16 + "\n\n";
                    }
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                showText.setText(allDataStr);
            }
        });

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Synching SQLite Data with Remote MySQL DB. Please wait...");
        prgDialog.setCancelable(false);
     //12/25/2020   synVisitBut.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.offline_end, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.offline_End) {
            Intent offIntent1 = new Intent(OfflinrDataActivity.this, OfflineVisitEndActivity.class);
            startActivity(offIntent1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

  /* 12/25/2020 @Override
    public void onClick(View v) {
        if (v.getId() == R.id.SyncVisit) {//  syncSQLiteMySQLDB();
            localEmpTripList = controller.getAllEmpTriprecordsList();
            localEmpTripList.size();
            Log.v("local emptrip data", "trip len" + localEmpTripList.size());
            for (int i = 0; i < localEmpTripList.size(); i++) {
                String tripEmpid = localEmpTripList.get(i).getEmpId();
                String tripcustName = localEmpTripList.get(i).getVisitedCustomerName();
                String tripAdd = localEmpTripList.get(i).getAddress();
                String tripDofT = localEmpTripList.get(i).getDateOfTravel();
                String tripDofR = localEmpTripList.get(i).getDateOfReturn();
                String tripDempTy = localEmpTripList.get(i).getDemotype();
                String tripVillage = localEmpTripList.get(i).getVillage();
                String tripTaluka = localEmpTripList.get(i).getTaluka();
                String tripDistrict = localEmpTripList.get(i).getDistrict();
                String tripContact = localEmpTripList.get(i).getContactdetail();
                double tripAcre = localEmpTripList.get(i).getAcre();
                String trippurpose = localEmpTripList.get(i).getVisitpurpose();
                String tripCrops = localEmpTripList.get(i).getCrops();
                String tripCropHealth = localEmpTripList.get(i).getCrophealth();
                String tripDemoname = localEmpTripList.get(i).getDemoname();
                String tripUsageTy = localEmpTripList.get(i).getUsagetype();
                String tripWQTY = localEmpTripList.get(i).getWaterquantity();
                String tripWaterAdd = localEmpTripList.get(i).getWateradditions();
                String tripAddition = localEmpTripList.get(i).getAdditions();
                int tripFollowRe = localEmpTripList.get(i).getFollowuprequired();
                String tripFollowDate = localEmpTripList.get(i).getFollowupdate();
                String tripDemoimg = localEmpTripList.get(i).getDemoimage();
                String tripSelfie = localEmpTripList.get(i).getSelfiewithcustomer();
                String tripObserv = localEmpTripList.get(i).getObservations();
                int tripCustomerRate = localEmpTripList.get(i).getCustomer_rating();
                String tripCustomerReview = localEmpTripList.get(i).getCustomer_review();
                String tripFollowImg = localEmpTripList.get(i).getFollow_up_image();
                int tripDemosReq = localEmpTripList.get(i).getDemorequired();
                String tripCropGrowth = localEmpTripList.get(i).getCrop_growth();
                String tripHealthBadR = "no";
                System.out.println("check off data Trip : " + tripEmpid + " " + tripcustName + " " + tripAdd + " " + tripDofT + " " + tripDofR + " " + tripDempTy + " " + tripVillage + " " + tripTaluka + " " + tripDistrict + " " + tripContact + " " + tripAcre + " " + trippurpose + " " + tripCrops + " " + tripCropHealth + " " + tripDemoname + " " + tripUsageTy + " " + tripWQTY + " " + tripWaterAdd + " " + tripAddition + " " + tripFollowRe + " " + tripFollowDate + " " + tripDemoimg + " " + tripSelfie + " " + tripObserv + " " + tripCustomerRate + " " + tripCustomerReview + " " + tripFollowImg + " " + tripDemosReq + " " + tripCropGrowth + " " + tripHealthBadR);
                sendOfflineTripData(tripEmpid, tripcustName, tripAdd, tripDofT, tripDofR, tripDempTy, tripVillage, tripTaluka, tripDistrict, tripContact, tripAcre, trippurpose, tripCrops, tripCropHealth, tripDemoname, tripUsageTy, tripWQTY, tripWaterAdd, tripAddition, tripFollowRe, tripFollowDate, tripDemoimg, tripSelfie, tripObserv, tripCustomerRate, tripCustomerReview, tripFollowImg, tripDemosReq, tripCropGrowth, tripHealthBadR);
            }
        }
    }*/

  /*12/25/2020  public void sendOfflineTripData(String tripEmpid, String tripcustName, String tripAdd, String tripDofT, String tripDofR, String tripDempTy, String tripVillage, String tripTaluka, String tripDistrict, String tripContact, double tripAcre,
                                    String trippurpose, final String tripCrops, String tripCropHealth, String tripDemoname, String tripUsageTy, String tripWQTY, String tripWaterAdd, String tripAddition, int tripFollowRe, String tripFollowDate, String tripDemoimg,
                                    String tripSelfie, String tripObserv, int tripCustomerRate, String tripCustomerReview, String tripFollowImg, int tripDemosReq, String tripCropGrowth, String tripHealthBadR) {
        System.out.println("in sending offline data to server");
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<TripModel>> calltList = apiInterface.sendAllOfflineDataTrip("sendLocalEmpTrip@meTableData",
                tripEmpid, tripcustName, tripAdd, tripDofT, tripDofR, tripDempTy, tripVillage,
                tripTaluka, tripDistrict, tripContact, tripAcre,
                trippurpose, tripCrops, tripCropHealth, tripDemoname, tripUsageTy,
                tripWQTY, tripWaterAdd, tripAddition, tripFollowRe, tripFollowDate, tripDemoimg,
                tripSelfie, tripObserv, tripCustomerRate, tripCustomerReview,
                tripFollowImg, tripDemosReq, tripCropGrowth, tripHealthBadR);
        calltList.enqueue(new Callback<ArrayList<TripModel>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<ArrayList<TripModel>> call, Response<ArrayList<TripModel>> response) {
                if (response.isSuccessful()) {// TripModel tripModelres=response.body();
                    System.out.println("Response fetched is " + response.toString());
                    try {
                        JSONArray arr = new JSONArray(response);
                        System.out.println("JSON Array fetched is - "+ arr.toString());
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = (JSONObject) arr.get(i);
                            String s1 = String.valueOf(obj.get("value"));
                            String s2 = String.valueOf(obj.get("message"));
                            System.out.println(obj.get("value"));
                            System.out.println(obj.get("message"));
                            System.out.println("check obj" + s1);
                            System.out.println("check obj2" + s2);
                            if (s1.equals("1")) {
                                Toast.makeText(OfflinrDataActivity.this, s2, Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TripModel>> call, Throwable t) {
                Toast.makeText(OfflinrDataActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}