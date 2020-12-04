package com.example.manishaagro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manishaagro.employee.CustomerVisitStartActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class OfflineVisitEndActivity extends AppCompatActivity {

    Toolbar offlinetoolbarEnd;
  Calendar calander;
  Button butRefreshEnd;

    TextView listVisitForEnd;
    TextView listVisitForEndComplete;
    Button submitEnd;
    EditText editTextCodeEnd;

    String allDataStr="";
    String allDataStrComp="";
    String forSaveEnd="";
    String displayStrEnd="";
    String DateCurrent="";
    SimpleDateFormat simpledateformat;
    String CurDefaultDattime="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_visit_end);
        offlinetoolbarEnd=findViewById(R.id.toolbarofflineEnd);
        setSupportActionBar(offlinetoolbarEnd);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }


        listVisitForEnd=findViewById(R.id.ListVisitForEndText);
        listVisitForEndComplete=findViewById(R.id.showCompEndText);
        submitEnd=findViewById(R.id.submitEndCode);
        editTextCodeEnd=findViewById(R.id.editextCodeForEnd);
        butRefreshEnd=findViewById(R.id.refreshbutEnd);

        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CurDefaultDattime = simpledateformat.format(calander.getTime());

        try {
            File myObj = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/VisitDataDir.txt");
            Scanner myReader = new Scanner(myObj);
            listVisitForEnd.setText("");
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
                allDataStr =allDataStr+""+count+", Name : "+str3+", Address : "+str4+"\n\n";



            }
            listVisitForEnd.setText(allDataStr);
            myReader.close();


        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        submitEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              final String codredit=editTextCodeEnd.getText().toString().trim();
              if(codredit.equals(""))
              {
                Toast.makeText(OfflineVisitEndActivity.this,"Enter Code",Toast.LENGTH_SHORT).show();
              }
              else
              {
                  int codeforsave= ParseInteger(codredit);

                  try {
                      File myObj = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/VisitDataDir.txt");
                      Scanner myReader = new Scanner(myObj);
                      listVisitForEndComplete.setText("");
                      allDataStrComp="";
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

                          if (count==codeforsave)
                          {
                              forSaveEnd="";
                              allDataStrComp =codredit+", Name : "+str3+", Address : "+str4+"\n\n";
                              // listVisitForEndComplete.setText(allDataStrComp);
                              forSaveEnd="Update@EndTripDate"+","+strid+","+codredit+","+str3+","+str4+","+CurDefaultDattime;
                          }

                          editTextCodeEnd.setText("");

                      }


                      // String StoreForEnd="Update@EndTripDate"+","+employeeID+","+farmerFullName+","+farmerAddressText;
                      String StoreForEnd=forSaveEnd;
                      String StoreForEndDir="StoreForEndDir.txt";
                      generateNoteOnSD(OfflineVisitEndActivity.this,StoreForEndDir,StoreForEnd);
                      myReader.close();


                  } catch (FileNotFoundException e) {
                      System.out.println("An error occurred.");
                      e.printStackTrace();
                  }

              }




            }
        });




        butRefreshEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    File myObj = new File(Environment.getExternalStorageDirectory() + "/ManishaAgroData/StoreForEndDir.txt");
                    Scanner myReader = new Scanner(myObj);
                    listVisitForEndComplete.setText("");
                    displayStrEnd="";
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



                        String strid=str2.replace("\"","");

                        System.out.println("1  :" +str1);
                        System.out.println("2  :" +str2);
                        System.out.println("3  :" +str3);
                        System.out.println("4  :" +str4);


                        //     offlinedataList.add(str3+","+str4+","+str5+","+str6+","+str7+","+str8+","+str9+","+str10);
                        displayStrEnd = displayStrEnd+""+str3+", Name : "+str4+", Address : "+str5+"\n\n";



                    }

                    myReader.close();


                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                listVisitForEndComplete.setText(displayStrEnd);


            }
        });

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

}
