package com.example.manishaagro;

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
    Button visitEntrySubmit, visitEntrySubmit1, selfieButton, demoButton;
    public String employeeID = "";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custome_visit_start);
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
        editTextFarmerName = findViewById(R.id.editTextFarmerName);
        editTextFarmerContact = findViewById(R.id.editTextContact);
        editTextVillage = findViewById(R.id.editTextVillage);
        editTextTaluka = findViewById(R.id.editTextTaluka);
        editTextDistrict = findViewById(R.id.editTextDistrict);
        visitEntrySubmit = findViewById(R.id.VisitStartSubmit);
        visitEntrySubmit1 = findViewById(R.id.VisitStartSubmit1);
        selfieButton = findViewById(R.id.goToSelfieActivity);
        demoButton = findViewById(R.id.goToDemoActivity);
        Intent intent = getIntent();

        String keyForCompare = intent.getStringExtra("CheckDemoActivity");
        Log.v("yek", "keyyy" + keyForCompare);
        if (keyForCompare != null && keyForCompare.equals("Customer@DemoEntry")) {
            employeeID = intent.getStringExtra("visitedEmployeeBackDemoEntry");
        }

        String keyCompare1 = intent.getStringExtra("visitedEmpID");
        if (keyCompare1 != null && keyCompare1.equals("Emp@ID")) {
            employeeID = intent.getStringExtra("visitedEmployee");
        }
        String keyCompare2 = intent.getStringExtra("CheckDemoImageActivity");
        if (keyCompare2 != null && keyCompare2.equals("Demo@Customerimage")) {
            employeeID = intent.getStringExtra("visitedEmployeeBackFromDemoImage");
        }

        String keyCompare3 = intent.getStringExtra("CheckSelfieImageActivity");
        if (keyCompare3 != null && keyCompare3.equals("Selfie@Customerimage")) {
            employeeID = intent.getStringExtra("visitedEmployeeBackFromSelfie");
        }
        visitEntrySubmit.setOnClickListener(this);
        demoButton.setOnClickListener(this);
        visitEntrySubmit1.setOnClickListener(this);
        selfieButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent visitIntent;
        switch (v.getId()) {
            case VisitStartSubmit:
                visitEntry();
                break;
            case R.id.VisitStartSubmit1:
                visitIntent = new Intent(CustomerVisitStartActivity.this, DemoImageActivity.class);
                visitIntent.putExtra("visitedEmployeeDemo", employeeID);
                startActivity(visitIntent);
                finish();
                break;
            case R.id.goToSelfieActivity:
                visitIntent = new Intent(CustomerVisitStartActivity.this, SelfieImageActivity.class);
                visitIntent.putExtra("visitedEmployeeSelfie", employeeID);
                startActivity(visitIntent);
                finish();
                break;
            case R.id.goToDemoActivity:
                visitIntent = new Intent(CustomerVisitStartActivity.this, DemoEntryActivity.class);
                visitIntent.putExtra("visitedEmployeeDemoEntry", employeeID);
                startActivity(visitIntent);
                finish();
                break;
        }
    }

    private void visitEntry() {
        final String farmerNameText = editTextFarmerName.getText().toString().trim();
        final String farmerAddressText = editTextFarmerAddress.getText().toString().trim();
        final String farmerContacts = editTextFarmerContact.getText().toString().trim();
        final String farmerVillage = editTextVillage.getText().toString().trim();
        final String farmerTaluka = editTextTaluka.getText().toString().trim();
        final String farmerDistrict = editTextDistrict.getText().toString().trim();
        Log.v("Check id emp", "emp id" + employeeID);

        if (employeeID.equals("") || farmerNameText.equals("") || farmerAddressText.equals("") ||
                farmerContacts.equals("") || farmerVillage.equals("") || farmerTaluka.equals("") || farmerDistrict.equals("")) {
            Toast.makeText(CustomerVisitStartActivity.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
        } else {
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<TripModel> empIdDesignationModelCall = apiInterface.insertVisitedStartEntry(VISITED_CUSTOMER_ENTRY, employeeID, farmerNameText, farmerAddressText, farmerVillage, farmerTaluka, farmerDistrict, farmerContacts);
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
                        Toast.makeText(CustomerVisitStartActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else if (value.equals("0")) {
                        Toast.makeText(CustomerVisitStartActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TripModel> call, Throwable t) {
                    if (t instanceof SocketTimeoutException) {
                        // "Connection Timeout";
                        Toast.makeText(CustomerVisitStartActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    } else if (t instanceof IOException) {
                        // "Timeout";
                        Toast.makeText(CustomerVisitStartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
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
            });
        }
    }
}
