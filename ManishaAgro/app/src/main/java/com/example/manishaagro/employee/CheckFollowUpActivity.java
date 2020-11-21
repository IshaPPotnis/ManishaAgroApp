package com.example.manishaagro.employee;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manishaagro.ApiClient;
import com.example.manishaagro.ApiInterface;
import com.example.manishaagro.ConnectionDetector;
import com.example.manishaagro.R;
import com.example.manishaagro.model.TripModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckFollowUpActivity extends AppCompatActivity {
    String employeeID = "";
    Toolbar followToolbar;
    ConnectionDetector connectionDetector;
    public ApiInterface apiInterface;
    public AdapterFollow adapterFollow;
    private RecyclerView recyclerViewFU;
    private List<TripModel> checkTripEndList;
    AdapterFollow.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_follow_up);
        connectionDetector=new ConnectionDetector();
        followToolbar = findViewById(R.id.toolbarFollowup);
        recyclerViewFU = findViewById(R.id.recyclerViewFollowup);
        setSupportActionBar(followToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }

        Intent intent = getIntent();
        employeeID = intent.getStringExtra("visitedEmployeeFollowup");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewFU.setLayoutManager(layoutManager);
        listener = new AdapterFollow.RecyclerViewClickListener() {
            @Override
            public void onFollowupClick(View view, int position) {
                if (connectionDetector.isConnected(CheckFollowUpActivity.this))
                {
                    Intent intent = new Intent(CheckFollowUpActivity.this, FollowUpEntryActivity.class);
                    intent.putExtra("CustomerOrFarmer_name", checkTripEndList.get(position).getVisitedCustomerName());
                    intent.putExtra("Follow_UP_Date", checkTripEndList.get(position).getFollowupdate());
                    intent.putExtra("Emp_Id_FromFollow_AcT", employeeID);
                    intent.putExtra("Check_Follow_Up_ACTV", "Follow_Up_click");
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(CheckFollowUpActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();


          getAllFollowupData();


    }

    private void getAllFollowupData() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<TripModel>> listFollow = apiInterface.checkFollowUp("Check@FollowUpData", employeeID);
        listFollow.enqueue(new Callback<List<TripModel>>() {
            @Override
            public void onResponse(Call<List<TripModel>> call, Response<List<TripModel>> response) {
                checkTripEndList = response.body();
                adapterFollow = new AdapterFollow(checkTripEndList, CheckFollowUpActivity.this, listener);
                recyclerViewFU.setAdapter(adapterFollow);
                adapterFollow.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TripModel>> call, Throwable t) {
                if (connectionDetector.isConnected(CheckFollowUpActivity.this))
                {
                    //Toast.makeText(CheckFollowUpActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(CheckFollowUpActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
