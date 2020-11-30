package com.example.manishaagro;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.manishaagro.DBHelper.COLUMN_ADDRESS;
import static com.example.manishaagro.DBHelper.COLUMN_CONTACT_DETAIL;
import static com.example.manishaagro.DBHelper.COLUMN_DESIGNATION;
import static com.example.manishaagro.DBHelper.COLUMN_DOB;
import static com.example.manishaagro.DBHelper.COLUMN_DOJ;
import static com.example.manishaagro.DBHelper.COLUMN_EMAIL_ID;
import static com.example.manishaagro.DBHelper.COLUMN_EMPI_ID;
import static com.example.manishaagro.DBHelper.COLUMN_HEADQUARTER;
import static com.example.manishaagro.DBHelper.COLUMN_IS_ACTIVE;
import static com.example.manishaagro.DBHelper.COLUMN_NAME;
import static com.example.manishaagro.DBHelper.COLUMN_PASSWORD;
import static com.example.manishaagro.DBHelper.COLUMN_USERNAME;
import static com.example.manishaagro.DBHelper.EMPLOYEE_DETAILS;

public class MainActivity extends AppCompatActivity {
    ConnectionDetector connectionDetector;
    DBHelper dbHelpers;
    SQLiteDatabase db2;
    Button exp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelpers = new DBHelper(this);
        exp=findViewById(R.id.export);

       // dbHelpers = new DBHelper(this);
        //dbHelpers.addData(1011, "offline", "offline");
        //dbHelpers.addData(1012, "offline2", "offline2");
       // importdata();

      /*  Toast.makeText(MainActivity.this, "Got Data Offline", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 1000);*/
      exp.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              importdata();

          }
      });
    }

    private void importdata()
    {
        db2 = dbHelpers.getWritableDatabase();

        db2.execSQL("delete from " + EMPLOYEE_DETAILS);

        InputStream opStream = null;
        try {
            BufferedReader buffer = null;
            opStream = getApplicationContext().getAssets().open("employee_details.csv");
            buffer = new BufferedReader(new InputStreamReader(opStream));
            // BufferedReader buffer = new BufferedReader(file);
            String line = "";
            db2.beginTransaction();
            try {
                while ((line = buffer.readLine()) != null) {
                    String[] colums = line.split(",");
                    if (colums.length != 12) {
                        Log.d("CSVParser", "Skipping Bad CSV Row");
                        continue;
                    }
                    ContentValues cv = new ContentValues(50);
                    cv.put(COLUMN_EMPI_ID, colums[0].trim());
                    // Log.v("resul3","res3"+(Col1, colums[0].trim()));
                    cv.put(COLUMN_USERNAME, colums[1].trim());
                    cv.put(COLUMN_PASSWORD, colums[2].trim());

                    cv.put(COLUMN_NAME, colums[3].trim());
                    cv.put(COLUMN_DESIGNATION, colums[4].trim());
                    cv.put(COLUMN_DOB, colums[5].trim());
                    cv.put(COLUMN_DOJ, colums[6].trim());

                    cv.put(COLUMN_EMAIL_ID, colums[7].trim());
                    cv.put(COLUMN_CONTACT_DETAIL, colums[8].trim());
                    cv.put(COLUMN_ADDRESS, colums[9].trim());
                    cv.put(COLUMN_HEADQUARTER, colums[10].trim());
                    cv.put(COLUMN_IS_ACTIVE, colums[11].trim());



                    db2.insert(EMPLOYEE_DETAILS, null, cv);
                    //  Log.v("resul3","res3"+db2.insert(Table, null, cv));

                    Toast.makeText(MainActivity.this, "Successfully Imported", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            db2.setTransactionSuccessful();
            db2.endTransaction();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}