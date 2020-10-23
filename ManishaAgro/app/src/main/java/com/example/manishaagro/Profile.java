package com.example.manishaagro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.manishaagro.ApiClient;
import com.example.manishaagro.ApiInterface;
import com.example.manishaagro.R;
import com.example.manishaagro.model.DealerModel;
import com.example.manishaagro.model.ProfileModel;
import com.example.manishaagro.model.TripModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.utils.Constants.EMPLOYEE_PROFILE;

public class Profile extends AppCompatActivity {

    public ApiInterface apiInterface;
    String employeeID="";
    private TextView nameText;
    private TextView dateOfBirth;
    private TextView dateOfJoining;
    private TextView designation;
    private TextView mobileNumber;
    private TextView address;
    private TextView employeeId;
    private TextView emailId;
    private TextView userEmailText, userEmailText1,userEmailText2,userTextView;
    public String employeeIdValue = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        userTextView = findViewById(R.id.appusername);
        userEmailText = findViewById(R.id.useremail);
        userEmailText1 = findViewById(R.id.useremail1);
        userEmailText2 = findViewById(R.id.useremail2);
        nameText = findViewById(R.id.pfl_name);
        dateOfBirth = findViewById(R.id.pfl_dob);
        dateOfJoining = findViewById(R.id.pfl_doj);
        designation = findViewById(R.id.pfl_desig);
        employeeId = findViewById(R.id.pfl_empid);
        address = findViewById(R.id.pfl_addr);
        mobileNumber = findViewById(R.id.pfl_mobile);
        emailId = findViewById(R.id.pfl_email);

        Intent intent = getIntent();
        employeeID = intent.getStringExtra("visitedEmployeeProfilePage");



       /* EmployeeActivity activity = (EmployeeActivity) ;
        if (activity != null) {
            Bundle results = activity.getEmpData();
            String value1 = results.getString("tempval1");
            employeeIdValue = results.getString("tempval2EMPID");
            userTextView.setText(value1);
            Log.v("Check Desig", "desig1" + value1);
            Log.v("check id", "id1" + employeeIdValue);
        }*/
        getProfileData();
        getTotalVisit();
        getTotalDealerSale();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfileData();
        getTotalVisit();
        getTotalDealerSale();
    }

    private void getTotalDealerSale()
    {
        final String userName = employeeID;
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
                        if (Salecom.equals("")) {
                            Salecom = String.valueOf(0);

                        }
                        userEmailText1.setText(String.format("Completed Dealer Sale:%s", Salecom));
                        Toast.makeText(Profile.this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case "0":
                        Toast.makeText(Profile.this, message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<DealerModel> call, Throwable t) {
                Toast.makeText(Profile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalVisit() {
        final String userName = employeeID;
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
                int followupRemain=response.body().getFollowuprequired();

                Log.v("T1", "Comp" + tripcom);
                Log.v("T2", "total" +totalTrip);
                Log.v("T3", "Follow" + followupRemain);




                switch (value) {
                    case "1":
                        if (totalTrip.equals("") || tripcom.equals("")) {
                            tripcom = String.valueOf(0);
                            totalTrip = String.valueOf(0);
                            followupRemain=0;
                        }
                        userEmailText.setText(String.format("Completed Trip:%s", tripcom));
                        userEmailText2.setText(String.format("Follow Up Pendings:%s", followupRemain));
                        Toast.makeText(Profile.this, message, Toast.LENGTH_SHORT).show();
                        break;
                    case "0":
                        Toast.makeText(Profile.this, message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<TripModel> call, Throwable t) {
                Toast.makeText(Profile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProfileData() {
        final String userName = employeeID;
        Log.v("yek", "rameshxxxxx" + userName);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ProfileModel> profileCalls = apiInterface.getEmpProfile(EMPLOYEE_PROFILE, userName);
        profileCalls.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                assert response.body() != null;
                String value = response.body().getValue();
                String message = response.body().getMassage();
                String resname = response.body().getName();
                String resempid = response.body().getEmpId();
                String resaddr = response.body().getContactDetails();
                String resmobile = response.body().getAddress();
                String resdesig = response.body().getDesignation();
                String resdob = response.body().getDob();
                String resdoj = response.body().getJoiningDate();
                String resemail = response.body().getEmail();
                String usrnm = response.body().getUsername();
                Log.v("CodeIncome", "user1" + resname);
                if (value.equals("1")) {
                    nameText.setText(resname);
                    employeeId.setText(resempid);
                    address.setText(resaddr);
                    mobileNumber.setText(resmobile);
                    designation.setText(resdesig);
                    emailId.setText(resemail);
                    dateOfBirth.setText(resdob);
                    dateOfJoining.setText(resdoj);
                    userTextView.setText(usrnm);

                    Log.v("CodeIncome", "user2" + resname);
                    Toast.makeText(Profile.this, message, Toast.LENGTH_SHORT).show();
                } else if (value.equals("0")) {
                    Toast.makeText(Profile.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(Profile.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}
