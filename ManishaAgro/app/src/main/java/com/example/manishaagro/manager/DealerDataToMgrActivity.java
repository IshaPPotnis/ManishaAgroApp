package com.example.manishaagro.manager;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.manishaagro.ApiClient;
import com.example.manishaagro.ApiInterface;
import com.example.manishaagro.ConnectionDetector;
import com.example.manishaagro.DealerProductListActivity;
import com.example.manishaagro.R;
import com.example.manishaagro.employee.DealerAdapterInEmp;
import com.example.manishaagro.model.DealerModel;
import com.example.manishaagro.model.TripModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerDataToMgrActivity extends AppCompatActivity {

    DealerAdapterInEmp.RecyclerViewClickListener listener;
    ConnectionDetector connectionDetector;
    private RecyclerView recyclerViewDealer;
    private List<DealerModel> rptDealerList;
    public DealerAdapterInEmp adapterDealer;
    public ApiInterface apiInterface;
    public String employeeIdValue = "";
    Toolbar empDetailTool;
    TextView totalText,compTotalText,followText,dealerCompText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_data_to_mgr);
        connectionDetector=new ConnectionDetector();
        empDetailTool=findViewById(R.id.toolbarEmpDetail);
        totalText=findViewById(R.id.totalTrip);
        compTotalText=findViewById(R.id.CompletedTrip);
        followText=findViewById(R.id.followupsPending);
        dealerCompText=findViewById(R.id.DealerComp);

        setSupportActionBar(empDetailTool);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }


        Intent intent = getIntent();
        String keyForCompare = intent.getStringExtra("EmpIDNAME");
        Log.v("yek", "keyyy" + keyForCompare);
        if (keyForCompare != null && keyForCompare.equals("EmployeeIDNamePassed")) {
            String name = intent.getStringExtra("EmployeeId");
            employeeIdValue=name;
           // getVisiteDetailOfEmployee();
        }

        listener=new DealerAdapterInEmp.RecyclerViewClickListener() {
            @Override
            public void onDealerProductDetailClick(View view, int position) {
                if (connectionDetector.isConnected(DealerDataToMgrActivity.this))
                {
                    Intent intent = new Intent(DealerDataToMgrActivity.this, DealerProductListActivity.class);
                    intent.putExtra("emp_dealer_name", rptDealerList.get(position).getDealername());
                    intent.putExtra("emp_dealer_product_purchase_date", rptDealerList.get(position).getDate_of_purchase());
                    intent.putExtra("EmpId_Dealer_product",employeeIdValue);
                    intent.putExtra("Emp_dealerProductVal", "Emp_Dealer_Product_List_Status");
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(DealerDataToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }

            }
        };



        recyclerViewDealer = findViewById(R.id.DealerTabrecyview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DealerDataToMgrActivity.this);
        recyclerViewDealer.setLayoutManager(layoutManager);
        if (connectionDetector.isConnected(DealerDataToMgrActivity.this))
        {
            getMgrActTotalDealerSale();
            getMgrActTotalVisit();
        }
        else
        {
            Toast.makeText(DealerDataToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
        }

    }


    private void getMgrActTotalDealerSale()
    {
        final String userName = employeeIdValue;
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
                        Toast.makeText(DealerDataToMgrActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case "0":
                        Toast.makeText(DealerDataToMgrActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<DealerModel> call, Throwable t) {

                if (connectionDetector.isConnected(DealerDataToMgrActivity.this))
                {
                    Toast.makeText(DealerDataToMgrActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(DealerDataToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getMgrActTotalVisit() {
        final String userName = employeeIdValue;
        Log.v("mgrcheckempid", "emp0" + employeeIdValue);
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
                        Toast.makeText(DealerDataToMgrActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case "0":
                        Toast.makeText(DealerDataToMgrActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<TripModel> call, Throwable t) {

                if (connectionDetector.isConnected(DealerDataToMgrActivity.this))
                {
                    Toast.makeText(DealerDataToMgrActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(DealerDataToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();

            getDealerLists();



    }

    private void getDealerLists() {
        final String STEmp_ID1 = employeeIdValue;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Log.v("CodeIncome", "user1" + employeeIdValue);
        Call<List<DealerModel>> listCall = apiInterface.getAllDealerListInEmp("get@llDealerListInEmp", STEmp_ID1);
        listCall.enqueue(new Callback<List<DealerModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<DealerModel>> call, @NonNull Response<List<DealerModel>> response) {
                rptDealerList = response.body();
                adapterDealer = new DealerAdapterInEmp(rptDealerList, DealerDataToMgrActivity.this, listener);
                recyclerViewDealer.setAdapter(adapterDealer);
                adapterDealer.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<DealerModel>> call, @NonNull Throwable t) {

                if (connectionDetector.isConnected(DealerDataToMgrActivity.this))
                {
                    Toast.makeText(DealerDataToMgrActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(DealerDataToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
