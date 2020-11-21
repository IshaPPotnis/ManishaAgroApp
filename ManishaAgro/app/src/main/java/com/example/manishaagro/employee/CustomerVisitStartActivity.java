package com.example.manishaagro.employee;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.manishaagro.ApiClient;
import com.example.manishaagro.ApiInterface;
import com.example.manishaagro.ConnectionDetector;
import com.example.manishaagro.R;
import com.example.manishaagro.model.TripModel;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.R.id.VisitStartSubmit;
import static com.example.manishaagro.utils.Constants.VISITED_CUSTOMER_ENTRY;

public class CustomerVisitStartActivity extends AppCompatActivity implements View.OnClickListener {

    ApiInterface apiInterface;
    Toolbar visitStartToolbar;
    EditText editTextFarmerName, editTextFarmerAddress, editTextFarmerContact, editTextVillage, editTextTaluka, editTextDistrict;
    EditText editAcre,editPurpose;
    Button visitEntrySubmit,demoButton;
    public String employeeID = "";
    double acreval=0;
    ConnectionDetector connectionDetector;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custome_visit_start);
        connectionDetector=new ConnectionDetector();
        visitStartToolbar = findViewById(R.id.toolbarvisit);
        setSupportActionBar(visitStartToolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#00A5FF"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }

        editTextFarmerAddress = findViewById(R.id.editTextFarmerAddress);
        editTextFarmerName = findViewById(R.id.editTextFarmersNames);
        editTextFarmerContact = findViewById(R.id.editTextContact);
        editTextVillage = findViewById(R.id.editTextVillage);
        editTextTaluka = findViewById(R.id.editTextTaluka);
        editTextDistrict = findViewById(R.id.editTextDistrict);
        editAcre=findViewById(R.id.editTextAcre);
        editPurpose=findViewById(R.id.editTextAddPurpose);
        visitEntrySubmit = findViewById(R.id.VisitStartSubmit);


        demoButton = findViewById(R.id.goToDemoActivity);

        Intent intent = getIntent();
        String keyCompare1 = intent.getStringExtra("visitedEmpID");
        if (keyCompare1 != null && keyCompare1.equals("Emp@ID")) {
            employeeID = intent.getStringExtra("visitedEmployee");
        }
      //  String keyCompare2 = intent.getStringExtra("CheckDemoImageActivity");
        //if (keyCompare2 != null && keyCompare2.equals("Demo@Customerimage")) {
          //  employeeID = intent.getStringExtra("visitedEmployeeBackFromDemoImage");
        //}

        visitEntrySubmit.setOnClickListener(this);
        demoButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent visitIntent;
        switch (v.getId()) {
            case VisitStartSubmit:
                if (connectionDetector.isConnected(this))
                {
                    visitEntry();
                }
                else
                {
                    Toast.makeText(this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.goToDemoActivity:


                if (connectionDetector.isConnected(this))
                {
                    visitIntent = new Intent(CustomerVisitStartActivity.this, DemoEntryActivity.class);
                    visitIntent.putExtra("visitedEmployeeDemoEntry", employeeID);
                    startActivity(visitIntent);
                    finish();
                }
                else
                {
                    Toast.makeText(this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }


                break;
        }
    }

    private void visitEntry() {

        final String farmerfullname=editTextFarmerName.getText().toString().trim();
        final String farmerAddressText = editTextFarmerAddress.getText().toString().trim();
        final String farmerContacts = editTextFarmerContact.getText().toString().trim();
        final String farmerVillage = editTextVillage.getText().toString().trim();
        final String farmerTaluka = editTextTaluka.getText().toString().trim();
        final String farmerDistrict = editTextDistrict.getText().toString().trim();
        acreval=ParseDouble(editAcre.getText().toString().trim());
        final String farmervisitPurpose = editPurpose.getText().toString().trim();


        Log.v("Check id emp", "emp id" + employeeID);

        if (employeeID.equals("") || farmerfullname.equals("")|| farmerAddressText.equals("") ||
                farmerContacts.equals("") || farmerVillage.equals("") || farmerTaluka.equals("") || farmerDistrict.equals("")||acreval==0||farmervisitPurpose.equals("")) {
            Toast.makeText(CustomerVisitStartActivity.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
           // final String fullname=farmerName1+" "+farmerName2+" "+farmerName3;
            if(farmerfullname.equals("")||editTextFarmerContact.length()!=10)
            {
                Toast.makeText(CustomerVisitStartActivity.this,"Check Filled Data",Toast.LENGTH_SHORT).show();

            }
            else
            {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                Call<TripModel> empIdDesignationModelCall = apiInterface.insertVisitedStartEntry(VISITED_CUSTOMER_ENTRY, employeeID, farmerfullname, farmerAddressText, farmerVillage, farmerTaluka, farmerDistrict, farmerContacts,acreval,farmervisitPurpose);
                empIdDesignationModelCall.enqueue(new Callback<TripModel>() {
                    @Override
                    public void onResponse(Call<TripModel> call, Response<TripModel> response) {
                        assert response.body() != null;
                        String value = response.body().getValue();
                        String message = response.body().getMassage();
                        if (value.equals("1")) {

                            editTextFarmerName.setText("");
                            editTextFarmerAddress.setText("");
                            editTextFarmerContact.setText("");
                            editTextVillage.setText("");
                            editTextDistrict.setText("");
                            editTextTaluka.setText("");
                            editAcre.setText("");
                            editPurpose.setText("");
                            //   Toast.makeText(CustomerVisitStartActivity.this, message, Toast.LENGTH_SHORT).show();
                        } else if (value.equals("0")) {
                            Toast.makeText(CustomerVisitStartActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TripModel> call, Throwable t) {
                        if (connectionDetector.isConnected(CustomerVisitStartActivity.this))
                        {

                            if (t instanceof SocketTimeoutException) {
                                // "Connection Timeout";
                                Toast.makeText(CustomerVisitStartActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            } else if (t instanceof IOException) {
                                // "Timeout";
                                Toast.makeText(CustomerVisitStartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                //Call was cancelled by user
                                if (call.isCanceled()) {
                                    System.out.println("Call was cancelled forcefully");
                                } else {
                                    //Generic error handling
                                    System.out.println("Network Error :: " + t.getLocalizedMessage());
                                }
                                Toast.makeText(CustomerVisitStartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(CustomerVisitStartActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                        }


                    }
                });

            }

        }
    }

    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;
            }
        }
        else return 0;
    }
}
