package com.example.manishaagro;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import cz.msebera.android.httpclient.Header;


public class OfflinrDataActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar offlinetoolbar;
    ProgressDialog prgDialog;

DBHelper controller;
    Button visitText;
    Button demoText;
    Button synVisitBut;

    String allDataStr="";
    TextView showText;
    public List<String> offlinedataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offlinr_data);
        controller=new DBHelper(this);
        offlinetoolbar=findViewById(R.id.toolbaroffline);

        setSupportActionBar(offlinetoolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        visitText=findViewById(R.id.visitEntry);
        demoText=findViewById(R.id.DemoEntry);
        showText=findViewById(R.id.showEntry);
        synVisitBut=findViewById(R.id.SyncVisit);



        visitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                try {
                    File myObj = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/VisitDataDir.txt");
                    Scanner myReader = new Scanner(myObj);
                    showText.setText("");
                    allDataStr="";
                    int count=0;
                    // String data = myReader.nextLine();
                    while (myReader.hasNextLine()) {
                        count=count+1;
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

                  //     offlinedataList.add(str3+","+str4+","+str5+","+str6+","+str7+","+str8+","+str9+","+str10);
                     allDataStr =allDataStr+""+count+", Name : "+str3+", Address : "+str4+", Village : "+str5+", Taluka: "+str6+", District: "+str7+", Contact : "+str8+", Acre(in area)"+str9+", Purpose : "+str10+", Date : "+str11+"\n\n";



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
                    allDataStr="";
                int count=0;
                try {
                    File myObj = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/VisitDemoEntryDir.txt");
                    Scanner myReader = new Scanner(myObj);

                    // String data = myReader.nextLine();
                    while (myReader.hasNextLine()) {
                        count=count+1;
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
                            String strs15 = columnDataOne[14];
                            String strs16 = columnDataOne[15];

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
                            System.out.println("14  :" +strs15);
                            System.out.println("14  :" +strs16);

                            allDataStr=allDataStr+""+count+", Name : "+strs3+", Demo Type : "+strs4+", Demo Name : "+strs7+", Crop Health : "+strs6+", Usage Type : "+strs8+", Crop Bad Reason : "+strs9+", Water Quantity : "+strs10+", Additions : "+strs11+", Crops : "+strs5+", Crops Additions : "+strs12+",Crop Stage : "+strs13+", Follow Up : "+strs14+", Follow Up Date : "+strs15+",Demo Visit : "+strs16+"\n\n";






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


        synVisitBut.setOnClickListener(this);

    }



  /*  public void syncSQLiteMySQLDB()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        ArrayList<HashMap<String, String>> userList =  controller.getAllUsers();
        if(userList.size()!=0){
            if(controller.dbSyncCount() != 0){
                prgDialog.show();
                params.put("usersJSON", controller.composeJSONfromSQLite());
                client.post("http://activexsolutions.com/php/offlineVisitDataSave.php",params ,new TextHttpResponseHandler() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers,String response) {


                        System.out.println(response);
                        prgDialog.hide();
                        try {
                            Log.i("tagconvertstr", "["+response+"]");
                            JSONArray arr = new JSONArray(response);
                            System.out.println("json array res: " + response);
                            System.out.println(arr.length());
                            for(int i=0; i<arr.length();i++){
                                JSONObject obj = (JSONObject)arr.get(i);
                                System.out.println(obj.get("date_of_travel"));
                                System.out.println(obj.get("visit_syn_status"));
                                controller.updateSyncStatus(obj.get("date_of_travel").toString(),obj.get("visit_syn_status").toString());
                            }
                            Toast.makeText(getApplicationContext(), "DB Sync completed!", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseBody, Throwable error) {
                        // TODO Auto-generated method stub
                        prgDialog.hide();
                        if(statusCode == 404){
                            Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                        }else if(statusCode == 500){
                            Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                        }
                    }


                });
            }else{
                Toast.makeText(getApplicationContext(), "SQLite and Remote MySQL DBs are in Sync!", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "No data in SQLite DB, please do enter User name to perform Sync action", Toast.LENGTH_LONG).show();
        }
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.offline_end, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.offline_End)
        {
            Intent offIntent1 = new Intent(OfflinrDataActivity.this, OfflineVisitEndActivity.class);

            startActivity(offIntent1);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.SyncVisit)
        {

          //  syncSQLiteMySQLDB();

        }
    }
}
