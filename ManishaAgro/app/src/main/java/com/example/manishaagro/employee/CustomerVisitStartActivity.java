package com.example.manishaagro.employee;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.manishaagro.ApiClient;
import com.example.manishaagro.ApiInterface;
import com.example.manishaagro.ConnectionDetector;
import com.example.manishaagro.DBHelper;
import com.example.manishaagro.MessageDialog;
import com.example.manishaagro.R;
import com.example.manishaagro.model.DailyEmpExpenseModel;
import com.example.manishaagro.model.TripModel;
import com.example.manishaagro.utils.Utilities;
import com.example.manishaagro.utils.Validator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.manishaagro.R.id.VisitStartSubmit;
import static com.example.manishaagro.utils.Constants.VISITED_CUSTOMER_ENTRY;

public class CustomerVisitStartActivity extends AppCompatActivity implements View.OnClickListener {
    boolean setOpening=false;
    DBHelper dbHelper;
    String publicFarmernm="";
    Calendar calander;
    String DateCurrent="";
    SimpleDateFormat simpledateformat;
    String CurDefaultDattime="";
    ApiInterface apiInterface;
    Toolbar visitStartToolbar;
    EditText editTextFarmerName, editTextFarmerAddress, editTextFarmerContact, editTextVillage, editTextTaluka, editTextDistrict;
    EditText editAcre, editPurpose;
    Button visitEntrySubmit, demoButton;
    public String employeeID = "";
    double acreValue = 0;
    ConnectionDetector connectionDetector;
    MessageDialog messageDialog;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_visit_start);
        connectionDetector = new ConnectionDetector();
        messageDialog=new MessageDialog();
        dbHelper=new DBHelper(this);

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
        editAcre = findViewById(R.id.editTextAcre);
        editPurpose = findViewById(R.id.editTextAddPurpose);
        visitEntrySubmit = findViewById(R.id.VisitStartSubmit);
        demoButton = findViewById(R.id.goToDemoActivity);
        Intent intent = getIntent();
        String keyCompare1 = intent.getStringExtra("visitedEmpID");
        if (keyCompare1 != null && keyCompare1.equals("Emp@ID")) {
            employeeID = intent.getStringExtra("visitedEmployee");
        }
        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CurDefaultDattime = simpledateformat.format(calander.getTime());
        visitEntrySubmit.setOnClickListener(this);
        demoButton.setOnClickListener(this);
        checkOpening();
    }

    private void checkOpening() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DailyEmpExpenseModel> meterModelCall1 = apiInterface.checkInsertStartReadEntry("CheckStart@entryMeterRead", employeeID);
        meterModelCall1.enqueue(new Callback<DailyEmpExpenseModel>() {
            @Override
            public void onResponse(Call<DailyEmpExpenseModel> call, Response<DailyEmpExpenseModel> response) {
                assert response.body() != null;
                String value = response.body().getValue();
                String message = response.body().getMessage();
                if (value.equals("1")) {
                     setOpening=true;
                } else if (value.equals("0")) {
                    Toast.makeText(CustomerVisitStartActivity.this, "Submit Opening Km First", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DailyEmpExpenseModel> call, Throwable t) {
                if (connectionDetector.isConnected(CustomerVisitStartActivity.this)) {
                    //System.out.println("CustomerVisitStartActivity : Is Connected");
                    //Toast.makeText(CustomerVisitStartActivity.this,"Cannot Communicate to Server",Toast.LENGTH_LONG).show();

                    //Toast.makeText(OpeningActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                        messageDialog.msgDialog(CustomerVisitStartActivity.this);
                } else {
                    Toast.makeText(CustomerVisitStartActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent visitIntent;
        switch (v.getId()) {
            case VisitStartSubmit:
              //  if (connectionDetector.isConnected(this)) {



                visitEntry();
               // } else {
                  //  Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
               // }
                break;
            case R.id.goToDemoActivity:
                if (connectionDetector.isConnected(this)) {
                    visitIntent = new Intent(CustomerVisitStartActivity.this, DemoEntryActivity.class);
                    visitIntent.putExtra("visitedEmployeeDemoEntry", employeeID);
                    visitIntent.putExtra("visitedEmployeeDemoEntryFarmernm", publicFarmernm);
                    startActivity(visitIntent);
                    finish();
                } else {
                  //  Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
                    visitIntent = new Intent(CustomerVisitStartActivity.this, DemoEntryActivity.class);
                    visitIntent.putExtra("visitedEmployeeDemoEntry", employeeID);
                    visitIntent.putExtra("visitedEmployeeDemoEntryFarmernm", publicFarmernm);
               //     visitIntent.putExtra("visitedEmployeeDemoEntryFarmernm", employeeID);
                    startActivity(visitIntent);
                    finish();
                }
                break;
        }
    }

    private void visitEntry() {
        publicFarmernm=editTextFarmerName.getText().toString().trim();
        final String farmerFullName = editTextFarmerName.getText().toString().trim();
        final String farmerAddressText = editTextFarmerAddress.getText().toString().trim();
        final String farmerContact = editTextFarmerContact.getText().toString().trim();
        final String farmerVillage = editTextVillage.getText().toString().trim();
        final String farmerTaluka = editTextTaluka.getText().toString().trim();
        final String farmerDistrict = editTextDistrict.getText().toString().trim();
        acreValue = ParseDouble(editAcre.getText().toString().trim());
        final String farmerVisitPurpose = editPurpose.getText().toString().trim();

        DateCurrent="0000-00-00 00:00:00";

        Log.v("Check id emp", "emp id" + employeeID);

        String alertMessage = "";
        if (employeeID.equals("")) {
            alertMessage = "Employee Id not assigned";
        } else if (!Validator.isValidName(farmerFullName)) {
            alertMessage = "Invalid Farmer Name (Either is Empty or includes digits)";
        } else if (farmerAddressText.equals("")) {
            alertMessage = "Invalid Farmer Address (cannot be empty)";
        } else if (!Validator.isValidMobileNumber(farmerContact)) {
            alertMessage = "Invalid Mobile Number";
        } else if (!Validator.isValidName(farmerVillage) || !Validator.isValidName(farmerTaluka)
                || !Validator.isValidName(farmerDistrict)) {
            alertMessage = "Invalid village / taluka / district (Either is Empty or includes digits)";
        } else if (acreValue == 0) {
            alertMessage = "Invalid acre value (Must be greater than 0)";
        } else if (!Validator.isValidName(farmerVisitPurpose)) {
            alertMessage = "Invalid visit purpose (Either is Empty or includes digits)";
        } else {

            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<TripModel> empIdDesignationModelCall = apiInterface.insertVisitedStartEntry(VISITED_CUSTOMER_ENTRY, employeeID, farmerFullName, farmerAddressText, farmerVillage, farmerTaluka, farmerDistrict, farmerContact, acreValue, farmerVisitPurpose,DateCurrent);
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
                        //Toast.makeText(CustomerVisitStartActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TripModel> call, Throwable t) {
                    if (connectionDetector.isConnected(CustomerVisitStartActivity.this)) {
                        if (t instanceof SocketTimeoutException) {
                        //    Toast.makeText(CustomerVisitStartActivity.this,"Cannot Communicate to Server",Toast.LENGTH_LONG).show();
                            messageDialog.msgDialog(CustomerVisitStartActivity.this);
                        } else if (t instanceof IOException) {
                            //Toast.makeText(CustomerVisitStartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            //Call was cancelled by user
                            if (call.isCanceled()) {
                                System.out.println("Call was cancelled forcefully");
                            } else {
                                System.out.println("Network Error :: " + t.getLocalizedMessage());
                            }
                        }
                    } else {


                        boolean Inserted = dbHelper.addvisitdata(employeeID, farmerFullName, farmerAddressText, farmerVillage, farmerTaluka, farmerDistrict, farmerContact, acreValue, farmerVisitPurpose,CurDefaultDattime);
                        if (Inserted == true) {
                            Toast.makeText(CustomerVisitStartActivity.this, "Success", Toast.LENGTH_SHORT).show();



                        }
                        else
                        {
                            Toast.makeText(CustomerVisitStartActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }

                        String StrVisitData=VISITED_CUSTOMER_ENTRY +","+employeeID+","+farmerFullName+","+farmerAddressText+","+farmerVillage+","+farmerTaluka+","+farmerDistrict+","+farmerContact+","+acreValue+","+farmerVisitPurpose+","+CurDefaultDattime;
                        String VisitDataDir="VisitDataDir.txt";
                        generateNoteOnSD(CustomerVisitStartActivity.this,VisitDataDir,StrVisitData);
                        editTextFarmerName.setText("");
                        editTextFarmerAddress.setText("");
                        editTextFarmerContact.setText("");
                        editTextVillage.setText("");
                        editTextDistrict.setText("");
                        editTextTaluka.setText("");
                        editAcre.setText("");
                        editPurpose.setText("");




                        // Toast.makeText(CustomerVisitStartActivity.this, "No Internet Connection offline saved data", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
        if (alertMessage.length() != 0) {
            Utilities.showAlertDialog(CustomerVisitStartActivity.this, alertMessage);
        }
    }

    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch (Exception e) {
                return -1;
            }
        } else return 0;
    }


    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
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
    }
}