package com.example.manishaagro.employee;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manishaagro.ApiClient;
import com.example.manishaagro.ApiInterface;
import com.example.manishaagro.ConnectionDetector;
import com.example.manishaagro.LoginActivity;
import com.example.manishaagro.R;
import com.example.manishaagro.model.TripModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.END_TRIP_ENTRY;
import static com.example.manishaagro.utils.Constants.VISITED_CUSTOMER_ENTRY;

public class CustomerVisitEndActivity extends AppCompatActivity {
    ConnectionDetector connectionDetector;
    Toolbar visitEndToolbar;
    Calendar calander;
    String DateCurrent="";
    SimpleDateFormat simpledateformat;
    String CurDefaultDattime="";
    public ApiInterface apiInterface;
    public AdapterEnd adapterEnd;
    private RecyclerView recyclerViewEndTrip;
    private List<TripModel> checkTripEndList;
    String employeeID = "";

    AdapterEnd.RecyclerViewClickListener listener;
    String tripCustomerName = "";
    String tripCustomerAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_visit_end);
        visitEndToolbar = findViewById(R.id.toolbarvisitEnd);
        connectionDetector = new ConnectionDetector();
        setSupportActionBar(visitEndToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        Intent intent = getIntent();
        employeeID = intent.getStringExtra("visitedEmployee");
        recyclerViewEndTrip = findViewById(R.id.EndTripCheckTRecyview);

        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CurDefaultDattime = simpledateformat.format(calander.getTime());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CustomerVisitEndActivity.this);
        recyclerViewEndTrip.setLayoutManager(layoutManager);
        listener = new AdapterEnd.RecyclerViewClickListener() {
            @Override
            public void onEndTripCardClick(View view, int position) {
                tripCustomerName = checkTripEndList.get(position).getVisitedCustomerName();
                tripCustomerAddress = checkTripEndList.get(position).getAddress();
                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerVisitEndActivity.this);

                builder.setTitle(R.string.app_name);
               builder.setIcon(R.mipmap.ic_launcher);

                builder.setMessage("End Visit?")

                        .setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                    entryEndTrip();



                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        };
    }





    @Override
    protected void onResume() {
        super.onResume();
        getCheckEndTrip();
    }

    private void entryEndTrip() {
        final String STEmp_ID1 = employeeID;
        final String customerName = tripCustomerName;
        final String customerAddress = tripCustomerAddress;

        DateCurrent="0000-00-00 00:00:00";

            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<TripModel> empIdDesignationModelCall = apiInterface.updateEndtripDateEntry(END_TRIP_ENTRY, STEmp_ID1, customerName, customerAddress,DateCurrent);
            empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
                @Override
                public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                    assert response.body() != null;
                    String value = response.body().getValue();
                    String message = response.body().getMassage();
                    switch (value) {
                        case "1":
                            finish();
                            break;
                        case "0":
                            //Toast.makeText(CustomerVisitEndActivity.this, message, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<TripModel> call, Throwable t) {
                    if (connectionDetector.isConnected(CustomerVisitEndActivity.this)) {
                        Toast.makeText(CustomerVisitEndActivity.this,"Cannot Communicate to Server",Toast.LENGTH_LONG).show();
                    }
                    else
                    {



                       // String StrEndVisitData=END_TRIP_ENTRY+","+STEmp_ID1+","+customerName+","+customerAddress;

                        //String EndVisitDataDir="EndVisitDataDir.txt";
                        //generateNoteOnSD(CustomerVisitEndActivity.this,EndVisitDataDir,StrEndVisitData);

                    }
                }
            });





    }


   /* public void generateNoteOnSD(Context context, String sFileName, String sBody) {
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
    }*/

    private void getCheckEndTrip() {
        final String STEmp_ID1 = employeeID;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("checkTrip", "emp1" + employeeID);
        Call<List<TripModel>> listCall = apiInterface.checkAndGetEndTrip("Check@ReturnEndTrip", STEmp_ID1);
        listCall.enqueue(new Callback<List<TripModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TripModel>> call, @NonNull Response<List<TripModel>> response) {
                checkTripEndList = response.body();
                Log.v("checkTripList", "empList" + checkTripEndList);
                adapterEnd = new AdapterEnd(checkTripEndList, CustomerVisitEndActivity.this, listener);
                recyclerViewEndTrip.setAdapter(adapterEnd);
                adapterEnd.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<TripModel>> call, @NonNull Throwable t) {
                //Toast.makeText(CustomerVisitEndActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
