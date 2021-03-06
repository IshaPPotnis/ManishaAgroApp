package com.example.manishaagro.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manishaagro.ConnectionDetector;
import com.example.manishaagro.EmployeePendingDataToMgrActivity;
import com.example.manishaagro.MessageDialog;
import com.example.manishaagro.manager.AdapterEmployeeDetails;
import com.example.manishaagro.ApiClient;
import com.example.manishaagro.ApiInterface;
import com.example.manishaagro.R;
import com.example.manishaagro.model.DealerModel;
import com.example.manishaagro.model.TripModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.GET_VISITED_DETAILS_EYLPME;

public class EmployeeVisitDetailsToMgrActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar empDetailTool;
    private Calendar myCalendar = Calendar.getInstance();
    private Calendar myCalendar1 = Calendar.getInstance();
    AdapterEmployeeDetails.RecyclerViewClickListener listener;
    ConnectionDetector connectionDetector;
    MessageDialog messageDialog;
    public ApiInterface apiInterface;
    private RecyclerView recyclerEmpDtl;
    private List<TripModel> EmpVisitList;
    private List<TripModel> EmpFromtoList;
    public AdapterEmployeeDetails adapterEmployeeDetails;
    String EmpRefId="";
    EditText fromdateEdit,toDateEdit;
    Button butsubmitFromTodt;
    TextView totalText,compTotalText,followText,dealerCompText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_visit_details_to_mgr);
        connectionDetector=new ConnectionDetector();
        empDetailTool=findViewById(R.id.toolbarEmpDetail);
        messageDialog=new MessageDialog();


        setSupportActionBar(empDetailTool);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }

        totalText=findViewById(R.id.totalTrip);
        compTotalText=findViewById(R.id.CompletedTrip);
        followText=findViewById(R.id.followupsPending);
        dealerCompText=findViewById(R.id.DealerComp);
        fromdateEdit=findViewById(R.id.editTextFromDate);
        toDateEdit=findViewById(R.id.editTextToDate);
        butsubmitFromTodt=findViewById(R.id.submitFromTodt);

        Intent intent = getIntent();
        String keyForCompare = intent.getStringExtra("EmpIDNAME");
        Log.v("yek", "keyyy" + keyForCompare);
        if (keyForCompare != null && keyForCompare.equals("EmployeeIDNamePassed")) {
            String name = intent.getStringExtra("EmployeeId");
             EmpRefId=name;
        }
        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                fromdateEdit.setText(sdf.format(myCalendar.getTime()));
            }

        };
        fromdateEdit.setFocusableInTouchMode(false);
        fromdateEdit.setFocusable(false);
        fromdateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EmployeeVisitDetailsToMgrActivity.this, R.style.DialogTheme, datePickerListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat, Locale.US);
                toDateEdit.setText(sdf1.format(myCalendar1.getTime()));
            }

        };
        toDateEdit.setFocusableInTouchMode(false);
        toDateEdit.setFocusable(false);
        toDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EmployeeVisitDetailsToMgrActivity.this, R.style.DialogTheme, datePickerListener1,
                        myCalendar1.get(Calendar.YEAR),
                        myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        recyclerEmpDtl=findViewById(R.id.EmployeeDetails);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EmployeeVisitDetailsToMgrActivity.this);
        recyclerEmpDtl.setLayoutManager(layoutManager);
        if (connectionDetector.isConnected(EmployeeVisitDetailsToMgrActivity.this))
        {
           // getVisiteDetailOfEmployee();
            getVisiteDetailOfEmployee();
            getMgrActTotalDealerSale();
            getMgrActTotalVisit();

        }
        else
        {
            Toast.makeText(EmployeeVisitDetailsToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
        }


        listener=new AdapterEmployeeDetails.RecyclerViewClickListener() {
            @Override
            public void onEmpVisitdtlClick(AdapterView<?> parent, final View view, final int position, final long id) {
                if (ContextCompat.checkSelfPermission(EmployeeVisitDetailsToMgrActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EmployeeVisitDetailsToMgrActivity.this, new String[] { Manifest.permission.CALL_PHONE}, 0);
                }
                else
                {



                    AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeVisitDetailsToMgrActivity.this);
                    builder.setTitle(R.string.app_name);
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setMessage("Are you sure you want to Call this number?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    switch (view.getId())
                                    {
                                        case R.id.EmpTextviewContact:
                                            String phonenumber=EmpVisitList.get(position).getContactdetail();
                                            Intent intentcall = new Intent(Intent.ACTION_CALL);
                                            intentcall.setData(Uri.parse("tel:" + phonenumber));


                                            if (ActivityCompat.checkSelfPermission(EmployeeVisitDetailsToMgrActivity.this,
                                                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                return;
                                            }
                                            startActivity(intentcall);
                                            break;

                                        //    case R.id.row_container:
                                        //      Intent intentadpEmp = new Intent(EmployeeVisitDetailsToMgrActivity.this, EmployeePendingDataToMgrActivity.class);

                                        //    startActivity(intentadpEmp);
                                        //  break;
                                    }
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

            }
        };

        butsubmitFromTodt.setOnClickListener(this);
    }



    private void getMgrActTotalDealerSale()
    {
        final String userName = EmpRefId;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DealerModel> totalCalls = apiInterface.getDealerTotalSale("Total@DealerSale", userName);
        totalCalls.enqueue(new Callback<DealerModel>() {
            @Override
            public void onResponse(Call<DealerModel> call, Response<DealerModel> response) {
                assert response.body() != null;
                String value = response.body().getValue();
                String message = response.body().getMessage();
                String Salecom = response.body().getEmpId();

                switch (value) {
                    case "1":
                      /*  if (Salecom.equals("")) {
                            Salecom = String.valueOf(0);

                        }*/
                        dealerCompText.setText(String.valueOf(Salecom));
                     //   Toast.makeText(EmployeeVisitDetailsToMgrActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case "0":
                        //Toast.makeText(EmployeeVisitDetailsToMgrActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<DealerModel> call, Throwable t) {
                if (connectionDetector.isConnected(EmployeeVisitDetailsToMgrActivity.this))
                {

                    //Toast.makeText(EmployeeVisitDetailsToMgrActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(EmployeeVisitDetailsToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void getMgrActTotalVisit() {
        final String userName = EmpRefId;
        Log.v("mgrcheckempid", "emp0" + EmpRefId);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<TripModel> totalCalls = apiInterface.getEmpTotalTrip("Total@tripofEmp", userName);
        totalCalls.enqueue(new Callback<TripModel>() {
            @Override
            public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                assert response.body() != null;
                String value = response.body().getValue();
                String message = response.body().getMassage();
                String tripcom = response.body().getDateOfReturn();
                String totalTrip = response.body().getDateOfTravel();
                    int followcount=response.body().getFollowuprequired();
                switch (value) {
                    case "1":
                        compTotalText.setText(tripcom);
                        totalText.setText(totalTrip);
                        followText.setText(Integer.toString(followcount));
                        //Toast.makeText(EmployeeVisitDetailsToMgrActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case "0":
                        //Toast.makeText(EmployeeVisitDetailsToMgrActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<TripModel> call, Throwable t) {
                if (connectionDetector.isConnected(EmployeeVisitDetailsToMgrActivity.this))
                {
                    messageDialog.msgDialog(EmployeeVisitDetailsToMgrActivity.this);
                  //  Toast.makeText(EmployeeVisitDetailsToMgrActivity.this,"Cannot Communicate to Server",Toast.LENGTH_LONG).show();
                    //Toast.makeText(EmployeeVisitDetailsToMgrActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(EmployeeVisitDetailsToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });
    }







    @Override
    protected void onResume() {
        super.onResume();

    }


    private void  getVisiteDetailOfEmployee()
    {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("checkTrip", "emp1" + EmpRefId);
        Call<List<TripModel>> listCall = apiInterface.getAllVisit(GET_VISITED_DETAILS_EYLPME,EmpRefId);
        listCall.enqueue(new Callback<List<TripModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TripModel>> call, @NonNull Response<List<TripModel>> response) {
                EmpVisitList = response.body();
                Log.v("checkTripList", "empList" + EmpVisitList);
                adapterEmployeeDetails = new AdapterEmployeeDetails(EmpVisitList, EmployeeVisitDetailsToMgrActivity.this, listener);
                recyclerEmpDtl.setAdapter(adapterEmployeeDetails);
                adapterEmployeeDetails.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<TripModel>> call, @NonNull Throwable t) {
                if (connectionDetector.isConnected(EmployeeVisitDetailsToMgrActivity.this))
                {
                    messageDialog.msgDialog(EmployeeVisitDetailsToMgrActivity.this);
                    //Toast.makeText(EmployeeVisitDetailsToMgrActivity.this,"Cannot Communicate to Server",Toast.LENGTH_LONG).show();
                    //Toast.makeText(EmployeeVisitDetailsToMgrActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(EmployeeVisitDetailsToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.submitFromTodt)
        {
            final String fromStrdt=fromdateEdit.getText().toString().trim();
            final String toStrdt=toDateEdit.getText().toString().trim();

            if (fromStrdt.equals("") && toStrdt.equals(""))
            {
                if (connectionDetector.isConnected(EmployeeVisitDetailsToMgrActivity.this))
                {

                   // getMgrActTotalDealerSale();
                    //getMgrActTotalVisit();
Toast.makeText(EmployeeVisitDetailsToMgrActivity.this,"Enter From Date And To Date",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(EmployeeVisitDetailsToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(EmployeeVisitDetailsToMgrActivity.this,"Enter From Date And To Date",Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (connectionDetector.isConnected(EmployeeVisitDetailsToMgrActivity.this))
                {
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                    Log.v("checkTrip", "emp1" + EmpRefId);
                    Log.v("checkTrip", "emp1" + fromStrdt);
                    Log.v("checkTrip", "emp1" + toStrdt);
                    Call<List<TripModel>> listCall = apiInterface.getAllFromToVisit("Get@allFromToToDateDateVisit",EmpRefId,fromStrdt,toStrdt);
                    listCall.enqueue(new Callback<List<TripModel>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<TripModel>> call, @NonNull Response<List<TripModel>> response) {
                            // EmpVisitList.clear();
                            EmpFromtoList = response.body();

                            Log.v("checkTripList", "empList" + EmpFromtoList);

                            adapterEmployeeDetails = new AdapterEmployeeDetails(EmpFromtoList, EmployeeVisitDetailsToMgrActivity.this, listener);

                            recyclerEmpDtl.clearOnScrollListeners();
                            adapterEmployeeDetails.notifyDataSetChanged();

                            recyclerEmpDtl.setAdapter(adapterEmployeeDetails);

                        }

                        @Override
                        public void onFailure(@NonNull Call<List<TripModel>> call, @NonNull Throwable t) {
                            if (connectionDetector.isConnected(EmployeeVisitDetailsToMgrActivity.this))
                            {
                                messageDialog.msgDialog(EmployeeVisitDetailsToMgrActivity.this);
                                //Toast.makeText(EmployeeVisitDetailsToMgrActivity.this,"Cannot Communicate to Server",Toast.LENGTH_LONG).show();
                                //Toast.makeText(EmployeeVisitDetailsToMgrActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(EmployeeVisitDetailsToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }

        }

    }
}
