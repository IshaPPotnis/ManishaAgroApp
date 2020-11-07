package com.example.manishaagro;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.manishaagro.employee.EmployeeActivity;
import com.example.manishaagro.model.TripModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeePendingDataToMgrActivity extends AppCompatActivity implements View.OnClickListener {
    public ApiInterface apiInterface;
    Toolbar pendingTool;
    public String employeeIdValue = "";
    TextView txtpenname,txtPenCnt;
    ConnectionDetector connectionDetector;
    public PendingAdapter pendingAdapter;
    PendingAdapter.RecyclerViewClickListener listener;
    private RecyclerView recyclerViewVisit;
    Button button1,button2,button3,button4;

    //   private RecyclerView recyclerViewDemoimg;
    // private RecyclerView recyclerViewSelfimg;
    //private RecyclerView recyclerViewfollowimg;
    private List<TripModel> rptVisitList;
    private List<TripModel> rptDemoImageList;
    private List<TripModel> rptSelfieImageList;
    private List<TripModel> rptfollowupList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_pending_data_to_mgr);
        connectionDetector=new ConnectionDetector();
        pendingTool=findViewById(R.id.toolbarEmpPending);

        setSupportActionBar(pendingTool);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }



        connectionDetector=new ConnectionDetector();
        recyclerViewVisit = findViewById(R.id.PendingTabrecyviewVisit);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        txtpenname=findViewById(R.id.textPennding);
        txtPenCnt=findViewById(R.id.textPendingCount);

        Intent intent = getIntent();
        String keyForCompare = intent.getStringExtra("EmpPending");
        Log.v("yek", "keyyy" + keyForCompare);
        if (keyForCompare != null && keyForCompare.equals("EmployeeVisitPendingToMgr")) {
            String name = intent.getStringExtra("EmployeeId");
            employeeIdValue=name;

        }

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(EmployeePendingDataToMgrActivity.this);
        recyclerViewVisit.setLayoutManager(layoutManager1);


        visitPenCount();
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);







    }

    @Override
    protected void onResume() {
        super.onResume();
        visitPenCount();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.button1)
        {
            if (connectionDetector.isConnected(EmployeePendingDataToMgrActivity.this))
            {
                getPendingsLists();
            }
            else
            {
                Toast.makeText(EmployeePendingDataToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }


        }
        if (v.getId()==R.id.button2)
        {
            if (connectionDetector.isConnected(EmployeePendingDataToMgrActivity.this))
            {
                getPendingsDemoImageLists();
            }
            else
            {
                Toast.makeText(EmployeePendingDataToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }


        }
        if (v.getId()==R.id.button3)
        {
            if (connectionDetector.isConnected(EmployeePendingDataToMgrActivity.this))
            {
                getPendingsSelfieImageLists();
            }
            else
            {
                Toast.makeText(EmployeePendingDataToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }


        }
        if (v.getId()==R.id.button4)
        {
            if (connectionDetector.isConnected(EmployeePendingDataToMgrActivity.this))
            {
                getPendingsFollowupLists();
            }
            else
            {
                Toast.makeText(EmployeePendingDataToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            }


        }

    }


    private void visitPenCount()
    {
        final String STEmp_ID1 = employeeIdValue;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("CodeIncome", "user1" + employeeIdValue);
        Call<TripModel> listCall = apiInterface.getVisitPendingCountInEmp("get@CountVisitPending", STEmp_ID1);
        listCall.enqueue(new Callback<TripModel>() {
            @Override
            public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                String value=response.body().getValue();
                String message=response.body().getMassage();
                String visitcount=response.body().getData1();
                String demoimgcount=response.body().getData2();
                String selfieimgcount=response.body().getData3();
                String followupcount=response.body().getData4();
                if(value.equals("1"))
                {
                    txtPenCnt.setText(visitcount + " Visit Pending ," + demoimgcount + " Demo Photo Pending ,"+ selfieimgcount + " Selfie With Cutomer Pending ,"+ followupcount + " Follow Up Pending.");
                }
                else if(value.equals("0"))
                {
                    Toast.makeText(EmployeePendingDataToMgrActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TripModel> call, Throwable t) {
                if (connectionDetector.isConnected(EmployeePendingDataToMgrActivity.this))
                {
                    Toast.makeText(EmployeePendingDataToMgrActivity.this,"Error",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(EmployeePendingDataToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getPendingsLists() {
        final String STEmp_ID1 = employeeIdValue;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("CodeIncome", "user1" + employeeIdValue);
        Call<List<TripModel>> listCall = apiInterface.getPendingListInEmp("get@AllPendingsVisit", STEmp_ID1);
        listCall.enqueue(new Callback<List<TripModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TripModel>> call, @NonNull Response<List<TripModel>> response) {
                txtpenname.setText("VISIT PENDING");
                rptVisitList = response.body();
                pendingAdapter= new PendingAdapter(rptVisitList, EmployeePendingDataToMgrActivity.this, listener);
                recyclerViewVisit.getRecycledViewPool().clear();
                pendingAdapter.notifyDataSetChanged();
                recyclerViewVisit.setAdapter(pendingAdapter);

                recyclerViewVisit.stopScroll();
            }

            @Override
            public void onFailure(@NonNull Call<List<TripModel>> call, @NonNull Throwable t) {

                if (connectionDetector.isConnected(EmployeePendingDataToMgrActivity.this))
                {
                    Toast.makeText(EmployeePendingDataToMgrActivity.this,"Error",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(EmployeePendingDataToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    private void getPendingsDemoImageLists() {
        final String STEmp_ID1 = employeeIdValue;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("CodeIncome", "user1" + employeeIdValue);
        Call<List<TripModel>> listCall = apiInterface.getPendingListInEmp("get@AllPendingsDemoImage", STEmp_ID1);
        listCall.enqueue(new Callback<List<TripModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TripModel>> call, @NonNull Response<List<TripModel>> response) {
                txtpenname.setText("DEMO PHOTO PENDING");
                rptDemoImageList = response.body();
                pendingAdapter= new PendingAdapter(rptDemoImageList, EmployeePendingDataToMgrActivity.this, listener);
                recyclerViewVisit.getRecycledViewPool().clear();
                pendingAdapter.notifyDataSetChanged();
                recyclerViewVisit.setAdapter(pendingAdapter);

                recyclerViewVisit.stopScroll();
            }

            @Override
            public void onFailure(@NonNull Call<List<TripModel>> call, @NonNull Throwable t) {

                if (connectionDetector.isConnected(EmployeePendingDataToMgrActivity.this))
                {
                    Toast.makeText(EmployeePendingDataToMgrActivity.this,"Error",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(EmployeePendingDataToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    private void getPendingsSelfieImageLists() {
        final String STEmp_ID1 = employeeIdValue;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("CodeIncome", "user1" + employeeIdValue);
        Call<List<TripModel>> listCall = apiInterface.getPendingListInEmp("get@AllPendingsSelfieImage", STEmp_ID1);
        listCall.enqueue(new Callback<List<TripModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TripModel>> call, @NonNull Response<List<TripModel>> response) {
                txtpenname.setText("SELFIE WITH CUTOMER PENDING");
                rptSelfieImageList = response.body();
                pendingAdapter= new PendingAdapter(rptSelfieImageList, EmployeePendingDataToMgrActivity.this, listener);
                recyclerViewVisit.getRecycledViewPool().clear();
                pendingAdapter.notifyDataSetChanged();
                recyclerViewVisit.setAdapter(pendingAdapter);

                recyclerViewVisit.stopScroll();
            }

            @Override
            public void onFailure(@NonNull Call<List<TripModel>> call, @NonNull Throwable t) {

                if (connectionDetector.isConnected(EmployeePendingDataToMgrActivity.this))
                {
                    Toast.makeText(EmployeePendingDataToMgrActivity.this,"Error",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(EmployeePendingDataToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    private void getPendingsFollowupLists() {
        final String STEmp_ID1 = employeeIdValue;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("CodeIncome", "user1" + employeeIdValue);
        Call<List<TripModel>> listCall = apiInterface.getPendingListInEmp("get@AllPendingsFollowImage", STEmp_ID1);
        listCall.enqueue(new Callback<List<TripModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<TripModel>> call, @NonNull Response<List<TripModel>> response) {
                txtpenname.setText("FOLLOW UP PENDING");
                rptfollowupList = response.body();
                pendingAdapter= new PendingAdapter(rptfollowupList, EmployeePendingDataToMgrActivity.this, listener);
                recyclerViewVisit.getRecycledViewPool().clear();
                pendingAdapter.notifyDataSetChanged();
                recyclerViewVisit.setAdapter(pendingAdapter);

                recyclerViewVisit.stopScroll();
            }

            @Override
            public void onFailure(@NonNull Call<List<TripModel>> call, @NonNull Throwable t) {

                if (connectionDetector.isConnected(EmployeePendingDataToMgrActivity.this))
                {
                    Toast.makeText(EmployeePendingDataToMgrActivity.this,"Error",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(EmployeePendingDataToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }

            }
        });
    }


}
