package com.example.manishaagro.employee;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.manishaagro.ConnectionDetector;
import com.example.manishaagro.manager.AdapterEmployeeDetails;
import com.example.manishaagro.ApiClient;
import com.example.manishaagro.ApiInterface;
import com.example.manishaagro.R;
import com.example.manishaagro.model.DealerModel;
import com.example.manishaagro.model.TripModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.GET_VISITED_DETAILS_EYLPME;

public class EmployeeVisitDetailsToMgrActivity extends AppCompatActivity {
    Toolbar empDetailTool;
    AdapterEmployeeDetails.RecyclerViewClickListener listener;
    ConnectionDetector connectionDetector;
    public ApiInterface apiInterface;
    private RecyclerView recyclerEmpDtl;
    private List<TripModel> EmpVisitList;
    public AdapterEmployeeDetails adapterEmployeeDetails;
    String EmpRefId="";
    TextView totalText,compTotalText,followText,dealerCompText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_visit_details_to_mgr);
        connectionDetector=new ConnectionDetector();
        empDetailTool=findViewById(R.id.toolbarEmpDetail);


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

        Intent intent = getIntent();
        String keyForCompare = intent.getStringExtra("EmpIDNAME");
        Log.v("yek", "keyyy" + keyForCompare);
        if (keyForCompare != null && keyForCompare.equals("EmployeeIDNamePassed")) {
            String name = intent.getStringExtra("EmployeeId");
             EmpRefId=name;
        }

        recyclerEmpDtl=findViewById(R.id.EmployeeDetails);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EmployeeVisitDetailsToMgrActivity.this);
        recyclerEmpDtl.setLayoutManager(layoutManager);
        if (connectionDetector.isConnected(EmployeeVisitDetailsToMgrActivity.this))
        {
            getVisiteDetailOfEmployee();
            getMgrActTotalDealerSale();
            getMgrActTotalVisit();

        }
        else
        {
            Toast.makeText(EmployeeVisitDetailsToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
        }



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
                        Toast.makeText(EmployeeVisitDetailsToMgrActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<DealerModel> call, Throwable t) {
                if (connectionDetector.isConnected(EmployeeVisitDetailsToMgrActivity.this))
                {

                    Toast.makeText(EmployeeVisitDetailsToMgrActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(EmployeeVisitDetailsToMgrActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case "0":
                        Toast.makeText(EmployeeVisitDetailsToMgrActivity.this, message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<TripModel> call, Throwable t) {
                if (connectionDetector.isConnected(EmployeeVisitDetailsToMgrActivity.this))
                {

                    Toast.makeText(EmployeeVisitDetailsToMgrActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(EmployeeVisitDetailsToMgrActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(EmployeeVisitDetailsToMgrActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
