package com.example.manishaagro;

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
import android.widget.Toast;

import com.example.manishaagro.employee.DealerAdapterInEmp;
import com.example.manishaagro.model.DailyEmpExpenseModel;
import com.example.manishaagro.model.DealerModel;
import com.example.manishaagro.model.MeterModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadingDataToMgrActivity extends AppCompatActivity {
    ConnectionDetector connectionDetector;
    public CloaseAdapter cloaseAdapter;
    CloaseAdapter.RecyclerViewClickListener listener;
    private RecyclerView recyclerViewKM;
    private List<DailyEmpExpenseModel> rptMeterList;
    public ApiInterface apiInterface;
    public String employeeIdValue = "";
    Toolbar empKMTool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_data_to_mgr);
        connectionDetector=new ConnectionDetector();
        empKMTool=findViewById(R.id.toolbarEmpKMDetail);
        recyclerViewKM=findViewById(R.id.closeRecyclerview);
        setSupportActionBar(empKMTool);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }

        Intent intent = getIntent();
        String keyForCompare = intent.getStringExtra("EmpIDNAMERead");
        Log.v("yek", "keyyy" + keyForCompare);
        if (keyForCompare != null && keyForCompare.equals("EmployeeIDNamePassedRead")) {
            String name = intent.getStringExtra("EmployeeId");
            employeeIdValue=name;

        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ReadingDataToMgrActivity.this);
        recyclerViewKM.setLayoutManager(layoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllCloseRead();
    }
    private void getAllCloseRead()
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("checkTrip", "emp1" + employeeIdValue);
        Call<List<DailyEmpExpenseModel>> listCall = apiInterface.getCloseRead("get@AllEmpCloseRead", employeeIdValue);
        listCall.enqueue(new Callback<List<DailyEmpExpenseModel>>() {
            @Override
            public void onResponse(Call<List<DailyEmpExpenseModel>> call, Response<List<DailyEmpExpenseModel>> response) {

                rptMeterList = response.body();


                Log.v("checkTripList", "empList" + rptMeterList);
                cloaseAdapter = new CloaseAdapter(rptMeterList, ReadingDataToMgrActivity.this, listener);
                recyclerViewKM.setAdapter(cloaseAdapter);
                cloaseAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<List<DailyEmpExpenseModel>> call, Throwable t) {
                if(connectionDetector.isConnected(ReadingDataToMgrActivity.this))
                {
                    Toast.makeText(ReadingDataToMgrActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(ReadingDataToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
